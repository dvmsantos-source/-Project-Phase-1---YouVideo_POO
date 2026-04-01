package YouVideo;

public class VideoClass implements VideoAll{
    private String ID;
    private int duration;
    private String URL;
    private String publisher;

    public VideoClass(String ID,int duration, String URL,String publisher){
        this.ID = ID;
        this.duration = duration;
        this.URL = URL;
        this.publisher = publisher;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof Video))
            return false;
        if (ID == null)
            return false;
        return this.ID.equals( ((Video) other).getId());
    }
}
