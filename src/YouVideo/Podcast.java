package YouVideo;

import dataStructures.Array;
import dataStructures.Iterator;

public interface Podcast {
    String getLang();
    String getTitle();
    String getAuthor();
    boolean equals(Object other);
    void addEpisode(String ID, int duration, String URL, String date );
     Iterator<EpisodeClass> getEpisode();
}
