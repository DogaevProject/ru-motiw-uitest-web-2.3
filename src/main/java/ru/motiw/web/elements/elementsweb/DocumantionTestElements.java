package ru.motiw.web.elements.elementsweb;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/*
*Элементы на странице Руководств
 */

public  class DocumantionTestElements {

    @FindBy(xpath = "//embed[@type='application/pdf']")
    private SelenideElement checkPdfManualPage;

    @FindBy(xpath = "//p/a[contains(@href,  'pdf')]")
    private ElementsCollection allReferenceToPdf;

    @FindBy(xpath = "//*[text()=\"Далее\" or text()=\"1 Глоссарий\" or text()=\"Вперед →\"]") //ссылка с текстом "Далее" во html-фрейме на большенстве страницах или ссылка с текстом "1 Глоссарий" на странице "Термины и определения".
    private SelenideElement checkHtmlManualPage;


    @FindBy(xpath = "//p/a[contains(@href,  'html')]")
    private ElementsCollection allReferenceToHtml;

    @FindBy(xpath = "//frame[@name='hmcontent']")
    private SelenideElement frameHtml;



    //наличе элемента отвечающего за отображение pdf
    public  SelenideElement getPdfPage() {
        return  checkPdfManualPage;
    }

    //Все ссылки на pdf-мануалы.
    public ElementsCollection getAllReferenceToPdfManual() {
        return allReferenceToPdf;
    }

    //наличе элемента  и текста, который отображается на странице html-мануала
    public SelenideElement getHtmlPage() {
        return  checkHtmlManualPage;
    }
    //Находим все ссылки на Html-мануалы.
    public ElementsCollection getAllReferenceToHtmlManual() {
        return allReferenceToHtml;
    }


    public SelenideElement getFrameHtml(){
        return frameHtml;
    }


}

