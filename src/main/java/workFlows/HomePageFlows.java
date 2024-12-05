package workFlows;

import com.microsoft.playwright.Page;

import extensions.UIActions;
import extensions.Verifications;
import org.testng.Assert;
import pageObjects.HomePage;
import utilities.Base;
import utilities.UsefulMethods;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class HomePageFlows {

    private final HomePage homePage;
    public final Page page;
    public final UIActions uiActions;
    public final Verifications verifications;
    public final UsefulMethods usefulMethods;

    public HomePageFlows(Base base, HomePage homePage) {
        this.homePage = homePage;
        this.page = base.getPageForFlow();
        this.uiActions = base.getUiActions();
        this.verifications = base.getVerifications();
        this.usefulMethods = base.getUsefulMethods();
    }

    public void getMenuDogNamesAndVerifyTargetNameExists(String targetDog) {
        homePage.menuButton.click();
        List<String> actualDogOptions = new ArrayList<>();
        for (int i = 0; i < homePage.menuOptionsList.all().size(); i++) {
            System.out.println(homePage.menuOptionsList.all().get(i));
            actualDogOptions.add(i, homePage.menuOptionsList.all().get(i).innerText());
            System.out.println(actualDogOptions.get(i));
        }
        assertTrue(actualDogOptions.contains(targetDog));
    }

    public void selectDogAndSendDetails(int menuListIndex, String name, String email, String uniqueMessage) {
        homePage.menuOptionsList.all().get(menuListIndex).click();
        homePage.name.click();
        homePage.name.fill(name);
        page.waitForTimeout(500);
        homePage.email.click();
        homePage.email.fill(email);
        page.waitForTimeout(500);
        homePage.message.click();
        homePage.message.fill(uniqueMessage);
        homePage.sendButton.click();
        page.waitForTimeout(500);
    }
}

