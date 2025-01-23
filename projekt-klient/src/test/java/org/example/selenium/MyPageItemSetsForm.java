package org.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyPageItemSetsForm {
    WebDriver webDriver;

    @FindBy(id="addSubmit")
    private WebElement addSubmitButton;

    @FindBy(id="addName")
    private WebElement addNameInput;

    @FindBy(id="add-bonus")
    private WebElement addBonusInput;

    @FindBy(name="effects")
    private WebElement addEffectsInput;

    @FindBy(id="naglowek")
    private WebElement header;

    @FindBy(id="editSubmit")
    private WebElement editSubmitButton;

    @FindBy(id="editedSet")
    private WebElement editSetInput;

    @FindBy(id="name")
    private WebElement editNameInput;

    @FindBy(id="username")
    WebElement username;

    @FindBy(id="password")
    WebElement password;

    @FindBy(id="login-button")
    WebElement loginButton;

    public void logIn(){
        this.username.sendKeys("admin");
        this.password.sendKeys("password");
        this.loginButton.click();
    }

    public MyPageItemSetsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public MyPageItemSetsForm openAdd() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemsetadd");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemsetadd");
        return this;
    }

    public MyPageItemSetsForm openEdit() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemsetedit");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemsetedit");
        return this;
    }

    public MyPageItemSetsForm fillInAddInputs(String name, String effects) {
        addNameInput.sendKeys(name);
        addBonusInput.click();
        addEffectsInput.sendKeys(effects);
        addSubmitButton.click();
        return this;
    }

    public boolean isHeaderDisplayed() {
        return header.isDisplayed();
    }

    public MyPageViewAll viewAll() {
        return new MyPageViewAll(webDriver).openSets();
    }

    public MyPageItemSetsForm fillInEditInputs(String name, String set) {
        new Select(editSetInput).selectByVisibleText(set);
        editNameInput.sendKeys(name);
        editSubmitButton.click();
        return this;
    }


}
