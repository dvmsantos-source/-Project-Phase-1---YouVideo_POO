package YouVideo;

import java.util.Iterator;

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



    void addEpisode(Episode episode);
}
