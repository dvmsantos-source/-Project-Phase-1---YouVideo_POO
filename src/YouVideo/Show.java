package YouVideo;

import java.util.Iterator;
import java.util.Locale;

/**
 * Represents a scheduled show in the platform.
 * A show broadcasts an existing publishable video on a given date,
 * and is identified by the title of the video being shown.
 * Removing a show does not affect the underlying video.
 */
public interface Show extends TaggedContent{
    /**
     * Returns the title of this show, which matches the title of its video.
     * @return the show title.
     */
    String getTitle();

    /**
     * Returns the transmission date of this show.
     * @return the transmission date in YYYY-MM-DD format.
     */
    String getDate();

    /**
     * Returns the name of the author who created this show.
     * @return the author's name.
     */
    String getAuthor();


    Locale getLanguage();
    int getDuration();

}