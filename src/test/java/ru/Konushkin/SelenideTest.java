package ru.Konushkin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selectors.withText;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

    private static final String REPOSITORY = "maxkonushkin/Homework8";
    private static final int ISSUE = 2;

    @BeforeAll
    public static void init(){
        Configuration.baseUrl = "https://github.com";
    }

    @BeforeEach
    public void initEach(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    public void testIssueSearch(){
        open("");
        $(".search-input").click();
        $("#query-builder-test").sendKeys("maxkonushkin/Homework8");
        $("#query-builder-test").submit();
        $(linkText("maxkonushkin/Homework8")).click();
        $("#issues-tab").click();
        $(withText("#2")).should(Condition.exist);
    }

    @Test
    public void testLambdaStep() {
        step("Открываем главную страницу", () -> {
            open("");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").sendKeys(REPOSITORY);
            $("#query-builder-test").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с номером " + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(Condition.exist);
        });

    }

    @Test
    public void testAnnotatedStep() {
        WebSteps steps = new WebSteps();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE);

    }
}



