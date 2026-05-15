package YouVideo;

import java.util.Comparator;

public class ComparatorByTagsDesc implements Comparator<TaggedContent> {

    @Override
    public int compare(TaggedContent o1, TaggedContent o2) {
        int compare = o2.getTitle().compareToIgnoreCase(o1.getTitle());
        if (compare != 0) {
            return compare;
        }

        if (o1 instanceof Show && o2 instanceof Podcast) {
            return -1;
        } else if (o1 instanceof Podcast && o2 instanceof Show) {
            return 1;
        }
        return 0;
    }
}
