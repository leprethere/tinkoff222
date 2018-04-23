package bellIntegrator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class TinkoffTest {

    private static WebDriver driver;
    private static String base_url;

    @BeforeClass
    public static void startWebDriver() {
        System.setProperty("webdriver.gecko.driver", "E:/Program Files/geckodriver.exe");
        base_url = "https://www.tinkoff.ru/";
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(base_url);

    }

    @Test
    public void userLogin() throws InterruptedException {

        driver.get(base_url + "payments");

        driver.findElement(By.cssSelector("a[href*=\"/payments/categories/kommunalnie-platezhi/\"")).click();
        if (driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[5]/div/div[2]/div/div/div/div[2]/div/div/div/span/span/span")).getText().contains("Москве") == true) {
            driver.findElement(By.cssSelector("a[href*=\"/zhku-moskva/\"")).click();
            TimeUnit.SECONDS.sleep(2);
        }
        else {
            driver.findElement(By.linkText("г. Москва")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.linkText("ЖКУ-Москва")).click();;
            TimeUnit.SECONDS.sleep(2);
        }

        driver.findElement(By.cssSelector("a[href*=\"/zhku-moskva/\"")).click();
        driver.findElement(By.cssSelector("a[href*=\"/zhku-moskva/oplata/\"")).click();

        //Заполняем данные
        WebElement payerCode = driver.findElement(By.id("payerCode"));
        payerCode.sendKeys("123451234");
        TimeUnit.SECONDS.sleep(2);

        assertTrue(driver.findElement(By.cssSelector(".ui-form-field-error-message ui-form-field-error-message_ui-form")).getText().contains("Поле неправильно заполнено"));
        TimeUnit.SECONDS.sleep(2);

        payerCode.clear();
        TimeUnit.SECONDS.sleep(2);
        payerCode.sendKeys("123451235");
        TimeUnit.SECONDS.sleep(2);

        driver.findElement(By.id("period")).sendKeys("12.232");
        TimeUnit.SECONDS.sleep(2);

        assertTrue(driver.findElement(By.cssSelector(".ui-form-field-error-message ui-form-field-error-message_ui-form")).getText().contains("Поле заполнено некорректно"));
        TimeUnit.SECONDS.sleep(2);

        //ввод корректного периода
        driver.findElement(By.id("period")).clear();
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.id("period")).sendKeys("12.2323");
        TimeUnit.SECONDS.sleep(2);

        //ввод суммы
        driver.findElement(By.linkText("ОПЛАТИТЬ ЖКУ В МОСКВЕ")).click();
        TimeUnit.SECONDS.sleep(2);

        WebElement checkClick = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[6]/div/div[1]/div/div/button"));
        checkClick.click();
        TimeUnit.SECONDS.sleep(2);
        checkClick.sendKeys("555");
        TimeUnit.SECONDS.sleep(2);
        checkClick.sendKeys(Keys.ENTER);

        if (driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[2]")).getText().contains("Поле обязательное") == true) {

            checkClick = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[1]/label/div/input"));
            checkClick.click();
            TimeUnit.SECONDS.sleep(2);
            checkClick.sendKeys("2");
            TimeUnit.SECONDS.sleep(2);
            checkClick.sendKeys(Keys.ENTER);

            if (driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[2]")).getText().contains("Минимум — 10") == true) {

                checkClick = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[1]/label/div/input"));
                checkClick.click();
                TimeUnit.SECONDS.sleep(2);
                checkClick.sendKeys("16543");
                TimeUnit.SECONDS.sleep(2);
                checkClick.sendKeys(Keys.ENTER);
            }
            if (driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[2]")).getText().contains("Максимум — 15 000") == true)
            {
                checkClick = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[4]/div/div[2]/div[1]/div/div/div/div[4]/div[1]/form/div[4]/div/div/div/div/div/div/div/div[1]/label/div/input"));
                checkClick.click();
                TimeUnit.SECONDS.sleep(2);
                checkClick.sendKeys("1500");
                TimeUnit.SECONDS.sleep(2);
                checkClick.sendKeys(Keys.ENTER);
            }
        }

        //возврат в коммунальные платежи
        driver.findElement(By.cssSelector("a[href*=\"/payments/categories/kommunalnie-platezhi/\"")).click();

        driver.findElement(By.xpath("//select[placeholder()='Название или ИНН получателя платежа']")).sendKeys("ЖКУ-Москва");

    }

    @AfterClass
    public static void tearDown() {

        driver.quit();
    }

}
