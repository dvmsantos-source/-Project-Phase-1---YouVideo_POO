package YouVideo;


import java.util.*;


/**
 * Implementation of an author.
 */
 class AuthorClass implements AuthorAll {

    private String author; // The name of this author
    private List<Podcast> podcasts;
    private Set<Show> shows;

    /**
     * Creates an author with the given name.
     * @param author the name of the author.
     */
    public AuthorClass (String author) {
        this.author = author;
        podcasts = new LinkedList<>();
        shows = new TreeSet<>(); // IMPLEMENTAR COMPARATOR
    }



    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void addPodcast(Podcast podcast) {
        podcasts.addLast(podcast);
    }

    public void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    public boolean isPodcastsEmpty() {
        return podcasts.isEmpty();
    }

    public Iterator<Podcast> podcastIterator() {
        return podcasts.iterator();
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void removeShow(Show show) {
        podcasts.remove(show);
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

