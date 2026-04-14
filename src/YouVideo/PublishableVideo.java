package YouVideo;

import java.util.Locale;

public interface PublishableVideo extends Video{
    String getTitle();

    String getPublisher();

    Locale getLang();
}
