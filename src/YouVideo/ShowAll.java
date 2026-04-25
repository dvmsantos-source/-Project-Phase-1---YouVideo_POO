package YouVideo;

/**
 * Extends the Show interface with mutating operations used internally.
 * This interface is not exposed publicly, only PlatformSystemClass uses it.
 */
public interface ShowAll extends Show {
    /**
     * Verifies if this Show is equal to another object.
     * Two authors are considered equal if they share the same title.
     * @param other the object to compare with.
     * @return true if both objects represent the same Show, false otherwise.
     */
    boolean equals(Object other);
}
