import java.util.Scanner;

public class Main {

    private static final String EXIT = "EXIT";
    private static final String HELP = "HELP";
    private static final String CREATEPUBLISHABLE = "CREATEPUBLISHABLE";
    private static final String CREATEPREMIUM = "CREATEPREMIUM";
    private static final String ADDSUBTITLE = "ADDSUBTITLE";
    private static final String GETVIDEO = "GETVIDEO";
    private static final String SUBTITLES = "SUBTITLES";
    private static final String CREATEPODCAST = "CREATEPODCAST";
    private static final String ADDEPISODE = "ADDEPISODE";
    private static final String GETPODCAST = "GETPODCAST";
    private static final String EPISODES = "EPISODES";
    private static final String AUTHORPODCASTS = "AUTHORPODCASTS";
    private static final String REMOVEPODCAST = "REMOVEPODCAST";
    private static final String CREATESHOW = "CREATESHOW";
    private static final String GETSHOW = "GETSHOW";
    private static final String REMOVESHOW = "REMOVESHOW";
    private static final String REMOVEVIDEO = "REMOVEVIDEO";


    private static final String MSG_DEFAULT = "Unknown command." +
            "Type help to see available commands";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String cmd = null;
        cmd = in.next().toUpperCase();
        do {
            switch (cmd) {
                case CREATEPUBLISHABLE -> createPublishable();
                case CREATEPREMIUM -> createPremium();
                case ADDSUBTITLE -> addSubtitle();
                case GETVIDEO -> getVideo();
                case SUBTITLES -> subtitles();
                case CREATEPODCAST -> createPodcast();
                case ADDEPISODE -> addEpisode();
                case GETPODCAST -> getPodcast();
                case EPISODES -> episodes();
                case AUTHORPODCASTS -> authorPodcasts();
                case REMOVEPODCAST -> removePodcast();
                case CREATESHOW -> createShow();
                case GETSHOW -> getShow();
                case REMOVESHOW -> removeShow();
                case REMOVEVIDEO -> removeVideo();
                case HELP -> help();
                case EXIT -> System.out.println("Bye");
                default -> System.out.println(MSG_DEFAULT);
            }

        }
        while (!cmd.equals(EXIT));
    }


    private static void help() {
    }

    private static void createPublishable() {

    }

    private static void createPremium() {

    }

    private static void addSubtitle() {

    }

    private static void getVideo() {

    }

    private static void subtitles() {

    }

    private static void createPodcast() {

    }

    private static void addEpisode() {

    }

    private static void getPodcast() {

    }

    private static void episodes() {

    }

    private static void authorPodcasts() {

    }

    private static void removePodcast() {

    }

    private static void createShow() {

    }

    private static void getShow() {

    }

    private static void removeShow() {

    }

    private static void removeVideo() {

    }
}


