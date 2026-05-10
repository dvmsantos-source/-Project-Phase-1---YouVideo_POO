package YouVideo;

import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Implementation of a show.
 * A show is identified by its title (equal to the broadcasted video's title).
 */
class ShowClass implements ShowAll, Comparable<Show> {
    private String author; // The name of the author who created this show.
    private String date; // The transmission date of this show in YYYY-MM-DD format.
    private final String title; // The title of this show, matching the title of the broadcasted video.
    private SortedSet<String> tags;

    private final Video video;
    /**
     * Creates a fully initialised show.
     *
     * @param author the name of the show's author.
     * @param date   the transmission date in YYYY-MM-DD format.
     * @param title  the title of the show (equal to the video's title).
     */
    public ShowClass(String author, String date, String title, Video video) {
        this.author = author;
        this.date = date;
        this.title = title;
        tags = new TreeSet<>();
        this.video = video;
    }


    // ----------------------------- TAGS -----------------------------


    @Override
    public boolean hasTagShow(String tag){
        return tags.contains(tag);
    }
    @Override
    public void addTagShow(String tag) {
        tags.add(tag);
    }

    @Override
    public boolean removeTagShow(String tag) {
          return tags.remove(tag);
    }

    @Override
    public Iterator<String> tagsShowIterator() {
        return tags.iterator();
    }

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
    public boolean isTagsEmpty(){
        return tags.isEmpty();
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
        if (other == null)
            return false;
        if (this == other)
            return true;
        if (title == null)
            return false;
        if (!(other instanceof Show))
            return false;
        return this.title.equalsIgnoreCase(((Show) other).getTitle());
    }
}