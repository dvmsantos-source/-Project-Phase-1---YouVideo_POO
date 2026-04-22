package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

import java.util.Locale;

public class PlatformSystemClass implements PlatformSystem {

    private Array<Video> videos;
    private Array<Podcast> podcasts;
    private Array<Show> shows;

    public PlatformSystemClass() {
        videos = new ArrayClass<>();
        podcasts = new ArrayClass<>();
        shows = new ArrayClass<>();
    }

    //------------------------------------------VIDEOS------------------------------------------
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

    //------------------------------------------PODCAST------------------------------------------
    @Override
    public boolean hasPodcast(String title) {
        return podcasts.searchForward(new PodcastClass(title));
    }

    @Override
    public void addPodcast(String title, String author, Locale lang) {
        podcasts.insertLast(new PodcastClass(title, author, lang));
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date) {
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
    public void removePodcast(String title) {
        int idx = podcasts.searchIndexOf(new PodcastClass(title));
        podcasts.removeAt(idx);
    }

    public Iterator<Episode> episodeIterator(String title) {
        Podcast podcast = getPodcast(title);
        return podcast.episodeIterator();
    }

    //------------------------------------------SHOW------------------------------------------
    @Override
    public boolean hasShow(String title) {
        return shows.searchForward(new ShowClass(title));
    }

    @Override
    public void addShow(String showAuthor, String videoID, String date) {
        PublishableVideo video = (PublishableVideo) getVideo(videoID);
        shows.insertLast(new ShowClass(showAuthor, video, date));
    }

    public Show getShow(String title) {
        int idx = shows.searchIndexOf(new ShowClass(title));
        return shows.get(idx);
    }
}
