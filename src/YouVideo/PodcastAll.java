package YouVideo;

/**
 * Extends the Podcast interface with mutating operations used internally.
 * Provides the ability to add episodes and enforce equality by title.
 * This interface is not exposed publicly, only PlatformSystemClass uses it.
 */
interface PodcastAll extends Podcast {
    /**
     * Verifies if this podcast is equal to another object.
     * Two podcasts are considered equal if they share the same title.
     * @param other the object to compare with.
     * @return true if both objects represent the same podcast, false otherwise.
     */
    boolean equals(Object other);

    /**
     * Adds a new episode to this podcast.
     * Episodes are stored in reverse chronological order (most recent first).
     * @param ID the unique identifier of the episode.
     * @param duration the duration of the episode in minutes.
     * @param URL the URL where the episode file is stored.
     * @param date the release date of the episode in YYYY-MM-DD format.
     * @pre isValidEpisodeDate(date)
     */
    void addEpisode(String ID, int duration, String URL, String date);
}
