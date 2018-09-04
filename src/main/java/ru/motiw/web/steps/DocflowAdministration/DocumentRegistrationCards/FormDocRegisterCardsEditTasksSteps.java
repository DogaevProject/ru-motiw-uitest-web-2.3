package ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import ru.motiw.web.elements.elementsweb.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditTasksElements;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.DocRegisterCards;
import ru.motiw.web.steps.BaseSteps;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Форма редактирвоания РКД - вкладка ЗАДАЧИ
 */
public class FormDocRegisterCardsEditTasksSteps extends BaseSteps {

    private static FormDocRegisterCardsEditTasksElements formEditRCDTasksElement = page(FormDocRegisterCardsEditTasksElements.class);

    /**
     * Копирование полей при создании задачи
     */
    private FormDocRegisterCardsEditTasksSteps copyingFieldsWhenCreatingTask(String valueIdentifiers) {
        if (valueIdentifiers == null) {
            return this;
        } else {
            formEditRCDTasksElement.getCopyingFieldsWhenCreatingTask().clear();
            formEditRCDTasksElement.getCopyingFieldsWhenCreatingTask().setValue(valueIdentifiers);
        }
        return this;
    }

    /**
     * Вводим значение в поле - Поля документа, содержащие...
     * Авторов задач
     * Контролеров задач
     * Ответственных руководителей задач
     * Исполнителей задач
     *
     * @param valueEnteredInTheSetup вводимое значение в настроку поля
     * @param fieldSettings          выбираемое поле - настройка (Авторов, Контролеров и т.д..)
     * @return FormDocRegisterCardsEditTasksSteps
     */
    private FormDocRegisterCardsEditTasksSteps settingsFieldsDocumentContaining(String valueEnteredInTheSetup,
                                                                                SelenideElement fieldSettings) {
        if (valueEnteredInTheSetup == null) {
            return this;
        } else {
            fieldSettings.clear();
            fieldSettings.setValue(valueEnteredInTheSetup);
        }
        return this;
    }

    /**
     * Метод выбора задач по рассмотрению из списка;
     *
     * @param fieldActivation передаваемый локатор для активация элемента для последующего поиска значения из списка
     * @param collection      все элементы коллекции
     * @param textValue       передаваемый элемент для выбора его из коллекции
     */

    private static void setOfValuesOnTheListOfReviewTasks(SelenideElement fieldActivation, ElementsCollection collection,
                                                       String textValue) {
        fieldActivation.click();
        try {
            for (SelenideElement e : collection) {
                if (e.getText().equals(textValue)) {
                    e.shouldBe(Condition.visible);
                    e.click();
                    break; //без break в случае большого списка значений в конт.меню происходил выбор найденного значения, а после этого продолжался цикл. Все падало.
                }
            }
        } catch (ElementNotFound e) {
            assertTrue("Actual error message: " + e.getMessage(),
                    e.getMessage().contains("Element list not found"));
        }

    }





    /**
     * Копирование полей при создании задачи
     *
     * @param copyingFields передаваемое значение - Идентификаторы полей Типа задач / Документа
     * @return FormDocRegisterCardsEditTasksSteps вкладка Задачи формы редактирования РКД
     */
    public FormDocRegisterCardsEditTasksSteps setCopyingFieldsWhenCreatingTask(DocRegisterCards copyingFields) {
        tasksTabRCD().
                copyingFieldsWhenCreatingTask(copyingFields.getCopyingFieldsWhenCreatingATask());
        return this;
    }

    /**
     * Устанавливаем настройку - Поля документа, содержащие...:
     *
     * @param settingsFields передаваемые значение настройки
     * @return FormDocRegisterCardsEditTasksSteps вкладка Задачи формы редактирования РКД
     */
    public FormDocRegisterCardsEditTasksSteps setSettingsFieldsDocumentContaining(DocRegisterCards settingsFields) {
        tasksTabRCD()
                .settingsFieldsDocumentContaining(settingsFields.getAuthorsObjectives(),
                        formEditRCDTasksElement.getAuthorsObjectives()) // авторов задач
                .settingsFieldsDocumentContaining(settingsFields.getControllersOfTasks(),
                        formEditRCDTasksElement.getControllersOfTasks()) // контролеров задач
                .settingsFieldsDocumentContaining(settingsFields.getDecisionMakersOfTasks(),
                        formEditRCDTasksElement.getDecisionMakersOfTasks()) // ответственных руководителей задач
                .settingsFieldsDocumentContaining(settingsFields.getExecutorsOfTasks(),
                        formEditRCDTasksElement.getExecutorsOfTasks()); // исполнителей задач
        return this;
    }

    /**
     * Производим выбор вкладки - ЗАДАЧИ
     *
     * @return FormDocRegisterCardsEditTasksSteps
     */
    public static FormDocRegisterCardsEditTasksSteps tasksTabRCD() {
        formEditRCDTasksElement.getTasksTab().click();
        return page(FormDocRegisterCardsEditTasksSteps.class);
    }

    /**
     * Тип задачи по рассмотрению / исполнению
     *
     * @param taskTypeValue            передаваемое значение - Тип задачи - для выбора из списка значений
     * @param taskTypeForConsideration значение для идентификации элемента настройки на странице
     * @return FormDocRegisterCardsEditTasksSteps
     */



    public FormDocRegisterCardsEditTasksSteps toSetTheTypeOfReviewTasks(DocRegisterCards taskTypeValue, String taskTypeForConsideration) {
        tasksTabRCD();
        setOfValuesOnTheListOfReviewTasks($(byXpath(String.format("(//span[contains(text(),'%s')]/../..//..//div[contains(@id,'combo')])[4]", taskTypeForConsideration))),
                getCollectionOfListItems(), taskTypeValue.getTheTypeOfTaskToReviewAndExecutionOfDocument());
        return this;
    }

}
