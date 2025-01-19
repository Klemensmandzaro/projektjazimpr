package org.example.selenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyPageViewAll {
    WebDriver webDriver;

    public MyPageViewAll(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(name="id")
    private List<WebElement> deleteButtons;

    public MyPageViewAll openItems() {
        webDriver.navigate().to("http://localhost:8080/view/itemscreatedbyuser");
        return this;
    }

    public MyPageViewAll openClasses() {
        webDriver.navigate().to("http://localhost:8080/view/classes");
        return this;
    }

    public MyPageViewAll openSubclasses() {
        webDriver.navigate().to("http://localhost:8080/view/subclasses");
        return this;
    }

    public MyPageViewAll openSets() {
        webDriver.navigate().to("http://localhost:8080/view/sets?page=11");
        return this;
    }

    public MyPageViewAll openSpells() {
        webDriver.navigate().to("http://localhost:8080/view/spells?page=108");
        return this;
    }

    public MyPageViewAll deleteSomething(int id) {
        deleteButtons.get(id).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1));
        Alert firstAlert = webDriver.switchTo().alert();
        firstAlert.accept();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert secondAlert = webDriver.switchTo().alert();
        secondAlert.accept();
        return this;
    }

    public MyPageViewAll deleteSomethingWithoutAlert(int id) {
        webDriver.findElement(By.id(id+"-delete")).click();
        return this;
    }
}
