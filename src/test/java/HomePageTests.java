

import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import utilities.Base;

@Listeners(utilities.Listeners.class)
public class HomePageTests extends Base {

    private boolean setupComplete = false;
    private final String firstDogName = "Kika";
    private final String secondDogName = "Lychee";
    private final String thirdDogName = "Kimba";
    String name = "Moshe";
    String email = "Moshe.cohen@gmail.com";
    private final String messageFromKika = "Hello, my name is Kika, how are you?";
    private final String messageFromLychee = "Hello, my name is Lychee, what's your name?";
    private final String messageFromKimba = "Hello, my name is Kimba, what's up?";


    @BeforeClass
    public void classSetup() {
        try {
            initCore();
            usefulMethods.deleteAllFilesInAGivenPath("test-output/screenshots");
            String url = usefulMethods.getData("baseURL");
            System.out.println("Navigating to: " + url);
            page.navigate(url);
            setupComplete = true;
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e.getMessage());
            closePlaywright();
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @AfterMethod
    public void doAfterMethod(){
        String url = usefulMethods.getData("baseURL");
        page.navigate(url);
    }

    @Test
    public void test_01_Kika() {
      homePageFlows.getMenuDogNamesAndVerifyTargetNameExists(firstDogName);
      homePageFlows.selectDogAndSendDetails(1, name, email, messageFromKika);
    }

    @Test
    public void test_02_Lychee() {
        homePageFlows.getMenuDogNamesAndVerifyTargetNameExists(secondDogName);
        homePageFlows.selectDogAndSendDetails(2, name, email, messageFromLychee);
    }

    @Test
    public void test_03_Kimba() {
        homePageFlows.getMenuDogNamesAndVerifyTargetNameExists(thirdDogName);
        homePageFlows.selectDogAndSendDetails(3, name, email, messageFromKimba);
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        if (hasPlaywrightResources()) {
            closePlaywright();
        }
    }
}


