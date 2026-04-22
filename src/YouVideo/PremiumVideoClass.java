package YouVideo;

import dataStructures.*;

import java.util.Locale;

class PremiumVideoClass extends BasicVideoClass{
    private Array<Subtitle> subtitle;


    public PremiumVideoClass(String ID, int duration, String URL, String publisher,
                             String title, Locale lang, String subtitleUrl, Locale subtitleLang){
        super(ID, duration, URL, publisher, title, lang);
        this.subtitle = new ArrayClass<>();
        addSubtitle( subtitleUrl, subtitleLang);

    }

    public void addSubtitle(String subtitleUrl,Locale subtitleLang){
        subtitle.insertLast(new Subtitle(subtitleLang,subtitleUrl));

    }

    public Iterator<Subtitle> subtitleIterator() {
    return subtitle.iterator();
}

}
