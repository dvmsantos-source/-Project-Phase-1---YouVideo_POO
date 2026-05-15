package YouVideo;

import Exceptions.TitleAlreadyTaggedException;

import java.util.Iterator;

public interface TaggedContent {

    Iterator<String> tagsIterator();

    boolean hasTag(String tag);

    boolean isTagsEmpty();

    void addTag(String tag) throws TitleAlreadyTaggedException;

    boolean removeTag(String tag);

    String getTitle();

    String getAuthor();
}
