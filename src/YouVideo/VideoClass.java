package YouVideo;

/**
 * Abstract class for all video types in the platform.
 * Stores the core video data: identifier, duration, and file URL.
 */
abstract class VideoClass implements VideoAll {
    private String id; // The unique identifier of this video.
    private int duration; // The duration of this video, in minutes.
    private String URL; // The URL where the video file is stored.

    /**
     * Creates a fully initialised video with all core attributes.
     * @param id the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param URL the URL where the video file is stored.
     */
    public VideoClass(String id, int duration, String URL) {
        this.id = id;
        this.duration = duration;
        this.URL = URL;
    }



    @Override
    public String getId() {
        return id;
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
        if (id == null)
            return false;
        return this.id.equalsIgnoreCase(((Video)other).getId());
    }
}
