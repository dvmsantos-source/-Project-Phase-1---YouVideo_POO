package YouVideo;

import java.util.Comparator;

public class ComparatorByTags implements Comparator<TaggedContent> {

    @Override
    public int compare(TaggedContent o1, TaggedContent o2) {
        // Ordem alfabética ignorando maiúsculas e minúsculas
        int titleCompare = o1.getTitle().compareToIgnoreCase(o2.getTitle());
        if (titleCompare != 0) {
            return titleCompare;
        }

        // Se o título for o MESMO, Show ganha prioridade e vem primeiro
        if (o1 instanceof Show && o2 instanceof Podcast) {
            return -1; // o1 (Show) vem antes
        } else if (o1 instanceof Podcast && o2 instanceof Show) {
            return 1; // o2 (Show) vem antes
        }
        return 0;
    }
}
