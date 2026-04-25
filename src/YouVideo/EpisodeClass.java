package YouVideo;

/**
 * Implementation of a podcast episode.
 * An episode extends the core video data with a release date.
 * Episodes are ordered within their podcast in reverse chronological order.
 */
class EpisodeClass extends VideoClass implements Episode {
     private String date; // The release date of this episode, in YYYY-MM-DD format.

    /**
     * Creates a fully initialised episode.
     * @param ID the unique identifier of the episode.
     * @param duration the duration of the episode in minutes.
     * @param URL the URL where the episode file is stored.
     * @param date the release date of the episode in YYYY-MM-DD format.
     */
     public EpisodeClass(String ID, int duration, String URL, String date) {
         super(ID, duration, URL);
         this.date = date;
     }

    /**
     * Creates an episode with only an identifier.
     * @param ID the unique identifier of the episode.
     */
     public EpisodeClass(String ID){
         super(ID);
     }

     @Override
     public String getDate() {
         return date;
     }
 }


