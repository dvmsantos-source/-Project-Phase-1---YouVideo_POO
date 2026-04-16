package YouVideo;

abstract class VideoClass implements VideoAll {
    private String ID;
    private int duration;
    private String URL;


    public VideoClass(String ID,int duration, String URL){
        this.ID = ID;
        this.duration = duration;
        this.URL = URL;

    }

    public VideoClass(String ID){
        this.ID = ID;

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
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof Video))
            return false;
        if (ID == null)
            return false;
        return this.ID.equalsIgnoreCase(((Video) other).getId());
    }
}
