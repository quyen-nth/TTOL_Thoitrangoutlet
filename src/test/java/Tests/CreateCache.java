package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateCache {
	WebDriver driver;
	
	String xpath_parentMenu = "//span[@class='title-text'][contains(text(),'%s')]";
	String xpath_childMenu ="//span[text()='%s']//parent::div//parent::a//parent::div//following-sibling::ul//a";

	@Test
	public void clickToLink() throws InterruptedException {
		Actions action = new Actions(driver);
		String parentUrl;
		String xpath_children;
		List<WebElement> listParents = driver.findElements(By.xpath("//span[@class='title-text']"));
		List <String> listParents_text = new ArrayList<String>();
		
		if(listParents.size() > 0) {
			for (WebElement element : listParents) {
				System.out.println(element.getText());
				listParents_text.add(element.getText());
			}
		}
		
		System.out.println(listParents_text.size());
		
		if(listParents_text.size() > 0) {
			for (String parentText : listParents_text) {
				parentUrl = String.format(xpath_parentMenu, parentText);
				
				System.out.println(parentUrl);
				
				WebElement element = driver.findElement(By.xpath(parentUrl));
				action.moveToElement(element);
				
				xpath_children = String.format(xpath_childMenu, parentText);
				List<WebElement> listChildrens = driver.findElements(By.xpath(xpath_children));
				List <String> listChildrenUrls = new ArrayList<String>();
				if(listChildrens.size() > 0)
				{
					for (WebElement webElement : listChildrens) {
						listChildrenUrls.add(webElement.getAttribute("href"));
					}
				}
				
				if(listChildrenUrls.size() > 0) {
					for (String string : listChildrenUrls) {
						System.out.println("Click to: " + string);
						driver.get(string);
					}
				}
			}
		}
	}

	@BeforeClass
	public void beforeClass() {
		String chromeDriver = System.getProperty("user.dir") + "//src//test//resources//chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://thoitrangoutlet.com");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
