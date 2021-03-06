package testAppium;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.List;
import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Appium Android Demo
 * @author yuqi
 *
 */
  
public class testcase4 {
    private AndroidDriver<AndroidElement> driver;
    @BeforeClass
    public void setUp() throws Exception {
    	
    	File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.UDID, "SGSO5HRCINJ77T5L");//devices ID
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus"); // 真机名字
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0"); //真机版本
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath()); //对应的被测APK文件路径
        

        capabilities.setCapability("unicodeKeyboard",true);//android输入法如果有问题开启这2项
     capabilities.setCapability("resetKeyboard", true);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
    private void login() {
        WebDriverWait wait = new WebDriverWait(driver, 40);
		//判断是否存在UserName输入框
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
        driver.findElement(By.id("UserName")).sendKeys("qq541005506@163.com");
        driver.findElement(By.id("UserName")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("PassWord")));
        driver.findElement(By.id("PassWord")).click();
        driver.findElement(By.id("PassWord")).sendKeys("wang199405017010");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//输入账号密码后登陆
        driver.findElement(By.id("login")).click();
    }
    
    public void swipeGuide()throws InterruptedException {
        // GuideActivity.java
        Dimension dimension;
        dimension = driver.manage().window().getSize();
        int SCREEN_WIDTH = dimension.getWidth();
        int SCREEN_HEIGHT = dimension.getHeight();

        System.out.println("被测设备宽高：" + SCREEN_WIDTH + "," + SCREEN_HEIGHT);
        Thread.sleep(5000);
        driver.swipe(SCREEN_WIDTH - 100, SCREEN_HEIGHT / 2, 100,  SCREEN_HEIGHT / 2, 2000);
        Thread.sleep(2000);
        driver.swipe(SCREEN_WIDTH - 100, SCREEN_HEIGHT / 2, 100, SCREEN_HEIGHT / 2, 2000);
        Thread.sleep(2000);
        driver.findElement(By.id("guide_btn")).click(); // 点击 立即体验
    }

    /**
     * 首次进入app，自动备份功能引导，跳过
     */
    public void hideAutoBackupGuide() {
        // AutoBackupGuideActivity.java

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("skipSet")));

        driver.findElement(By.id("skipSet")).click(); // 点击 跳过
    }
    @Test
    public void firstDemo() throws InterruptedException {
    	 swipeGuide();
         hideAutoBackupGuide();  
    	login();
        // 等待进入app
        WebDriverWait wait = new WebDriverWait(driver, 30);
       // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_num")));
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
         Assert.assertTrue(driver.findElement(By.name("本地相册")).isDisplayed(),"本地相册未展示");
         
         //用例1
         driver.findElements(By.id("local_album_name")).get(0).click();
         driver.findElements(By.id("image_photo")).get(0).click();
         
         //查看照片信息
         driver.findElement(By.id("photo_info_btn")).click();
         driver.findElement(By.name("确定")).click();
         
         //备份照片
         driver.findElement(By.id("photo_back_or_not")).click();
         driver.findElements(By.id("cloud_album_name")).get(0).click();//只有第一次备份或切换相册
         driver.findElement(By.name("确认备份")).click();
        // 验证照片是否备份   找提示备份成功的字符  或者到云端找
      
         //数据清理
         
         
        
 
      
    }
}