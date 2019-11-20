import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private String URL_MATCH = "ozon";
    private WebDriver driver;

    @FindBy(xpath = "//*[@value='Каталог']")
    private WebElement catalog;

    @FindBy(xpath = "//a[contains(.,'Музыка')]")
    private WebElement music;



    public HomePage(WebDriver driver, WebDriverWait wait) {
     //ждем смены урл
        wait.until(ExpectedConditions.urlContains(URL_MATCH));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='cookies-info']")));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public void openCategoryMusic() {
     //раскрытие каталога
        catalog.click();
     //выбор категории
        music.click();
    }
}
