package YouVideo;

import java.util.Comparator;

public class ComparatorShowByTitles implements Comparator<Show> {

    @Override
    public int compare(Show o1, Show o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
}
