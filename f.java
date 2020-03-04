import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class f {
	public static void main(String[] args) throws InterruptedException {
		Scanner scan = new Scanner(System.in);
		// "35um";// ivmh_
		// "4iiiii"
		/*
		 * System.out.print("Username: "); String user = scan.next();
		 * System.out.print("Password: "); String pass = scan.next();
		 * System.out.print("User followers: "); String userToFollowFollowers =
		 * scan.next(); System.out.print("Wave wait: "); long waitTime =
		 * scan.nextLong();
		 */
		String user = "35um";
		String pass = "mmmmmm";
		String userToFollowFollowers = "iih66";
		long waitTime = 120;
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		ChromeOptions options = new ChromeOptions();
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get("https://www.instagram.com/" + userToFollowFollowers + "/");
		try {
			// login first
			driver.findElement(
					By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[3]/div/span/a[1]/button")).click();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			driver.findElement(By.name("username")).sendKeys(user);
			driver.findElement(By.name("password")).sendKeys(pass);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button/div")));
			driver.findElement(
					By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button/div"))
					.click();
			Thread.sleep(3000);
			/*
			 * try { DoownLoadPic(driver, user); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 * 
			 * // trying to list followers
			 * 
			 * List<String> folist = new ArrayList<String>(); folist = listf(driver,user);
			 * System.out.println("SCRAPED "+folist.size()+" FOLLOWERS NAMES");
			 * 
			 * //UNFOLLOW NONFOLLOWERS SPREE unfollown(driver, user, folist);
			 */

			// follow spree
			int followed = 0;
			for (int i = 1; i <= 16; i++) {
				Thread.sleep(800);
				driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a/span"))
						.click();
				System.out.println("");
				System.out.println("wave number ---: " + i + " Total Followed ---: " + followed);
				followed += newfollow(driver,waitTime);
			}
			System.out.println("closing Chrome driver - have a good night");
			driver.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("closing chrome driver");
			driver.close();
		}

	}

	// this method lists current followers
	public static List<String> listf(WebDriver d, String u) throws InterruptedException {
		List<String> flist = new ArrayList<String>();
		d.get("https://www.instagram.com/" + u + "/");
		Thread.sleep(1000);
		d.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a/span")).click();
		Thread.sleep(1000);
		JavascriptExecutor jsExec = (JavascriptExecutor) d;
		for (int i = 0; i < 100; i++) {
			jsExec.executeScript("document.querySelector('div[role=dialog] ul').parentNode.scrollTop=100000000");
			Thread.sleep(200);
		}
		Thread.sleep(800);

		List<WebElement> listofItems = d.findElements(By.className("wo9IH"));
		WebDriverWait wait2 = new WebDriverWait(d, 90);
		for (int i = 1; i <= listofItems.size(); i++) {
			listofItems = d.findElements(By.className("wo9IH"));
			wait2.until(ExpectedConditions.visibilityOf(listofItems.get(i - 1)));
			flist.add(listofItems.get(i - 1).findElement(By.className("d7ByH")).getText());

		}
		d.navigate().back();
		Thread.sleep(800);
		return flist;

	}

	// this method unfollows non-followers
	public static void unfollown(WebDriver driver, String u, List<String> followers) throws InterruptedException {
		driver.get("https://www.instagram.com/" + u + "/");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[3]/a/span")).click();
		Thread.sleep(1000);
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		for (int i = 0; i < 250; i++) {
			jsExec.executeScript("document.querySelector('div[role=dialog] ul').parentNode.scrollTop=100000000");
			Thread.sleep(200);
		}
		Thread.sleep(800);

		List<WebElement> listofItems = driver.findElements(By.className("wo9IH"));
		WebDriverWait wait2 = new WebDriverWait(driver, 90);
		for (int i = 1; i <= 199; i++) {
			listofItems = driver.findElements(By.className("wo9IH"));
			wait2.until(ExpectedConditions.visibilityOf(listofItems.get(i - 1)));
			WebElement btn = listofItems.get(i - 1).findElement(By.tagName("button"));
			String currentUser = listofItems.get(i - 1).findElement(By.className("d7ByH")).getText();

			if (followers.contains(currentUser)) {

				System.out.println(currentUser + " is a follower");

			} else {
				btn.click();
				Thread.sleep(800);
				driver.findElement(By.xpath("/html/body/div[4]/div/div/div[3]/button[1]")).click();
				Thread.sleep(800);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				System.out.println("unfollowed " + currentUser);
				Thread.sleep(800);

			}

		}
	}

	// this method downloads all pics and videos
	public static void DoownLoadPic(WebDriver d, String u) throws InterruptedException, IOException {

		d.get("https://www.instagram.com/" + u + "/");
		Thread.sleep(1000);

		URL imageURL;
		BufferedImage saveImage;
		int n = Integer.parseInt(
				d.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[1]/span/span"))
						.getText());
		d.findElement(By.className("_9AhH0")).click();
		if (d.findElements(By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div[1]/img")).size() > 0) {
			imageURL = new URL(d.findElement(By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div[1]/img"))
					.getAttribute("src"));
			saveImage = ImageIO.read(imageURL);
			ImageIO.write(saveImage, "png", new File("img-" + u + "-" + 0 + ".png"));
			d.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/a")).click();
		} else {
 
			System.out.println("not an img");
			imageURL = new URL(
					d.findElement(By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div/div[1]/div/video"))
							.getAttribute("src"));
			saveImage = ImageIO.read(imageURL);
			ImageIO.write(saveImage, "mp4", new File("vid-" + u + "-" + 0 + ".mp4"));
			d.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/a")).click();

		}

		for (int z = 1; z < n; z++) {
			Thread.sleep(1000);
			if (d.findElements(By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div[1]/img")).size() > 0) {
				imageURL = new URL(
						d.findElement(By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div[1]/img"))
								.getAttribute("src"));
				saveImage = ImageIO.read(imageURL);
				ImageIO.write(saveImage, "png", new File("img-" + u + "-" + z + ".png"));
				try {
					d.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/a[2]")).click();
				} catch (Exception ex) {
					z += n;
				}
			} else {

				System.out.println("not an img");
				imageURL = new URL(d
						.findElement(
								By.xpath("/html/body/div[3]/div[2]/div/article/div[1]/div/div/div/div[1]/div/video"))
						.getAttribute("src"));
				File file = new File("C:\\Users\\qna9\\eclipse-workspace\\WEB\\vid-" + u + "-" + z + ".mp4");
				file.getParentFile().mkdirs();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(imageURL.openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(
						"C:/Users/qna9/eclipse-workspace/WEB/vid-" + u + "-" + z + ".mp4");

				int count = 0;
				byte[] b = new byte[100];

				while ((count = bufferedInputStream.read(b)) != -1) {
					fileOutputStream.write(b, 0, count);
				}
				try {
					d.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/a[2]")).click();
				} catch (Exception ex) {
					z += n;
				}
			}

		}

	}

	public static int newfollow(WebDriver driver,long total) throws InterruptedException {
		int followed = 0;
		Thread.sleep(20000);
		for (int i = 1; i <= 12; i++) {
			WebElement current = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/ul/div/li[" + i + "]"));
			WebElement btn = current.findElement(By.tagName("button"));
			if (btn.getText().equals("Follow")) {
				btn.click();
				followed++;
				Thread.sleep(1000);
				System.out.println("followed" + " "
						+ current.findElement(By.cssSelector("a[class='FPmhX notranslate _0imsa ']")).getText());
			} else
				System.out.println("already following" + " "
						+ current.findElement(By.cssSelector("a[class='FPmhX notranslate _0imsa ']")).getText());

		}
		driver.navigate().back();

		long startTime = System.currentTimeMillis();

		for (int i = 1; i <= total; i++) {
			try {
				Thread.sleep(1000L);
				printProgress(startTime, total, i);
			} catch (InterruptedException e) {
			}
		}

		driver.navigate().refresh();
		return followed;

	}

	public static Boolean isDisplayed(WebElement element, String locator) {

		Boolean passFail = false;

		try {
			if (element.isDisplayed())
				passFail = true;
		} catch (NullPointerException | NoSuchElementException e) {
			System.err.println("Unable to locate element '" + locator + "'");
		} catch (Exception e) {
			System.err.println("Unable to check display status of element '" + locator + "'");
			e.printStackTrace();
		}

		return passFail;

	}

	private static void printProgress(long startTime, long total, long current) {
		long eta = current == 0 ? 0 : (total - current) * (System.currentTimeMillis() - startTime) / current;

		String etaHms = current == 0 ? "N/A"
				: String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
						TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
						TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

		StringBuilder string = new StringBuilder(140);
		int percent = (int) (current * 100 / total);
		string.append('\r')
				.append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
				.append(String.format(" %d%% [", percent)).append(String.join("", Collections.nCopies(percent, "=")))
				.append('>').append(String.join("", Collections.nCopies(100 - percent, " "))).append(']')
				.append(String.join("",
						Collections.nCopies(current == 0 ? (int) (Math.log10(total))
								: (int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
				.append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

		System.out.print(string);
		System.out.flush();
	}

	// this method is outdated
	public static void follow(WebDriver driver) throws InterruptedException {
		Thread.sleep(3000);
		// WebDriverWait wait = new WebDriverWait(driver, 60);
		List<WebElement> listofItems = driver.findElements(By.className("/html/body/div[3]/div/div[2]/ul/div/li"));
		WebDriverWait wait2 = new WebDriverWait(driver, 90);

		int x = 1;
		int randomNum = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);

		for (int i = 1; i <= listofItems.size(); i++) {
			/*
			 * Getting the list of items again so that when the page is navigated back to,
			 * then the list of items will be refreshed again
			 */
			listofItems = driver.findElements(By.className("/html/body/div[3]/div/div[2]/ul/div/li"));

			// Waiting for the element to be visible
			// Used (i-1) because the list's item start with 0th index, like in an array
			wait2.until(ExpectedConditions.visibilityOf(listofItems.get(i - 1)));

			// Clicking on the first element
			WebElement btn = listofItems.get(i - 1).findElement(By.tagName("button"));
			System.out
					.println("trying to follow " + listofItems.get(i - 1).findElement(By.className("d7ByH")).getText());

			if (btn.getAttribute("innerHTML").equalsIgnoreCase("follow")) {
				btn.click();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				System.out.print(i + " followed\t--");
				System.out.println("pass");
				try {

					Thread.sleep(x + randomNum);
				} catch (InterruptedException ex) {
					// do stuff
				}
				x++;
			} else
				System.out.println("already following");

		}
		driver.navigate().back();
		int i = 180;
		while (i > 0) {
			System.out.println("Remaining: " + i + " seconds");
			try {
				i--;
				Thread.sleep(1000L); // 1000L = 1000ms = 1 second
			} catch (InterruptedException e) {
				// I don't think you need to do anything for your particular problem
			}
		}

		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
}
