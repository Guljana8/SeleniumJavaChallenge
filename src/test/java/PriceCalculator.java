import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class PriceCalculator {
    public static void main(String[] args) throws InterruptedException {
        //set the path to the driver to link it with our class(on Windows add .exe at the end )
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        //create a WebDriver instance
        WebDriver driver=new ChromeDriver();
        driver.get("http://localhost:3000/");
        driver.manage().window().maximize();

//1. Change Base Price value to 5
        //Hover row
        WebElement element = driver.findElement(By.xpath("(//div[@class='flex-grow flex flex-col'])[1]"));
        Actions action=new Actions(driver);
        action.moveToElement(element).perform();
        //Click on ‘Pencil’ icon
        WebElement pencil = driver.findElement(By.xpath("(//i[@class='fas fa-pencil-alt'])[1]"));
        pencil.click();
        //Enter new value
        WebElement input = driver.findElement(By.id("base-value-input"));
        input.clear();
        String actualResult="5.00";
        input.sendKeys(actualResult);
        //Click on ‘Check’ icon
        WebElement Icon = driver.findElement(By.xpath("//i[@class='fas fa-check']"));
        Icon.click();
//------> Verify Expected Results A
        WebElement elementText = driver.findElement(By.xpath("//span[@class='font-bold']"));
        String expectedResult = elementText.getText();
        Assert.assertEquals(actualResult, expectedResult);
        System.out.println(expectedResult);

//2. Add all price components from Testdata
        WebElement label = driver.findElement(By.id("ghost-label-input"));
        label.click();
        label.clear();
        label.sendKeys("Alloy surcharge");
        WebElement value = driver.findElement(By.id("ghost-value-input"));
        value.sendKeys("2.15");
        WebElement checkIcon = driver.findElement(By.id("ghost-check-icon"));
        checkIcon.click();
        //Verify that values always show 1 or 2 decimal digits
        String alloyPrice="7.15";
        String expectedResultA = elementText.getText();
        Assert.assertEquals(alloyPrice, expectedResultA);
        System.out.println("Values always show 1 or 2 decimal digits " + expectedResultA);

        label.click();
        label.clear();
        label.sendKeys("Scrap surcharge");
        value.sendKeys("3.14");
        checkIcon.click();
        //Verify that values always show 1 or 2 decimal digits
        String scapPrice="10.29";
        String expectedResultB = elementText.getText();
        Assert.assertEquals(scapPrice, expectedResultB);
        System.out.println("Values always show 1 or 2 decimal digits " + expectedResultB);

        label.click();
        label.clear();
        label.sendKeys("Internal surcharge");
        value.sendKeys("0.7658");
        checkIcon.click();
        //Verify that if value has more than 2 decimal digits, round to 2 decimal digits
        String internalPrice = "11.06";
        String expectedResultC = elementText.getText();
        Assert.assertEquals(internalPrice, expectedResultC);
        System.out.println("If value has more than 2 decimal digits, round to 2 decimal digits " +expectedResultC);

        label.click();
        label.clear();
        label.sendKeys("External surcharge");
        value.sendKeys("1");
        checkIcon.click();
        //Verify that if value has more than 2 decimal digits, round to 2 decimal digits
        //If value has no decimal digits, show a 0 as decimal digit
        String externalPrice="12.06";
        String expectedResultD = elementText.getText();
        Assert.assertEquals(externalPrice, expectedResultD);
        System.out.println("If value has no decimal digits, show a 0 as decimal digit " +expectedResultD);


        label.click();
        label.clear();
        label.sendKeys("Storage surcharge");
        value.sendKeys("0.3");
        checkIcon.click();
        //Verify that if value has more than 2 decimal digits, round to 2 decimal digits
        String storagePrice="12.36";
        String expectedResultE = elementText.getText();
        Assert.assertEquals(storagePrice, expectedResultE);
        System.out.println("If value has more than 2 decimal digits, round to 2 decimal digits " + expectedResultE);


//3. Remove price component: Internal surcharge
        //Hover row
        WebElement InternalSurLabel = driver.findElement(By.xpath("(//div[@class='w-16'])[4]"));
        Actions action1=new Actions(driver);
        action1.moveToElement(InternalSurLabel).perform();
        //Click on ‘Trash’ icon
        WebElement componentToRemove = driver.findElement(By.xpath("(//i[@class='fas fa-trash-alt'])[4]"));
        componentToRemove.click();
        //Verify Expected Results A.
        String actualSumResult="11.59";
        String expectedSumResult = elementText.getText();
        Assert.assertEquals(actualSumResult, expectedSumResult);
        System.out.println(expectedSumResult);

//4. Edit price component: Storage surcharge
        //Hover row
        WebElement storageEl =driver.findElement(By.xpath("(//div[@class='w-16'])[5]"));
        Actions action2=new Actions(driver);
        action2.moveToElement(storageEl).perform();
        //Click on ‘Pencil’ icon
        WebElement pencil5 = driver.findElement(By.xpath("(//i[@class='fas fa-pencil-alt'])[5]"));
        pencil5.click();
        //Enter new label: ‘T’
        WebElement newLabel = driver.findElement(By.xpath("(//div[@class='flex-grow flex flex-col']//input)[1]"));
        newLabel.click();
        newLabel.clear();
        newLabel.sendKeys("T");
        //Verify Expected Results C
        //get the error message
        WebElement actualErrorMsg = driver.findElement(By.xpath("//p[text()=' This label is too short! ']"));
        //If input is invalid, restore last valid state
        String errorMsg = actualErrorMsg.getText();
        Assert.assertEquals("This label is too short!", errorMsg);
        newLabel = driver.findElement(By.xpath("(//div[@class='flex-grow flex flex-col']//input)[1]"));
        newLabel.click();
        newLabel.clear();
        newLabel.sendKeys("Storage surcharge");


//5. Edit price component: Scrap surcharge
        //Hover row
        WebElement scrapEl =driver.findElement(By.xpath("(//div[@class='w-16'])[3]"));
        Actions action3=new Actions(driver);
        action3.moveToElement(scrapEl).perform();
        //Click on ‘Pencil’ icon
        WebElement pencil3 = driver.findElement(By.xpath("(//i[@class='fas fa-pencil-alt'])[3]"));
        pencil3.click();
        // Enter new value: -2.15
        WebElement negativeValue = driver.findElement(By.xpath("//input[@class='text-right focus:border-black focus:text-black']"));
        negativeValue.click();
        negativeValue.clear();
        negativeValue.sendKeys("-2.15");
        WebElement valueErrorMsg = driver.findElement(By.xpath("//p[text()=' Cannot be negative! ']"));
        String inputValue  = valueErrorMsg.getText();
        //Click on ‘Check’ icon
        WebElement checkIcon3 = driver.findElement(By.xpath("(//i[@class='fas fa-check'])[3]"));
        checkIcon3.click();
        // Verify Expected Results D:
        //a.Values cannot be negative
        //b.If input is invalid, restore last valid state
        System.out.println(inputValue);


//6. Edit price component: Alloy surcharge
        //Hover row
        WebElement alloyEl = driver.findElement(By.xpath("(//div[@class='w-16'])[2]"));
        Actions action4=new Actions(driver);
        action4.moveToElement(alloyEl).perform();
        //Click on ‘Pencil’ icon
        WebElement pencilIcon = driver.findElement(By.xpath("(//i[@class='fas fa-pencil-alt'])[2]"));
        pencilIcon.click();
        //Enter new value: 1.79
        WebElement newValue2 = driver.findElement(By.xpath("//input[@class='text-right focus:border-black focus:text-black']"));
        newValue2.click();
        newValue2.clear();
        newValue2.sendKeys("1.79");
        //Click on ‘Check’ icon
        WebElement checkIcon2 = driver.findElement(By.xpath("(//i[@class='fas fa-check'])[2]"));
        checkIcon2.click();
        //Verify Expected Results A. Displayed sum shows correct sum
        elementText = driver.findElement(By.xpath("//span[@class='font-bold']"));
        String expectedResult2 = elementText.getText();
        Assert.assertEquals("11.23", expectedResult2);

        driver.quit();
    }

}

