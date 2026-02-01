import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaTests extends TestBase {

    @Test
    void shouldSubmitStudentRegistrationForm() {

        open("/automation-practice-form");

        $("#firstName").shouldBe(visible).setValue("Ivana");
        $("#lastName").setValue("Ivanova");
        $("#userEmail").setValue("tester@gmail.com");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("9876543210");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption("1980");
        $(".react-datepicker__day--006:not(.react-datepicker__day--outside-month)").click();

        $("#subjectsInput").scrollTo().setValue("Physics").pressEnter();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#currentAddress").setValue("Yerevan some street");
        $("#uploadPicture").uploadFromClasspath("ForDemoQaTests.jpeg");

        $("#state").scrollTo().click();
        $("#stateCity-wrapper").$(byText("NCR")).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText("Noida")).click();

        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $(".table-responsive table")
                .shouldHave(text("Ivana Ivanova"))
                .shouldHave(text("tester@gmail.com"))
                .shouldHave(text("Female"))
                .shouldHave(text("9876543210"))
                .shouldHave(text("06 January"))
                .shouldHave(text("1980"))
                .shouldHave(text("Physics"))
                .shouldHave(text("Reading"))
                .shouldHave(text("Yerevan some street"))
                .shouldHave(text("ForDemoQaTests.jpeg"))
                .shouldHave(text("NCR Noida"));
    }
}
