import YouVideo.*;
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
    private static final String MSG_DEFAULT =
            "Unknown command. Type help to see available commands.";
    private static final String MSG_INVALID_VALUE = "Invalid value.";
    private static final String MSG_INVALID_LANGUAGE = "Invalid language type.";
    private static final String MSG_VIDEO_ALREADY_EXIST = "Video with this ID already exists.";
    private static final String MSG_VIDEO_CREATED = "Video %s created successfully.\n";
    private static final String MSG_VIDEO_PREMIUM_CREATED =
            "PREMIUM Video %s created successfully.\n";
    private static final String MSG_INVALID_LANGUAGE_SUBTITLE =
            "Invalid language type in subtitle.";
    private static final String MSG_VIDEO_NOT_EXIST = "Video does not exist.";
    private static final String MSG_SUBTITLE_ADDED = "Subtitle added successfully.";
    private static final String MSG_REQUIRES_PREMIUM_VIDEO =
            "This operation requires a Premium video.";
    private static final String MSG_PUBLISHABLE_VIDEO_NOT_EXIST =
            "Publishable Video %s does not exist.\n";
    private static final String MSG_GET_PREMIUM_VIDEO = "PREMIUM Video %s %d Title: %s\n";
    private static final String MSG_GET_BASIC_VIDEO = "Video %s %d Title: %s\n";
    private static final String MSG_GET_VIDEO = "File: %s Publisher: %s Language: %s\n";
    private static final String MSG_VIDEO_SUBTITLES = "Subtitles for video %s:\n";
    private static final String MSG_VIDEO_NOT_PREMIUM = "No Premium Video with ID.";
    private static final String MSG_PODCAST_ALREADY_EXIST =
            "Podcast with this title already exists.";
    private static final String MSG_PODCAST_CREATED = "Podcast created successfully.";
    private static final String MSG_PODCAST_NOT_EXIST = "Podcast does not exist.";
    private static final String MSG_EPISODE_ADDED = "Episode added successfully.";
    private static final String MSG_EPISODE_ALREADY_EXIST =
            "Episode ID already exists in the system.";
    private static final String MSG_DATA_EPISODE_INVALIDA =
            "Episode date must be >= than latest episode date.";
    private static final String MSG_GET_PODCAST = "Podcast: %s Author: %s Language: %s\n";
    private static final String MSG_LAST_EPISODE_DATE = "Latest episode date: %s\n";
    private static final String MSG_PODCAST_EPISODES = "Episodes for podcast %s:\n";
    private static final String MSG_EPISODES = "Episode %s: %d min Date: %s\n" +
            "URL: %s\n";
    private static final String MSG_PODCAST_AUTHOR = "Podcasts by author %s:\n";
    private static final String MSG_PODCAST = "Podcast: %s Author: %s Language: %s\n";
    private static final String MSG_PODCAST_NOT_FOUND = "No podcasts found for this author.";

    private static final String MSG_EMPTY_PODCAST = "No episodes available for this podcast.";
    private static final String MSG_PODCAST_REMOVED = "Podcast removed successfully.";

    private static final String MSG_VIDEO_FOR_SHOW_NOT_EXIST = "Video for show does not exist.";
    private static final String MSG_SHOW_ALREADY_EXIST = "Show with this title already exists.";
    private static final String MSG_SHOW_CREATED = "Show created successfully.";
    private static final String MSG_SHOW_NOT_EXIST = "Show does not exist.";
    private static final String MSG_GET_SHOW = "Show Date: %s Author: %s\n" +
            "Video: %s\n";
    private static final String MSG_SHOW_REMOVED = "Show removed successfully.";

    public static final String MSG_VIDEO_REMOVED = "Video removed successfully.";

    public static final String MSG_ERROR_VIDEO_IS_EPISODE = "Cannot remove: video is an episode of a podcast.";
    public static final String MSG_ERROR_VIDEO_IN_SHOW = "Cannot remove: video is used in a show.";



    public static void main(String[] args) {
        Locale.setDefault(Locale.of("EN","GB" ));
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
                case CREATEPODCAST -> createPodcast(in, platformSystem);
                case ADDEPISODE -> addEpisode(in, platformSystem);
                case GETPODCAST -> getPodcast(in, platformSystem);
                case EPISODES -> episodes(in, platformSystem);
                case AUTHORPODCASTS -> authorPodcasts(in, platformSystem);
                case REMOVEPODCAST -> removePodcast(in,platformSystem);
                case CREATESHOW -> createShow(in, platformSystem);
                case GETSHOW -> getShow(in, platformSystem);
                case REMOVESHOW -> removeShow(in, platformSystem);
                case REMOVEVIDEO -> removeVideo(in, platformSystem);
                case HELP -> help();
                case EXIT -> System.out.println(MSG_EXIT);
                default -> executeDefault(in);
            }
        } while (!cmd.equals(EXIT));
    }

    private static void executeDefault(Scanner in) {
        System.out.println(MSG_DEFAULT);
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

    private static Locale convert(String lang){
        return new Locale(lang);
    }

    private static boolean validLanguage(Locale lang) {
        String[] isoLanguages = Locale.getISOLanguages();
        for (int i = 0; i < isoLanguages.length; i++) {
            Locale isoLanguage = Locale.of(isoLanguages[i].toUpperCase());
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
        } else if (!validLanguage(convert(lang))) {
            System.out.println(MSG_INVALID_LANGUAGE);
        } else if (platformSystem.hasPublishable(id)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {
            platformSystem.addPublishable(id, duration, URL, publisher, title, convert(lang));
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
        } else if (!validLanguage(convert(lang))) {
            System.out.println(MSG_INVALID_LANGUAGE);
        } else if (!validLanguage(convert(subtitleLang))) {
            System.out.println(MSG_INVALID_LANGUAGE_SUBTITLE);
        } else if (platformSystem.hasPublishable(id)) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        } else {
            platformSystem.addPremiumPublishable(id, duration, URL, publisher, title,
                    convert(lang), subtitleUrl, convert(subtitleLang));
            System.out.printf(MSG_VIDEO_PREMIUM_CREATED, id);
        }
    }

    private static void addSubtitle(Scanner in, PlatformSystem platformSystem) {
        String id = in.next().trim();
        String subtitleUrl = in.nextLine().trim();
        String subtitleLang = in.nextLine().trim().toUpperCase();
        if (!validLanguage(convert(subtitleLang))) {
            System.out.println(MSG_INVALID_LANGUAGE_SUBTITLE);
        } else if (!platformSystem.hasPublishable(id)) {
            System.out.println(MSG_VIDEO_NOT_EXIST);
        } else if (!platformSystem.IsPremiumVideo(id)) {
            System.out.println(MSG_REQUIRES_PREMIUM_VIDEO);
        } else {
            platformSystem.addSubtitle(id, subtitleUrl, convert(subtitleLang));
            System.out.println(MSG_SUBTITLE_ADDED);
        }
    }

    private static void getVideo(Scanner in, PlatformSystem platformSystem) {
        String id = in.nextLine().trim();
        if (!platformSystem.hasPublishable(id) || platformSystem.isEpisode(id)) {
            System.out.printf(MSG_PUBLISHABLE_VIDEO_NOT_EXIST, id);
        } else {
            PublishableVideo video = (PublishableVideo) platformSystem.getVideo(id);
            if (platformSystem.IsPremiumVideo(video.getId())) {
                System.out.printf(MSG_GET_PREMIUM_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle());
            } else {
                System.out.printf(MSG_GET_BASIC_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle());
            }
            System.out.printf(MSG_GET_VIDEO, video.getUrl(), video.getPublisher(),
                    video.getLang().getDisplayLanguage().toUpperCase());
        }
    }

    private static void subtitles(Scanner in, PlatformSystem platformSystem) {
        String id = in.nextLine().trim();
        if (platformSystem.hasPublishable(id)) {
            if (!platformSystem.IsPremiumVideo(id)) {
                System.out.println(MSG_VIDEO_NOT_PREMIUM);
            } else {
                PublishableVideo video = (PublishableVideo) platformSystem.getVideo(id);
                System.out.printf(MSG_VIDEO_SUBTITLES, video.getTitle());
                Iterator<Subtitle> it = platformSystem.subtitleIterator(id);
                while (it.hasNext()) {
                    Subtitle subtitle = it.next();
                    System.out.printf("- %s (%s)\n", subtitle.URL(),subtitle.lang().getDisplayLanguage().toUpperCase());
                }
            }
        } else {
            System.out.println(MSG_VIDEO_NOT_PREMIUM);
        }
    }

    private static void createPodcast(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        String author = in.nextLine().trim();
        String lang = in.nextLine();

        if (!validLanguage(convert(lang))){
            System.out.println(MSG_INVALID_LANGUAGE);
        }
        else if (platformSystem.hasPodcast(title)){
            System.out.println(MSG_PODCAST_ALREADY_EXIST);
        } else {
            platformSystem.addPodcast(title, author, convert(lang));
            System.out.println(MSG_PODCAST_CREATED);
        }
    }

    private static void addEpisode(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        String id = in.next().trim();
        int duration = in.nextInt();
        String URL = in.next().trim();
        in.nextLine();
        String date = in.nextLine();
        if (duration <= 0) {
            System.out.println(MSG_INVALID_VALUE);
        } else if (!platformSystem.hasPodcast(title)) {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        } else if (platformSystem.hasEpisode(id)) {
            System.out.println(MSG_EPISODE_ALREADY_EXIST);
        } else if (!platformSystem.isValidEpisodeDate(title, date)) {
            System.out.println(MSG_DATA_EPISODE_INVALIDA);
        } else {
            platformSystem.addEpisode(title, id, duration, URL, date);
            System.out.println(MSG_EPISODE_ADDED);
        }
    }

    private static void getPodcast(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        if (platformSystem.hasPodcast(title)) {
            Podcast podcast = platformSystem.getPodcast(title);
            if (podcast.isEmpty()) {
                System.out.printf(MSG_GET_PODCAST,
                         podcast.getTitle(), podcast.getAuthor(), podcast.getLang());
            } else {
                System.out.printf(MSG_GET_PODCAST,
                        podcast.getTitle(), podcast.getAuthor(), podcast.getLang());
                System.out.printf(MSG_LAST_EPISODE_DATE, podcast.getLastEpDate());
            }
        } else {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
    }

    private static void episodes(Scanner in, PlatformSystem platformSystem) {
        String podcastTitle = in.nextLine().trim();
        if (platformSystem.hasPodcast(podcastTitle)) {
            Podcast podcast = platformSystem.getPodcast(podcastTitle);
            if (!podcast.isEmpty()) {
                System.out.printf(MSG_PODCAST_EPISODES, podcastTitle);
                Iterator<Episode> it = platformSystem.episodeIterator(podcastTitle);
                while (it.hasNext()) {
                    Episode ep = it.next();
                    System.out.printf(MSG_EPISODES, ep.getId(), ep.getDuration(),
                            ep.getDate(), ep.getUrl());
                }
            } else {
                System.out.println(MSG_EMPTY_PODCAST);
            }
        } else {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
    }

    private static void authorPodcasts(Scanner in, PlatformSystem platformSystem) {
        String authorName = in.nextLine().trim();
        Iterator<Podcast> it = platformSystem.authorPodcast(authorName);
        if (!it.hasNext()){
            System.out.println(MSG_PODCAST_NOT_FOUND);
        }
        else {
            System.out.printf(MSG_PODCAST_AUTHOR, authorName);
            while (it.hasNext()){
                Podcast pd = it.next();
                System.out.printf(MSG_PODCAST, pd.getTitle(), pd.getAuthor(), pd.getLang());
            }
        }
    }

    private static void removePodcast(Scanner in,PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        if (!platformSystem.hasPodcast(title)){
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
        else {
            platformSystem.removePodcast(title);
            System.out.println(MSG_PODCAST_REMOVED);
        }
    }

    private static void createShow(Scanner in, PlatformSystem platformSystem) {
        String showAuthor = in.nextLine().trim();
        String videoID = in.next().trim();
        String date = in.nextLine().trim();
        if (!platformSystem.hasPublishable(videoID)) {
            System.out.println(MSG_VIDEO_FOR_SHOW_NOT_EXIST);
        } else {
            PublishableVideo video = (PublishableVideo) platformSystem.getVideo(videoID);
            if (platformSystem.hasShow(video.getTitle())) {
                System.out.println(MSG_SHOW_ALREADY_EXIST);
            } else {
                platformSystem.addShow(showAuthor, videoID, date);
                System.out.println(MSG_SHOW_CREATED);
            }
        }
    }

    private static void getShow(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        if (!platformSystem.hasShow(title)) {
            System.out.println(MSG_SHOW_NOT_EXIST);
        } else {
            Show show = platformSystem.getShow(title);
            System.out.printf(MSG_GET_SHOW, show.getDate(), show.getAuthor(),
                    show.getTitle());
        }
    }

    private static void removeShow(Scanner in, PlatformSystem platformSystem) {
        String showTitle = in.nextLine().trim();
        if (!platformSystem.hasShow(showTitle)) {
            System.out.println(MSG_SHOW_NOT_EXIST);
        } else {
            platformSystem.removeShow(showTitle);
            System.out.println(MSG_SHOW_REMOVED);
        }
    }

    private static void removeVideo(Scanner in, PlatformSystem platformSystem) {
        String videoID = in.nextLine().trim();
        if (!platformSystem.hasPublishable(videoID)) {
            System.out.println(MSG_VIDEO_NOT_EXIST);
        }
        else if (platformSystem.isEpisode(videoID)) {
            System.out.println(MSG_ERROR_VIDEO_IS_EPISODE);
        }
        else if (platformSystem.isInShow(videoID)) {
            System.out.println(MSG_ERROR_VIDEO_IN_SHOW);
        }
        else {
            platformSystem.removeVideo(videoID);
            System.out.println(MSG_VIDEO_REMOVED);
        }
    }
}

