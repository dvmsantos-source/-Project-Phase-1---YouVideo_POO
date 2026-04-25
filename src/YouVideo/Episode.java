package YouVideo;

/**
 * Represents a podcast episode in the platform.
 * An episode is a video with a release date, and can only exist
 * as part of a podcast.
 */
public interface Episode extends Video {
    /**
     * Returns the release date of this episode.
     * @return the release date in YYYY-MM-DD format.
     */
    String getDate();
}
