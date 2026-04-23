package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;

public class AuthorClass implements Author {
    String author;

    public AuthorClass (String author){
        this.author = author;

    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof Author))
            return false;
        if (author == null)
            return false;
        return this.author.equalsIgnoreCase(((Author)other).getAuthor());
    }
}

