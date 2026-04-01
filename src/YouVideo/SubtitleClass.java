package YouVideo;

public class SubtitleClass implements Subtitle{
    private String lang;
    private String URL;

    public SubtitleClass (String lang, String URL){
        this.lang = lang;
        this.URL = URL;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public boolean equals() {
        return false;
    }
}
