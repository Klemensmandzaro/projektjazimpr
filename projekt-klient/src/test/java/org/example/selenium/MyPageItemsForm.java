package org.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyPageItemsForm {
    WebDriver webDriver;

    @FindBy(id="addName")
    private WebElement addNameInput;

    @FindBy(id="addClass")
    private WebElement addClassInput;

    @FindBy(id="addSubclass")
    private WebElement addSubclassInput;

    @FindBy(id="addSet")
    private WebElement addSetInput;

    @FindBy(id="spell-search")
    private WebElement addSpellInput;

    @FindBy(id="addStrength")
    private WebElement addStrengthInput;

    @FindBy(id="addAgility")
    private WebElement addAgilityInput;

    @FindBy(id="addIntellect")
    private WebElement addIntellectInput;

    @FindBy(id="addStamina")
    private WebElement addStaminaInput;

    @FindBy(id="addCriticalStrike")
    private WebElement addCriticalStrikeInput;

    @FindBy(id="addMastery")
    private WebElement addMasteryInput;

    @FindBy(id="addVersatility")
    private WebElement addVersatilityInput;

    @FindBy(id="addHaste")
    private WebElement addHasteInput;

    @FindBy(id="addArmor")
    private WebElement addArmorInput;

    @FindBy(id="addBlock")
    private WebElement addBlockInput;

    @FindBy(id="addDodge")
    private WebElement addDodgeInput;

    @FindBy(id="addHealthRegeneration")
    private WebElement addHealthRegenerationInput;

    @FindBy(id="addArcaneResistance")
    private WebElement addArcaneResistanceInput;

    @FindBy(id="addFireResistance")
    private WebElement addFireResistanceInput;

    @FindBy(id="addFrostResistance")
    private WebElement addFrostResistanceInput;

    @FindBy(id="addNatureResistance")
    private WebElement addNatureResistanceInput;

    @FindBy(id="addShadowResistance")
    private WebElement addShadowResistanceInput;

    @FindBy(id="addCritRanged")
    private WebElement addCritRangedInput;

    @FindBy(id="addParry")
    private WebElement addParryInput;

    @FindBy(id="addSpellPower")
    private WebElement addSpellPowerInput;

    @FindBy(id="addSpirit")
    private WebElement addSpiritInput;

    @FindBy(id="addDamageMin")
    private WebElement addDamageMinInput;

    @FindBy(id="addDamageMax")
    private WebElement addDamageMaxInput;

    @FindBy(id="addAttackSpeed")
    private WebElement addAttackSpeedInput;

    @FindBy(id="addDPS")
    private WebElement addDPSInput;

    @FindBy(id="addAttackPower")
    private WebElement addAttackPowerInput;

    @FindBy(id="addRangedAttackPower")
    private WebElement addRangedAttackPowerInput;

    @FindBy(id="addCraftingSpeed")
    private WebElement addCraftingSpeedInput;

    @FindBy(id="addDeftness")
    private WebElement addDeftnessInput;

    @FindBy(id="addFinesse")
    private WebElement addFinesseInput;

    @FindBy(id="addIngenuity")
    private WebElement addIngenuityInput;

    @FindBy(id="addMulticraft")
    private WebElement addMulticraftInput;

    @FindBy(id="addPerception")
    private WebElement addPerceptionInput;

    @FindBy(id="addResourcefulness")
    private WebElement addResourcefulnessInput;

    @FindBy(id="addAvoidance")
    private WebElement addAvoidanceInput;

    @FindBy(id="addLifesteal")
    private WebElement addLifestealInput;

    @FindBy(id="addSpeed")
    private WebElement addSpeedInput;

    @FindBy(id="addSturdiness")
    private WebElement addSturdinessInput;

    @FindBy(id="addCorruptionResistance")
    private WebElement addCorruptionResistanceInput;

    @FindBy(id="addExtraArmor")
    private WebElement addExtraArmorInput;

    @FindBy(id="addMana")
    private WebElement addManaInput;

    @FindBy(id="addSubmit")
    private WebElement addSubmitButton;

    @FindBy(id="naglowek")
    private WebElement header;

    @FindBy(id="editSubmit")
    private WebElement editSubmitButton;

    @FindBy(id="item-search")
    private WebElement itemSearchInput;

    @FindBy(id="add-spell")
    private WebElement addSpellButton;

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

    public MyPageItemsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public MyPageItemsForm openAdd() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemadd");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemadd");
        return this;
    }

    public MyPageItemsForm openEdit() throws InterruptedException {
        webDriver.navigate().to("http://localhost:8080/view/itemedit");
        logIn();
        Thread.sleep(1000);
        webDriver.navigate().to("http://localhost:8080/view/itemadd");
        return this;
    }

    public MyPageItemsForm fillInAddInputs(String name, String itemClass, String subclass, String set, int Strenght, String itemSpell) throws InterruptedException {
        addNameInput.sendKeys(name);
        new Select(addClassInput).selectByVisibleText(itemClass);
        new Select(addSubclassInput).selectByVisibleText(subclass);
        new Select(addSetInput).selectByVisibleText(set);
        addStrengthInput.sendKeys(String.valueOf(Strenght));
        addSpellInput.sendKeys(itemSpell);
        addSpellButton.click();
        addSubmitButton.click();
        return this;
    }

    public boolean isHeaderDisplayed() throws InterruptedException {
        return header.isDisplayed();
    }

    public MyPageViewAll viewAll() {
        return new MyPageViewAll(webDriver).openItems();
    }

    public MyPageItemsForm fillInEditInputs(String name, String itemClass, String subclass, String set, int Strenght, String itemSpell) throws InterruptedException {
        itemSearchInput.sendKeys(name);
        new Select(addClassInput).selectByVisibleText(itemClass);
        new Select(addSubclassInput).selectByVisibleText(subclass);
        new Select(addSetInput).selectByVisibleText(set);
        addStrengthInput.sendKeys(String.valueOf(Strenght));
        addSpellInput.sendKeys(itemSpell);
        editSubmitButton.click();
        return this;
    }


}
