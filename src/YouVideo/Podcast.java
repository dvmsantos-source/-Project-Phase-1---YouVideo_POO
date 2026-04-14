package YouVideo;

import java.util.Locale;

public interface Podcast {
    Locale getLang();

    String getTitle();

    String getAuthor();

    String getLastEpDate();

    boolean isEmpty();
}
