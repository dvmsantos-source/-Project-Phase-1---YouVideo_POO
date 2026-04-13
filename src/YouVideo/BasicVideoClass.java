package YouVideo;

  class BasicVideoClass extends VideoClass implements PublishableVideo{
      private String title;
      private String lang;
      private String publisher;

      public BasicVideoClass(String ID,int duration, String URL,String publisher,
              String title,String lang){
          super( ID, duration, URL);
          this.title = title;
          this.lang = lang;
          this.publisher = publisher;
      }

      public BasicVideoClass(String id) {
          super(id);
      }

      public String getTitle() {
          return title;
      }

      public String getLang() {
          return lang;
      }

      public String getPublisher() {
          return publisher;
      }
  }
