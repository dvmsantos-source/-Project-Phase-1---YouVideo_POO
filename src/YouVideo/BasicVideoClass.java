package YouVideo;

  class BasicVideoClass extends VideoClass{
      private String title;
      private String lang;

      public BasicVideoClass(String ID,int duration, String URL,String publisher,String title,String lang){
          super( ID, duration, URL, publisher);
          this.title = title;
          this.lang = lang;
      }

      public String getTitle() {
          return title;
      }

      public String getLang() {
          return lang;
      }
  }
