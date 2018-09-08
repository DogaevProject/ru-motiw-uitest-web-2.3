package ru.motiw.testsWeb.Administration;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.TextReport;
import com.codeborne.selenide.testng.annotations.Report;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.motiw.data.BaseTest;
import ru.motiw.data.listeners.ScreenShotOnFailListener;
import ru.motiw.web.model.Administration.Users.Department;
import ru.motiw.web.model.Administration.Users.Employee;
import ru.motiw.web.model.Administration.Users.Module;
import ru.motiw.web.steps.Administration.Users.UsersSteps;
import ru.motiw.web.steps.Home.InternalSteps;
import ru.motiw.web.steps.Login.LoginStepsSteps;
import ru.motiw.web.steps.Login.RestorePasswordSteps;

import static com.codeborne.selenide.Selenide.page;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;
import static ru.motiw.data.dataproviders.Administration.getRandomDepartment;
import static ru.motiw.data.dataproviders.Administration.getRandomEmployer;
import static ru.motiw.web.steps.Administration.Users.DepartmentSteps.goToURLDepartments;

@Listeners({ScreenShotOnFailListener.class, TextReport.class, VideoListener.class})
@Report
public class UsersTest extends BaseTest {

    private LoginStepsSteps loginPageSteps;
    private InternalSteps internalPageSteps;
    private UsersSteps userPageSteps;
    private RestorePasswordSteps restorePasswordSteps;

    @BeforeClass
    public void beforeTest() {
        loginPageSteps = page(LoginStepsSteps.class);
        internalPageSteps = page(InternalSteps.class);
        userPageSteps = page(UsersSteps.class);
        restorePasswordSteps = page(RestorePasswordSteps.class);
    }

    /*
      * Инициализация - Подразделение
      */
    Department department1 = getRandomDepartment();
    Department department2 = getRandomDepartment();
    Department department2_1 = getRandomDepartment();
    Department department2_1_1 = getRandomDepartment();
    Department editDepartment = getRandomDepartment();
    Department department3 = getRandomDepartment();
    Department department2_2 = getRandomDepartment();

    /*
    * Инициализация переменных - Подразделение для создания пользователей
    */
    Department departmentUser = getRandomDepartment();
    Department departmentUser1 = getRandomDepartment();

    /*
     * Инициализация переменных - Пользователь
     */
    Employee user = getRandomEmployer()
            .setModule(Module.COMPLETE) // Право на модуль
            .setNeedsPasswordChange(false)
            .setDepartment(departmentUser);

    Employee editUser = getRandomEmployer()
            .setNeedsPasswordChange(false)
            .setModule(Module.COMPLETE)
            .setNeedsPasswordChange(true)
            .setDepartment(departmentUser); // Передаем подразделение того пользователя, к-го собираемся отредактировать

    Employee changepass = getRandomEmployer()
            .setNeedsPasswordChange(true) // Сменить пароль при следующем входе (false - оставляем признак без изменеиня)
            .setDepartment(departmentUser);

    Employee workflow = getRandomEmployer()
            .setLastName("WORKFLOW " + BaseTest.randomString(10))
            .setNeedsPasswordChange(false)
            .setModule(Module.WORKFLOW)
            .setDepartment(departmentUser1);

    Employee docflow = getRandomEmployer()
            .setLastName("DOCFLOW " + BaseTest.randomString(10))
            .setNeedsPasswordChange(false)
            .setModule(Module.DOCFLOW)
            .setDepartment(departmentUser1);

    @Video(name = "Редактирование подразделений (создание / удаление)")
    @Test(priority = 1)
    public void verifyCreatingAndRemovalDepartments() throws Exception {
        loginPageSteps.loginAs(ADMIN);
        assertThat("Check that the displayed menu item 8 (Logo; Tasks; Documents; Messages; Calendar; Library; Tools; Details)",
                internalPageSteps.hasMenuUserComplete()); // Проверяем отображение п.м. на внутренней странице
        assertTrue(loginPageSteps.isLoggedIn());

        // Инициализируем страницу и переходим на нее - Администрирование/Пользователи
        goToURLDepartments()
                .createDepartment(department1)
                .createDepartment(department2)
        /*
         * Создаем подразделение А
         * определяем для подразделения А, родительское подразделение Б, ИНАЧЕ создается, как НЕ дочернее
         * т.е. если есть в памяти - .setParentDepartment (Название родительского под-ия), подразделение создается как Дочернее
         */
                .createDepartment(department2_1
                        .setParentDepartment(department2))
                .createDepartment(department2_1_1
                        .setParentDepartment(department2_1))
                .dndSavePermissions(department2_1_1, department1)
                .createDepartment(editDepartment
                        .setParentDepartment(department1))
                .editDepartments(editDepartment, department3)
                .createDepartment(department2_2
                        .setParentDepartment(department2_1))

        /*
         проверяем - удаление ранеее созданных Подразделений
         */
                .deleteDepartment(department1)
                .deleteDepartment(department2)
                .deleteDepartment(department2_1)
                .deleteDepartment(department2_1_1)
                .deleteDepartment(department3)
                .deleteDepartment(department2_2);

        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

    }

