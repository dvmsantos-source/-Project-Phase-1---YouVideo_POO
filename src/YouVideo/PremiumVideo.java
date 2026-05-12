
package YouVideo;

import java.util.Iterator;
import java.util.Locale;

interface PremiumVideo extends PublishableVideo {
    /**
     * Adds a subtitle to this premium video.
     * @param subtitleUrl  the URL of the subtitle file.
     * @param subtitleLang the language of the subtitle.
     */
    void addSubtitle(String subtitleUrl, Locale subtitleLang);

    /**
     * Returns an iterator over the subtitles of this video.
     * @return a subtitle iterator.
     */
    Iterator<Subtitle> subtitleIterator();
}



