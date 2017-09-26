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
  
public class testAppium {
    private AndroidDriver<AndroidElement> driver;
    @BeforeClass
    public void setUp() throws Exception {
    	
    //	File classpathRoot = new File(System.getProperty("user.dir"));
      //  File appDir = new File(classpathRoot, "app");
       // File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.UDID, "SGSO5HRCINJ77T5L");//devices ID
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus"); // 真机名字
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0"); //真机版本
       // capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath()); //对应的被测APK文件路径
        

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
        driver.findElement(By.id("PassWord")).sendKeys("wang674081");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//输入账号密码后登陆
        driver.findElement(By.id("login")).click();
    }
    @Test
    public void firstDemo() throws InterruptedException {
    	
    	//login();
        // 等待进入app
        WebDriverWait wait = new WebDriverWait(driver, 30);
       // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_num")));
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
         Assert.assertTrue(driver.findElement(By.name("本地相册")).isDisplayed(),"本地相册未展示");
         
         driver.swipe(100, 200, 400, 200, 200);
         driver.findElement(By.name("云端相册")).click();
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("我的云相册")));
        int b= driver.findElements(By.id("cloud_album_name")).size();
        System.out.println("b="+b);
         driver.findElements(By.id("cloud_album_name")).get(0).click();
         
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("操 作")));
         driver.findElement(By.name("操 作")).click();
         driver.findElement(By.name("相册设置")).click();
         driver.findElement(By.id("createalbum_edit_name")).sendKeys("qwera");
         driver.findElement(By.name("确定")).click();
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("操 作")));
        // int a= driver.findElements(By.className("android.widget.RelativeLayout")).size();  a=7
         int a=driver.findElementsByAndroidUIAutomator("new UiSelector().className("+"android.widget.RelativeLayout"+").index(0)").size();
         System.out.println("a="+a);
       //  driver.findElement(By.id("pullToRefresh_internalGridViewId")).findElements(By.className("android.widget.RelativeLayout")).get(0).click();
         driver.findElementsByAndroidUIAutomator("new UiSelector().className(android.widget.RelativeLayout).index(0)").get(2).click();
         //driver.findElementByAndroidUIAutomator("new ")
         
        
        
        /*
        java.util.List<AndroidElement> lists=driver.findElements(By.className("android.widget.RelativeLayout"));
        for(AndroidElement Listx:lists)
        {
        	System.out.println(Listx);
        }
        // driver.findElements(By.className("android.widget.RelativeLayout")).get(0).click();*/
         driver.findElement(By.id("photo_save_btn")).click();
         //driver.f
         
         
         
         
         
         
         
         
         
        /*
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
        */
        //输入用户名和密码前由于起LE APP 用例开始前所以建议等待   提高用例健壮性。
        WebDriverWait wait2=new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_logo")));
        driver.findElements(By.className("android.widget.EditText")).get(0).sendKeys("qq541005506@163.com");

        driver.findElements(By.className("android.widget.EditText")).get(0).click();//获得执行焦点 避免出现问题
        //提高容错率

        driver.findElements(By.className("android.widget.EditText")).get(1).sendKeys("wang199405017010");

        driver.hideKeyboard();//收起键盘
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
        driver.findElement(By.id("login"));
        
        //登陆有发包的过程  需要等待
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
        //切换到云端相册
        driver.swipe(100, 200, 400, 200, 200);
        driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
        
        //打开android通知栏
        driver.openNotifications();
        Thread.sleep(2000);
        //关闭通知栏 back在code表中
        driver.pressKeyCode(4);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("我的云相册")));
          
        driver.findElement(By.name("123")).click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("123")));
        driver.findElements(By.id("cloud_image_photo")).get(0).click();
        
        Thread.sleep(2000);
        driver.closeApp();
        driver.launchApp();
        Thread.sleep(2000);
    }
}