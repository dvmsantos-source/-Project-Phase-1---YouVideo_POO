package YouVideo;

import java.util.Locale;

/**
 * Implementation of a publishable video published by a basic subscriber.
 * Stores the video's title, primary language, and publisher name,
 * in addition to the core video data inherited from VideoClass.
 */
class BasicVideoClass extends VideoClass implements PublishableVideo {
      private String title; // The title of this video.
      private Locale lang; // The primary language of this video's content.
      private String publisher; // The name of the publisher who uploaded this video.

    /**
     * Creates a basic publishable video.
     * @param ID the unique identifier of the video.
     * @param duration the duration of the video in minutes.
     * @param URL the URL where the video file is stored.
     * @param publisher the name of the publisher.
     * @param title the title of the video.
     * @param lang the primary language of the video content.
     */
      public BasicVideoClass(String ID, int duration, String URL, String publisher,
                             String title, Locale lang) {
          super(ID, duration, URL);
          this.title = title;
          this.lang = lang;
          this.publisher = publisher;
      }


      @Override
      public String getTitle() {
          return title;
      }

      @Override
      public Locale getLang() {
          return lang;
      }

      @Override
      public boolean isPremium() {
          return false;
      }

      @Override
      public String getPublisher() {
          return publisher;
      }
  }
