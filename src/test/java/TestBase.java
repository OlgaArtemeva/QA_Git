import java.awt.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Browser;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


//import java.util.logging.Logger;


public class TestBase {

    final static Logger logger = LoggerFactory.getLogger(TestBase.class);
    private ScreenRecorder sr;
    String recordsFolder = "src/test/resources/records";
    WebDriver wd;
    String browser;

    @BeforeTest
    public void prepare() throws InterruptedException {
//        wd = new ChromeDriver();
        deleteAllRecordings();

        /* */
        String path;

        browser = System.getProperty("browser"); //-Dbrowser=chrome
        logger.info("Running test system property browser set to " + browser);

        if (browser.equals(Browser.CHROME.browserName())) {
            path = System.getenv("chromeDriver"); // chromeDriver=/Users/ilya/telran/Tools/chromedriver
            System.setProperty("webdriver.chrome.driver", path);
            wd = new ChromeDriver();
        } else if (browser.equals(Browser.FIREFOX.browserName())) {
            path = System.getenv("firefoxDriver"); // firefoxDriver=/Users/ilya/telran/Tools/geckodriver
            System.setProperty("webdriver.gecko.driver", path);
            wd = new FirefoxDriver();
//        } else if (browser.equals(Browser.OPERA.browserName())) {
//            path = System.getenv("operaDriver");
//            System.setProperty("webdriver.opera.driver", path);
//            wd = new OperaDriver();
        } else if (browser.equals(Browser.SAFARI.browserName())) {
            path = System.getenv("safariDriver");
            System.setProperty("webdriver.safari.driver", path);
            wd = new SafariDriver();
        } else if (browser.equals(Browser.EDGE.browserName())) {
            path = System.getenv("edgeDriver");
            System.setProperty("webdriver.ms edge.driver", path);
            wd = new EdgeDriver();
        } else {
//            System.out.println(Browser.EDGE.browserName());
            logger.error("No supported browser specified. Supported browsers: chrome, firefox, edge, opera");
        }
        /* */
        wd.get("https://derrick686.softr.app/login");
        wd.manage().

                window().

                maximize();
        logger.info("Starting AllAuthorization");
    }


    @AfterTest
    public void after() throws InterruptedException {
//        wd.close();
        wd.quit();
        Thread.sleep(1000);
    }

    //    authorization
    public void auth(String email, String password) throws InterruptedException {
        Thread.sleep(1000);
        jumpDown();
        WebElement authorizationEmail = wd.findElement(By.xpath("/html/body/div[1]/section/div/div/div/div/div[2]/div[3]/input"));
        authorizationEmail.click();
        authorizationEmail.clear();
        authorizationEmail.sendKeys(email);
        Thread.sleep(2000);

        WebElement authorizationPassword = wd.findElement(By.xpath("/html/body/div[1]/section/div/div/div/div/div[2]/div[4]/div/input"));
        authorizationPassword.click();
        authorizationPassword.clear();
        authorizationPassword.sendKeys(password);
        Thread.sleep(1000);

//        jumpDown();
        Thread.sleep(500);
        WebElement signIn = wd.findElement(By.id("sw-sign-in-submit-btn"));
        signIn.click();
        Thread.sleep(2000);
    }

    public void jumpDown() {
        Actions actions = new Actions(wd);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
    }

    public void searchInClients(String searchInClients) throws InterruptedException {
        Thread.sleep(1000);
//        WebElement searchResult = wd.findElement(By.className("search"));
        WebElement searchResult = wd.findElement(By.xpath("/html/body/div[1]/section[2]/div[1]/div/div/div/input"));
        searchResult.click();
        searchResult.clear();
        searchResult.sendKeys(searchInClients);
        Thread.sleep(4000);
    }

    //    authorization check
    public void authCheck(Boolean flag) throws InterruptedException {
        String source = wd.getPageSource();
        Thread.sleep(500);
        String errorMessage = "Invalid email or password";
        System.out.println(source.contains(errorMessage));
        Boolean containsText = source.contains(errorMessage);
        Assert.assertEquals(containsText, flag); //  результат выполнения теста (выводится в "Total tests run: 2, Passes: 1, Failures: 1, Skips: 0")
        Thread.sleep(500);
    }

