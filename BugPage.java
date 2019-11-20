import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BugPage {

    private static String URL_MATCH = "cart";

    private WebDriver driver;
    //информация о товарах
    private Map<String, String> resultInfo = new HashMap<String, String>();

    @FindBy(xpath = "//a[@class='title']/span")
    private List<WebElement> infoName;

    @FindBy(xpath = "//div[@class='price-block-part']/span")
    private List<WebElement> infoCost;

    @FindBy(xpath = "//span[contains(.,'Общая стоимость')]/../span[2]")
    private WebElement commonCost;

    @FindBy(xpath = "//span[contains(.,'Удалить выбранные')]")
    private WebElement deleteChosen;

    @FindBy(xpath = "//button[contains(.,'Удалить')]")
    private WebElement delete;

    @FindBy(xpath = "//h1[contains(.,'Корзина пуста')]")
    private WebElement bagIsEmpty;

    public BugPage(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.urlContains(URL_MATCH));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
//запись информации о товарах из листа объектов
    public Map<String, String> info() {
        for(int i = 0; i < infoName.size(); i++) {
            resultInfo.put(infoName.get(i).getText(),infoCost.get(i).getText());
                }
        return resultInfo;
    }
    //получение общей стоимсоти товаров в корзине
    public String returnCost(){
        return commonCost.getText();
    }
    //очистка корзины с подтверждением
    public void deleteGoods(){
        deleteChosen.click();
        delete.click();
    }
    //проверка пустой корзины
    public boolean bagIsEmpty(){
        return bagIsEmpty.isEnabled();
    }
}
