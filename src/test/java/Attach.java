
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.ByteArrayInputStream;

public class Attach {

    public static void screenshotAs(String name) {
        byte[] bytes = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(bytes));
    }

    public static void pageSource() {
        String source = WebDriverRunner.source();
        Allure.addAttachment("Page source", "text/html", source, ".html");
    }

    public static void browserConsoleLogs() {
        try {
            String logs = String.join("\n",
                    WebDriverRunner.getWebDriver().manage().logs().get("browser").getAll()
                            .stream().map(Object::toString).toList()
            );
            Allure.addAttachment("Browser console logs", "text/plain", logs, ".log");
        } catch (Exception e) {
            Allure.addAttachment("Browser console logs", "text/plain",
                    "Console logs are not available: " + e, ".log");
        }
    }

    public static void addVideo() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (!(driver instanceof RemoteWebDriver)) return;

        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        String videoHost = System.getProperty("videoHost", "https://selenoid.autotests.cloud");
        String videoUrl = videoHost + "/video/" + sessionId + ".mp4";

        String html = "<html><body>" +
                "<video width='100%' height='100%' controls autoplay>" +
                "<source src='" + videoUrl + "' type='video/mp4'></video>" +
                "</body></html>";

        Allure.addAttachment("Video", "text/html", html, ".html");
    }
}
