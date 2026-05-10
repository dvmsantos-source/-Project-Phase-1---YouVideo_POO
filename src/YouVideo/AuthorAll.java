package YouVideo;

import java.util.Iterator;

interface AuthorAll extends Author {
    void addPodcast(Podcast podcast);

    void removePodcast(Podcast podcast);



    void addShow(Show show);

    void removeShow(Show show);
}

