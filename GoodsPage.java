import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoodsPage {
    private static String URL_MATCH = "detail";
    //xpath  к ссылке на корзину, если корзина не пуста
    private String xpath = "//a[contains(.,'Корзина') and count(./span)>1]";
    private String countBag;
    private WebDriver driver;
    private WebDriverWait wait;
    public String Name;
    public String Cost;

    @FindBy(xpath = "//span[@class='price']/ancestor::span/span[1]")
    private WebElement name;

    @FindBy(xpath = "//span[@class='price-number']/span")
    private WebElement cost;

    @FindBy(xpath = "//button[contains(.,'Добавить в корзину')]")
    private WebElement addToBag;

    @FindBy(xpath = "//a[contains(.,'Корзина')]/span")
    private WebElement infoBag;

    public GoodsPage(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.urlContains(URL_MATCH));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
//сохранение названия и стоимсоти товара
    public String getInfo() {
        Name = name.getText();
        Cost = cost.getText();
        return Name;
    }

    public int addToBag() {
     /*получение текста  из Span(значок корзины). если корзина пуста, то span один с текстом "корзина", если есть товар,
     то добавляется еще span с количеством товаров*/
        countBag = infoBag.getText();
        addToBag.click();
        //ожидаем смены числа товаров, где значок корзины
        if(checkString(countBag)) {
            wait.until(ExpectedConditions.textToBePresentInElement(infoBag,Integer.toString(Integer.parseInt(countBag)+1)));
            return Integer.parseInt(countBag);
        }
        else return 0;
    }
    //строка xpath для перехода в корзину имеет переменную, поэтому в аннотацию добавить не можем
    public String checkXpath() {
        if (checkString(countBag)) {
            xpath = "//a[contains(.,'Корзина') and ./span[contains(.,'" + (Integer.parseInt(countBag) + 1) + "')]]";
        }
        return xpath;
    }
    //переход в корзину
    public void Bag() {
        driver.findElement(By.xpath(checkXpath())).click();
    }
    //проверка возможности перевода строки в число методом Integer.parseInt
    public static boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
