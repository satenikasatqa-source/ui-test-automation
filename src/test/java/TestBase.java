import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://demoqa.com");
        Configuration.timeout = Long.parseLong(System.getProperty("timeout", "60000"));

        String remote = System.getProperty("remote");

        if (remote != null && !remote.isBlank()) {
            // REMOTE (Selenoid)
            Configuration.remote = remote;

            MutableCapabilities capabilities = new MutableCapabilities();

            LoggingPreferences logs = new LoggingPreferences();
            logs.enable(LogType.BROWSER, Level.ALL);
            capabilities.setCapability("goog:loggingPrefs", logs);

            capabilities.setCapability("selenoid:options",
                    Map.of("enableVNC", true, "enableVideo", true));

            Configuration.browserCapabilities = capabilities;

        } else {
            // LOCAL — headless (если хочешь, тоже сделаем параметром)
            Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

            MutableCapabilities capabilities = new MutableCapabilities();
            capabilities.setCapability("goog:chromeOptions", Map.of(
                    "args", List.of(
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--window-size=1920,1080"
                    )
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        Selenide.closeWebDriver();
    }
}