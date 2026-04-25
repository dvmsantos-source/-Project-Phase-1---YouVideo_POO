package YouVideo;

/**
 * Represents a video in the YouVideo platform.
 * Every video has a unique identifier, a duration, and a file location.
 */
public interface Video {
    /**
     * Returns the unique identifier of the video.
     * @return the video's unique identifier.
     */
    String getId();

    /**
     * Returns the duration of the video, in minutes.
     * @return the video duration in minutes.
     */
    int getDuration();

    /**
     * Returns the URL where the video file is stored.
     * @return the video file URL.
     */
    String getUrl();
}
