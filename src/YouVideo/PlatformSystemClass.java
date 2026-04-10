package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class PlatformSystemClass implements PlatformSystem {

    private final Array<Video> video;
    private final Array<Podcast> podcast;

    public PlatformSystemClass() {
        video = new ArrayClass<>();
        podcast = new ArrayClass<>();
    }

    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                               String title, String lang) {
        video.insertLast(new BasicVideoClass(id, duration, url, publisher,
                title, lang));
    }

    @Override
    public boolean hasPublishable(String id) {
        return video.searchForward(new BasicVideoClass(id));
    }

    @Override
    public void addPremiumPublishable(String id, int duration, String url,
                                      String publisher, String title, String lang,
                                      String subtitleUrl, String subtitleLang) {
        video.insertLast(new PremiumVideoClass(id, duration, url,
                publisher, title, lang, subtitleUrl, subtitleLang));
    }

    @Override
    public boolean IsPremiumVideo(String id) {
        return getVideo(id) instanceof PremiumVideoClass;
    }

    public Video getVideo(String id) {
        int idx = video.searchIndexOf(new BasicVideoClass(id));
        return video.get(idx);
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, String subtitleLang) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        premiumVideo.addSubtitle(subtitleUrl, subtitleLang);
    }

    public Iterator<Subtitle> subtitleIterator(String id) {
        PremiumVideoClass premiumVideo = (PremiumVideoClass) getVideo(id);
        return premiumVideo.subtitleIterator();
    }

    @Override
    public boolean hasPodcast(String title) {
        return podcast.searchForward(new PodcastClass(title));
    }

    @Override
    public void addPodcast(String title, String author, String lang) {
        podcast.insertLast(new PodcastClass(title,author,lang));
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date) {
        getPodCast(title).addEpisode(id,duration,url,date);

    }

    @Override
    public Podcast getPodCast(String title) {
        int idx = podcast.searchIndexOf(new PodcastClass(title));
        return podcast.get(idx);
    }

    @Override
    public boolean hasEpisode(String id) {
        return video.searchForward(new EpisodeClass(id));
    }

    @Override
    public boolean isValidEpisodeDate(String title, String date) {
        Podcast podcast = getPodCast(title);
        Iterator it = podcast.getEpisode();
        if (it.hasNext()){
            Episode ep = (EpisodeClass) it.next();
            return (ep.getDate().compareTo(date) > 0);

    }
        return false;
    }
}
