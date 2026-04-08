package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class PlatformSystemClass implements PlatformSystem {
    private static final String X = "X";
    private static final int ZERO = 0;

    private final Array<Video> video;

    public PlatformSystemClass() {
        video = new ArrayClass<>();
    }

    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                            String title, String lang) {
        video.insertLast(new BasicVideoClass(id, duration, url, publisher,
                title, lang));
    }

    @Override
    public boolean hasPublishable(String id) {
        return video.searchForward(new BasicVideoClass(id, ZERO, X, X, X, X));
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
        int idx = video.searchIndexOf(new BasicVideoClass(id, ZERO, X, X, X, X));
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
}
