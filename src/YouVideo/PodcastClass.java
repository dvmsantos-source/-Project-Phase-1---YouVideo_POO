package YouVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class PodcastClass implements Podcast{
    private Array<EpisodeClass> episode;
    private String title;
    private String author;
    private String lang;


    public PodcastClass(String title,String author,String lang ){
        this.title = title;
        this.author = author;
        this.lang = lang;
        episode = new ArrayClass<>();
    }



    public PodcastClass(String title){
        this.title = title;
    }
    @Override
    public boolean equals(Object other){
        if (other== null)
            return false;
        if (this == other)
            return true;
        if (title == null)
            return false;
        if (!(other instanceof Podcast))
            return false;
        return this.title.equals( ((Podcast) other).getTitle());


    }

    @Override
    public void addEpisode(String ID, int duration, String URL, String date ) {
        this.episode.insertLast(new EpisodeClass(ID,duration,URL,date));
    }

    public Iterator<EpisodeClass> getEpisode() {
        return episode.iterator();
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

}
