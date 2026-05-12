package YouVideo;

import Exceptions.*;

import java.util.*;


/**
 * Implementation of the YouVideo platform system.
 * Maintains four internal collections: all videos (publishable and episodes),
 * all podcasts, all known authors, and all shows.
 * All map keys are normalized to uppercase to ensure case-insensitive access.
 */
public class PlatformSystemClass implements PlatformSystem {
    private static final int MAX_VIDEOS = 2000;
    private static final int MAX_PODS_SHOWS_AUTHORS = 500;

    // All videos registered in the system, including publishable videos and episodes.
    private Map<String, Video> videos;
    // All podcasts registered in the system, in insertion order.
    private Map<String, Podcast> podcasts;
    // All authors known to the system (from podcasts and shows), in insertion order.
    private Map<String, Author> authors;
    // All shows registered in the system, in insertion order.
    private Map<String, Show> shows;

    // Map linking a Tag to a list of Podcasts that have it.
    private Map<String, List<Podcast>> podcastsByTag;
    // Map linking a Tag to a list of Shows that have it.
    private Map<String, List<Show>> showsByTag;

    /**
     * Creates an empty platform with no content.
     */
    public PlatformSystemClass() {
        videos        = new HashMap<>(MAX_VIDEOS);
        podcasts      = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        authors       = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        shows         = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        podcastsByTag = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        showsByTag    = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
    }

    /**
     * Normalizes a map key to uppercase for case-insensitive access.
     * @param key the original key string.
     * @return the key in uppercase.
     */
    private String normalizeKey(String key) {
        return key.toUpperCase();
    }


