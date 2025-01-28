package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class LoginAutomation {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(LoginAutomation.class.getName());
    private static final String BASE_URL = "https://the-internet.herokuapp.com/login";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    // Page elements
    private static final By USERNAME_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By SUCCESS_MESSAGE = By.cssSelector(".flash.success");
    private static final By ERROR_MESSAGE = By.cssSelector(".flash.error");
    private static final By LOGOUT_BUTTON = By.cssSelector(".button.secondary");

    // Test data
    private static final String VALID_USERNAME = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";
    private static final String INVALID_USERNAME = "wronguser";
    private static final String INVALID_PASSWORD = "wrongpassword";

    public LoginAutomation(String browser) {
        setupLogger();
        initializeDriver(browser);
    }

    private void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("login_automation.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }

    private void initializeDriver(String browser) {
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            wait = new WebDriverWait(driver, TIMEOUT);
            LOGGER.info("Browser initialized: " + browser);
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    public void setup() {
        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(TIMEOUT);
            navigateToLoginPage();
        } catch (Exception e) {
            LOGGER.severe("Setup failed: " + e.getMessage());
            throw new RuntimeException("Setup failed", e);
        }
    }

    public void navigateToLoginPage() {
        driver.get(BASE_URL);
        LOGGER.info("Navigated to: " + BASE_URL);
    }

    public void logout() {
        try {
            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
            logoutButton.click();
            LOGGER.info("Logged out successfully");
            // Wait for the login page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_FIELD));
        } catch (Exception e) {
            LOGGER.warning("Logout failed: " + e.getMessage());
            // If logout fails, navigate back to login page
            navigateToLoginPage();
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            LOGGER.info("Browser closed");
        }
    }

    public LoginResult performLogin(String username, String password) {
        try {
            LOGGER.info("Attempting login with username: " + username);

            // Wait for and enter username
            WebElement usernameField = waitForElement(USERNAME_FIELD);
            clearAndType(usernameField, username);

            // Enter password
            WebElement passwordField = waitForElement(PASSWORD_FIELD);
            clearAndType(passwordField, password);

            // Click login button
            WebElement loginButton = waitForElement(LOGIN_BUTTON);
            loginButton.click();
            LOGGER.info("Login form submitted");

            // Check login status
            return checkLoginStatus();

        } catch (Exception e) {
            LOGGER.severe("Login process failed: " + e.getMessage());
            return new LoginResult(false, "Login process failed: " + e.getMessage());
        }
    }

    private WebElement waitForElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            LOGGER.severe("Element not found: " + locator);
            throw e;
        }
    }

    private void clearAndType(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            LOGGER.severe("Failed to enter text: " + e.getMessage());
            throw e;
        }
    }

    private LoginResult checkLoginStatus() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(SUCCESS_MESSAGE),
                    ExpectedConditions.presenceOfElementLocated(ERROR_MESSAGE)
            ));

            if (driver.findElements(SUCCESS_MESSAGE).size() > 0) {
                String successText = driver.findElement(SUCCESS_MESSAGE).getText();
                LOGGER.info("Login Successful: " + successText);
                return new LoginResult(true, successText);
            } else {
                String errorText = driver.findElement(ERROR_MESSAGE).getText();
                LOGGER.warning("Login Failed: " + errorText);
                return new LoginResult(false, errorText);
            }
        } catch (Exception e) {
            LOGGER.severe("Could not determine login status: " + e.getMessage());
            return new LoginResult(false, "Login status could not be determined");
        }
    }

    public static void main(String[] args) {
        LoginAutomation automation = new LoginAutomation("chrome");
        try {
            automation.setup();

            // Test valid login
            LOGGER.info("\nTesting valid credentials:");
            LoginResult validResult = automation.performLogin(VALID_USERNAME, VALID_PASSWORD);
            System.out.println(validResult);
            Thread.sleep(2000);

            // Logout before testing invalid credentials
            if (validResult.isSuccess()) {
                automation.logout();
            }

            // Test invalid login
            LOGGER.info("\nTesting invalid credentials:");
            LoginResult invalidResult = automation.performLogin(INVALID_USERNAME, INVALID_PASSWORD);
            System.out.println(invalidResult);
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            LOGGER.severe("Test interrupted: " + e.getMessage());
        } finally {
            automation.tearDown();
        }
    }
}

class LoginResult {
    private final boolean success;
    private final String message;

    public LoginResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return success ? "Login Successful: " + message : "Login Failed: " + message;
    }
}