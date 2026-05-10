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
    @Override
    public void removePodcast(Podcast podcast) {
         podcasts.remove(podcast);
    }
    @Override
    public boolean isPodcastsEmpty() {
        return podcasts.isEmpty();
    }
    @Override
    public Iterator<Podcast> podcastIterator() {
        return podcasts.iterator();
    }
    @Override
    public Iterator<Show> showIterator() {
        return shows.iterator();
    }
    @Override
    public void addShow(Show show) {
        shows.add(show);
    }
    @Override
    public void removeShow(Show show) {
         shows.remove(show);
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

