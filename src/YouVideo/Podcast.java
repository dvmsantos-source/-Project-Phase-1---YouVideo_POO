package YouVideo;

import dataStructures.Iterator;

import java.util.Locale;

public interface Podcast {
    Locale getLang();

    String getTitle();

    String getAuthor();

    String getLastEpDate();

    boolean isEmpty();

    Iterator<Episode> episodeIterator();

    boolean isValidEpisodeDate(String date);

    boolean hasEpisode(String ID);
}
