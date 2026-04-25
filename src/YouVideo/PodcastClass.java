package YouVideo;

import dataStructures.*;

import java.util.Locale;

/**
 * Implementation of a podcast.
 * Episodes are stored in an array and inserted at position 0, so the
 * most recently added episode is always at index 0 (reverse chronological order).
 */
class PodcastClass implements PodcastAll {
    private Array<Episode> episodes; // The ordered collection of episodes, newest first.
    private String title; // The unique title of this podcast.
    private String author; // The name of the author of this podcast.
    private Locale lang; // The primary language of this podcast.

    /**
     * Creates a fully initialised podcast with no episodes.
     * @param title the unique title of the podcast.
     * @param author the name of the podcast's author.
     * @param lang the primary language of the podcast.
     */
    public PodcastClass(String title, String author, Locale lang ) {
        this.title = title;
        this.author = author;
        this.lang = lang;
        episodes = new ArrayClass<>();
    }

    /**
     * Creates a podcast with only a title, used for lookup purposes.
     * @param title the title of the podcast.
     */
    public PodcastClass(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object other) {
        if (other== null)
            return false;
        if (this == other)
            return true;
        if (title == null)
            return false;
        if (!(other instanceof Podcast))
            return false;
        return this.title.equalsIgnoreCase(((Podcast)other).getTitle());
    }

    @Override
    public Locale getLang() {
        return lang;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getLastEpDate() {
        Episode ep = episodes.get(episodes.size() - 1);
        return ep.getDate();
    }

    @Override
    public boolean isValidEpisodeDate(String date) {
        if (episodes.size() == 0){
            return true;
        }
        else {
            String lastDate = getLastEpDate();
            return date.compareTo(lastDate) >= 0;
        }
    }

    @Override
    public boolean hasEpisode(String episodeID) {
        return episodes.searchForward(new EpisodeClass(episodeID));
    }

    @Override
    public boolean isEmpty() {
        return episodes.size() == 0;
    }

    @Override
    public void addEpisode(String ID, int duration, String URL, String date ) {
        this.episodes.insertAt(new EpisodeClass(ID, duration, URL, date),0);
    }

    @Override
    public Iterator<Episode> episodeIterator() {
        return episodes.iterator();
    }
}