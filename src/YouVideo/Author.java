package YouVideo;

import java.util.Iterator;

/**
 * Represents an author in the platform.
 * Authors are associated with podcasts and shows.
 */
interface Author {
    /**
     * Returns the name of this author.
     * @return the author's name.
     */
    String getAuthor();

    boolean isPodcastsEmpty();

    Iterator<Podcast> podcastIterator();
    Iterator<Show> showIterator();

    /**
     * Verifies if this Author is equal to another object.
     * Two authors are considered equal if they share the same name.
     * @param other the object to compare with.
     * @return true if both objects represent the same author, false otherwise.
     */
    boolean equals(Object other);
}
