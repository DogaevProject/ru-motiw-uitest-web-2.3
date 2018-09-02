package ru.motiw.web.steps.Administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.motiw.web.elements.elementsweb.DocumantionTestElements;
import ru.motiw.web.steps.BaseSteps;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static ru.motiw.utils.WindowsUtil.newWindowForm;
import static ru.motiw.utils.WindowsUtil.selectLinkOpenInNewTab;
import static ru.motiw.web.model.URLMenu.MANUALS;

/**
 * Руководства
 */
public class DocumentionTestSteps extends BaseSteps {

    private DocumantionTestElements documantionTestElements = page(DocumantionTestElements.class);


    /*
     *  Проверка Загрузки pdf-страницы
     * */
    private void ensurePageLoaded()  {
        documantionTestElements.getPdfPage().shouldBe(Condition.visible);
    }


    /*
     *  Проверка Загрузки html-страницы
     * */

    private void ensureHtmlPageLoaded() {
        documantionTestElements.getHtmlPage().shouldBe(Condition.visible);
    }



    /**
     * раздел: Справка - Руководства
     */
    public static DocumentionTestSteps goToURLManuals() {
        openSectionOnURL(MANUALS.getMenuURL());
        return page(DocumentionTestSteps.class);
    }



    /*
     *  Открываем руководство, Проверяем Загрузку pdf-страницы, Переход в раздел "Руководства"
     * */


    private void checkPdfManuals() {
        //проверяем кол-во ссылок на pdf-мануалы
        documantionTestElements.getAllReferenceToPdfManual().shouldHaveSize(14);
        String parentWindowHandler = getWebDriver().getWindowHandle(); //Запоминаем окно в котором находимся
        //все ссылки на pdf добавляем в список
        List<SelenideElement> elements = new ArrayList<>($$(documantionTestElements.getAllReferenceToPdfManual()));

        for (SelenideElement link:elements) {
            link.sendKeys(selectLinkOpenInNewTab);
            getWebDriver().switchTo().window(new WebDriverWait(getWebDriver(), 10).until(newWindowForm(By.xpath("//embed[@type='application/pdf']"))));
            ensurePageLoaded();
            closeWindow();
            getWebDriver().switchTo().window(parentWindowHandler); // Возращаемся в окно страницы /user/tab/user/manuals
            switchTo().frame($(By.cssSelector("#flow"))); //Переходим во фрейм на странице /user/tab/user/manuals

        }


    }


    private void checkHtmlManuals() {

        //проверяем кол-во ссылок на html-руководства
        documantionTestElements.getAllReferenceToHtmlManual().shouldHaveSize(14);
        String parentWindowHandler = getWebDriver().getWindowHandle(); //Запоминаем окно в котором находимся
        //все ссылки на html добавляем в список
        List<SelenideElement> elements = new ArrayList<>($$(documantionTestElements.getAllReferenceToHtmlManual()));

        for (SelenideElement link:elements) {
            link.sendKeys(selectLinkOpenInNewTab);
            ArrayList<String> tabs2 = new ArrayList<> (getWebDriver().getWindowHandles());
            switchTo().window(tabs2.get(1));

            {
                if (isElementPresent(By.xpath("//frame[@name='hmcontent']"))) {
                    getFrameObject($(documantionTestElements.getFrameHtml())); //  уходим во фрейм hmcontent
                    ensureHtmlPageLoaded();//Проверяем Загрузку html-страницы
                    //checkTextOnHtmlPage();   // Проверяем наличе текста, который отображается на страницах html-мануала
                }
                ensureHtmlPageLoaded(); // на страницах "Термины и определения" и "Описание таблиц базы данных" сразу проверяем загрузку, без перехода во фрейм (его там нет)
            }
            closeWindow();
            getWebDriver().switchTo().window(parentWindowHandler); // Возращаемся в окно страницы /user/tab/user/manuals
            switchTo().frame($(By.cssSelector("#flow"))); //Переходим во фрейм на странице /user/tab/user/manuals

        }



    }


    public void checkManuals() {
        checkPdfManuals();
        checkHtmlManuals();
    }


}