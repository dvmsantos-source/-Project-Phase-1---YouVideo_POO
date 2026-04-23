package YouVideo;

class ShowClass implements Show {
    private String author;
    private String date;
    private String title;

    public ShowClass(String author, String date, String title) {
        this.author = author;
        this.date = date;
        this.title = title;
    }

    public ShowClass(String title) {
        this.title = title;
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
        if (title == null)
            return false;
        if (!(other instanceof Show))
            return false;
        return this.title.equalsIgnoreCase(((Show)other).getTitle());
    }
}