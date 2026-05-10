package YouVideo;

import Exceptions.*;

import java.util.*;


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

    // Mapa que liga uma Tag a uma lista de Podcasts que a possuem
    private Map<String, List<Podcast>> podcastsByTag;

    // Mapa que liga uma Tag a uma lista de Shows que a possuem
    private Map<String, List<Show>> showsByTag;

    /**
     * Creates an empty platform with no content.
     */
    public PlatformSystemClass() {
        videos = new HashMap<>(MAX_VIDEOS);
        podcasts = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        authors = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        shows = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        podcastsByTag = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        showsByTag = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);

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
            throws PublishableNotExistsException, NotAPremiumVideoException {
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        if (!(video instanceof PremiumVideo))
            throw new NotAPremiumVideoException();
        ((PremiumVideo) video).addSubtitle(subtitleUrl, subtitleLang);
    }

    @Override
    public Iterator<Subtitle> subtitleIterator(String id) throws
            PublishableNotExistsException, IsEpisodeException, NotAPremiumVideoException {
        Video video = videos.get(id);
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
        Video video = videos.get(id);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        if (shows.containsKey(((PublishableVideo) video).getTitle()))
            throw new VideoUsedInShowException();
        videos.remove(id);
    }


    // ----------------------------- PODCAST -----------------------------


    @Override
    public void addPodcast(String title, String author, Locale lang)
            throws PodcastAlreadyExistsException {


        if (podcasts.containsKey(title))
            throw new PodcastAlreadyExistsException();
        // If the author creating a new Podcast already is in the system,
        //we use the first name entered into the system (au.getAuthor()).
        Author au;
        if (authors.containsKey(author)) {
            au = authors.get(author);
        } else {
            au = new AuthorClass(author);
            authors.put(author, au);
        }

        Podcast podcast = new PodcastClass(title, au.getAuthor(), lang);
        podcasts.put(title, podcast);
        ((AuthorAll) au).addPodcast(podcast); // ← sempre adiciona ao author
    }


    @Override
    public void addEpisode(String title, String id, int duration, String url, String date)
            throws PodcastNotExistsException,
            EpisodeAlreadyExistsException, InvalidEpisodeDateException {
        if (!podcasts.containsKey(title))
            throw new PodcastNotExistsException();
        if (videos.containsKey(id))
            throw new EpisodeAlreadyExistsException();
        if (!isValidEpisodeDate(title, date))
            throw new InvalidEpisodeDateException();

        Episode episode = new EpisodeClass(id, duration, url, date);
        videos.put(id, episode);

        PodcastAll podcast = (PodcastAll) podcasts.get(title);
        podcast.addEpisode(episode);

    }

    @Override
    public Podcast getPodcast(String title)
            throws PodcastNotExistsException {
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
    public Iterator<Podcast> authorPodcast(String author) throws PodcastNotFoundException {
        Author au = authors.get(author);
        if (au.isPodcastsEmpty())
            throw new PodcastNotFoundException();
        return au.podcastIterator();
    }

    @Override
    public void removePodcast(String podcastTitle) throws PodcastNotExistsException {
        if (!podcasts.containsKey(podcastTitle))
            throw new PodcastNotExistsException();
        Podcast podcast = getPodcast(podcastTitle);

        Iterator <Episode> it = podcast.episodeIterator();
        while (it.hasNext()){
            Episode ep = it.next();
            videos.remove(ep.getId());
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
        String title = video.getTitle();
        if (shows.containsKey(title))
            throw new ShowAlreadyExistsException();

        if (authors.containsKey(showAuthor)) {


            Author au = authors.get(showAuthor);

            Show show = new ShowClass(au.getAuthor(), date, title,video);
            shows.put(title, show);
            ((AuthorAll) au).addShow(show);


        } else {
            AuthorAll author = new AuthorClass(showAuthor);
            authors.put(showAuthor, author);
            Show show2 = new ShowClass(showAuthor, date, title,video);
            shows.put(title, show2);
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
    @Override
    public Iterator<Show> authorShows(String author)
            throws NoShowsFoundForTheAuthorException {
        if (!authors.containsKey(author)){
            throw new NoShowsFoundForTheAuthorException();
        }
        Author au = authors.get(author);
        return au.showIterator();

    }

    // ----------------------------- TAGS -----------------------------
    @Override
    public void addTag(String title, String tag)
            throws TitleAlreadyTaggedException, TitleDoesNotExistException {
        Podcast pd = podcasts.get(title);
        Show sw = shows.get(title);

        if (pd == null && sw == null) {
            throw new TitleDoesNotExistException();
        }

        if ((sw != null && sw.hasTagShow(tag)) || (pd != null && pd.hasTagPodcast(tag))) {
            throw new TitleAlreadyTaggedException();
        }

        if (pd != null) {
            // Vai buscar a gaveta desta tag. Se não existir, cria!
            ((PodcastAll) pd).addTagPodcast(tag);
            List <Podcast> pdList = podcastsByTag.get(tag);
            if (pdList == null){
                pdList = new LinkedList<>();
                podcastsByTag.put(tag,pdList);
            }
            pdList.add(pd);  // Adiciona o podcast à gaveta desta tag

        }

        if (sw != null){
            ((ShowAll)sw).addTagShow(tag);
            List<Show> swList = showsByTag.get(tag);

            if (swList == null) {
                swList = new LinkedList<>();
                showsByTag.put(tag, swList);
            }
            swList.add(sw);

        }
    }



    @Override
    public void removeTag(String title, String tag)
            throws TitleDoesNotExistException,TitleIsNotTaggedException{
        Show sw = shows.get(title);
        Podcast pd = podcasts.get(title);

        if (sw == null && pd == null){
            throw new TitleDoesNotExistException();
        }

        boolean wasRemoved = false;

        if ((pd != null) && (((PodcastAll) pd).removeTagPodcast(tag))) {
            podcastsByTag.get(tag).remove(pd);
            wasRemoved = true;
        }
        if ((sw != null) &&(((ShowAll)sw).removeTagShow(tag)) ){
            showsByTag.get(tag).remove(sw);
            wasRemoved = true;
        }
        if(!wasRemoved){
            throw new TitleIsNotTaggedException();
        }
    }
}