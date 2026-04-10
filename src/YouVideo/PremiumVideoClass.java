package YouVideo;

import dataStructures.*;

class PremiumVideoClass extends BasicVideoClass{
    private Array<Subtitle> subtitle;


    public PremiumVideoClass(String ID,int duration, String URL,String publisher,
                             String title,String lang,String subtitleUrl,String subtitleLang){
        super(ID, duration, URL, publisher,title,lang);
        this.subtitle = new ArrayClass<>();
        addSubtitle( subtitleUrl, subtitleLang);

    }

    public void addSubtitle(String subtitleUrl,String subtitleLang){
        subtitle.insertLast(new Subtitle(subtitleUrl,subtitleLang));

    }

     public Iterator<Subtitle> subtitleIterator() {
    return subtitle.iterator();
}

}
