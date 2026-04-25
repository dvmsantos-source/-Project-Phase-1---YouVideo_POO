package YouVideo;

import java.util.Locale;

/**
 * Represents a subtitle associated with a premium video.
 * A subtitle is defined by its language and the URL of the subtitle file.
 * @param lang the language of this subtitle.
 * @param URL the URL where the subtitle file is stored.
 */
public record Subtitle(Locale lang, String URL) {
}
