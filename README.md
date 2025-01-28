# Login Automation Testing Project

This project demonstrates automated testing of a login functionality using Selenium WebDriver with Java. The automation script tests both valid and invalid login scenarios on a sample website.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Project Structure](#project-structure)
4. [Running the Tests](#running-the-tests)
   - [Running from IDE](#method-1-running-from-ide)
   - [Running from Command Line](#method-2-running-from-command-line)
5. [Test Cases](#test-cases)
6. [Logging](#logging)
7. [Troubleshooting](#troubleshooting)
8. [Contact Me](#contact-me)

## Prerequisites

Before running the automation script, ensure you have the following installed:

- **Java Development Kit (JDK)**
  - Version 8 or higher
  - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
  - Set the `JAVA_HOME` environment variable

- **Maven**
  - Latest version
  - [Download Maven](https://maven.apache.org/download.cgi)
  - Add Maven to the system `PATH`

- **IDE (Integrated Development Environment)**
  - IntelliJ IDEA (recommended)
  - [Download IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
  - Alternative: Eclipse IDE

- **Web Browser**
  - Google Chrome (latest version)
  - [Download Chrome](https://www.google.com/chrome/)

## Project Setup

1. Clone or download the project to your local machine:
   ```bash
   git clone https://github.com/Isuru-Pradeep/Automating-Login-Functionality-Validation.git
   cd LoginAutomation
   ```

2. Open the project in IntelliJ IDEA:
   - Go to **File** → **Open**
   - Navigate to the project directory
   - Select the `pom.xml` file
   - Choose "Open as Project"

3. Update `pom.xml` with required dependencies:
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.seleniumhq.selenium</groupId>
           <artifactId>selenium-java</artifactId>
           <version>4.16.1</version>
       </dependency>
   </dependencies>
   ```

4. Maven will automatically download all required dependencies.

## Project Structure

```
LoginAutomation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               └── LoginAutomation.java
├── pom.xml
└── README.md
```

## Running the Tests

### Method 1: Running from IDE

1. Open the `LoginAutomation.java` file.
2. Right-click in the editor.
3. Select "Run 'LoginAutomation.main()'."

### Method 2: Running from Command Line

1. Navigate to the project directory.
2. Run the following commands:
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="org.example.LoginAutomation"
   ```

## Test Cases

The automation script includes two test scenarios:

1. **Valid Login Test**
   - Username: `tomsmith`
   - Password: `SuperSecretPassword!`
   - Expected: Successful login

2. **Invalid Login Test**
   - Username: `wronguser`
   - Password: `wrongpassword`
   - Expected: Failed login with an error message

## Logging

- Test execution logs are saved in the project directory under `login_automation.log`.
- Check logs for detailed information about test execution.

## Troubleshooting

1. **ChromeDriver Issues**
   - The project uses Selenium's built-in WebDriver manager.
   - No manual driver download is required.
   - If you see driver-related errors, ensure Chrome browser is up to date.

2. **Element Not Found Exceptions**
   - Check if the website is accessible.
   - Verify your internet connection.
   - Ensure proper wait times are set.

3. **Maven Dependencies**
   - Run `mvn clean install` to refresh dependencies.
   - Check Maven settings in your IDE.
   - Verify `pom.xml` is properly configured.


## Contact Me

- [LinkedIn](http://linkedin.com/in/isuru-pradeep)
- [Email](mailto:pradeepisuru31@gmail.com)
