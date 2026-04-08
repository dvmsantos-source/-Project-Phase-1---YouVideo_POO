import YouVideo.PlatformSystem;
import YouVideo.PlatformSystemClass;
import YouVideo.Subtitle;
import YouVideo.Video;
import dataStructures.Iterator;

import java.util.Locale;
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

    private static final String CMD_CREATE_PUBLISHABLE =
            "createpublishable - creates a new publishable video";
    private static final String CMD_CREATE_PREMIUM =
            "createpremium - creates a new publishable Premium video";
    private static final String CMD_ADD_SUBTITLE =
            "addsubtitle - adds subtitle to Premium video";
    private static final String CMD_GET_VIDEO =
            "getvideo - presents publishable video data from its id";
    private static final String CMD_SUBTITLES =
            "subtitles - Lists Premium video subtitles";
    private static final String CMD_CREATE_PODCAST =
            "createpodcast - creates a new podcast with no episodes";
    private static final String CMD_ADD_EPISODE =
            "addepisode - adds an episode to a podcast";
    private static final String CMD_GET_PODCAST =
            "getpodcast - presents podcast data from its title";
    private static final String CMD_EPISODES =
            "episodes - List podcast episodes";
    private static final String CMD_AUTHOR_PODCASTS =
            "authorpodcasts - List all podcasts of an author";
    private static final String CMD_REMOVE_PODCAST =
            "removepodcast - removes a podcast";
    private static final String CMD_CREATE_SHOW =
            "createshow - creates show using an existing publishable video";
    private static final String CMD_GET_SHOW =
            "getshow - presents show data from its title";
    private static final String CMD_REMOVE_SHOW =
            "removeshow - removes a show";
    private static final String CMD_REMOVE_VIDEO =
            "removevideo - removes a publishable video";
    private static final String CMD_HELP =
            "help - shows the available commands";
    private static final String CMD_EXIT =
            "exit - terminates the execution of the program";

    private static final String MSG_EXIT = "Bye!";
    private static final String MSG_DEFAULT = "Unknown command. Type help to see available commands";
    private static final String MSG_INVALID_VALUE = "Invalid value.";
    private static final String MSG_INVALIG_LANGUAGE = "Invalid language type.";
    private static final String MSG_VIDEO_ALREADY_EXIST = "Video with this ID already exists.";
    private static final String MSG_VIDEO_CREATED = "Video %s created successfully.\n";
    private static final String MSG_VIDEO_CREATED_PREMIUM =
            "PREMIUM Video %s created successfully.\n";
    private static final String MSG_INVALIG_LANGUAGE_SUBTITLE =
            "Invalid language type in subtitle.";
    private static final String VIDEO_NOT_EXIST = "Video does not exist.";
    private static final String SUBTITLE_ADDED = "Subtitle added successfully.";
    private static final String REQUIRES_PREMIUM_VIDEO =
            "This operation requires a Premium video.";
    private static final String PUBLISHABLE_VIDEO_NOT_EXIST =
            "Publishable Video %s does not exist.\n";
    private static final String GET_PREMIUM_VIDEO = "PREMIUM Video %s %d Title: %s\n";
    private static final String GET_BASIC_VIDEO = "Video %s %d Title: %s\n";
    private static final String GET_VIDEO = "File: %s Publisher: %s Language: %s\n";
    private static final String VIDEO_SUBTITLES = "Subtitles for video %s:\n";



    static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PlatformSystem platformSystem = new PlatformSystemClass();
        command(in, platformSystem);
        in.close();
    }

    private static void command(Scanner in, PlatformSystem platformSystem) {
        String cmd;
        do {
            cmd = in.next().trim().toUpperCase();
            switch (cmd) {
                case CREATEPUBLISHABLE -> createPublishable(in, platformSystem);
                case CREATEPREMIUM -> createPremium(in, platformSystem);
                case ADDSUBTITLE -> addSubtitle(in, platformSystem);
                case GETVIDEO -> getVideo(in, platformSystem);
                case SUBTITLES -> subtitles(in, platformSystem);
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
                    in.nextLine();
                }
            }
        }
        while (!cmd.equals(EXIT));

    }


    private static void help() {
        System.out.println(CMD_CREATE_PUBLISHABLE);
        System.out.println(CMD_CREATE_PREMIUM);
        System.out.println(CMD_ADD_SUBTITLE);
        System.out.println(CMD_GET_VIDEO);
        System.out.println(CMD_SUBTITLES);
        System.out.println(CMD_CREATE_PODCAST);
        System.out.println(CMD_ADD_EPISODE);
        System.out.println(CMD_GET_PODCAST);
        System.out.println(CMD_EPISODES);
        System.out.println(CMD_AUTHOR_PODCASTS);
        System.out.println(CMD_REMOVE_PODCAST);
        System.out.println(CMD_CREATE_SHOW);
        System.out.println(CMD_GET_SHOW);
        System.out.println(CMD_REMOVE_SHOW);
        System.out.println(CMD_REMOVE_VIDEO);
        System.out.println(CMD_HELP);
        System.out.println(CMD_EXIT);
    }


    private static boolean validLanguage(String lang) {
        String[] isoLanguages = Locale.getISOLanguages();
        for (int i = 0; i < isoLanguages.length; i++) {
            String isoLanguage = isoLanguages[i].toUpperCase();
            if (isoLanguage.equals(lang)) {
                return true;
            }
        }
        return false;
    }

    private static void createPublishable(Scanner in, PlatformSystem platformSystem) {
        String id = in.next();
        int duration = in.nextInt();
        String URL = in.next().trim();
        in.nextLine();
        String publisher = in.nextLine();
        String title = in.nextLine().trim();
        String lang = in.next().toUpperCase();

        if (duration <= 0) {
            System.out.println(MSG_INVALID_VALUE);
        } else if (!validLanguage(lang)) {
            System.out.println(MSG_INVALIG_LANGUAGE);
        } else if (platformSystem.hasPublishable(id)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {
            platformSystem.addPublishable(id, duration, URL, publisher, title, lang);
            System.out.printf(MSG_VIDEO_CREATED, id);
        }
    }

    private static void createPremium(Scanner in, PlatformSystem platformSystem) {
        String id = in.next().trim();
        int duration = in.nextInt();
        String URL = in.next().trim();
        in.nextLine();
        String publisher = in.nextLine();
        String title = in.nextLine().trim();
        String lang = in.nextLine().trim();
        String subtitleUrl = in.nextLine();
        String subtitleLang = in.nextLine();

        if (duration <= 0) {
            System.out.println(MSG_INVALID_VALUE);
        } else if (!validLanguage(lang) || !validLanguage(subtitleLang)) {
            System.out.println(MSG_INVALIG_LANGUAGE);
        } else if (platformSystem.hasPublishable(id)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {
            platformSystem.addPremiumPublishable(id, duration, URL, publisher, title,
                    lang, subtitleUrl, subtitleLang);
            System.out.printf(MSG_VIDEO_CREATED_PREMIUM, id);
        }
    }

    private static void addSubtitle(Scanner in, PlatformSystem platformSystem) {
        String id = in.next().trim();
        String subtitleUrl = in.next().trim();
        String subtitleLang = in.nextLine().trim().toUpperCase();
        if (!platformSystem.hasPublishable(id)) {
            System.out.println(VIDEO_NOT_EXIST);
        } else if (!platformSystem.IsPremiumVideo(id)) {
            System.out.println(REQUIRES_PREMIUM_VIDEO);
        } else if (!validLanguage(subtitleLang)) {
            System.out.println(MSG_INVALIG_LANGUAGE_SUBTITLE);
        } else {
            platformSystem.addSubtitle(id, subtitleUrl, subtitleLang);
            System.out.println(SUBTITLE_ADDED);
        }
    }

    private static void getVideo(Scanner in, PlatformSystem platformSystem) {
        String id = in.next(); in.nextLine();
        if (!platformSystem.hasPublishable(id)) {
            System.out.printf(PUBLISHABLE_VIDEO_NOT_EXIST, id);
        } else {
            Video video = platformSystem.getVideo(id);
            if (platformSystem.IsPremiumVideo(video.getId())) {
                System.out.printf(GET_PREMIUM_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle());
            } else {
                System.out.printf(GET_BASIC_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle() );
            }
            System.out.printf(GET_VIDEO, video.getUrl(), video.getPublisher(), video.getLang());
        }
    }

    private static void subtitles(Scanner in, PlatformSystem platformSystem) {
        String id = in.next(); in.nextLine();
        Video video = platformSystem.getVideo(id);
        System.out.printf(VIDEO_SUBTITLES, video.getTitle());
        Iterator<Subtitle> it = platformSystem.subtitleIterator(id);
        while (it.hasNext()) {
            Subtitle subtitle = it.next();
            System.out.printf("- %s (%s)\n", subtitle.getUrl(), subtitle.getLang());
        }
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


