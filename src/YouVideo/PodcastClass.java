package YouVideo;

import java.util.*;

/**
 * Implementation of a podcast.
 * Episodes are stored in an array and inserted at position 0, so the
 * most recently added episode is always at index 0 (reverse chronological order).
 */
class PodcastClass extends TaggedContentClass implements PodcastAll,Comparable<Podcast> {
    private String title; // The unique title of this podcast.
    private String author; // The name of the author of this podcast.
    private Locale lang; // The primary language of this podcast.
    private List<Episode> episodes; // The ordered collection of episodes, newest first//

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

    //  retorna a menor data
    public String getLastEpDate() {
         if (episodes.isEmpty()) {
            return "";
        }

        String menorData = episodes.get(0).getDate();

        for (int i = 1; i < episodes.size(); i++) {
            String dataAtual = episodes.get(i).getDate();
            if (dataAtual.compareTo(menorData) > 0) {
                menorData = dataAtual;
            }
        }

        return menorData;
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


    // serve para ordenar os podcast por em ordem alfabetica dos titulos
    @Override
    public int compareTo(Podcast o) {
        return this.title.compareTo(o.getTitle());
    }
}