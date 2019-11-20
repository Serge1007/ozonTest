import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MusicPage {
    //переход из музыки в категорию винил, попутно закрыв сообщение куки
    private String URL_MATCH = "muzyka";
    private WebDriver driver;

    @FindBy(xpath = "//a[contains(.,'Виниловые пластинки')]")
    private WebElement vinyl;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement cookieClose;

    public MusicPage(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.urlContains(URL_MATCH));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void cookieClose(){
        cookieClose.click();
    }
    public void openCategoryVinul() {
        vinyl.click();
    }
}
