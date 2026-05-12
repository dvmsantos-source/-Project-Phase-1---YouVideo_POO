import YouVideo.*;

import Exceptions.*;

import java.util.*;

/**
 * @Author Djeison Santos (75651) and Victor Rocha (75645)
 */

public class Main {
    // Constants defining messages for the user
    private static final String MSG_EXIT =
            "Bye!";
    private static final String MSG_DEFAULT =
            "Unknown command. Type help to see available commands.";
    private static final String MSG_INVALID_VALUE =
            "Invalid value.";
    private static final String MSG_INVALID_LANGUAGE =
            "Invalid language type.";
    private static final String MSG_INVALID_LANGUAGE_SUBTITLE =
            "Invalid language type in subtitle.";
    private static final String MSG_VIDEO_ALREADY_EXIST =
            "Video with this ID already exists.";
    private static final String MSG_VIDEO_CREATED =
            "Video %s created successfully.\n";
    private static final String MSG_VIDEO_PREMIUM_CREATED =
            "PREMIUM Video %s created successfully.\n";
    private static final String MSG_VIDEO_NOT_EXIST =
            "Video does not exist.";
    private static final String MSG_SUBTITLE_ADDED =
            "Subtitle added successfully.";
    private static final String MSG_REQUIRES_PREMIUM_VIDEO =
            "This operation requires a Premium video.";
    private static final String MSG_PUBLISHABLE_VIDEO_NOT_EXIST =
            "Publishable Video %s does not exist.\n";
    private static final String MSG_GET_PREMIUM_VIDEO =
            "PREMIUM Video %s %d Title: %s\n";
    private static final String MSG_GET_BASIC_VIDEO =
            "Video %s %d Title: %s\n";
    private static final String MSG_GET_VIDEO =
            "File: %s Publisher: %s Language: %s\n";
    private static final String MSG_VIDEO_SUBTITLES =
            "Subtitles for video %s:\n";
    private static final String MSG_SUBTITLE_LINE =
            "- %s (%s)\n";
    private static final String MSG_VIDEO_NOT_PREMIUM =
            "No Premium Video with ID.";
    private static final String MSG_PODCAST_ALREADY_EXIST =
            "Podcast with this title already exists.";
    private static final String MSG_PODCAST_CREATED =
            "Podcast created successfully.";
    private static final String MSG_PODCAST_NOT_EXIST =
            "Podcast does not exist.";
    private static final String MSG_EPISODE_ADDED =
            "Episode added successfully.";
    private static final String MSG_EPISODE_ALREADY_EXIST =
            "Episode ID already exists in the system.";
    private static final String MSG_DATA_EPISODE_INVALID =
            "Episode date must be >= than latest episode date.";
    private static final String MSG_GET_PODCAST =
            "Podcast: %s Author: %s Language: %s\n";
    private static final String MSG_LAST_EPISODE_DATE =
            "Latest episode date: %s\n";
    private static final String MSG_PODCAST_EPISODES =
            "Episodes for podcast %s:\n";
    private static final String MSG_EPISODES =
            "Episode %s: %d min Date: %s\n" +
                    "URL: %s\n";
    private static final String MSG_EMPTY_PODCAST =
            "No episodes available for this podcast.";
    private static final String MSG_AUTHOR_PODCASTS =
            "Podcasts by author %s:\n";
    private static final String MSG_PODCAST =
            "Podcast: %s Author: %s Language: %s\n";
    private static final String MSG_PODCAST_NOT_FOUND =
            "No podcasts found for this author.";
    private static final String MSG_PODCAST_REMOVED =
            "Podcast removed successfully.";
    private static final String MSG_VIDEO_FOR_SHOW_NOT_EXIST =
            "Video for show does not exist.";
    private static final String MSG_SHOW_ALREADY_EXIST =
            "Show with this title already exists.";
    private static final String MSG_SHOW_CREATED =
            "Show created successfully.";
    private static final String MSG_SHOW_NOT_EXIST =
            "Show does not exist.";
    private static final String MSG_GET_SHOW =
            "Show Date: %s Author: %s\n" +
                    "Video: %s\n";
    private static final String MSG_TAG = "%s\n";

