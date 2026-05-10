package YouVideo;

public enum Command {
    CREATEPUBLISHABLE ( "createpublishable - creates a new publishable video"),
    CREATEPREMIUM(  "createpremium - creates a new publishable Premium video"),
    ADDSUBTITLE ( "addsubtitle - adds subtitle to Premium video"),
    GETVIDEO("getvideo - presents publishable video data from its id"),
    SUBTITLES ("subtitles - Lists Premium video subtitles"),
    CREATEPODCAST("createpodcast - creates a new podcast with no episodes"),
    ADDEPISODE("addepisode - adds an episode to a podcast"),
    GETPODCAST("getpodcast - presents podcast data from its title"),
    EPISODES("episodes - List podcast episodes"),
    AUTHORPODCASTS("authorpodcasts - List all podcasts of an author"),
    REMOVEPODCAST("removepodcast - removes a podcast"),
    CREATESHOW("createshow - creates show using an existing publishable video"),
    GETSHOW("getshow - presents show data from its title"),
    REMOVESHOW("removeshow - removes a show"),
    REMOVEVIDEO( "removevideo - removes a publishable video"),
    HELP("help - shows the available commands"),
    EXIT("exit - terminates the execution of the program"),
    ///NEW CMD
    AUTHORSHOWS("authorshows - List all shows of an author"),
    AUTHORSPRODUCTIVITY("authorsproductivity - List authors by their productivity"),
    ADDTAG("addtag - adds a tag to a show or podcast"),
    REMOVETAG("removetag - removes a tag from a show or podcast"),
    TAGGED("tagged - List content tagged with a given tag"),
    UNKNOWN("");


    private String msgHelp;

    Command(String s) {
        msgHelp = s;
    }

    public String getMsg() {
        return msgHelp;
    }

    public static void executeHelp() {
        Command[] help = Command.values();
        for (int i = 0; i < help.length; i++){
            System.out.println(help[i].getMsg());
        }
    }
}
