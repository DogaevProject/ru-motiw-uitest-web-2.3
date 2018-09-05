package ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards;

import com.codeborne.selenide.SelenideElement;
import ru.motiw.web.elements.elementsweb.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditTasksElements;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.DocRegisterCards;
import ru.motiw.web.steps.BaseSteps;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static ru.motiw.utils.ElementUtil.setOfValuesOnTheList;

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
        setOfValuesOnTheList($(byXpath(String.format("(//span[contains(text(),'%s')]/../..//..//div[contains(@id,'combo')])[4]", taskTypeForConsideration))),
                getCollectionOfListItems(), taskTypeValue.getTheTypeOfTaskToReviewAndExecutionOfDocument());
        return this;
    }

}
