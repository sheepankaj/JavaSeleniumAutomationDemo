# Java Selenium Automation Demo

A TestNG + Selenium WebDriver automation framework using the **Page Object Model (POM)** pattern for UI browser testing, with RestAssured for API testing.

## Project Structure

```
src/
├── base/
│   └── BaseTest.java
├── common/
│   └── CommonMethods.java
├── pages/
│   ├── GoogleSearchPage.java
│   └── SmitheryPage.java
├── api/
│   ├── base/
│   │   └── ApiBaseTest.java
│   └── models/
│       └── User.java
└── tests/
    ├── ui/
    │   ├── GoogleSearchTest.java
    │   └── SmitherySearchTest.java
    └── api/
        └── JsonPlaceholderTest.java
```

## Prerequisites

This section guides you through setting up everything needed to run the project. Follow each step carefully.

### 1. Java Development Kit (JDK) 21+

Selenium 4.x requires Java 11 or higher. This project uses **Java 21**.

**Installation:**
- **Windows**: Download from [Adoptium (Eclipse Temurin)](https://adoptium.net/) → Select JDK 21 → Download `.msi` installer → Run and follow the wizard.
- **macOS**: `brew install openjdk@21`
- **Linux (Ubuntu)**: `sudo apt-get update && sudo apt-get install openjdk-21-jdk`

**Verify installation:**
```bash
java -version
```
Expected output: `openjdk version "21.x.x"`

**Set JAVA_HOME (Windows):**
1. Search for "Environment Variables" in Windows Settings
2. Click "Edit the system environment variables"
3. Click "Environment Variables"
4. Under "System variables", click "New"
5. Variable name: `JAVA_HOME`
6. Variable value: Path to your JDK (e.g., `C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot`)
7. Click OK on all dialogs

### 2. Apache Maven 3.6+

Maven manages all project dependencies automatically — no manual JAR downloads needed.

**Installation:**
- **Windows**: Download from [Apache Maven](https://maven.apache.org/download.cgi) → Download `binaries.zip` → Extract to a folder (e.g., `C:\apache-maven-3.9.9`) → Add to PATH.
- **macOS**: `brew install maven`
- **Linux (Ubuntu)**: `sudo apt-get install maven`

**Add to PATH (Windows):**
1. Search for "Environment Variables"
2. Under "System variables", find `Path` → Edit
3. Add a new entry: `C:\apache-maven-3.9.9\bin`
4. Click OK on all dialogs

**Verify installation:**
```bash
mvn -version
```
Expected output: `Apache Maven 3.x.x`

> **Note:** If you use **IntelliJ IDEA**, it has built-in Maven support — you don't need to install Maven separately. IntelliJ will download dependencies automatically when you open the project.

### 3. Google Chrome Browser

Selenium drives the Chrome browser, so it must be installed on your system.

**Installation:**
- Download from [Google Chrome](https://www.google.com/chrome/) → Install the latest version.

**Verify installation:**
- Open Chrome and go to `chrome://version/` to see your version number.

### 4. Git (Optional but Recommended)

Git is needed if you want to clone the repository or push changes to GitHub.

**Installation:**
- **Windows**: Download from [git-scm.com](https://git-scm.com/download/win) → Run installer.
- **macOS**: `brew install git`
- **Linux (Ubuntu)**: `sudo apt-get install git`

**Verify installation:**
```bash
git --version
```

### 5. IntelliJ IDEA (Optional but Recommended)

IntelliJ IDEA provides the best development experience for Java + Selenium projects.

**Installation:**
- Download **IntelliJ IDEA Community** (free) or **Ultimate** from [jetbrains.com/idea](https://www.jetbrains.com/idea/download/)
- Run the installer and follow the wizard

**Import the project:**
1. Open IntelliJ IDEA
2. File → Open → Select the project folder
3. IntelliJ will auto-detect `pom.xml` and import Maven dependencies
4. Wait for dependency download to complete (bottom-right corner shows progress)

## Dependencies

| Dependency | Version |
|------------|---------|
| Selenium WebDriver | 4.50.0 |
| TestNG | 7.10.2 |
| RestAssured | 5.5.7 |
| Jackson Databind | 2.22.2 |

All dependencies are managed via Maven — no manual JAR downloads required.

## Running Tests

### Via Maven Command Line

```bash
mvn clean test
```

Tests are configured in `testng.xml` and executed by the Maven Surefire plugin.

### Via IntelliJ IDEA

1. Open `testng.xml` → Right-click → **Run 'testng.xml'**
2. Or open any test class → Right-click → **Run with TestNG**

### Test Reports

After running tests, reports are generated in:

- **Text reports**: `target/surefire-reports/`
- **IntelliJ**: Built-in TestNG runner shows results in the IDE

## Test Cases

### UI Tests

| Test | Description | Status |
|------|-------------|--------|
| `GoogleSearchTest` | Opens Google, searches for "Ireland", and validates results | ⚠️ **Expected to fail** — Google CAPTCHA blocks automated searches; the anti-CAPTCHA measures in place reduce but do not fully bypass Google's bot detection |
| `SmitherySearchTest` | Opens Smithery.ai, searches for "Hugging Face" MCP server, and validates results | ✅ **Passing** |

### API Tests

| Test | Description | Status |
|------|-------------|--------|
| `JsonPlaceholderTest` | Calls `GET /users` on JsonPlaceholder API, finds user with `id: 2`, and validates `name`, `username`, and `email` | ✅ **Passing** |

#### API Test Details

The API test uses **RestAssured** to make HTTP requests without launching a browser, making it faster and more reliable than UI tests.

- **Base URL**: `https://jsonplaceholder.typicode.com` — a free fake REST API for testing
- **Endpoint**: `GET /users` — returns a list of 10 mock users
- **Validation**: Filters the response for user `id: 2` and asserts:
  - `name` equals `"Ervin Howell"`
  - `username` equals `"Antonette"`
  - `email` equals `"Shanna@melissa.tv"`
- **Response Model**: `User.java` POJO maps only the required fields (`id`, `name`, `username`, `email`) using `@JsonIgnoreProperties(ignoreUnknown = true)` to ignore extra fields from the API response
- **Test Reports**: Each API test prints both **expected** and **actual** response values in the test output, so you can see exactly what was returned vs. what was expected. This output is captured in the Maven Surefire XML reports (`target/surefire-reports/TEST-TestSuite.xml`) under the `<system-out>` section for each test case

> **Note:** The Google test is intentionally kept in the suite to demonstrate a known-failing scenario. You should see **2 passed** and **1 failed** test in the reports.

## Key Features

- **Page Object Model (POM)**: Each page has its own class with locators and actions
- **API Testing**: RestAssured-based API tests with separate base class (`ApiBaseTest`), no browser required — faster execution
- **JSON Deserialization**: Jackson Databind maps API responses to Java POJOs for type-safe assertions
- **Anti-CAPTCHA**: ChromeDriver configured to reduce bot detection (disabled automation flags, human-like typing delays)
- **Reusable Utilities**: Common methods for waits and random delays in `CommonMethods`
- **CI/CD Ready**: GitHub Actions workflow for automated test execution on push/PR
- **Headless Mode**: Automatically enabled in CI environments via `CI=true` environment variable

## GitHub Actions

The project includes a CI workflow (`.github/workflows/build.yml`) that:

- Runs on every push and pull request to `main`/`master`
- Sets up Java 21 and installs Chrome on Ubuntu
- Executes all tests via Maven
- Uploads test results as artifacts

## Configuration

- **Test Suite**: `testng.xml` — add or remove test classes here
- **Maven Settings**: `pom.xml` — manage dependencies and build plugins
- **Git Ignore**: `.gitignore` — excludes build artifacts, IDE files, and manually downloaded JARs
