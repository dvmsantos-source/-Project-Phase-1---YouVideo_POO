package YouVideo;

import dataStructures.Iterator;

interface PodcastAll extends Podcast{
    boolean equals(Object other);

    void addEpisode(String ID, int duration, String URL, String date);

    Iterator<EpisodeClass> episodeIterator();
}
