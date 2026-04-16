package YouVideo;

import dataStructures.Iterator;

import java.util.Locale;

public interface PlatformSystem {
    /**
     * Verifies if the system has a publishable given its ID.
     * @param id
     * @return TRUE if the system has it or FALSE if the system does not have it.
     */
    boolean hasPublishable(String id);

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
                        String title, Locale lang);

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
                               Locale lang, String subtitleUrl, Locale subtitleLang);

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
    void addSubtitle(String id, String subtitleUrl, Locale subtitleLang);

    /**
     * Returns a subtitle iterator given an ID of a premium video.
     * @param id
     * @return a subtitle iterator.
     */
    Iterator<Subtitle> subtitleIterator(String id);

    boolean hasPodcast(String title);

    void addPodcast(String title, String author, Locale lang);

    void addEpisode(String title, String id, int duration, String url, String date);

    Podcast getPodcast(String title);

    boolean hasEpisode(String id);

    boolean isValidEpisodeDate(String id, String date);

    Iterator<Episode> episodeIterator(String title);
}
