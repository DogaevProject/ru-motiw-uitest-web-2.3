package ru.motiw.web.steps.DocflowAdministration.DocumentRegistrationCards;

import com.codeborne.selenide.SelenideElement;
import ru.motiw.web.elements.elementsweb.DocflowAdministration.DocumentRegistrationCards.FormDocRegisterCardsEditRightsElements;
import ru.motiw.web.model.AccessRights;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.CreationOfLinkedDocuments;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.DocRegisterCards;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.EditionOwnDocuments;
import ru.motiw.web.model.DocflowAdministration.DocumentRegistrationCards.SettingsFinalVersion;
import ru.motiw.web.steps.BaseSteps;

import static com.codeborne.selenide.Selenide.page;
import static ru.motiw.utils.ElementUtil.setOfValuesOnTheList;

/**
 * Форма редактирвоания РКД - вкладка ПРАВА
 */
public class FormDocRegisterCardsEditRightsSteps extends BaseSteps {

    private static FormDocRegisterCardsEditRightsElements formEditRCDRightsElement = page(FormDocRegisterCardsEditRightsElements.class);

    /**
     * Метод выбора уровня доступа к РКД (Регистрационная карточка документа)
     *
     * @param accessRights право доступа к документу
     * @return FormDocRegisterCardsEditRightsSteps вкладка - ПРАВА (РКД)
     */
    private FormDocRegisterCardsEditRightsSteps setAccessesRCD(AccessRights accessRights) {
        switch (accessRights) {
            case AVAILABLE_TO_ALL:
                formEditRCDRightsElement.getAccessAvailableToAll().click();
                break;
            case PERSONAL:
                formEditRCDRightsElement.getPersonal().click();
                break;
            case READONLY:
                formEditRCDRightsElement.getReadOnly().click();
                break;
        }
        return this;
    }

    /**
     * Изменение признака "Окончательная версия":
     * Автор документа
     * Пользователь с правами редактирования
     * Конролер типа документа
     *
     * @param finalSelectionOfFeatureVersionForSelectedRoles выбираемая настройка для роли
     * @param finalVersionChanging                           передаваемая настройка (Значение на выбрано (null); Нет или Да)
     * @return FormDocRegisterCardsEditRightsSteps вкладка - ПРАВА (РКД)
     */
    private FormDocRegisterCardsEditRightsSteps settingsChangingTheSignOfFinalVersion(SelenideElement finalSelectionOfFeatureVersionForSelectedRoles,
                                                                                      SettingsFinalVersion finalVersionChanging) {
        if (finalVersionChanging == null) return this;
        else if (finalVersionChanging == SettingsFinalVersion.NO) {
            setOfValuesOnTheList(finalSelectionOfFeatureVersionForSelectedRoles,
                    getCollectionOfListItems(), SettingsFinalVersion.NO.getNameOfTheEnumerationValues());
        } else if (finalVersionChanging == SettingsFinalVersion.YES) {
            setOfValuesOnTheList(finalSelectionOfFeatureVersionForSelectedRoles,
                    getCollectionOfListItems(), SettingsFinalVersion.YES.getNameOfTheEnumerationValues());
        } else if (finalVersionChanging == SettingsFinalVersion.VALUE_IS_NOT_DEFINED) {
            setOfValuesOnTheList(finalSelectionOfFeatureVersionForSelectedRoles,
                    getCollectionOfListItems(), SettingsFinalVersion.VALUE_IS_NOT_DEFINED.getNameOfTheEnumerationValues());
        }
        return this;
    }

