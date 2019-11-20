import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OzonTest {

    //path to driver in my pc
    private String Path = "C:\\Users\\Сергей\\IdeaProjects\\testselenium\\drivers\\geckodriver.exe";
    private static WebDriver driver;
    private static WebDriverWait wait;
    //содержимое корзины
    private Map<String, String> goodsInfo;
    //переменные для рандомного выбора пластинок
    private int count, rnd, min = 1;
    //информация об именах, которую запоминаем на странице товара
    private String name1, name2;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", Path);
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 15);
        goodsInfo = new HashMap<String, String>();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
     //1
        driver.get("https://www.ozon.ru/");
    }

    @AfterClass
    public void tearDown(){
     //21 - close browser
        driver.close();
    }

    @Test
    public void test() {
     //2 - openCategoryMusic
        HomePage homePage = new HomePage(driver, wait);
        homePage.openCategoryMusic();
     //3 - openCategoryVinyl
        MusicPage musicPage = new MusicPage(driver, wait);
     //появляется окно о соглашении использовании куки, мешает добавлению в корзину, поэтому закрываем
        musicPage.cookieClose();
        musicPage.openCategoryVinul();
     //4and5 - checkList
        VinylPage vinylPage = new VinylPage(driver, wait);
        vinylPage.checkList();
        count = vinylPage.countOfElements;
        Assert.assertTrue(count > 0);
     //6 - generateNumber
        rnd = vinylPage.rnd(min, count);
     //7 - chooseGoods
        vinylPage.chooseGoods(rnd);
     //8 - rememberInfo
        GoodsPage goodsPage = new GoodsPage(driver, wait);
        name1 = goodsPage.getInfo();
     //9 - addToBag
        goodsPage.addToBag();
     //10 - openBag
        goodsPage.Bag();
        BugPage bugPage = new BugPage(driver, wait);
        goodsInfo=bugPage.info();
        Assert.assertTrue(goodsInfo.toString().contains(name1));
     //11 - returnToVinyl
        homePage.openCategoryMusic();
        musicPage.openCategoryVinul();
    //12 - generateNumber(значение количества элементов на странице может измениться, поэтому еще раз получаем count)
        vinylPage.checkList();
        count = vinylPage.countOfElements;
        rnd = vinylPage.rnd(min, count);
    //13 - chooseGoods
        vinylPage.chooseGoods(rnd);
    //14 - rememberInfo
        name2 = goodsPage.getInfo();
     //15and16 - addToBag
        Assert.assertEquals(goodsPage.addToBag(),2);
     //17 - openBag
        goodsPage.Bag();
    //18
        String cost1;
        String cost2;
        String commonCost;
    //common cost from bag
        String cost = bugPage.returnCost();
        cost = cost.substring(0, cost.length() - 1);
        commonCost = cost.replaceAll(" ", "");
        Integer.parseInt(commonCost);
     //multiple goods cost
        cost1=goodsInfo.get(name1);
        cost2=goodsInfo.get(name2);
        cost1 = cost1.substring(0, cost1.length() - 1);
        cost2 = cost2.substring(0, cost2.length() - 1);
        cost = cost1.replaceAll(" ", "");
        cost1 = cost2.replaceAll(" ", "");
        Integer.parseInt(cost);
        Integer.parseInt(cost1);
        Assert.assertEquals(cost+cost1,commonCost);
    //19 - delete goods
        bugPage.deleteGoods();
    //20 - empty bag
        Assert.assertTrue(bugPage.bagIsEmpty());
    }

}
