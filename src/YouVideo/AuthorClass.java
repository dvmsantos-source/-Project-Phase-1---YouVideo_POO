package YouVideo;

/**
 * Implementation of an author.
 */
 class AuthorClass implements Author {

   private String author; // The name of this author

    /**
     * Creates an author with the given name.
     * @param author the name of the author.
     */
    public AuthorClass (String author){
        this.author = author;
    }


    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object other) {
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

