package ru.motiw.testsWeb.DocflowAdministration;

import com.codeborne.selenide.testng.TextReport;
import com.codeborne.selenide.testng.annotations.Report;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.motiw.data.dataproviders.DocflowAdministration;
import ru.motiw.data.listeners.ScreenShotOnFailListener;
import ru.motiw.web.model.Administration.Directories.Directories;
import ru.motiw.web.model.Administration.TasksTypes.TasksTypes;
import ru.motiw.web.model.Administration.Users.Department;
import ru.motiw.web.model.Administration.Users.Employee;
import ru.motiw.web.model.DocflowAdministration.DictionaryEditor.DictionaryEditor;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.DocRegisterCards;
import ru.motiw.web.model.Document.Document;
import ru.motiw.web.steps.Administration.Directories.DirectoriesEditSteps;
import ru.motiw.web.steps.Administration.TasksTypes.TaskTypesEditSteps;
import ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditGeneralSteps;
import ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.GridDocRegisterCardsSteps;
import ru.motiw.web.steps.Home.InternalSteps;
import ru.motiw.web.steps.Login.LoginStepsSteps;

import static com.codeborne.selenide.Selenide.page;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;
import static ru.motiw.web.steps.Administration.Directories.DirectoriesSteps.goToURLDirectories;
import static ru.motiw.web.steps.Administration.TasksTypes.TaskTypesSteps.goToURLTaskTypes;
import static ru.motiw.web.steps.DocflowAdministration.DictionaryEditorSteps.goToURLDictionaryEditor;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditConnectedRoutesSteps.routesTabRCD;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditFieldsSteps.fieldsTabRCD;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditGeneralSteps.generalTabRCD;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditRightsSteps.rightsTabRCD;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditTasksSteps.tasksTabRCD;
import static ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditTemplateSteps.TemplateTabRCD;


@Listeners({ScreenShotOnFailListener.class, TextReport.class})
@Report
public class DocumentRegistrationCardsTest extends DocflowAdministration {

    private LoginStepsSteps loginPageSteps;
    private InternalSteps internalPage;
    private TaskTypesEditSteps formEditTaskTypes;

    @BeforeClass
    public void beforeTest() {
        loginPageSteps = page(LoginStepsSteps.class);
        internalPage = page(InternalSteps.class);
        formEditTaskTypes = page(TaskTypesEditSteps.class);
    }


    @Test(priority = 1, dataProvider = "objectDataRCD", dataProviderClass = DocflowAdministration.class)
    public void verifyCreateRegCardDocumentAllFields(Department[] departments, Employee[] employees, Directories directories, TasksTypes tasksTypes, DictionaryEditor dictionaryEditor,
                                                     DocRegisterCards registerCards, Document document) throws Exception {
        loginPageSteps.loginAs(ADMIN);
        assertThat("Check that the displayed menu item 8 (Logo; Tasks; Documents; Messages; Calendar; Library; Tools; Details)",
                internalPage.hasMenuUserComplete()); // Проверяем отображение п.м. на внутренней странице

        // ---------------- ТИПЫ ЗАДАЧ
        goToURLTaskTypes().addObjectTaskTypeList(tasksTypes.getObjectTypeName());

        formEditTaskTypes.addAllFieldsTaskTypeList(tasksTypes)
                .saveChangesOnObject(tasksTypes);

        // ---------------- СПРАВОЧНИКИ
        goToURLDirectories().addObjectTaskTypeList(directories.getObjectTypeName());  // Добавляем объект - Справочник

        DirectoriesEditSteps directoriesEditSteps = new DirectoriesEditSteps().directoriesEditPage();

        assertTrue("check display the form directories editing", directoriesEditSteps.loadedDirectoriesEditPage());

        // Добавляем поля и сохранеяем объект
        directoriesEditSteps.addAllFieldsTaskTypeList(directories).saveChangesOnObject(directories);

        // ----------------Редактор словарей
        goToURLDictionaryEditor().addDictionaryEditor(dictionaryEditor);

        // ----------------РКД
        FormDocRegisterCardsEditGeneralSteps editRCD = GridDocRegisterCardsSteps
                .goToURLGridDocRegisterCards().addDocRegisterCards(); // Добавить РКД

        /*
         * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - ПОЛЯ
         */
         fieldsTabRCD().addFieldsDocRegisterCards(registerCards);  // TODO при запуске подряд DocumentRegistrationCardsTest и CreateDocumentTest, почему-то здесь валится. Если отдельно запустить - проходит. Повторяется в 2.2

        /*
         * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - ОБЩЕЕ
         */
        generalTabRCD().addNameDocumentRegisterCards(registerCards)
                .directionOfDisplacementOfTheDate(registerCards) // Направление смещения даты при попадании на нерабочее время
                .defaultSettingsWhenYouSendTheDocumentBackForRevision(registerCards) // Настройки по умолчанию при отправке документа на доработку
                .statusOfTheDocumentLifeCycle(registerCards) // Статусы документа
                .openFilesForEditDocument(registerCards) // Открывать файлы для редактирования
                .setAutoCalculationNumeratorFields(registerCards); // Автоматическое вычисление полей-нумераторов

        /*
        * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - ШАБЛОНЫ ОТОБРАЖЕНИЯ
        * */
        TemplateTabRCD().displayNameTemplate(registerCards);

        /*
         * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - ПРАВА
         */


        rightsTabRCD().accessesRCD(registerCards)
                .setSettingsChangingTheSignOfFinalVersion(registerCards)
                .setSettingsStatusEditYourDocuments(registerCards)
                .setSettingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(registerCards)
                .setCreationOfLinkedDocuments(registerCards);


        /*
         * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - МАРШРУТЫ
         */
        routesTabRCD().checkBoxUseAllPossibleRoutes(registerCards);

        /*
         * Устанавливаем настройки для РКД (регистрационная карточка документа) на вкладке - ЗАДАЧИ
         */
        tasksTabRCD().setCopyingFieldsWhenCreatingTask(registerCards)
                .setSettingsFieldsDocumentContaining(registerCards)
                .toSetTheTypeOfReviewTasks(registerCards, "Тип задачи по рассмотрению") // Тип задачи по рассмотрению документа
                .toSetTheTypeOfReviewTasks(registerCards, "Тип задачи по исполнению"); // Тип задачи по рассмотрению документа
        GridDocRegisterCardsSteps gridDocRegisterCards = editRCD.saveAllChangesInDoc(); // Сохранение всех изменений в РКД
        gridDocRegisterCards.verifyDocRegisterCards(registerCards.getDocRegisterCardsName());

        internalPage.logout(); // Выход из системы
        assertTrue(loginPageSteps.isNotLoggedIn());
    }


}