    private static final String MSG_TITLE_NOT_EXIST = "Title does not exist";
    private static final String MSG_TITLE_ALREADY_TAGGED = "Title is already tagged with %s.\n";
    private static final String MSG_SHOW_REMOVED =
            "Show removed successfully.";
    private static final String MSG_VIDEO_REMOVED =
            "Video removed successfully.";
    private static final String MSG_ERROR_VIDEO_IS_EPISODE =
            "Cannot remove: video is an episode of a podcast.";
    private static final String MSG_ERROR_VIDEO_IN_SHOW =
            "Cannot remove: video is used in a show.";

    private static final String MSG_AUTHOR_SHOWS_HEADER = "Shows by author %s:\n";
    private static final String MSG_AUTHOR_SHOWS_FORMAT = "Date: %s Show: %s Duration: %d Language: %s\n";
    private static final String MSG_NO_SHOWS_FOUND = "No shows found for this author.";

    // --- TAGS COMMANDS CONSTANTS ---
    private static final String MSG_TAGS = "Tags:";
    private static final String MSG_TAG_ADDED =
            "Tag added successfully.";
    private static final String MSG_TITLE_NOT_TAGGED =
            "Title is not tagged with %s.\n";
    private static final String MSG_TAG_REMOVED =
            "Tag removed successfully.";

    // --- AUTHORS PRODUCTIVITY COMMAND CONSTANTS ---
    private static final String MSG_AUTHORS_PRODUCTIVITY_HEADER =
            "Authors productivity:\n";
    private static final String MSG_AUTHOR_PRODUCTIVITY_LINE =
            "%s with %d contributions.\n";
    private static final String MSG_NO_PRODUCTIVE_AUTHORS =
            "No productive authors.";

    // --- TAGGED COMMAND CONSTANTS ---
    private static final String MSG_TAGGED_HEADER =
            "Content tagged with %s in %s order:\n";
    private static final String MSG_TAGGED_PODCAST =
            "Podcast Title: %s Author: %s\n";
    private static final String MSG_TAGGED_SHOW =
            "Show Title: %s Author: %s\n";
    private static final String MSG_NO_CONTENT_TAGGED =
            "No content tagged with %s.\n";
    private static final String MSG_INVALID_TAGGED_PARAMS =
            "Invalid tagged parameters.";
    /**
     * Initialises the platform and starts the command read-eval loop.
     * Sets the default locale to British English so that language display
     * names are generated consistently across all environments.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("EN", "GB"));
        Scanner in = new Scanner(System.in);
        PlatformSystem platformSystem = new PlatformSystemClass();
        processCommands(platformSystem, in);
        in.close();
    }

    private static Command getCommand(Scanner in) {
        try {
            String comm = in.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    private static void processCommands(PlatformSystem platformSystem, Scanner in) {
        Command cmd;
        do {
            cmd = getCommand(in);
            executeCommand(in, cmd, platformSystem);
        } while (!cmd.equals(Command.EXIT));
    }

    /**
     * Reads and dispatches commands from standard input until the user types exit.
     * Each token on the input is uppercased and matched against the known commands.
     * Unknown tokens produce a default error message.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void executeCommand(Scanner in, Command cmd, PlatformSystem platformSystem) {
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
            case REMOVEPODCAST -> removePodcast(in, platformSystem);
            case CREATESHOW -> createShow(in, platformSystem);
            case GETSHOW -> getShow(in, platformSystem);
            case REMOVESHOW -> removeShow(in, platformSystem);
            case REMOVEVIDEO -> removeVideo(in, platformSystem);
            case ADDTAG -> addTag(in, platformSystem);
            case AUTHORSHOWS -> authorShows(in, platformSystem);
            case REMOVETAG -> removeTag(in,platformSystem);
            case HELP -> help();
            case EXIT -> System.out.println(MSG_EXIT);
            default -> System.out.println(MSG_DEFAULT);
        }
    }





    /**
     * Prints the list of all available commands to standard output.
     */
    private static void help() {
        Command.executeHelp();
    }

