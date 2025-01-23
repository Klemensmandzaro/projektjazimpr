package org.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyPageItemClassesForm {
    WebDriver webDriver;

    @FindBy(id="addSubmit")
    private WebElement addSubmitButton;

    @FindBy(id="addName")
    private WebElement addNameInput;

    @FindBy(id="editClass")
    private WebElement editClassInput;

    @FindBy(id="editSubmit")
    private WebElement editSubmitButton;

    @FindBy(id="editedName")
    private WebElement editedNameInput;

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

    public MyPageItemClassesForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public MyPageItemClassesForm openAdd() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemclassadd");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemclassadd");
        return this;
    }

    public MyPageItemClassesForm openEdit() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemclassedit");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemclassedit");
        return this;
    }

    public MyPageItemClassesForm fillInAddInputs(String name) {
        addNameInput.sendKeys(name);
        addSubmitButton.click();
        return this;
    }

    public boolean isHeaderDisplayed() {
        return header.isDisplayed();
    }

    public MyPageViewAll viewAll() {
        return new MyPageViewAll(webDriver).openClasses();
    }

    public MyPageItemClassesForm fillInEditInputs(String name, String editedName) {
        editClassInput.sendKeys(name);
        editedNameInput.sendKeys(editedName);
        editSubmitButton.click();
        return this;
    }

}
