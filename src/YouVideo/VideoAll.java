package YouVideo;

/**
 * Extends the Video interface with an equality contract.
 * Used internally to allow searching videos by identifier in collections.
 */
interface VideoAll extends Video {
    /**
     * Verifies if this video is equal to another object.
     * Two videos are considered equal if they share the same identifier.
     * @param other the object to compare with.
     * @return true if both objects represent the same video, false otherwise.
     */
    boolean equals(Object other);
}
