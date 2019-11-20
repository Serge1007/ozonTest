import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VinylPage {

    private static String URL_MATCH = "vinil";
    private WebDriver driver;
    int countOfElements;

    @FindBy(xpath = "//a[@class='tile-wrapper']")
    private List<WebElement> listOfAllWebElements;

    public VinylPage(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.urlContains(URL_MATCH));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
//подсчет количества виниловх плстинок на странице
    public void checkList() {
        countOfElements = listOfAllWebElements.size();
    }
//метода random в заданном диапозоне
    public int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
//выбор товара выбранного random
    public void chooseGoods(int rnd) {
        listOfAllWebElements.get(rnd).click();
    }
}
