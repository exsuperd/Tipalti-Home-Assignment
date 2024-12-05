package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    public final Page page;
    public final Locator menuButton;
    public final Locator menuOptionsList;
    public final Locator name;
    public final Locator email;
    public final Locator message;
    public final Locator sendButton;

    public HomePage(Page page) {
        this.page = page;
        menuButton = page.locator("ul a[href='#menu']");
        menuOptionsList = page.locator("nav[id='menu'] ul li a");
        name = page.locator("#name");
        email = page.locator("#email");
        message = page.locator("#message");
        sendButton = page.locator("input[value='Send']");
    }
}
