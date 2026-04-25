package YouVideo;

import java.util.Locale;

/**
 * Represents a publishable video in the platform.
 * A publishable video extends a basic video with a title, a publisher name,
 * and a primary language. Publishable videos can be independently published
 * and referenced by shows.
 */
public interface PublishableVideo extends Video{
    /**
     * Returns the title of the video.
     * @return the video title.
     */
    String getTitle();

    /**
     * Returns the name of the publisher who uploaded this video.
     * @return the publisher's name.
     */
    String getPublisher();

    /**
     * Returns the primary language of the video content.
     * @return the video's primary language as a Locale.
     */
    Locale getLang();

    /**
     * Verifies if the video is Premium.
     * @return true if is Premium, false otherwise.
     */
    boolean isPremium();
}
