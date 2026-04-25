package YouVideo;

import dataStructures.Iterator;

import java.util.Locale;

/**
 * Represents a podcast in the platform.
 * A podcast has a unique title, an author, and a primary language.
 * It manages a collection of episodes that are organised in reverse
 * chronological order (most recent first).
 */
public interface Podcast {
    /**
     * Returns the primary language of this podcast.
     * @return the podcast language as a Locale.
     */
    Locale getLang();

    /**
     * Returns the title of this podcast.
     * @return the podcast title.
     */
    String getTitle();

    /**
     * Returns the name of the author of this podcast.
     * @return the author's name.
     */
    String getAuthor();

    /**
     * Returns the release date of the most recently added episode.
     * @return the latest episode's release date in YYYY-MM-DD format.
     * @pre !isEmpty()
     */
    String getLastEpDate();

    /**
     * Verifies if this podcast has no episodes yet.
     * @return true if the podcast has no episodes, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the episodes of this podcast,
     * ordered from the most recent to the oldest.
     * @return an iterator over the podcast's episodes.
     */
    Iterator<Episode> episodeIterator();

    /**
     * Verifies if a new episode with the given date can be added to this podcast.
     * The date must be greater than or equal to the most recent existing episode's date.
     * @param date the candidate release date in YYYY-MM-DD format.
     * @return true if the date is valid for a new episode, false otherwise.
     */
    boolean isValidEpisodeDate(String date);

    /**
     * Verifies if this podcast contains an episode with the given identifier.
     * @param ID the episode identifier to search for.
     * @return true if the episode exists in this podcast, false otherwise.
     */
    boolean hasEpisode(String ID);

}
