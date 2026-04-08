package YouVideo;

import dataStructures.*;

class PremiumVideoClass extends VideoClass{
    private Array<Subtitle> subtitles;

    public PremiumVideoClass(String ID, int duration, String URL, String publisher,
                             String title, String lang, String subtitleUrl, String subtitleLang) {
        super(ID, duration, URL, publisher, title, lang);
        this.subtitles = new ArrayClass<>();
        addSubtitle(subtitleUrl, subtitleLang);
    }

    public void addSubtitle(String subtitleUrl,String subtitleLang) {
        subtitles.insertLast(new SubtitleClass(subtitleUrl,subtitleLang));
    }

    public Iterator<Subtitle> subtitleIterator() {
        return subtitles.iterator();
    }
}
