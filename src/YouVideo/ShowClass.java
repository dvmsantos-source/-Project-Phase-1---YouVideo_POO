package YouVideo;

import java.util.Locale;

/**
 * Implementation of a show.
 * A show is identified by its title (equal to the broadcasted video's title).
 */
class ShowClass extends TaggedContentClass implements ShowAll, Comparable<Show> {
    private String author; // The name of the author who created this show.
    private String date; // The transmission date of this show in YYYY-MM-DD format.
    private final String title; // The title of this show, matching the title of the broadcasted video.

    private final Video video;
    /**
     * Creates a fully initialised show.
     *
     * @param author the name of the show's author.
     * @param date   the transmission date in YYYY-MM-DD format.
     * @param title  the title of the show (equal to the video's title).
     */
    public ShowClass(String author, String date, String title, Video video) {
        super();
        this.author = author;
        this.date = date;
        this.title = title;
        this.video = video;
    }


    // ----------------------------- TAGS -----------------------------


    @Override
    public int compareTo(Show other) {
        int cmp = this.getDate().compareTo(other.getDate());
        if (cmp != 0) return cmp;
        return this.getTitle().compareToIgnoreCase(other.getTitle());
    }

    // ----------------------------- SHOW -----------------------------
    @Override
    public Locale getLanguage(){
        return ((PublishableVideo)video).getLang();
    }

    @Override
    public int getDuration(){
        return video.getDuration();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getAuthor() {
        return author;
    }

}