package YouVideo;

import dataStructures.Iterator;

public interface PlatformSystem {
    /**
     * Adds a publishable into the system.
     * @param id
     * @param duration
     * @param url
     * @param publisher
     * @param title
     * @param lang
     */
    void addPublishable(String id, int duration, String url, String publisher,
                        String title, String lang);

    /**
     * Verifies if the system has a publishable given its ID.
     * @param id
     * @return TRUE if the system has it or FALSE if the system does not have it.
     */
    boolean hasPublishable(String id);

    /**
     * Adds a premium publishable into the system.
     * @param id
     * @param duration
     * @param url
     * @param publisher
     * @param title
     * @param lang
     * @param subtitleUrl
     * @param subtitleLang
     */
    void addPremiumPublishable(String id, int duration, String url, String publisher, String title,
                               String lang, String subtitleUrl, String subtitleLang);

    /**
     * Verifies if a video is premium by its ID.
     * @param id
     * @return TRUE if it is premium or FALSE if it is basic.
     */
    boolean IsPremiumVideo(String id);

    /**
     * Returns a Video given its ID.
     * @param id
     * @return a premium or basic video,
     */
    Video getVideo(String id);

    /**
     * Adds a subtitle to a premium video.
     * @param id
     * @param subtitleUrl
     * @param subtitleLang
     */
    void addSubtitle(String id, String subtitleUrl, String subtitleLang);

    /**
     * Returns a subtitle iterator given an ID of a premium video.
     * @param id
     * @return a subtitle iterator.
     */
    Iterator<Subtitle> subtitleIterator(String id);
}