    // ----------------------------- VIDEO -----------------------------

    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                               String title, Locale lang)
            throws PublishableAlreadyExistsException {
        String idKey = normalizeKey(id);

        if (videos.containsKey(idKey))
            throw new PublishableAlreadyExistsException();
        videos.put(idKey, new BasicVideoClass(id, duration, url, publisher, title, lang));
    }

    @Override
    public void addPremiumPublishable(String id, int duration, String url,
                                      String publisher, String title, Locale lang,
                                      String subtitleUrl, Locale subtitleLang)
            throws PublishableAlreadyExistsException {
        String idKey = normalizeKey(id);

        if (videos.containsKey(idKey))
            throw new PublishableAlreadyExistsException();
        videos.put(idKey, new PremiumVideoClass(id, duration, url,
                publisher, title, lang, subtitleUrl, subtitleLang));
    }

    @Override
    public Video getVideo(String id)
            throws PublishableNotExistsException, IsEpisodeException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        return video;
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, Locale subtitleLang)
            throws PublishableNotExistsException, NotAPremiumVideoException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (!(video instanceof PremiumVideo))
            throw new NotAPremiumVideoException();
        ((PremiumVideo) video).addSubtitle(subtitleUrl, subtitleLang);
    }

    @Override
    public Iterator<Subtitle> subtitleIterator(String id) throws
            PublishableNotExistsException, IsEpisodeException, NotAPremiumVideoException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        else if (video instanceof Episode)
            throw new IsEpisodeException();
        else if (!(video instanceof PremiumVideo))
            throw new NotAPremiumVideoException();
        return ((PremiumVideo) video).subtitleIterator();
    }

    @Override
    public void removeVideo(String id) throws PublishableNotExistsException,
            IsEpisodeException, VideoUsedInShowException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        if (shows.containsKey(normalizeKey(((PublishableVideo) video).getTitle())))
            throw new VideoUsedInShowException();
        videos.remove(idKey);
    }


    // ----------------------------- PODCAST -----------------------------

    @Override
    public void addPodcast(String title, String author, Locale lang)
            throws PodcastAlreadyExistsException {
        String titleKey  = normalizeKey(title);
        String authorKey = normalizeKey(author);

        if (podcasts.containsKey(titleKey))
            throw new PodcastAlreadyExistsException();

        Author au;
        if (authors.containsKey(authorKey)) {
            au = authors.get(authorKey);
        } else {
            au = new AuthorClass(author);
            authors.put(authorKey, au);
        }

        Podcast podcast = new PodcastClass(title, au.getAuthor(), lang);
        podcasts.put(titleKey, podcast);
        ((AuthorAll) au).addPodcast(podcast);
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date)
            throws PodcastNotExistsException,
            EpisodeAlreadyExistsException, InvalidEpisodeDateException {
        String titleKey = normalizeKey(title);
        String idKey    = normalizeKey(id);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        if (videos.containsKey(idKey))
            throw new EpisodeAlreadyExistsException();

        PodcastAll podcast = (PodcastAll) podcasts.get(titleKey);
        if (!podcast.isValidEpisodeDate(date))
            throw new InvalidEpisodeDateException();

        Episode episode = new EpisodeClass(id, duration, url, date);
        videos.put(idKey, episode);
        podcast.addEpisode(episode);
    }

    @Override
    public Podcast getPodcast(String title)
            throws PodcastNotExistsException {
        String titleKey = normalizeKey(title);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        return podcasts.get(titleKey);
    }

    @Override
    public Iterator<Episode> episodeIterator(String title) throws PodcastNotExistsException {
        String titleKey = normalizeKey(title);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        return podcasts.get(titleKey).episodeIterator();
    }

    @Override
    public Iterator<Podcast> authorPodcast(String author) throws PodcastNotFoundException {
        String authorKey = normalizeKey(author);

        Author au = authors.get(authorKey);
        if (au == null || au.isPodcastsEmpty())
            throw new PodcastNotFoundException();
        return au.podcastIterator();
    }

    @Override
    public void removePodcast(String podcastTitle) throws PodcastNotExistsException {
        String titleKey = normalizeKey(podcastTitle);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();

        Podcast podcast = podcasts.get(titleKey);

        Iterator<Episode> it = podcast.episodeIterator();
        while (it.hasNext()) {
            Episode ep = it.next();
            videos.remove(normalizeKey(ep.getId()));
        }

        Author author = authors.get(normalizeKey(podcast.getAuthor()));
        ((AuthorAll) author).removePodcast(podcast);
        podcasts.remove(titleKey);
    }


    // ----------------------------- SHOW -----------------------------

    @Override
    public void addShow(String showAuthor, String videoID, String date)
            throws PublishableNotExistsException, ShowAlreadyExistsException {
        String idKey     = normalizeKey(videoID);
        String authorKey = normalizeKey(showAuthor);

        Video v = videos.get(idKey);
        if (v == null || v instanceof Episode)
            throw new PublishableNotExistsException();

        PublishableVideo video = (PublishableVideo) v;
        String titleKey = normalizeKey(video.getTitle());

        if (shows.containsKey(titleKey))
            throw new ShowAlreadyExistsException();

        Author au;
        if (authors.containsKey(authorKey)) {
            au = authors.get(authorKey);
        } else {
            au = new AuthorClass(showAuthor);
            authors.put(authorKey, au);
        }

        Show show = new ShowClass(au.getAuthor(), date, video.getTitle(), video);
        shows.put(titleKey, show);
        ((AuthorAll) au).addShow(show);
    }

    @Override
    public Show getShow(String title) throws ShowNotExistsException {
        String titleKey = normalizeKey(title);

        if (!shows.containsKey(titleKey))
            throw new ShowNotExistsException();
        return shows.get(titleKey);
    }

    @Override
    public void removeShow(String showTitle) throws ShowNotExistsException {
        String titleKey = normalizeKey(showTitle);

        if (!shows.containsKey(titleKey))
            throw new ShowNotExistsException();

        Show show = shows.get(titleKey);
        Author author = authors.get(normalizeKey(show.getAuthor()));
        ((AuthorAll) author).removeShow(show);
        shows.remove(titleKey);
    }

    @Override
    public Iterator<Show> authorShows(String author)
            throws NoShowsFoundForTheAuthorException {
        String authorKey = normalizeKey(author);

        Author au = authors.get(authorKey);
        if (au == null )
            throw new NoShowsFoundForTheAuthorException();
        return au.showIterator();
    }


    // ----------------------------- TAGS -----------------------------

    @Override
    public void addTag(String title, String tag)
            throws TitleAlreadyTaggedException, TitleDoesNotExistException {
        String titleKey = normalizeKey(title);
        String tagKey   = normalizeKey(tag);

        Podcast pd = podcasts.get(titleKey);
        Show    sw = shows.get(titleKey);

        if (pd == null && sw == null)
            throw new TitleDoesNotExistException();

        if ((sw != null && ((Tag) sw).hasTag(tag)) || (pd != null && ((Tag) pd).hasTag(tag)))
            throw new TitleAlreadyTaggedException();

        if (pd != null) {
            ((Tag) pd).addTag(tag);
            List<Podcast> pdList = podcastsByTag.get(tagKey);
            if (pdList == null) {
                pdList = new LinkedList<>();
                podcastsByTag.put(tagKey, pdList);
            }
            pdList.add(pd);
        }

        if (sw != null) {
            ((Tag) sw).addTag(tag);
            List<Show> swList = showsByTag.get(tagKey);
            if (swList == null) {
                swList = new LinkedList<>();
                showsByTag.put(tagKey, swList);
            }
            swList.add(sw);
        }
    }

    @Override
    public void removeTag(String title, String tag)
            throws TitleDoesNotExistException, TitleIsNotTaggedException {
        String titleKey = normalizeKey(title);
        String tagKey   = normalizeKey(tag);

        Podcast pd = podcasts.get(titleKey);
        Show    sw = shows.get(titleKey);

        if (pd == null && sw == null)
            throw new TitleDoesNotExistException();

        boolean wasRemoved = false;

        if (pd != null && ((Tag) pd).removeTag(tag)) {
            podcastsByTag.get(tagKey).remove(pd);
            wasRemoved = true;
        }
        if (sw != null && ((Tag) sw).removeTag(tag)) {
            showsByTag.get(tagKey).remove(sw);
            wasRemoved = true;
        }

        if (!wasRemoved)
            throw new TitleIsNotTaggedException();
    }
}