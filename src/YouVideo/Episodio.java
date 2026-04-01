package YouVideo;

 class Episodio extends VideoClass {
     private int date;

     public Episodio (String ID,int duration, String URL,String publisher,int date){
         super( ID, duration, URL, publisher);
         this.date = date;

     }

     public int getDate() {
         return date;
     }
 }


