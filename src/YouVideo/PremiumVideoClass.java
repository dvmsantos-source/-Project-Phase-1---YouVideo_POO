package YouVideo;


import java.util.*;

/**
 * Implementation of a premium publishable video.
 * In addition to all publishable video data, a premium video supports
 * one or more subtitles in different languages. Subtitles are stored
 * in insertion order, and duplicate languages are permitted.
 */
class PremiumVideoClass extends BasicVideoClass implements PremiumVideo {
    // The ordered collection of subtitles associated with this video.
    private List<Subtitle> subtitles;

    /**
     * Creates a fully initialised premium video with an initial subtitle.
     * @param ID the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param URL the URL where the video file is stored.
     * @param publisher the name of the publisher.
     * @param title the title of the video.
     * @param lang the primary language of the video content.
     * @param subtitleUrl the URL of the initial subtitle file.
     * @param subtitleLang the language of the initial subtitle.
     */
    public PremiumVideoClass(String ID, int duration, String URL, String publisher,
                             String title, Locale lang, String subtitleUrl, Locale subtitleLang) {
        super(ID, duration, URL, publisher, title, lang);
        this.subtitles = new ArrayList<>();
        addSubtitle(subtitleUrl, subtitleLang);
    }


    /**
     * Adds a new subtitle to this video, preserving insertion order.
     * Multiple subtitles in the same language are permitted.
     * @param subtitleUrl the URL of the subtitle file.
     * @param subtitleLang the language of the subtitle.
     */
    @Override
    public void addSubtitle(String subtitleUrl,Locale subtitleLang) {
        subtitles.addLast(new Subtitle(subtitleLang,subtitleUrl));

    }

    @Override
    public boolean isPremium() {
        return true;
    }

    /**
     * Returns an iterator over the subtitles of this video, in insertion order.
     * @return an iterator over the video's subtitles.
     */
    @Override
    public Iterator<Subtitle> subtitleIterator() {
        return subtitles.iterator();
    }
}