    @Video(name = "Создание пользователей. Авторизация под созданными пользователями")
    @Test(priority = 2)
    public void verifyCreatingUsers() throws Exception {
        loginPageSteps.loginAs(ADMIN);
        assertThat("Check that the displayed menu item 8 (Logo; Tasks; Documents; Messages; Calendar; Library; Tools; Details)",
                internalPageSteps.hasMenuUserComplete()); // Проверяем отображение п.м. на внутренней странице
        assertTrue(loginPageSteps.isLoggedIn());

        // Создаем подразделения для пользователей
        goToURLDepartments().
                createDepartment(departmentUser)
                .createDepartment(departmentUser1);

        userPageSteps
                // Создание пользователей
                .createUser(user)
                .editUser(user, editUser)

                .createUser(changepass)
                .createUser(workflow) // Создание пользователя с правом на модуль "WORKFLOW"
                .createUser(docflow) // Создание пользователя с правом на модуль "DOCFLOW"

        /*
         * Проверка создания псевдонима пользователя
         */
                .createAndCheckAliasForDep(editUser, departmentUser1);

        internalPageSteps.logout(); // Выход из системы
        assertTrue(loginPageSteps.isNotLoggedIn()); // Проверка того, что пользователь разлогинен

        /*
         авторизируемся под данными пользователя, к-е были отредактированы
          */
        loginPageSteps.loginAs(user);
        assertTrue("Log in to the dataproviders fails", loginPageSteps.isNotLoggedIn());

        /*
         * валидация - авторизации под вновь созданными пользователями
         */
        loginPageSteps.loginAs(editUser);
        assertTrue(loginPageSteps.newUserIsLoggedInAs(editUser));
        assertTrue(loginPageSteps.checkTheSystemFolderMappingUserLibrary(editUser)); // проверяем отображение системной папки Библиотека пользователя
        internalPageSteps.logout(); // Выход из системы
        assertTrue(loginPageSteps.isNotLoggedIn()); // Проверка того, что пользователь разлогинен

        loginPageSteps.loginAs(editUser);
        assertTrue(loginPageSteps.newUserIsLoggedInAs(editUser));
        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

        loginPageSteps.loginAs(changepass);
        restorePasswordSteps.passwordChange(changepass);
        assertTrue(loginPageSteps.newUserIsLoggedInAs(changepass));
        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

        loginPageSteps.loginAs(docflow);
        assertTrue(loginPageSteps.newUserIsLoggedInAs(docflow));
        userPageSteps.checkUserDocflow();
        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

        loginPageSteps.loginAs(workflow);
        assertTrue(loginPageSteps.newUserIsLoggedInAs(workflow));
        userPageSteps.checkUserWorkflow(18);

        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

    }

    @Video(name = "Удаление пользователей из системы")
    @Test(dependsOnMethods = "verifyCreatingUsers")
    public void verifyRemovalUsers() throws Exception {
        loginPageSteps.loginAs(ADMIN);
        assertThat("Check that the displayed menu item 8 (Logo; Tasks; Documents; Messages; Calendar; Library; Tools; Details)",
                internalPageSteps.hasMenuUserComplete()); // Проверяем отображение п.м. на внутренней странице
        assertTrue(loginPageSteps.isLoggedIn());

        // Инициализируем страницу и переходим на нее - Администрирование/Пользователи
        goToURLDepartments();

        // Инициализируем страницу - Администрирование/Пользователи
        userPageSteps
                .deleteUser(editUser)
                .deleteUser(changepass)
                .deleteUser(docflow)
                .deleteUser(workflow)

                .deleteDepartment(departmentUser);

        internalPageSteps.logout();
        assertTrue(loginPageSteps.isNotLoggedIn());

    }
}