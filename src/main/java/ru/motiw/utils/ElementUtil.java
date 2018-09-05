package ru.motiw.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Взаимодействие с элементами на стр.
 */
public abstract class ElementUtil {

    private static Actions action = new Actions(getWebDriver());

    /**
     * Метод выбора настроек, смещение на позиции вниз и выбор значения взависимости от передаваемого аргумента,
     * например, Обязательное - Обязательное при создании, Обязательное при завершении...;
     *
     * @param fieldActivation передаваемый локатор для активация элемента для последующего поиска значения из списка
     * @param collection      все элементы коллекции
     * @param textValue       передаваемый элемент для выбора его из коллекции
     */
    public static void offsetAndRangeOfValuesOnTheList(SelenideElement fieldActivation, ElementsCollection collection,
                                                       String textValue) {
        fieldActivation.click();
        try {
            for (SelenideElement e : collection) {
                if (e.getText().equals(textValue)) {
                    e.shouldBe(Condition.visible);
                    e.click();
                }
            }
        } catch (ElementNotFound e) {
            assertTrue("Actual error message: " + e.getMessage(),
                    e.getMessage().contains("Element list not found"));
        }

    }


    /**
     * Метод выбора значений из списка;
     *
     * @param fieldActivation передаваемый локатор для активация элемента для последующего поиска значения из списка
     * @param collection      все элементы коллекции
     * @param textValue       передаваемый элемент для выбора его из коллекции
     */

    public static void setOfValuesOnTheList(SelenideElement fieldActivation, ElementsCollection collection,
                                                          String textValue) {
        fieldActivation.click();
        try {
            for (SelenideElement e : collection) {
                if (e.getText().equals(textValue)) {
                    e.shouldBe(Condition.visible);
                    e.click();
                    break; //без break в случае большого списка значений в конт.меню происходил выбор найденного значения, а после этого продолжался цикл. Тест падает.
                }
            }
        } catch (ElementNotFound e) {
            assertTrue("Actual error message: " + e.getMessage(),
                    e.getMessage().contains("Element list not found"));
        }

    }



    /**
     * Имитации нажатия правой кнопки мыши. Клик осуществляется в центр элемента и
     * ожидание появляющегося элемента
     *
     * @param element               передаваемая переменная для взаимодействия
     * @param elementWaitVisibility передаваемая переменная (элемент DOM) для взаимодействия, ожидание
     *                              появления данного элемента
     */
    public static void contextClickAction(SelenideElement element, SelenideElement elementWaitVisibility) {
        action.contextClick(element).perform();
        elementWaitVisibility.shouldBe(Condition.visible);
    }

    /**
     * Кликнуть по невидимому элементу с помощью javascript
     *
     * @param element переменная для взаимодействия
     */
    public static void clickOnInvisibleElement(SelenideElement element) {

        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);";

        ((JavascriptExecutor) getWebDriver()).executeScript(script, element);
    }

    /**
     * Attempts to click on an element multiple times (to avoid stale element
     * exceptions caused by rapid DOM refreshes)
     *
     * @param d  The WebDriver
     * @param by By element locator
     */
    public static void dependableClick(WebDriver d, By by) {
        final int MAXIMUM_WAIT_TIME = 10;
        final int MAX_STALE_ELEMENT_RETRIES = 5;

        WebDriverWait wait = new WebDriverWait(d, MAXIMUM_WAIT_TIME);
        int retries = 0;
        while (true) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(by)).click();
                return;
            } catch (StaleElementReferenceException e) {
                if (retries < MAX_STALE_ELEMENT_RETRIES) {
                    retries++;
                    continue;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * Скроллинг к элементу вниз и выбор (сlick) данного элемента из списка
     * <p/>
     * пример - scrollToAndClick()
     *
     * @param locator элемент к к-му необходимо проскроллировать список
     */
    public static void scrollToAndClick(String locator) {
        SelenideElement element = $(By.xpath(locator));
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView();"
                , element);
        sleep(200);
        element.click();
    }

    /**
     * Метод реализующий взаиодействие с полем ввода типа file, предварительно делая его видимым
     *
     * @param driver
     * @param element элемент для взаимодействия
     */
    public static void unHide(WebDriver driver, WebElement element) {
        String script = "arguments[0].style.opacity=1;"
                + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
                + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    /**
     * Прикрепление файла
     * example - WebDriver driver = new FirefoxDriver();
     * <p>
     * driver.get("http://blueimp.github.io/jQuery-File-Upload/basic.html");
     * fileAttachmentToAnInvisibleField(driver, By.id("fileupload"), "C:\\temp\\image.png");
     *
     * @param driver  инстанс драйвера
     * @param locator передаваемый locator поля ввода типа file
     * @param file    путь к файлу
     */
    public static void fileAttachmentToAnInvisibleField(WebDriver driver, By locator, String file) {
        WebElement input = driver.findElement(locator);
        unHide(driver, input);
        input.sendKeys(file);
    }


}