    /**
     * Converts a two-letter language code string into a Locale object.
     *
     * @param lang the ISO 639-1 two-letter language code.
     * @return the corresponding Locale.
     */
    private static Locale convert(String lang) {
        return Locale.of(lang);
    }

    /**
     * Verifies if the given Locale corresponds to a valid ISO 639-1 language.
     *
     * @param lang the Locale to validate.
     */
    private static void isValidLanguage(Locale lang)
            throws InvalidLanguageException {
        String[] isoLanguages = Locale.getISOLanguages();
        for (String language : isoLanguages) {
            Locale isoLanguage = Locale.of(language);
            if (isoLanguage.equals(lang)) {
                return;
            }
        }
        throw new InvalidLanguageException();
    }

    private static int durationValue(Scanner in) throws InvaldValueException{
        int duration = in.nextInt();
        if (duration <= 0) throw new InvaldValueException();
        return duration;
    }
    /**
     * Verifies if the given Locale corresponds to a valid ISO 639-1 language.
     *
     * @param lang the Locale to validate.
     */
    private static void isValidSubtitle(Locale lang)
            throws InvalidSubtitleLanguageException {
        String[] isoLanguages = Locale.getISOLanguages();
        for (String language : isoLanguages) {
            Locale isoLanguage = Locale.of(language);
            if (isoLanguage.equals(lang)) {
                return;
            }
        }
        throw new InvalidSubtitleLanguageException();
    }