    /**
     * Редактирование своих документов
     * - На рассмотрении
     * - На исполнении
     * - В архиве
     *
     * @param statusEditYourDocuments     элемент поля настройки
     * @param editionOwnDocumentsOnReview передаваемое значение настройки "Редактирование своих документов"
     * @return FormDocRegisterCardsEditRightsSteps вкладка - ПРАВА (РКД)
     */
    private FormDocRegisterCardsEditRightsSteps selectionOfSettingsStatusEditYourDocuments(SelenideElement statusEditYourDocuments,
                                                                                           EditionOwnDocuments editionOwnDocumentsOnReview) {
        if (editionOwnDocumentsOnReview == null) return this;
        switch (editionOwnDocumentsOnReview) {
            case VALUE_IS_NOT_DEFINED:
                setOfValuesOnTheList(statusEditYourDocuments, getCollectionOfListItems(),
                        EditionOwnDocuments.YES.getNameOfTheEnumerationValues());
                break;
            case NO:
                setOfValuesOnTheList(statusEditYourDocuments, getCollectionOfListItems(),
                        EditionOwnDocuments.NO.getNameOfTheEnumerationValues());
                break;
            case YES:
                setOfValuesOnTheList(statusEditYourDocuments, getCollectionOfListItems(),
                        EditionOwnDocuments.YES.getNameOfTheEnumerationValues());
            case USER_RIGHT_EDIT_THEIR_DOCUMENTS:
                setOfValuesOnTheList(statusEditYourDocuments, getCollectionOfListItems(),
                        EditionOwnDocuments.USER_RIGHT_EDIT_THEIR_DOCUMENTS.getNameOfTheEnumerationValues());
        }
        return this;
    }

    /**
     * Доступ к разделам документа при просмотре/редактировании:
     * Маршрут
     * Файлы
     * Резолюции
     * Журнал
     *
     * @param selectionTab передаваемое значение выбираемой опции
     * @param accessToTab  передаваемое значение параметра (true OR false)
     * @return FormDocRegisterCardsEditRightsSteps вкладка - ПРАВА (РКД)
     */
    private FormDocRegisterCardsEditRightsSteps settingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(SelenideElement selectionTab, boolean accessToTab) {
        if (accessToTab) {
            selectionTab.click();
        }
        return this;
    }

