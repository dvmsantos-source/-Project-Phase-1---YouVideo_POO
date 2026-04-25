package YouVideo;

/**
 * Implementation of a show.
 * A show is identified by its title (equal to the broadcasted video's title).
 */
class ShowClass implements ShowAll {
    private String author; // The name of the author who created this show.
    private String date; // The transmission date of this show in YYYY-MM-DD format.
    private String title; // The title of this show, matching the title of the broadcasted video.

    /**
     * Creates a fully initialised show.
     * @param author the name of the show's author.
     * @param date the transmission date in YYYY-MM-DD format.
     * @param title the title of the show (equal to the video's title).
     */
    public ShowClass(String author, String date, String title) {
        this.author = author;
        this.date = date;
        this.title = title;
    }

    /**
     * Creates a show with only a title.
     * @param title the title of the show.
     */
    public ShowClass(String title) {
        this.title = title;
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

    @Override
    public boolean equals(Object other) {
        if (other== null)
            return false;
        if (this == other)
            return true;
        if (title == null)
            return false;
        if (!(other instanceof Show))
            return false;
        return this.title.equalsIgnoreCase(((Show)other).getTitle());
    }
}