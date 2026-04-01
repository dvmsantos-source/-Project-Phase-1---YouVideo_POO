package YouVideo;

public interface PlatformSystem {
    void addPublishe(String id, int duration, String url, String publisher, String title, String lang);
    boolean hasPublishe(String id, int duration, String url, String publisher);

    void addPremiumPublishe(String id, int duration, String url, String publisher, String title, String lang, String subtitleUrl, String subtitleLang);

    void addSubtitle(String id, String subtitleUrl, String subtitleLang);

    boolean hasVideo(String id, String subtitleUrl, String subtitleLang);

    boolean IsPremiumVideo(String id, String subtitleUrl, String subtitleLang);
}
