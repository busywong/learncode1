package testAppium;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Appium Android Demo
 * @author yuqi
 *
 */
  
public class testcase1 {
    private AndroidDriver<AndroidElement> driver;
    @BeforeClass
    public void setUp() throws Exception {
    	File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.UDID, "0123456789ABCDEF");//devices ID
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus"); // 真机名字
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0"); //真机版本
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath()); //对应的被测APK文件路径
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
    @Test
    public void firstDemo() throws InterruptedException {
        // 等待进入app
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
          
        driver.swipe(100, 200, 400, 200, 200);
        driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
        driver.findElement(By.name("手机相册")).click();
        driver.findElement(By.name("操 作")).click();
        driver.findElements(By.id("text1")).get(0).click();
        driver.findElement(By.id("createalbum_edit_name")).sendKeys("123");
        driver.hideKeyboard();
        driver.findElement(By.id("createalbum_confirm_btn")).click();
        //等待相册名称编辑成功
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("123")));
        driver.findElements(By.id("cloud_image_photo")).get(0).click();
        driver.findElement(By.id("photo_save_btn")).click();
    }
}