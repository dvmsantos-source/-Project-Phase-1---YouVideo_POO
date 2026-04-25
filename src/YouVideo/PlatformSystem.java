package YouVideo;

import dataStructures.Iterator;

import java.util.Locale;

/**
 * Represents the central system of the YouVideo platform.
 * Manages all videos (publishable and episodes), podcasts, shows, and authors,
 * providing operations to create, query, and remove each type of content.
 */
public interface PlatformSystem {

    // ----------------------------- VIDEO -----------------------------
    /**
     * Verifies if a video with the given identifier exists in the system.
     * @param id the video identifier to look up.
     * @return true if any video with that identifier exists, false otherwise.
     */
    boolean hasPublishable(String id);

    /**
     * Verifies if a video with the given identifier exists in the system and
     * if it is not an Episode.
     * @param id the video identifier to look up.
     * @return true if any video with that identifier exists and is not an Episode,
     * false otherwise.
     */
    boolean hasPublishableVideo(String id);

    /**
     * Adds a new basic publishable video to the system.
     * @param id the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param url the URL where the video file is stored.
     * @param publisher the name of the publisher.
     * @param title the title of the video.
     * @param lang the primary language of the video content.
     * @pre !hasPublishable(id) && duration > 0
     */
    void addPublishable(String id, int duration, String url, String publisher,
                        String title, Locale lang);

    /**
     * Adds a new premium publishable video to the system.
     * Premium videos support subtitles and are published by premium subscribers.
     * @param id the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param url the URL where the video file is stored.
     * @param publisher the name of the publisher.
     * @param title the title of the video.
     * @param lang the primary language of the video content.
     * @param subtitleUrl the URL of the initial subtitle file.
     * @param subtitleLang the language of the initial subtitle.
     * @pre !hasPublishable(id) && duration > 0
     */
    void addPremiumPublishable(String id, int duration, String url, String publisher, String title,
                               Locale lang, String subtitleUrl, Locale subtitleLang);

    /**
     * Verifies if the video with the given identifier is a premium video.
     * @param id the video identifier to check.
     * @return true if the video is premium, false if it is basic.
     * @pre hasPublishable(id)
     */
   boolean isPremiumVideo(String id);

    /**
     * Verifies if the video with the given identifier is a podcast episode.
     * @param id the video identifier to check.
     * @return true if the video is a podcast episode, false otherwise.
     * @pre hasPublishable(id)
     */
    boolean isEpisode(String id);

    /**
     * Returns the video with the given identifier.
     * @param id the video identifier to look up.
     * @return the video associated with the given identifier.
     * @pre hasPublishable(id)
     */
    Video getVideo(String id);

    /**
     * Adds a subtitle to an existing premium video.
     * @param id the identifier of the premium video.
     * @param subtitleUrl the URL of the subtitle file.
     * @param subtitleLang the language of the subtitle.
     * @pre hasPublishable(id) && IsPremiumVideo(id)
     */
    void addSubtitle(String id, String subtitleUrl, Locale subtitleLang);

    /**
     * Returns an iterator over the subtitles of a premium video, in insertion order.
     * @param id the identifier of the premium video.
     * @return an iterator over the video's subtitles.
     * @pre hasPublishable(id) && IsPremiumVideo(id)
     */
    Iterator<Subtitle> subtitleIterator(String id);

    /**
     * Removes a publishable video from the system.
     * @param id the identifier of the video to remove.
     * @pre hasPublishable(id) && !isEpisode(id) && !hasShow(id)
     */
    void removeVideo(String id);

    // ----------------------------- PODCAST -----------------------------
    /**
     * Verifies if a podcast with the given title exists in the system.
     * @param title the podcast title to look up.
     * @return true if the podcast exists, false otherwise.
     */
    boolean hasPodcast(String title);

    /**
     * Adds a new podcast with no episodes to the system.
     * @param title the unique title of the podcast.
     * @param author the name of the podcast's author.
     * @param lang the primary language of the podcast.
     * @pre !hasPodcast(title)
     */
    void addPodcast(String title, String author, Locale lang);

    /**
     * Adds a new episode to an existing podcast, and registers it as a video in the system.
     * @param title the title of the podcast to add the episode to.
     * @param id the unique identifier of the episode.
     * @param duration the duration of the episode in minutes.
     * @param url the URL where the episode file is stored.
     * @param date the release date of the episode in YYYY-MM-DD format.
     * @pre hasPodcast(title) && !hasPublishable(id) && duration > 0
     */
    void addEpisode(String title, String id, int duration, String url, String date);

    /**
     * Returns the podcast with the given title.
     * @param title the title of the podcast to retrieve.
     * @return the podcast associated with the given title.
     * @pre hasPodcast(title)
     */
    Podcast getPodcast(String title);

    /**
     * Verifies if a video identifier belongs to a podcast episode.
     * @param id the video identifier to check.
     * @return true if a podcast episode with that identifier exists, false otherwise.
     */
    boolean hasEpisode(String id);

    /**
     * Verifies if a new episode with the given date can be added to the specified podcast.
     * @param title the title of the podcast.
     * @param date the candidate release date in YYYY-MM-DD format.
     * @return true if the date is valid for a new episode, false otherwise.
     * @pre hasPodcast(title)
     */
    boolean isValidEpisodeDate(String title, String date);

    /**
     * Returns an iterator over the episodes of a podcast, newest first.
     * @param title the title of the podcast.
     * @return an iterator over the podcast's episodes in reverse chronological order.
     * @pre hasPodcast(title)
     */
    Iterator<Episode> episodeIterator(String title);

    /**
     * Returns an iterator over all podcasts created by the given author,
     * in order of insertion.
     * @param author the name of the author to look up.
     * @return an iterator over the author's podcasts.
     */
    Iterator<Podcast> authorPodcast(String author);

    /**
     * Removes a podcast and all its episodes from the system.
     * All episodes belonging to the podcast are also removed from the video collection.
     * @param podcastTitle the title of the podcast to remove.
     * @pre hasPodcast(podcastTitle)
     */
    void removePodcast(String podcastTitle);

    // ----------------------------- SHOW -----------------------------
    /**
     * Verifies if the video with the given identifier is already used in a show.
     * @param videoID the identifier of the video.
     * @return true if a show exists with that video's title, false otherwise.
     * @pre hasPublishable(videoID)
     */
    boolean hasShow(String videoID);

    /**
     * Verifies a show with the given title exists in the system.
     * @param title the show title to look up.
     * @return true if the show exists, false otherwise.
     */
    boolean hasShowTitle(String title);

    /**
     * Creates a new show that broadcasts an existing publishable video.
     * @param showAuthor the name of the show's author.
     * @param videoID the identifier of the video to be shown.
     * @param date the transmission date in YYYY-MM-DD format.
     * @pre hasPublishable(videoID) && !isEpisode(videoID) && !hasShow(videoID)
     */
    void addShow(String showAuthor, String videoID, String date);

    /**
     * Returns the show with the given title.
     * @param title the title of the show to retrieve.
     * @return the show associated with the given title.
     * @pre hasShowTitle(title)
     */
    Show getShow(String title);

    /**
     * Removes a show from the system without affecting the underlying video.
     * @param showTitle the title of the show to remove.
     * @pre hasShowTitle(showTitle)
     */
    void removeShow(String showTitle);
}