package YouVideo;

/**
 * Abstract class for all video types in the platform.
 * Stores the core video data: identifier, duration, and file URL.
 */
abstract class VideoClass implements VideoAll {
    private String ID; // The unique identifier of this video.
    private int duration; // The duration of this video, in minutes.
    private String URL; // The URL where the video file is stored.

    /**
     * Creates a fully initialised video with all core attributes.
     * @param ID the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param URL the URL where the video file is stored.
     */
    public VideoClass(String ID, int duration, String URL) {
        this.ID = ID;
        this.duration = duration;
        this.URL = URL;
    }

    /**
     * Creates a video with only an identifier.
     * @param ID the unique identifier of the video.
     */
    public VideoClass(String ID) {
        this.ID = ID;

    }


    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof Video))
            return false;
        if (ID == null)
            return false;
        return this.ID.equalsIgnoreCase(((Video)other).getId());
    }
}
