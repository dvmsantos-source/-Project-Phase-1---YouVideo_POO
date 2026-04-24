package YouVideo;

import java.util.Locale;

class BasicVideoClass extends VideoClass implements PublishableVideo{
      private String title;
      private Locale lang;
      private String publisher;

      public BasicVideoClass(String ID, int duration, String URL, String publisher,
                             String title, Locale lang){
          super(ID, duration, URL);
          this.title = title;
          this.lang = lang;
          this.publisher = publisher;
      }

      public BasicVideoClass(String id) {
          super(id);
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
      public boolean isPremium() { return false; }

      @Override
      public String getPublisher() {
          return publisher;
      }
  }
