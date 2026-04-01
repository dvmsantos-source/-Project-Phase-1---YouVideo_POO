import YouVideo.PlatformSystem;
import YouVideo.PlatformSystemClass;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final String EXIT = "EXIT";
    private static final String HELP = "HELP";
    private static final String MSG_EXIT = "Bye!";
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
    private static final String MSG_INVALID_VALUE = "Invalid value.";
    private static final String MSG_INVALIG_LANGUAGE = "Invalid language type.";
    private static final String MSG_VIDEO_ALREADY_EXIST = "Video with this ID already exists";
    private static final String MSG_VIDEO_CREATED = "Video %s created successfully.\n";
    private static final String MSG_VIDEO_CREATED_PREMIUM = "PREMIUM Video %s created successfully.\n";
    private static final String MSG_INVALIG_LANGUAGE_SUBTITLE = "Invalid language type in subtitle";
    private static final String VIDEO_NOT_EXIST = "Video does not exist.";
    private static final String SUBTITLE_ADDED = "Subtitle added successfully";
    private static final String REQUIRES_PREMIUM_VIDEO = "This operation requires a Premium video";


    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PlatformSystem platformSystem = new PlatformSystemClass();
        command(input, platformSystem);


    }

    private static void command(Scanner input, PlatformSystem platformSystem) {
        String cmd = null;
        do {
            cmd = input.next().trim().toUpperCase();
            switch (cmd) {
                case CREATEPUBLISHABLE -> createPublishable(input, platformSystem);
                case CREATEPREMIUM -> createPremium(input, platformSystem);
                case ADDSUBTITLE -> addSubtitle(input, platformSystem);
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
                case EXIT -> System.out.println(MSG_EXIT);
                default -> {
                    System.out.println(MSG_DEFAULT);
                    input.nextLine();
                }
            }

        }
        while (!cmd.equals(EXIT));

    }


    private static void help() {
    }


    private static boolean validLanguage(String lang) {
        String[] isoLanguages = Locale.getISOLanguages();
        for (int i = 0; i < isoLanguages.length; i++) {
            if (isoLanguages[i].toUpperCase().equals(lang)) {
                return true;
            }
        }
        return false;

    }


    private static void createPublishable(Scanner input, PlatformSystem platformSystem) {
        String id = input.next();
        int duration = input.nextInt();
        String URL = input.next().trim();
        input.nextLine();

        String publisher = input.nextLine();
        String title = input.nextLine().trim();
        String lang = input.next().toUpperCase();

        if (duration <= 0) {
            System.out.println(MSG_INVALID_VALUE);
        } else if (!validLanguage(lang)) {
            System.out.println(MSG_INVALIG_LANGUAGE);
        } else if (platformSystem.hasPublishe(id, duration, URL, publisher)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {
            platformSystem.addPublishe(id, duration, URL, publisher, title, lang);
            System.out.printf(MSG_VIDEO_CREATED, id);

        }
    }

    private static void createPremium(Scanner input, PlatformSystem platformSystem) {
        String id = input.next().trim();
        int duration = input.nextInt();
        String URL = input.next().trim();
        input.nextLine();
        String publisher = input.nextLine();
        String title = input.nextLine().trim();

        String lang = input.nextLine().trim();
        String subtitleUrl = input.nextLine();
        String subtitleLang = input.nextLine();

        if (duration <= 0) {
            System.out.println(MSG_INVALID_VALUE);

        } else if (!validLanguage(lang) || !validLanguage(subtitleLang)) {
            System.out.println(MSG_INVALIG_LANGUAGE);

        } else if (platformSystem.hasPublishe(id, duration, URL, publisher)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {

            platformSystem.addPremiumPublishe(id, duration, URL, publisher, title,
                    lang, subtitleUrl, subtitleLang);
            System.out.printf(MSG_VIDEO_CREATED_PREMIUM, id);

        }


    }

    private static void addSubtitle(Scanner input, PlatformSystem platformSystem) {
        String id = input.next().trim();
        String subtitleUrl = input.next();
        String subtitleLang = input.nextLine().toUpperCase();
        if (!platformSystem.hasVideo(id, subtitleUrl, subtitleLang)) {
            System.out.println(VIDEO_NOT_EXIST);
        } else if (!platformSystem.IsPremiumVideo(id, subtitleUrl, subtitleLang)) {
            System.out.println(REQUIRES_PREMIUM_VIDEO);
        } else if (!validLanguage(subtitleLang)) {
            System.out.println(MSG_INVALIG_LANGUAGE_SUBTITLE);
        } else {
            platformSystem.addSubtitle(id, subtitleUrl, subtitleLang);
        }

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


