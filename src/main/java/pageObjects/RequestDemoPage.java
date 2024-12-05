package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RequestDemoPage {

    public final Page page;
    public final Locator getStartedPageMainTitle;
    public final Locator getStartedPageSecondaryTitle;
    public final Locator emailInputField;
    public final Locator industrySelectionDropdown;
    public final Locator industrySelectionOptionsList;
    public final Locator companySizeSelectionDropdown;
    public final Locator companySizeOptionsList;
    public final Locator firstNameInputField;
    public final Locator lastNameInputField;
    public final Locator companyNameInputField;
    public final Locator countryNameSelectionDropdown;
    public final Locator countryNameOptionsList;
    public final Locator phoneInputField;
    public final Locator privacyPolicyConsentCheckbox;
    public final Locator requestDemoButton;
    public final Locator consentCheckboxText;
    public final Locator consentDeclarationLink;
    public final Locator closeGetStartedWindowXButton;
    public final Locator missingFieldErrorMessage;

    public RequestDemoPage(Page page) {
        this.page = page;

        getStartedPageMainTitle = page.locator("#visible h2");
        getStartedPageSecondaryTitle = page.locator("#visible p");
        emailInputField = page.getByPlaceholder("*Work email address");
        industrySelectionDropdown = page.locator("select[name='industry']");
        industrySelectionOptionsList = page.locator("select[name='industry'] option");//16 options
        companySizeSelectionDropdown = page.locator("select[name='Size_range']");//12 options
        companySizeOptionsList = page.locator("select[name='Size_range'] option");
        firstNameInputField = page.locator("input[name='firstName']");
        lastNameInputField = page.locator("input[name='lastName']");
        companyNameInputField = page.locator("input[name='company']");
        countryNameSelectionDropdown = page.locator("select[name='Country']");
        countryNameOptionsList = page.locator("select[name='Country'] option");
        phoneInputField = page.locator("input[name='phone']");
        privacyPolicyConsentCheckbox = page.locator("input[name='data_opt_in']");
        consentCheckboxText = page.locator("//div[@class='form-group']/label");
        consentDeclarationLink = page.locator("div.form-group label a");
        requestDemoButton = page.getByText("Request Demo");
        missingFieldErrorMessage = page.locator("div.error-message");
        closeGetStartedWindowXButton = page.locator("span.popup__close");
    }
}