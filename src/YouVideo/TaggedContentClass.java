package YouVideo;

import Exceptions.TitleAlreadyTaggedException;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class TaggedContentClass implements TaggedContent{
    private final SortedSet<String> tags;
    
    public TaggedContentClass() {
        this.tags = new TreeSet<>(new ComparatorByName());
    }


    public boolean hasTag(String tag){
        for (String Tag : tags) {
            if (Tag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addTag(String tag) throws TitleAlreadyTaggedException {
        if (hasTag(tag)){
            throw new TitleAlreadyTaggedException();
        }
        tags.add(tag);
    }

    @Override
    public boolean removeTag(String tag) {
        if (hasTag(tag)){
            tags.remove(tag);
            return true;
        }

        return false;
    }

    @Override
    public Iterator<String> tagsIterator() {
        return tags.iterator();
    }
    @Override
    public boolean isTagsEmpty(){
        return tags.isEmpty();
    }






}
