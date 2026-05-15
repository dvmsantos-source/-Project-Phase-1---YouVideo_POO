package YouVideo;

import java.util.Comparator;

public class ComparatorPodcastByTitles implements Comparator<Podcast> {



    @Override
    public int compare(Podcast o1, Podcast o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
}