    //    authorization check for class AllAuthorization
    public void authCheckAll(String errorMessage, Boolean flag) throws InterruptedException {
        String source = wd.getPageSource();
        Thread.sleep(500);
        System.out.println(source.contains(errorMessage));
        Boolean containsText = source.contains(errorMessage);
        Assert.assertEquals(containsText, flag); //  результат выполнения теста (выводится в "Total tests run: 2, Passes: 1, Failures: 1, Skips: 0")
        String result = "";
        Thread.sleep(500);
    }

    // logout of client account
    public void logoutClient() throws InterruptedException {
        WebElement authOut = wd.findElement(By.id("navbarDropdown"));
        authOut.click();
        Thread.sleep(500);
        WebElement signOut = wd.findElement(By.xpath("//*[@id=\"home-header3\"]/div/div[1]/ul/li[3]/div/a/span/span"));
//        WebElement signOut = wd.findElement(By.id("\"home-header3\""));
        signOut.click();
        Thread.sleep(500);

        WebElement login = wd.findElement(By.xpath("//*[@id=\"home-header2\"]/div/div[1]/ul/li[2]/a"));
        login.click();
        Thread.sleep(500);
    }

    // logout of manager or consultant account
    public void logoutManagerConsultant() throws InterruptedException {
        WebElement authOut = wd.findElement(By.id("navbarDropdown"));
        authOut.click();
        Thread.sleep(500);

        WebElement signOut = wd.findElement(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[5]/div/a/span/span"));
        signOut.click();
        Thread.sleep(500);

        WebElement login = wd.findElement(By.xpath("//*[@id=\"home-header2\"]/div/div[1]/ul/li[2]/a"));
        login.click();
        Thread.sleep(500);
    }

    public void logoutIlya() throws InterruptedException {
        wd.findElement(By.id("navbarDropdown")).click();
        //Find element by class name via cssSelector
        //Attention to that spaces in class name are replaced by dots for css selector
        wd.findElement(By.cssSelector(".d-item.d-flex.justify-content-start.align-items-center.navigate")).click();

        //Second option: locate a link by its text
        //wd.findElement(By.partialLinkText("Sign Out")).click();

        // не от Ильи
        WebElement login = wd.findElement(By.xpath("//*[@id=\"home-header2\"]/div/div[1]/ul/li[2]/a"));
        login.click();
        Thread.sleep(500);
    }

    public String takeScreenshot() {
        File tmp = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("src/test/resources/screenshots/screen" + System.currentTimeMillis() + ".png");
        try {
            screenshot.createNewFile();
            Files.copy(tmp, screenshot); // обязательно должен быть загружен из библиотеки гугл
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "Failed to create a screenshot";
        }
        return screenshot.getAbsolutePath();
    }

    public void startRecording() {
        File file = new File(recordsFolder);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //  import java.awt.Dimension; (разместить на самом верху)
        Rectangle captureSize = new Rectangle(0, 0, dimension.width, dimension.height);
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        logger.info("Starting screen recording");
        try {
            sr = new Recorder(gc, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_MJPG,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, "MyVideo");
            sr.start();   //import org.monte.screenrecorder.ScreenRecorder;
        } catch (IOException | AWTException e1) {
            logger.error(e1.getMessage());
        }
    }

    public void stopRecoding() {
        logger.info("Stopping screen recording");
        try {
            sr.stop();   //import org.monte.screenrecorder.ScreenRecorder;
        } catch (IOException e1) {
            logger.error(e1.getMessage());
        }
    }

    public void deleteAllRecordings() {
        File dir = new File(recordsFolder);
        for (File f : dir.listFiles()) {
            try {
                f.delete();
            } catch (Exception e) {
                logger.error("Error while cleaning Records folder " + e.getMessage());
            }
        }
        logger.info("Cleaned Records folder");
    }

}
