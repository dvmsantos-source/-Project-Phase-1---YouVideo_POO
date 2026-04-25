package YouVideo;

/**
 * Extends the Author interface with mutating operations used internally.
 * This interface is not exposed publicly, only PlatformSystemClass uses it.
 */
public interface AuthorAll extends Author {
    /**
     * Verifies if this Author is equal to another object.
     * Two authors are considered equal if they share the same name.
     * @param other the object to compare with.
     * @return true if both objects represent the same author, false otherwise.
     */
    boolean equals(Object other);
}
