package org.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyPageItemSubclassesForm {
    WebDriver webDriver;

    @FindBy(id="addSubmit")
    private WebElement addSubmitButton;
    
    @FindBy(id="addName")
    private WebElement addNameInput;
    
    @FindBy(id="addClass")
    private WebElement addClassInput;
    
    @FindBy(id="editSubclass")
    private WebElement editSubclassInput;
    
    @FindBy(id="editSubmit")
    private WebElement editSubmitButton;
    
    @FindBy(id="editName")
    private WebElement editNameInput;
    
    @FindBy(id="editedClass")
    private WebElement editedClassInput;
    
    @FindBy(id="naglowek")
    private WebElement header;
    
    
    public MyPageItemSubclassesForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public MyPageItemSubclassesForm openAdd() {
        webDriver.navigate().to("http://localhost:8080/view/itemsubclassadd");
        return this;
    }

    public MyPageItemSubclassesForm openEdit() {
        webDriver.navigate().to("http://localhost:8080/view/itemsubclassedit");
        return this;
    }
    
    public MyPageItemSubclassesForm fillInAddInputs(String name, String itemClass){
        addNameInput.sendKeys(name);
        new Select(addClassInput).selectByVisibleText(itemClass);
        addSubmitButton.click();
        return this;
    }
    
    public boolean isHeaderDisplayed(){
        return header.isDisplayed();
    }
    
    public MyPageViewAll viewAll() {
        return new MyPageViewAll(webDriver).openSubclasses();
    }

    public MyPageItemSubclassesForm fillInEditInputs(String name, String itemClass, String subclass){

        new Select(editedClassInput).selectByVisibleText(itemClass);
        new Select(editSubclassInput).selectByVisibleText(subclass);
        editNameInput.clear();
        editNameInput.sendKeys(name);
        editSubmitButton.click();
        return this;
    }
}
