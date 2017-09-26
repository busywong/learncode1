//注意 此代码没有数据清理的内容，请大家自行添加
package testAppium;
 
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
 
import java.io.File;
import java.net.URL;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;;
 
/**
 * Appium Android Demo
 * @author yuqi
 *
 */
public class teachercase1 {
    private AndroidDriver<AndroidElement> driver;
    private WebDriverWait wait;
    private int pics;
 
   @BeforeClass
    public void setUp() throws Exception {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, "e9de920d"); 
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ZUK"); 
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard",true);
        capabilities.setCapability("resetKeyboard", true);
//        capabilities.setCapability("automationName","Selendroid");
//        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.netease.cloudalbum");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
 
    @Test
    public void waitLaunch() throws InterruptedException {
 
            wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_logo")));
 
    }
    @Test(dependsOnMethods = "waitLaunch")
    public void login(){
        driver.findElement(By.id("UserName")).sendKeys("rpmmstest@163.com");
        driver.findElement(By.id("PassWord")).click();
        driver.findElement(By.id("PassWord")).sendKeys("qa123456");
        driver.hideKeyboard();
        driver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
        Assert.assertTrue(driver.findElementByName("本地相册").isDisplayed(),"登录失败");
        driver.findElement(By.name("163photo")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("image_photo")));
        pics = driver.findElements(By.id("image_photo")).size();
        driver.pressKeyCode(4);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test( dependsOnMethods= "login")
    public void cloudalbum(){
        driver.swipe(100, 200, 600, 200, 200);
        driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("我的云相册")));
    }
     
    @Test(dependsOnMethods = "cloudalbum",dataProvider = "data")
    public void download(String albumname){
 
        driver.findElement(By.name(albumname)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(albumname)));
        driver.findElements(By.id("cloud_image_photo")).get(0).click();
        driver.findElement(By.id("photo_save_btn")).click();
         
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.pressKeyCode(4);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(albumname)));
        driver.pressKeyCode(4);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("我的云相册")));
         
    }
     
    @Test(dependsOnMethods = "download")
    public void getpics(){
        driver.swipe(100, 200, 600, 200, 200);
        driver.findElement(By.id("g_slidemenu_local_txt")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
        driver.findElement(By.name("163photo")).click();
        pics += 2;
        Assert.assertEquals(driver.findElements(By.id("image_photo")).size(), pics);
    }
     
    @DataProvider
    public Object[][] data(){
        return new Object[][]{
                {"123"},{"手机相册"}
        };
    }
}