    /**
     * Создание связанных документов
     *
     * @param creationOfLinkedDocuments параметр значения создания связанного документа
     * @return FormDocRegisterCardsEditRightsSteps вкладка - ПРАВА (РКД)
     */
    private FormDocRegisterCardsEditRightsSteps creationOfLinkedDocuments(CreationOfLinkedDocuments creationOfLinkedDocuments) {
        if (creationOfLinkedDocuments == null) return this;
        switch (creationOfLinkedDocuments) {
            case VALUE_IS_NOT_SELECTED:
                setOfValuesOnTheList(formEditRCDRightsElement.getCreationOfLinkedDocuments(), getCollectionOfListItems(),
                        CreationOfLinkedDocuments.VALUE_IS_NOT_SELECTED.getNameOfTheEnumerationValues());
                break;
            case NO:
                setOfValuesOnTheList(formEditRCDRightsElement.getCreationOfLinkedDocuments(), getCollectionOfListItems(),
                        CreationOfLinkedDocuments.NO.getNameOfTheEnumerationValues());
                break;
            case USERS_WITH_RIGHT:
                setOfValuesOnTheList(formEditRCDRightsElement.getCreationOfLinkedDocuments(), getCollectionOfListItems(),
                        CreationOfLinkedDocuments.USERS_WITH_RIGHT.getNameOfTheEnumerationValues());
                break;
            case USERS_WITH_RIGHT_LINKED_DOCUMENTS_CREATION:
                setOfValuesOnTheList(formEditRCDRightsElement.getCreationOfLinkedDocuments(), getCollectionOfListItems(),
                        CreationOfLinkedDocuments.USERS_WITH_RIGHT_LINKED_DOCUMENTS_CREATION.getNameOfTheEnumerationValues());
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * Выбираем уровень доступа
     *
     * @param accesses передаваемое право доступа к РКД
     */
    public FormDocRegisterCardsEditRightsSteps accessesRCD(DocRegisterCards accesses) {
        rightsTabRCD().
                setAccessesRCD(accesses.getAccessDoc());
        return this;
    }

    /**
     * Изменение признака "Окончательная версия"
     */
    public FormDocRegisterCardsEditRightsSteps setSettingsChangingTheSignOfFinalVersion(DocRegisterCards registerCards) {
        rightsTabRCD().
                settingsChangingTheSignOfFinalVersion(formEditRCDRightsElement.getAuthorFinalVersionFiles(),
                        registerCards.getDocAuthorFinalVersionFiles()) // Автор документа
                .settingsChangingTheSignOfFinalVersion(formEditRCDRightsElement.getUserWithEditRightFinalVersionFiles(),
                        registerCards.getUserWithEditRightFinalVersionFiles()) // Пользователь с правами редактирования
                .settingsChangingTheSignOfFinalVersion(formEditRCDRightsElement.getDocTypeControllerFinalVersionFiles(),
                        registerCards.getDocTypeControllerFinalVersionFiles()); // Контролер типа документа
        return this;
    }

    /**
     * Редактирование своих документов
     *
     * @param statusEditYourDocuments передаваемые параметры редактирования настройки
     */
    public FormDocRegisterCardsEditRightsSteps setSettingsStatusEditYourDocuments(DocRegisterCards statusEditYourDocuments) {
        rightsTabRCD().
                selectionOfSettingsStatusEditYourDocuments(formEditRCDRightsElement.getEditionOwnDocumentsOnReview(),
                        statusEditYourDocuments.getEditionOwnDocumentsOnReview()) //  - На рассмотрении
                .selectionOfSettingsStatusEditYourDocuments(formEditRCDRightsElement.getEditionOwnDocumentsOnExecution(),
                        statusEditYourDocuments.getEditionOwnDocumentOnExecution()) // - На исполнении
                .selectionOfSettingsStatusEditYourDocuments(formEditRCDRightsElement.getEditionOwnDocumentsInArchive(),
                        statusEditYourDocuments.getEditionOwnDocumentInArchive()); // - В архиве
        return this;
    }


    /**
     * Доступ к разделам документа при просмотре/редактировании
     *
     * @param documentWhenViewingOREditing передаваемое значение доступа
     */
    public FormDocRegisterCardsEditRightsSteps setSettingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(DocRegisterCards documentWhenViewingOREditing) {
        rightsTabRCD()
                .settingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(formEditRCDRightsElement.getBoxAccessToSectionsDocumentRoute(),
                        documentWhenViewingOREditing.getAccessToSectionsDocumentRoute()) // - Маршрут
                .settingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(formEditRCDRightsElement.getBoxAccessToSectionsDocumentFiles(),
                        documentWhenViewingOREditing.getAccessToSectionsDocumentFiles()) // - Файлы
                .settingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(formEditRCDRightsElement.getBoxAccessToSectionsDocumentResolution(),
                        documentWhenViewingOREditing.getAccessToSectionsDocumentResolution()) // - Резолюции
                .settingUpAccessToTheSectionsOfTheDocumentWhenViewingOREditing(formEditRCDRightsElement.getBoxAccessToSectionsDocumentLog(),
                        documentWhenViewingOREditing.getAccessToSectionsDocumentLog()); // - Журнал
        return this;
    }

    /**
     * Создание связанных документов
     *
     * @param linkedDocuments
     */
    public FormDocRegisterCardsEditRightsSteps setCreationOfLinkedDocuments(DocRegisterCards linkedDocuments) {
        rightsTabRCD()
                .creationOfLinkedDocuments(linkedDocuments.getCreationOfLinkedDocuments());
        return this;
    }

    /**
     * Производим выбор вкладки - ПРАВА
     *
     * @return FormDocRegisterCardsEditRightsSteps форма редактирования РКД
     */
    public static FormDocRegisterCardsEditRightsSteps rightsTabRCD() {
        formEditRCDRightsElement.getRightsTab().click();
        return page(FormDocRegisterCardsEditRightsSteps.class);
    }


}
