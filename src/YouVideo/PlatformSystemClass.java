package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;

public class PlatformSystemClass implements PlatformSystem {
    private final Array<Video> video;

    public PlatformSystemClass() {
        video = new ArrayClass<>();

    }

    @Override
    public void addPublishe(String id, int duration, String url, String publisher, String title, String lang) {
        video.insertLast(new BasicVideoClass(id, duration, url, publisher, title, lang));

    }


    @Override
    public boolean hasPublishe(String id, int duration, String url, String publisher) {
        return video.searchForward(new VideoClass(id, duration, url, publisher));
    }

    @Override
    public void addPremiumPublishe(String id, int duration, String url, String publisher, String title,
                                   String lang, String subtitleUrl, String subtitleLang) {
        video.insertLast(new PremiumVideoClass(id, duration, url, publisher, title, lang, subtitleUrl,subtitleLang));
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, String subtitleLang) {
        int op = video.searchIndexOf(new VideoClass(id,0,subtitleUrl,subtitleLang));
        Video video1 = video.get(op);
        PremiumVideoClass premiumVideo = (PremiumVideoClass) video1;
        premiumVideo.addSubtitle(subtitleUrl,subtitleLang);

}

    @Override
    public boolean hasVideo(String id, String subtitleUrl, String subtitleLang) {
        return video.searchForward(new VideoClass(id, 0,subtitleUrl , subtitleLang));
    }

    @Override
    public boolean IsPremiumVideo(String id, String subtitleUrl, String subtitleLang) {
        int op = video.searchIndexOf(new VideoClass(id,0,subtitleUrl,subtitleLang));
        Video video1 = video.get(op);
        return video1 instanceof PremiumVideoClass;
    }
}
