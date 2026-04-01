package YouVideo;

import dataStructures.*;

class PremiumVideoClass extends VideoClass{
    private Array<Subtitle> subtitle;
    private String title;
    private String lang;


    public PremiumVideoClass(String ID,int duration, String URL,String publisher,
                             String title,String lang,String subtitleUrl,String subtitleLang){
        super(ID, duration, URL, publisher);
        this.subtitle = new ArrayClass<>();
        this.title = title;
        this.lang = lang;
        addSubtitle( subtitleUrl, subtitleLang);

    }

    public void addSubtitle(String subtitleUrl,String subtitleLang){
        subtitle.insertLast(new SubtitleClass(subtitleUrl,subtitleLang));

    }

    public String getTitle() {
        return title;
    }

    public String getLang() {
        return lang;
    }
}
