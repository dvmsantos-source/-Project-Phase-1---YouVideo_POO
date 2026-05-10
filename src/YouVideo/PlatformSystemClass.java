package YouVideo;

import Exceptions.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import java.util.Map;


/**
 * Implementation of the YouVideo platform system.
 * Maintains four internal collections: all videos (publishable and episodes),
 * all podcasts, all known authors, and all shows.
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

    /**
     * Creates an empty platform with no content.
     */
    public PlatformSystemClass() {
        videos = new HashMap<>(MAX_VIDEOS);
        podcasts = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        authors = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        shows = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
    }


    // ----------------------------- VIDEO -----------------------------
    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                               String title, Locale lang)
            throws PublishableAlreadyExistsException {
        if (videos.containsKey(id))
            throw new PublishableAlreadyExistsException();
        videos.put(id, new BasicVideoClass(id, duration, url, publisher, title, lang));
    }

    @Override
    public void addPremiumPublishable(String id, int duration, String url,
                                      String publisher, String title, Locale lang,
                                      String subtitleUrl, Locale subtitleLang)
            throws PublishableAlreadyExistsException {
        if (videos.containsKey(id))
            throw new PublishableAlreadyExistsException();
        videos.put(id, new PremiumVideoClass(id, duration, url,
                publisher, title, lang, subtitleUrl, subtitleLang));
    }

    @Override
    public Video getVideo(String id)
            throws PublishableNotExistsException, IsEpisodeException {
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        return video;
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, Locale subtitleLang)
            throws PublishableNotExistsException, IsNotPremiumVideoException {
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        if (!(video instanceof PremiumVideo))
            throw new IsNotPremiumVideoException();
        ((PremiumVideo) video).addSubtitle(subtitleUrl, subtitleLang);
    }

    @Override
    public Iterator<Subtitle> subtitleIterator(String id) throws
            PublishableNotExistsException, IsEpisodeException, IsNotPremiumVideoException {
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        else if (video instanceof Episode)
            throw new IsEpisodeException();
        else if (!(video instanceof PremiumVideo))
            throw new IsNotPremiumVideoException();
        return ((PremiumVideo) video).subtitleIterator();
    }

    @Override
    public void removeVideo(String id) throws PublishableNotExistsException,
            IsEpisodeException, VideoUsedInShowException {
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        if (shows.containsKey(((PublishableVideo)video).getTitle()))
            throw new VideoUsedInShowException();
        videos.remove(id);
    }


    // ----------------------------- PODCAST -----------------------------
    /**
     * Verifies if an author with the given name is already known to the system.
     * @param author the author name to search for.
     * @return true if the author exists, false otherwise.
     */
    private boolean hasAuthor(String author) {
        return authors.containsKey(author);
    }

    @Override
    public void addPodcast(String title, String author, Locale lang)
            throws PodcastAlreadyExistsException {
        if (podcasts.containsKey(title))
            throw new PodcastAlreadyExistsException();
        // If the author creating a new Podcast already is in the system,
        //we use the first name entered into the system (au.getAuthor()).
        if (authors.containsKey(author)) {
            podcasts.put(title, new PodcastClass(title, authors.get(author).getAuthor(), lang));
        } else {
            authors.put(author, new AuthorClass(author));
            podcasts.put(title, new PodcastClass(title, author, lang));
        }
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date)
            throws PodcastNotExistsException,
            EpisodeAlreadyExistsException, IsNotValidEpisodeDateException {
        if (!podcasts.containsKey(title))
            throw new PodcastNotExistsException();
        if (videos.containsKey(id))
            throw new EpisodeAlreadyExistsException();
        if (!isValidEpisodeDate(title, date))
            throw new IsNotValidEpisodeDateException();
        videos.put(title, new EpisodeClass(id, duration, url, date));
        PodcastAll podcast = (PodcastAll) podcasts.get(title);
        podcast.addEpisode(id, duration, url, date);
        Author author = authors.get(podcast.getAuthor());
        ((AuthorAll) author).addPodcast(podcast);
    }

    @Override
    public Podcast getPodcast(String title) throws PodcastNotExistsException {
        if (!podcasts.containsKey(title))
            throw new PodcastNotExistsException();
        return podcasts.get(title);
    }

    private boolean isValidEpisodeDate(String title, String date) {
        Podcast podcast = podcasts.get(title);
        return podcast.isValidEpisodeDate(date);
    }

    @Override
    public Iterator<Episode> episodeIterator(String title) throws PodcastNotExistsException {
        if (!podcasts.containsKey(title))
            throw new PodcastNotExistsException();
        return podcasts.get(title).episodeIterator();
    }

    @Override
    public Iterator<Podcast> authorPodcast(String author) throws NotFoundPodcastException {
        Author au = authors.get(author);
        if (au.isPodcastsEmpty())
            throw new NotFoundPodcastException();
        return au.podcastIterator();
    }

    @Override
    public void removePodcast(String podcastTitle) throws PodcastNotExistsException {
        if (!podcasts.containsKey(podcastTitle))
            throw new PodcastNotExistsException();
        Podcast podcast = getPodcast(podcastTitle);
        for (Video video : videos.values()) {
            // Checks if the current video is an episode AND if it belongs to the target podcast
            if (video instanceof Episode && podcast.hasEpisode(video)) {
                // Removes the episode. Since removeAt shifts the subsequent elements to the left,
                // we DO NOT increment 'i'. The next element to be checked is now at the current
                // index 'i'.
                videos.remove(video.getId());
            }
        }
        Author author = authors.get(podcast.getAuthor());
        ((AuthorAll) author).removePodcast(podcast);
        podcasts.remove(podcastTitle);
    }

    // ----------------------------- SHOW -----------------------------
    @Override
    public void addShow(String showAuthor, String videoID, String date)
            throws PublishableNotExistsException, ShowAlreadyExistsException {
        if (!videos.containsKey(videoID))
            throw new PublishableNotExistsException();
        PublishableVideo video = (PublishableVideo) videos.get(videoID);
        if (shows.containsKey(video.getTitle()))
            throw new ShowAlreadyExistsException();
        if (hasAuthor(showAuthor)) {
            Author au = authors.get(showAuthor);
            Show show = new ShowClass(au.getAuthor(), date, video.getTitle());
            shows.put(video.getTitle(), show);
            ((AuthorAll) au).addShow(show);
        } else {
            AuthorAll author = new AuthorClass(showAuthor);
            authors.put(showAuthor, author);
            Show show2 = new ShowClass(showAuthor, date, video.getTitle());
            shows.put(video.getTitle(), show2);
            author.addShow(show2);
        }
    }

    @Override
    public Show getShow(String title) throws ShowNotExistsException {
        if (!shows.containsKey(title))
            throw new ShowNotExistsException();
        return shows.get(title);
    }

    @Override
    public void removeShow(String showTitle) throws ShowNotExistsException {
        if (!shows.containsKey(showTitle))
            throw new ShowNotExistsException();
        Show show = shows.get(showTitle);
        Author author = authors.get(show.getAuthor());
        ((AuthorAll) author).removeShow(show);
        shows.remove(showTitle);
    }
}