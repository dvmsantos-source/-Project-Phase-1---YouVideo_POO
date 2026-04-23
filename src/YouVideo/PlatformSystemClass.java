package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

import java.util.Locale;

public class PlatformSystemClass implements PlatformSystem {

    private Array<Video> videos;
    private Array<Podcast> podcasts;
    private Array<Author> authors;
    private Array<Show> shows;

    public PlatformSystemClass() {
        videos = new ArrayClass<>();
        podcasts = new ArrayClass<>();
        authors = new ArrayClass<>();
        shows = new ArrayClass<>();

    }

    //------------------------------------------VIDEO------------------------------------------
    @Override
    public boolean hasPublishable(String id) {
        return videos.searchForward(new BasicVideoClass(id));
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
    public boolean IsPremiumVideo(String id) {
        return getVideo(id) instanceof PremiumVideoClass;
    }

    public boolean isEpisode(String id) {
        return getVideo(id) instanceof Episode;
    }

    public Video getVideo(String id) {
        int idx = videos.searchIndexOf(new BasicVideoClass(id));
        return videos.get(idx);
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, Locale subtitleLang) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        premiumVideo.addSubtitle(subtitleUrl, subtitleLang);
    }

    public Iterator<Subtitle> subtitleIterator(String id) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        return premiumVideo.subtitleIterator();
    }

    @Override
    public void removeVideo(String id) {
        int idx = videos.searchIndexOf(getVideo(id));
        videos.removeAt(idx);
    }
    //------------------------------------------PODCAST------------------------------------------
    @Override
    public boolean hasPodcast(String title) {
        return podcasts.searchForward(new PodcastClass(title));
    }
    private boolean hasAuthor(String author){
        return authors.searchForward(new AuthorClass(author));
    }

    private Author getAuthor (String author ){
        int idx = authors.searchIndexOf(new AuthorClass(author));
        return authors.get(idx);
    }


    @Override
    public void addPodcast(String title, String author, Locale lang) {
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
    public void removePodcast(String podcastTitle) {
        Iterator<Video> it = videos.iterator();
        while (it.hasNext()) {
            Video video = it.next();
            if (video instanceof Episode && isFromPodcast(video.getId(), podcastTitle)) {
                int idx = videos.searchIndexOf(it.next());
                videos.removeAt(idx);
            }
        }
        int idx = podcasts.searchIndexOf(new PodcastClass(podcastTitle));
        podcasts.removeAt(idx);
    }

    private boolean isFromPodcast(String episodeID, String podcastTitle) {
        Podcast podcast = getPodcast(podcastTitle);
        return podcast.hasEpisode(episodeID);
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

    public Iterator<Episode> episodeIterator(String title) {
        Podcast podcast = getPodcast(title);
        return podcast.episodeIterator();
    }

    //------------------------------------------SHOW------------------------------------------
    @Override
    public boolean hasShow(String videoID) {
        PublishableVideo video = (PublishableVideo) getVideo(videoID);
        return shows.searchForward(new ShowClass(video.getTitle()));
    }

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