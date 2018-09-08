package ru.motiw.web.steps.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import ru.motiw.web.elements.elementsweb.Login.LoginPageElements;
import ru.motiw.web.model.Administration.Users.Employee;
import ru.motiw.web.steps.Administration.Users.UsersSteps;
import ru.motiw.web.steps.BaseSteps;
import ru.motiw.web.steps.Home.InternalSteps;
import ru.motiw.web.steps.Home.LibrarySteps;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;
import static ru.motiw.web.steps.Options.MyPropertiesSteps.goToURLPwd;

/**
 * Стараница авторизации - Web
 */
public class LoginStepsSteps extends BaseSteps {

    private LoginPageElements loginPageElements = page(LoginPageElements.class);

    /**
     * Вводим Login пользователя
     *
     * @param text передаваемое значение логина
     */
    public LoginStepsSteps setLoginField(String text) {
        loginPageElements.getLogin().setValue(text);
        return this;
    }

    /**
     * Вводим пароль пользователя
     *
     * @param text передаваемое значение
     */
    public LoginStepsSteps setPasswordField(String text) {
        loginPageElements.getPassword().setValue(text);
        return this;
    }

    /**
     * Инициализируем внутреннюю страницу
     *
     * @return results internal page instance
     */
    public InternalSteps initializedInsidePage() {
        return page(InternalSteps.class);
    }

    /**
     * Авторизация под указанным пользователем
     *
     * @param user loginPageElements and pass user
     */
    public LoginStepsSteps loginAs(Employee user) {
        setLoginField(user.getLoginName());
        setPasswordField(user.getPassword());
        loginPageElements.getLogon().submit();
        waitAfterAuthorization();
        return this;
    }

    /**
     * Проверяем валидность авторизации пользователя в систему
     *
     * @param login    input text loginPageElements
     * @param password input text password
     */
    public LoginStepsSteps loginAs(String login, String password) {
        inputField(loginPageElements.getLogin(), login);
        inputField(loginPageElements.getPassword(), password);
        loginPageElements.getLogon().submit();
        return this;
    }


    /*
     *В некоторых случаях необходимо ожидание после авторизации. Учтены три разных случаев возможных после авторизации.
     * Заодно проверяем элементы и текст, которые должны быть при этих случаях
     */
    private void waitAfterAuthorization() {
        sleep(1000);
        if (loginPageElements.getLogin().exists()) // если после sleep, все ещё есть поля формы логина, то значит авторизация не прошла
        {
            $(By.xpath("//div[text()='Доступ запрещен']")).waitUntil(visible, 10000);
        } else
            $(By.xpath("//div[@id='logo' or label[text()='Вам необходимо сменить пароль']]")).waitUntil(visible, 10000); // авторизация прошла, должно быть либо лого в меню, либо форма смены пароля.

    }

    /**
     * Проверка истинности загрузки внутренней стр-цы
     */
    public boolean isLoggedIn() {
        return page(InternalSteps.class).isPageLoadedInternal();
    }

    /**
     * Проверяем то, что мы разлогинены
     *
     * @return LoginStepsSteps
     */
    public boolean isNotLoggedIn() {
        return page(LoginStepsSteps.class).isPageNotLoadedInternal();
    }

    /**
     * Метод обращается к ensurePageLoaded и возвращает булевское значение,
     * (false - не дождались загрузки стр.; true - дождались) ждет загрузки
     * страницы
     */
    public boolean isPageNotLoadedInternal() {
        try {
            loginPageElements.getLogon().shouldBe(visible);
            loginPageElements.getLogin().shouldBe(visible);
            loginPageElements.getPassword().shouldBe(visible);
            return true;
        } catch (TimeoutException to) {
            return false;
        }
    }

    /**
     * Проверяем, что мы не только залогинены, но залогинены под конкретным пользователем
     *
     * @param user атрибуты пользователя (в данном случае Логин пользователя)
     * @return возвращаяет истенность, если Логин пользователя (при авторизации) совпадает с логином указанный
     * в реквизитах пользователя
     */
    public boolean newUserIsLoggedInAs(Employee user) {
        return isLoggedIn()
                && getLoggedUser().getLoginName().equals(user.getLoginName());
    }

    /**
     * Проверяем отображение системной папки пользователя в разделе Библиотека
     *
     * @param user атрибуты пользователя (в данном случае Логин пользователя) ФИО
     * @return возвращаяет истенность, если ФИО пользователя (при создании) совпадает с названием системной папки Библиотеки
     */
    public boolean checkTheSystemFolderMappingUserLibrary(Employee user) {
        return isLoggedIn()
                && getNameOfTheSystemFolderUserLibrary().getFullName()
                .equals(user.getLastName() + " " + user.getName() + " " + user.getPatronymic());
    }

    /*
     TODO 0006 Обязательно Добавить проверку отображения АК
     TODO 0007  проверка отображения системных библиотек при создании Подразделения
      */


    private Employee getNameOfTheSystemFolderUserLibrary() {

        LibrarySteps userNameOfSystemLibraryFolder = initializedInsidePage().goToLibrary()
                .ensurePageLoaded();
        return new Employee().setFullName(userNameOfSystemLibraryFolder.getParentFullNameSystemBoxLibrary());

    }

    /**
     * Реквизиты пользователя
     *
     * @return логин пользователя, к-й используется при авторизации
     */
    private Employee getLoggedUser() {
        goToURLPwd();
        return new Employee().setLoginName(new UsersSteps().getAttrLoginName());

    }


}
