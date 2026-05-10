package YouVideo;

import java.util.*;

/**
 * Implementation of a podcast.
 * Episodes are stored in an array and inserted at position 0, so the
 * most recently added episode is always at index 0 (reverse chronological order).
 */
class PodcastClass implements PodcastAll {
    private List<Episode> episodes; // The ordered collection of episodes, newest first//
    private SortedSet<String> tags;
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
        episodes = new LinkedList<>();
        tags = new TreeSet<>();
    }


    // ----------------------------- TAGS -----------------------------
    @Override
    public boolean hasTagPodcast(String tag){
        return tags.contains(tag);
    }
    @Override
    public void addTagPodcast(String tag){
        tags.add(tag);
    }

    @Override
    public boolean isTagsEmpty(){
        return tags.isEmpty();
    }

    @Override
    public boolean removeTagPodcast(String tag){
        return tags.remove(tag);
    }

    @Override
    public Iterator<String> tagsPodcastIterator() {
        return tags.iterator();
    }


    // ----------------------------- EPISODE -----------------------------
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
        Episode ep = episodes.getLast();
        return ep.getDate();
    }

    @Override
    public boolean isValidEpisodeDate(String date) {
        if (episodes.isEmpty()) {
            return true;
        }
        else {
            String lastDate = getLastEpDate();
            return date.compareTo(lastDate) >= 0;
        }
    }

    @Override
    public boolean hasEpisode(Video episode) {
        return episodes.contains(episode);
    }

    @Override
    public boolean isEmpty() {
        return episodes.isEmpty();
    }

    @Override
    public void addEpisode(Episode episode) {
        episodes.addFirst(episode);
    }

    @Override
    public Iterator<Episode> episodeIterator() {
        return episodes.iterator();
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
}