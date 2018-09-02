package ru.motiw.testsWeb;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.TextReport;
import com.codeborne.selenide.testng.annotations.Report;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.motiw.data.BaseTest;
import ru.motiw.data.listeners.ScreenShotOnFailListener;
import ru.motiw.web.steps.Administration.DocumentionTestSteps;
import ru.motiw.web.steps.Home.InternalSteps;
import ru.motiw.web.steps.Login.LoginStepsSteps;

import static com.codeborne.selenide.Selenide.page;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;
import static ru.motiw.web.steps.Administration.DocumentionTestSteps.goToURLManuals;


@Listeners({ScreenShotOnFailListener.class, TextReport.class, VideoListener.class})
@Report
public class DocumantionTest extends BaseTest {

    private LoginStepsSteps loginPageSteps;
    private InternalSteps internalPage;
    private DocumentionTestSteps documantionTestSteps;

    @BeforeClass
    public void beforeTest() {
        loginPageSteps = page(LoginStepsSteps.class);
        internalPage = page(InternalSteps.class);
        documantionTestSteps = page(DocumentionTestSteps.class);
    }

    @Video(name = "Отсутствует Документация")
    @Test(priority = 1)
    public void CheckManuals() throws Exception {
        loginPageSteps.loginAs(ADMIN);
        assertThat("Check that the displayed menu item 8 (Logo; Tasks; Documents; Messages; Calendar; Library; Tools; Details)",
                internalPage.hasMenuUserComplete()); // Проверяем отображение п.м. на внутренней странице
        assertTrue(loginPageSteps.isLoggedIn());

        // Переход в раздел Справка/Руководства
        goToURLManuals();
        //Проверка всех Руководств
        documantionTestSteps.checkManuals();


    }
}