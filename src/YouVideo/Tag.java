package YouVideo;

import java.util.Iterator;

 public interface Tag {
    Iterator<String> tagsIterator();
    boolean hasTag(String tag);

    boolean isTagsEmpty();

    void addTag(String tag);

    boolean removeTag(String tag);

}
