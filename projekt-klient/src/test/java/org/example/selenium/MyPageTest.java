package org.example.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyPageTest {
    WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        this.webDriver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        this.webDriver.close();
    }

    @Test
    @Order(1)
    public void testItemAddInputs() throws InterruptedException {
        MyPageItemsForm myPage = new MyPageItemsForm(webDriver).openAdd();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInAddInputs("Maciek", "Weapon", "Sword", "Neutral", 1, "Food");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue(webDriver.findElement(By.id("row-3-id")).isDisplayed());
    }

    @Test
    @Order(2)
    public void testItemEditInputs() throws InterruptedException {
        MyPageItemsForm myPage = new MyPageItemsForm(webDriver).openEdit();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInEditInputs("Maciek", "Weapon", "Axe", "Neutral", 1, "Food");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue("Axe".contentEquals(webDriver.findElement(By.id("row-3-subclass")).getText()));
    }

    @Test
    @Order(3)
    public void testItemDeleteInputs() throws InterruptedException {
        MyPageViewAll myPage = new MyPageViewAll(webDriver).openItems();
        Thread.sleep(3000);
        myPage.deleteSomething(2);
        Thread.sleep(3000);
        assertThrows(NoSuchElementException.class, () -> {
            webDriver.findElement(By.id("row-3-id"));
        });
    }

    @Test
    @Order(4)
    public void testClassAddInputs() throws InterruptedException {
        MyPageItemClassesForm myPage = new MyPageItemClassesForm(webDriver).openAdd();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInAddInputs("Maciek");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue(webDriver.findElement(By.id("row-18-name")).isDisplayed());
    }

    @Test
    @Order(5)
    public void testClassEditInputs() throws InterruptedException {
        MyPageItemClassesForm myPage = new MyPageItemClassesForm(webDriver).openEdit();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInEditInputs("Maciek", "Maciek2");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue("Maciek2".contentEquals(webDriver.findElement(By.id("row-18-name")).getText()));
    }

    @Test
    @Order(6)
    public void testClassDeleteInputs() throws InterruptedException {
        MyPageViewAll myPage = new MyPageViewAll(webDriver).openClasses();
        Thread.sleep(3000);
        myPage.deleteSomethingWithoutAlert(18);
        Thread.sleep(3000);
        assertThrows(NoSuchElementException.class, () -> {
            webDriver.findElement(By.id("row-18-name"));
        });
    }

    @Test
    @Order(7)
    public void testSubclassAddInputs() throws InterruptedException {
        MyPageItemSubclassesForm myPage = new MyPageItemSubclassesForm(webDriver).openAdd();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInAddInputs("Maciek", "Weapon");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue(webDriver.findElement(By.id("row-102-name")).isDisplayed());
    }

    @Test
    @Order(8)
    public void testSubclassEditInputs() throws InterruptedException {
        MyPageItemSubclassesForm myPage = new MyPageItemSubclassesForm(webDriver).openEdit();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInEditInputs("Maciek2", "Weapon", "Maciek");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue("Maciek2".contentEquals(webDriver.findElement(By.id("row-102-name")).getText()));
    }

    @Test
    @Order(9)
    public void testSubclassDeleteInputs() throws InterruptedException {
        MyPageViewAll myPage = new MyPageViewAll(webDriver).openSubclasses();
        Thread.sleep(3000);
        myPage.deleteSomethingWithoutAlert(102);
        Thread.sleep(3000);
        assertThrows(NoSuchElementException.class, () -> {
            webDriver.findElement(By.id("row-102-name"));
        });
    }

    @Test
    @Order(10)
    public void testSetAddInputs() throws InterruptedException {
        MyPageItemSetsForm myPage = new MyPageItemSetsForm(webDriver).openAdd();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInAddInputs("Maciek", "something");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue(webDriver.findElement(By.id("row-14-name")).isDisplayed());
    }

    @Test
    @Order(11)
    public void testSetEditInputs() throws InterruptedException {
        MyPageItemSetsForm myPage = new MyPageItemSetsForm(webDriver).openEdit();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInEditInputs("Maciek2", "Maciek");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue("Maciek2".contentEquals(webDriver.findElement(By.id("row-14-name")).getText()));
    }

    @Test
    @Order(12)
    public void testSetDeleteInputs() throws InterruptedException {
        MyPageViewAll myPage = new MyPageViewAll(webDriver).openSets();
        Thread.sleep(3000);
        myPage.deleteSomethingWithoutAlert(14);
        Thread.sleep(3000);
        MyPageViewAll myPage2 = new MyPageViewAll(webDriver).openSets();
        assertThrows(NoSuchElementException.class, () -> {
            webDriver.findElement(By.id("row-14-name"));
        });
    }

    @Test
    @Order(13)
    public void testSpellAddInputs() throws InterruptedException {
        MyPageItemSpellsForm myPage = new MyPageItemSpellsForm(webDriver).openAdd();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInAddInputs("Maciek", "something");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue(webDriver.findElement(By.id("row-17-name")).isDisplayed());
    }

    @Test
    @Order(14)
    public void testSpellEditInputs() throws InterruptedException {
        MyPageItemSpellsForm myPage = new MyPageItemSpellsForm(webDriver).openEdit();
        Thread.sleep(3000);
        assertTrue(myPage.isHeaderDisplayed());
        myPage.fillInEditInputs("Maciek", "Maciek2", "something");
        Thread.sleep(3000);
        MyPageViewAll viewAll = myPage.viewAll();
        assertTrue("Maciek2".contentEquals(webDriver.findElement(By.id("row-17-name")).getText()));
    }

    @Test
    @Order(15)
    public void testSpellDeleteInputs() throws InterruptedException {
        MyPageViewAll myPage = new MyPageViewAll(webDriver).openSpells();
        Thread.sleep(3000);
        myPage.deleteSomethingWithoutAlert(17);
        Thread.sleep(3000);
        MyPageViewAll myPage2 = new MyPageViewAll(webDriver).openSpells();
        assertThrows(NoSuchElementException.class, () -> {
            webDriver.findElement(By.id("row-17-name"));
        });
    }


}
