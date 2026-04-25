package YouVideo;

import dataStructures.*;

import java.util.Locale;

/**
 * Implementation of the YouVideo platform system.
 * Maintains four internal collections: all videos (publishable and episodes),
 * all podcasts, all known authors, and all shows.
 */
public class PlatformSystemClass implements PlatformSystem {

    // All videos registered in the system, including publishable videos and episodes.
    private Array<Video> videos;
    // All podcasts registered in the system, in insertion order.
    private Array<Podcast> podcasts;
    // All authors known to the system (from podcasts and shows), in insertion order.
    private Array<Author> authors;
    // All shows registered in the system, in insertion order.
    private Array<Show> shows;

    /**
     * Creates an empty platform with no content.
     */
    public PlatformSystemClass() {
        videos = new ArrayClass<>();
        podcasts = new ArrayClass<>();
        authors = new ArrayClass<>();
        shows = new ArrayClass<>();
    }


    // ----------------------------- VIDEO -----------------------------
    @Override
    public boolean hasPublishable(String id) {
        return videos.searchForward(new BasicVideoClass(id));
    }

    @Override
    public boolean hasPublishableVideo(String id) {
        return hasPublishable(id) && !isEpisode(id);
    }

    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                               String title, Locale lang) {
        videos.insertLast(new BasicVideoClass(id, duration, url, publisher,
                title, lang));
    }

    @Override
    public void addPremiumPublishable(String id, int duration, String url,
                                      String publisher, String title, Locale lang,
                                      String subtitleUrl, Locale subtitleLang) {
        videos.insertLast(new PremiumVideoClass(id, duration, url,
                publisher, title, lang, subtitleUrl, subtitleLang));
    }

    @Override
    public boolean isPremiumVideo(String id) {
        return getVideo(id) instanceof PremiumVideo;
    }

    public boolean isEpisode(String id) {
        return getVideo(id) instanceof Episode;
    }

    @Override
    public Video getVideo(String id) {
        int idx = videos.searchIndexOf(new BasicVideoClass(id));
        return videos.get(idx);
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, Locale subtitleLang) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        premiumVideo.addSubtitle(subtitleUrl, subtitleLang);
    }

    @Override
    public Iterator<Subtitle> subtitleIterator(String id) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        return premiumVideo.subtitleIterator();
    }

    @Override
    public void removeVideo(String id) {
        int idx = videos.searchIndexOf(getVideo(id));
        videos.removeAt(idx);
    }


    // ----------------------------- PODCAST -----------------------------
    @Override
    public boolean hasPodcast(String title) {
        return podcasts.searchForward(new PodcastClass(title));
    }

    /**
     * Verifies if an author with the given name is already known to the system.
     * @param author the author name to search for.
     * @return true if the author exists, false otherwise.
     */
    private boolean hasAuthor(String author) {
        return authors.searchForward(new AuthorClass(author));
    }

    /**
     * Returns the Author object for the given name.
     * @param author the name of the author.
     * @return the matching Author object.
     * @pre hasAuthor(author)
     */
    private Author getAuthor (String author) {
        int idx = authors.searchIndexOf(new AuthorClass(author));
        return authors.get(idx);
    }

    @Override
    public void addPodcast(String title, String author, Locale lang) {
        /* If the author creating a new Podcast already is in the system,
         * we use the first name entered into the system (au.getAuthor()).
         */
        if (hasAuthor(author)) {
            Author au = getAuthor(author);
            podcasts.insertLast(new PodcastClass(title, au.getAuthor(), lang));
        } else {
            authors.insertLast(new AuthorClass(author));
            podcasts.insertLast(new PodcastClass(title, author, lang));
        }
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date) {
        videos.insertLast(new EpisodeClass(id, duration, url, date));
        getPodcastAll(title).addEpisode(id, duration, url, date);
    }

    /**
     * Returns the internal PodcastAll reference for the podcast with the given title.
     * This gives access to mutating operations not exposed through the Podcast interface.
     * @param title the title of the podcast.
     * @return the PodcastAll object for the podcast.
     * @pre hasPodcast(title)
     */
    private PodcastAll getPodcastAll(String title) {
        int idx = podcasts.searchIndexOf(new PodcastClass(title));
        return (PodcastAll) podcasts.get(idx);
    }

    @Override
    public Podcast getPodcast(String title) {
        int idx = podcasts.searchIndexOf(new PodcastClass(title));
        return podcasts.get(idx);
    }

    @Override
    public boolean hasEpisode(String id) {
        return videos.searchForward(new EpisodeClass(id));
    }

    @Override
    public boolean isValidEpisodeDate(String title, String date) {
        Podcast podcast = getPodcast(title);
        return podcast.isValidEpisodeDate(date);
    }

    @Override
    public Iterator<Episode> episodeIterator(String title) {
        Podcast podcast = getPodcast(title);
        return podcast.episodeIterator();
    }

    @Override
    public Iterator<Podcast> authorPodcast(String author) {
        Iterator<Podcast> it = podcasts.iterator();
        Array <Podcast> authorPodcast = new ArrayClass<>();
        while (it.hasNext()) {
            Podcast podcast = it.next();
            if (podcast.getAuthor().equalsIgnoreCase(author)){
                authorPodcast.insertLast(podcast);
            }
        }
        return authorPodcast.iterator();
    }

    @Override
    public void removePodcast(String podcastTitle) {
        Podcast podcast = getPodcast(podcastTitle);
        int i = 0;
        while (i < videos.size()) {
            Video video = videos.get(i);
            if (video instanceof Episode && podcast.hasEpisode(video.getId())) {
                videos.removeAt(i);
            } else {
                i++;
            }
        }
        int idx = podcasts.searchIndexOf(new PodcastClass(podcastTitle));
        podcasts.removeAt(idx);
    }

    // ----------------------------- SHOW -----------------------------
    @Override
    public boolean hasShow(String videoID) {
        PublishableVideo video = (PublishableVideo) getVideo(videoID);
        return shows.searchForward(new ShowClass(video.getTitle()));
    }

    @Override
    public boolean hasShowTitle(String title) {
        return shows.searchForward(new ShowClass(title));
    }

    @Override
    public void addShow(String showAuthor, String videoID, String date) {
        PublishableVideo video = (PublishableVideo) getVideo(videoID);
        if (hasAuthor(showAuthor)) {
            Author au = getAuthor(showAuthor);
            shows.insertLast(new ShowClass(au.getAuthor(), date, video.getTitle()));
        } else {
            authors.insertLast(new AuthorClass(showAuthor));
            shows.insertLast(new ShowClass(showAuthor, date, video.getTitle()));
        }
    }

    @Override
    public Show getShow(String title) {
        int idx = shows.searchIndexOf(new ShowClass(title));
        return shows.get(idx);
    }

    @Override
    public void removeShow(String showTitle) {
        int idx = shows.searchIndexOf(new ShowClass(showTitle));
        shows.removeAt(idx);
    }
}