    /**
     * Executes the createpublishable command.
     * Reads the video id, duration, URL, publisher, title, and language from input,
     * validates them in this order, and creates a basic publishable video if all are valid.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void createPublishable(Scanner in, PlatformSystem platformSystem) {
        try {
            String id = in.next();
            int duration = durationValue(in);
            String URL = in.next().trim();
            in.nextLine();
            String publisher = in.nextLine();
            String title = in.nextLine().trim();
            String lang = in.next().toUpperCase();
            isValidLanguage(convert(lang));
            platformSystem.addPublishable(id, duration, URL, publisher, title, convert(lang));
            System.out.printf(MSG_VIDEO_CREATED, id);
        } catch (InvalidLanguageException e) {
            System.out.println(MSG_INVALID_LANGUAGE);
        } catch (InvaldValueException e) {
            System.out.println(MSG_INVALID_VALUE);
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
        } catch (PublishableAlreadyExistsException e) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        }
    }


    /**
     * Executes the createpremium command.
     * Reads the video id, duration, URL, publisher, title, primary language,
     * subtitle URL, and subtitle language from input, validates them in this order,
     * and creates a premium video if all are valid.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void createPremium(Scanner in, PlatformSystem platformSystem) {
        try {
            String id = in.next().trim();
            int duration = durationValue(in);
            String URL = in.next().trim();
            in.nextLine();
            String publisher = in.nextLine();
            String title = in.nextLine().trim();
            String lang = in.nextLine().trim().toUpperCase();
            String subtitleUrl = in.nextLine();
            String subtitleLang = in.nextLine().toUpperCase();
            isValidLanguage(convert(lang));
            isValidSubtitle(convert(subtitleLang));
            platformSystem.addPremiumPublishable(id, duration, URL, publisher, title,
                    convert(lang), subtitleUrl, convert(subtitleLang));
            System.out.printf(MSG_VIDEO_PREMIUM_CREATED, id);
        } catch (InvalidLanguageException e) {
            System.out.println(MSG_INVALID_LANGUAGE);
        } catch (InvalidSubtitleLanguageException e) { //verificar se precisa de duas classes
            System.out.println(MSG_INVALID_LANGUAGE_SUBTITLE);
        } catch (InvaldValueException e) {
            System.out.println(MSG_INVALID_VALUE);
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
        } catch (PublishableAlreadyExistsException e) {
            System.out.println(MSG_VIDEO_ALREADY_EXIST);
        }

    }

    /**
     * Executes the addsubtitle command.
     * Reads the video id, subtitle URL, and subtitle language from input,
     * validates them in this order, and adds the subtitle if all are valid.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void addSubtitle(Scanner in, PlatformSystem platformSystem) {
        try {
            String id = in.next().trim();
            String subtitleUrl = in.nextLine().trim();
            String subtitleLang = in.nextLine().trim().toUpperCase();
            isValidLanguage(convert(subtitleLang));
            platformSystem.addSubtitle(id, subtitleUrl, convert(subtitleLang));
            System.out.println(MSG_SUBTITLE_ADDED);
        } catch (InvalidLanguageException e) {
            System.out.println(MSG_INVALID_LANGUAGE_SUBTITLE);
        } catch (PublishableAlreadyExistsException e) {
            System.out.println(MSG_VIDEO_NOT_EXIST);
        } catch (NotAPremiumVideoException e) {
            System.out.println(MSG_REQUIRES_PREMIUM_VIDEO);
        }

    }

    /**
     * Executes the getvideo command.
     * Reads a video id and prints its full details. Episodes are not considered
     * publishable videos and produce an error message.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void getVideo(Scanner in, PlatformSystem platformSystem) {
        String id = in.nextLine().trim();
        try {
            PublishableVideo video = (PublishableVideo) platformSystem.getVideo(id);
            if (video.isPremium()) {
                System.out.printf(MSG_GET_PREMIUM_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle());
            } else {
                System.out.printf(MSG_GET_BASIC_VIDEO,
                        video.getId(), video.getDuration(), video.getTitle());
            }
            System.out.printf(MSG_GET_VIDEO, video.getUrl(), video.getPublisher(),
                    video.getLang().getDisplayLanguage().toUpperCase());
        } catch (PublishableNotExistsException | IsEpisodeException e) {
            System.out.printf(MSG_PUBLISHABLE_VIDEO_NOT_EXIST, id);
        }
    }
    /**
     * Executes the removevideo command.
     * Reads a video id and removes the publishable video from the system.
     * Episodes and videos currently used in shows cannot be removed.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void removeVideo(Scanner in, PlatformSystem platformSystem) {
        try {
            String videoID = in.nextLine().trim();
            platformSystem.removeVideo(videoID);
            System.out.println(MSG_VIDEO_REMOVED);
        } catch (PublishableNotExistsException e) {
            System.out.println(MSG_VIDEO_NOT_EXIST);
        } catch (IsEpisodeException e) {
            System.out.println(MSG_ERROR_VIDEO_IS_EPISODE);
        } catch (VideoUsedInShowException e) {
            System.out.println(MSG_ERROR_VIDEO_IN_SHOW);
        }
    }

    /**
     * Executes the subtitles command.
     * Reads a video id and lists all its subtitles in insertion order.
     * Produces an error if the video does not exist or is not a premium video.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void subtitles(Scanner in, PlatformSystem platformSystem) {
        try {
            String id = in.nextLine().trim();
            PublishableVideo video = (PublishableVideo) platformSystem.getVideo(id);
            Iterator<Subtitle> it = platformSystem.subtitleIterator(id);
            System.out.printf(MSG_VIDEO_SUBTITLES, video.getTitle());
            while (it.hasNext()) {
                Subtitle subtitle = it.next();
                System.out.printf(MSG_SUBTITLE_LINE, subtitle.URL(),
                        subtitle.lang().getDisplayLanguage().toUpperCase());
            }
        } catch (PublishableNotExistsException | NotAPremiumVideoException
                 | IsEpisodeException e) {
            System.out.println(MSG_VIDEO_NOT_PREMIUM);
        }
    }

    /**
     * Handles the createpodcast command.
     * Reads the podcast title, author, and language from input,
     * validates them in this order, and creates the podcast if all are valid.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void createPodcast(Scanner in, PlatformSystem platformSystem) {
        try {
            String title = in.nextLine().trim();
            String author = in.nextLine().trim();
            String lang = in.nextLine().toUpperCase();
            isValidLanguage(convert(lang));
            platformSystem.addPodcast(title, author, convert(lang));
            System.out.println(MSG_PODCAST_CREATED);
        } catch (InvalidLanguageException e) {
            System.out.println(MSG_INVALID_LANGUAGE);
        } catch (PodcastAlreadyExistsException e) {
            System.out.println(MSG_PODCAST_ALREADY_EXIST);
        }
    }

    /**
     * Executes the addepisode command.
     * Reads the podcast title, episode id, duration, URL, and release date from input,
     * validates them in this order, and adds the episode if all are valid.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void addEpisode(Scanner in, PlatformSystem platformSystem) {
        try {
            String title = in.nextLine().trim();
            String id = in.next().trim();
            int duration = durationValue(in);
            String URL = in.next().trim();
            in.nextLine();
            String date = in.nextLine();
            platformSystem.addEpisode(title, id, duration, URL, date);
            System.out.println(MSG_EPISODE_ADDED);
        } catch (InvaldValueException e) {
            System.out.println(MSG_INVALID_VALUE);
            in.nextLine();
            in.nextLine();
        } catch (PodcastNotExistsException e) {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        } catch (EpisodeAlreadyExistsException e) {
            System.out.println(MSG_EPISODE_ALREADY_EXIST);
        } catch (InvalidEpisodeDateException e) {
            System.out.println(MSG_DATA_EPISODE_INVALID);

        }
    }

    /**
     * Executes the getpodcast command.
     * Reads a podcast title and prints its details, including the most recent
     * episode date if the podcast has at least one episode.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void getPodcast(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        try {
            Podcast podcast = platformSystem.getPodcast(title);

            if (podcast.isEmpty() || ((Tag)podcast).isTagsEmpty()) {
                System.out.printf(MSG_GET_PODCAST,
                        podcast.getTitle(), podcast.getAuthor(),
                        podcast.getLang().getLanguage().toUpperCase());
            } else {
                System.out.printf(MSG_GET_PODCAST,
                        podcast.getTitle(), podcast.getAuthor(),
                        podcast.getLang().getLanguage().toUpperCase());
                System.out.printf(MSG_LAST_EPISODE_DATE, podcast.getLastEpDate());
                Iterator <String> it = ((Tag)podcast).tagsIterator();
                System.out.println(MSG_TAGS);
                while (it.hasNext()){
                    System.out.printf(MSG_TAG,it.next());
                }

            }
        } catch (PodcastNotExistsException e) {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
    }

    /**
     * Executes the episodes command.
     * Reads a podcast title and lists all its episodes in reverse chronological order.
     * Produces an error if the podcast has no episodes.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void episodes(Scanner in, PlatformSystem platformSystem) {
        String podcastTitle = in.nextLine().trim();
        try {
            Podcast podcast = platformSystem.getPodcast(podcastTitle);
            if (podcast.isEmpty()) {
                System.out.println(MSG_EMPTY_PODCAST);
            } else {
                System.out.printf(MSG_PODCAST_EPISODES, podcastTitle);
                Iterator<Episode> it = platformSystem.episodeIterator(podcastTitle);
                while (it.hasNext()) {
                    Episode ep = it.next();
                    System.out.printf(MSG_EPISODES, ep.getId(), ep.getDuration(),
                            ep.getDate(), ep.getUrl());
                }
            }
        } catch (PodcastNotExistsException e) {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
    }

    /**
     * Executes the authorpodcasts command.
     * Reads an author name and lists all podcasts created by that author,
     * in order of insertion. Produces an error if no podcasts are found.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void authorPodcasts(Scanner in, PlatformSystem platformSystem) {
        try {
            String authorName = in.nextLine().trim();
            Iterator<Podcast> it = platformSystem.authorPodcast(authorName);
            System.out.printf(MSG_AUTHOR_PODCASTS, authorName);
            while (it.hasNext()) {
                Podcast pd = it.next();
                    System.out.printf(MSG_PODCAST, pd.getTitle(), pd.getAuthor(),
                            pd.getLang().getLanguage().toUpperCase());
                }

        } catch (PodcastNotFoundException e) {
            System.out.println(MSG_PODCAST_NOT_FOUND);
        }
    }

    /**
     * Executes the removepodcast command.
     * Reads a podcast title and removes the podcast and all its episodes from the system.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void removePodcast(Scanner in, PlatformSystem platformSystem) {
        try {
            String title = in.nextLine().trim();
            platformSystem.removePodcast(title);
            System.out.println(MSG_PODCAST_REMOVED);
        } catch (PodcastNotExistsException e) {
            System.out.println(MSG_PODCAST_NOT_EXIST);
        }
    }

    /**
     * Executes the createshow command.
     * Reads the show author, video id, and transmission date from input,
     * validates them in this order, and creates the show if all are valid.
     * Episodes cannot be used in shows.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void createShow(Scanner in, PlatformSystem platformSystem) {
        try {
            String showAuthor = in.nextLine().trim();
            String videoID = in.next().trim();
            String date = in.nextLine().trim();
            platformSystem.addShow(showAuthor, videoID, date);
            System.out.println(MSG_SHOW_CREATED);
        } catch (PublishableNotExistsException e) {
            System.out.println(MSG_VIDEO_FOR_SHOW_NOT_EXIST);
        } catch (ShowAlreadyExistsException e) {
            System.out.println(MSG_SHOW_ALREADY_EXIST);
        }
    }

    /**
     * Executes the     show command.
     * Reads a show title and prints its transmission date, author, and video title.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void getShow(Scanner in, PlatformSystem platformSystem) {
        try {
            String title = in.nextLine().trim();
            Show show = platformSystem.getShow(title);
            if (((Tag)show).isTagsEmpty()) {
                System.out.printf(MSG_GET_SHOW, show.getDate(), show.getAuthor(),
                        show.getTitle());
            }
            else {
                System.out.printf(MSG_GET_SHOW, show.getDate(), show.getAuthor(), show.getTitle());
                Iterator<String> it = ((Tag)show).tagsIterator();
                System.out.println(MSG_TAGS);
                while (it.hasNext()){
                    System.out.printf(MSG_TAG,it.next());
                }
            }

        } catch (ShowNotExistsException e) {
            System.out.println(MSG_SHOW_NOT_EXIST);
        }
    }

    /**
     * Executes the removeshow command.
     * Reads a show title and removes the show from the system.
     * The underlying video is not affected.
     *
     * @param in             the scanner reading from standard input.
     * @param platformSystem the platform system to interact with.
     */
    private static void removeShow(Scanner in, PlatformSystem platformSystem) {
        try {
            String showTitle = in.nextLine().trim();
            platformSystem.removeShow(showTitle);
            System.out.println(MSG_SHOW_REMOVED);
        } catch (ShowNotExistsException e) {
            System.out.println(MSG_SHOW_NOT_EXIST);
        }
    }

