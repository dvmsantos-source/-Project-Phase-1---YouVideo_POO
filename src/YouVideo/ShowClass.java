package YouVideo;

class ShowClass implements Show {
    private String author;
    private PublishableVideo video;
    private String date;
    private String title;

    public ShowClass(String author, PublishableVideo video, String date) {
        this.author = author;
        this.video = video;
        this.date = date;
        this.title = getVideoTitle();
    }

    public ShowClass(String title) {
        this.title = title;
    }

    private String getVideoTitle() {
        return video.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object other) {
        if (other== null)
            return false;
        if (this == other)
            return true;
        if (video.getTitle() == null)
            return false;
        if (!(other instanceof Show))
            return false;
        return this.title.equalsIgnoreCase(((Show)other).getTitle());
    }
}