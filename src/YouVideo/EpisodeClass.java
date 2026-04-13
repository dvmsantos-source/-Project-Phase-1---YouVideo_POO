package YouVideo;

class EpisodeClass extends VideoClass implements Episode {
     private String date;

     public EpisodeClass(String ID, int duration, String URL, String date){
         super(ID, duration, URL);
         this.date = date;
     }

     public EpisodeClass(String ID){
         super(ID);
     }

     @Override
     public String getDate() {
         return date;
     }
 }