    private static void authorShows(Scanner in, PlatformSystem platformSystem) {
        try {
            String author = in.nextLine().trim();
            Iterator<Show> it = platformSystem.authorShows(author);

            System.out.printf(MSG_AUTHOR_SHOWS_HEADER, author);

            while (it.hasNext()) {
                Show sw = it.next();
                System.out.printf(MSG_AUTHOR_SHOWS_FORMAT,
                        sw.getDate(),
                        sw.getTitle(),
                        sw.getDuration(),
                        sw.getLanguage().getLanguage().toUpperCase());
            }

        } catch (NoShowsFoundForTheAuthorException e) {
            System.out.println(MSG_NO_SHOWS_FOUND);
        }
    }



    private static void addTag(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        String tag = in.nextLine().trim();
        try {

            platformSystem.addTag(title, tag);

            System.out.println(MSG_TAG_ADDED);

        } catch (TitleDoesNotExistException e) {
            System.out.println(MSG_TITLE_NOT_EXIST);
        } catch (TitleAlreadyTaggedException e) {
            System.out.printf(MSG_TITLE_ALREADY_TAGGED, tag);
        }
    }



    private static void removeTag(Scanner in, PlatformSystem platformSystem) {
        String title = in.nextLine().trim();
        String tag = in.nextLine().trim();
        try {
            platformSystem.removeTag(title, tag);
            System.out.println(MSG_TAG_REMOVED);

        } catch (TitleDoesNotExistException e) {
            System.out.println(MSG_TITLE_NOT_EXIST);
        } catch (TitleIsNotTaggedException e) {
            System.out.printf(MSG_TITLE_NOT_TAGGED, tag);
        }
    }

}

