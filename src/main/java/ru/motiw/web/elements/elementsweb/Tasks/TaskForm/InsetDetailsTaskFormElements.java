package ru.motiw.web.elements.elementsweb.Tasks.TaskForm;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Элементы формы - ЗАДАЧА - вкладка Дополнительно
 */
public class InsetDetailsTaskFormElements {

    @FindBy(xpath = "//li[contains (@id, 'additional')]//span[contains (@class, 'strip')]/img")
    private SelenideElement additionalTab;

    @FindBy(xpath = "//*[contains (@id, 'iswithreport')]")
    private SelenideElement checkboxWithReport;

    @FindBy(xpath = "//*[contains (@id, 'issecret')]")
    private SelenideElement checkboxSecretTask;

    @FindBy(xpath = "//*[contains (@id, 'only_for_view')]")
    private SelenideElement checkboxOnlyForView;


    /**
     * Вкладка Дополнительно
     */
    public SelenideElement getAdditionalTab() {
        return additionalTab;
    }

    /**
     * C докладом
     */
    public SelenideElement getCheckboxWithReport() {
        return checkboxWithReport;
    }

    /**
     * Секретная задача
     */
    public SelenideElement getCheckboxSecretTask() {
        return checkboxSecretTask;
    }

    /**
     * Только для ознакомления
     */
    public SelenideElement getCheckboxOnlyForView() {
        return checkboxOnlyForView;
    }
}
