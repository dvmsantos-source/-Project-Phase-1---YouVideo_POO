package YouVideo;

import java.util.Comparator;

public class ComparatorByProductivity implements Comparator<Author> {
    @Override
    public int compare(Author o1, Author o2) {
        int productivity = o2.getProductivity() - o1.getProductivity();
        if (productivity != 0){
            return productivity;
        }
        return o1.getAuthor().compareTo(o2.getAuthor());
    }
}
