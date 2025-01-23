package org.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyPageItemSpellsForm {
    WebDriver webDriver;

    @FindBy(id="addSubmit")
    private WebElement addSubmitButton;

    @FindBy(id="addName")
    private WebElement addNameInput;

    @FindBy(id="addDescription")
    private WebElement addDescriptionInput;

    @FindBy(id="editSubmit")
    private WebElement editSubmitButton;

    @FindBy(id="editedSpell")
    private WebElement editedSpellInput;

    @FindBy(id="editName")
    private WebElement editNameInput;

    @FindBy(id="editDescription")
    private WebElement editDescriptionInput;

    @FindBy(id="naglowek")
    private WebElement header;

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

    public MyPageItemSpellsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public MyPageItemSpellsForm openAdd() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemspelladd");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemspelladd");
        return this;
    }

    public MyPageItemSpellsForm openEdit() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemspelledit");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemspelledit");
        return this;
    }

    public MyPageItemSpellsForm fillInAddInputs(String name, String description){
        addNameInput.sendKeys(name);
        addDescriptionInput.sendKeys(description);
        addSubmitButton.click();
        return this;
    }

    public boolean isHeaderDisplayed(){
        return header.isDisplayed();
    }

    public MyPageViewAll viewAll() {
        return new MyPageViewAll(webDriver).openSpells();
    }

    public MyPageItemSpellsForm fillInEditInputs(String spellEdit, String name, String description){
        new Select(editedSpellInput).selectByVisibleText(spellEdit);
        editNameInput.sendKeys(name);
        editDescriptionInput.clear();
        editDescriptionInput.sendKeys(description);
        editSubmitButton.click();
        return this;
    }
}
