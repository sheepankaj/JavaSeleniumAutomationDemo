---
sessionId: session-261010-124419-1cu5
---

# Requirements

### Overview
Extend the existing Selenium UI automation framework with API testing capability using RestAssured. Add a test that validates the JsonPlaceholder API response for user `id: 2`.

### Scope
**In Scope:**
- Add RestAssured dependency to `pom.xml`
- Create `ApiBaseTest` class (no browser, HTTP client setup only)
- Create a POJO model for the API response (`User`)
- Create an API test that calls `GET https://jsonplaceholder.typicode.com/users`, finds user with `id: 2`, and validates `name`, `username`, and `email`
- Reorganize existing UI tests into `src/tests/ui/`
- Place new API tests in `src/tests/api/`
- Update `testng.xml` to include both UI and API test classes
- Update `README.md` with API test documentation

**Out of Scope:**
- Validating all response fields (only `id`, `name`, `username`, `email`)
- POST/PUT/DELETE API tests (only GET for now)
- API test reporting beyond TestNG defaults

### Acceptance Criteria
- `mvn clean test` runs all tests (2 UI + 1 API) without compilation errors
- API test passes and validates the four fields for user `id: 2`
- UI tests continue to work as before (1 pass, 1 expected fail)
- Project structure clearly separates UI and API tests

# Technical Design

### Current Implementation
- **6 source files** in packages `base`, `common`, `pages`, `tests` (default package style)
- **`pom.xml`**: Selenium 4.15.0 + TestNG 7.10.2, Maven Surefire runs `testng.xml`
- **`testng.xml`**: Suite `WebAutomationSuite` references `tests.GoogleSearchTest` and `tests.SmitherySearchTest`
- **`BaseTest.java`**: `@BeforeMethod` initializes ChromeDriver, `@AfterMethod` quits
- Test classes extend `BaseTest`, instantiate page objects, and use TestNG `Assert`

### Key Decisions
1. **Folder structure**: `src/tests/ui/` and `src/tests/api/` — keeps all tests under one `tests` package, minimal migration impact
2. **RestAssured** for HTTP requests — fluent DSL, built-in JSON path assertions, no need for manual JSON parsing
3. **Separate `ApiBaseTest`** — no ChromeDriver initialization, only RestAssured base URL setup
4. **User POJO model** — a simple Java class in `src/api/models/` to map JSON response fields for type-safe assertions

### Proposed Changes

**New files:**
- `src/api/base/ApiBaseTest.java` — Base class with `@BeforeSuite` to set RestAssured base URI
- `src/api/models/User.java` — POJO with fields: `id` (int), `name` (String), `username` (String), `email` (String)
- `src/tests/api/JsonPlaceholderTest.java` — Test class extending `ApiBaseTest`, calls GET `/users`, filters by `id: 2`, asserts name, username, email

**Modified files:**
- `pom.xml` — Add RestAssured 5.3.0 dependency
- `testng.xml` — Add API test class, update UI test class references (new package paths)
- `README.md` — Add API test section, update project structure tree

**Moved files:**
- `src/tests/GoogleSearchTest.java` → `src/tests/ui/GoogleSearchTest.java`
- `src/tests/SmitherySearchTest.java` → `src/tests/ui/SmitherySearchTest.java`

### File Structure After Changes
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

### Data Models

**User.java** (POJO for JSON response):
```java
package api.models;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    // getters and setters
}
```

**JsonPlaceholderTest.java** (test flow):
1. Call `GET https://jsonplaceholder.typicode.com/users`
2. Parse response as `User[]`
3. Find user where `id == 2`
4. Assert `name == "Ervin Howell"`, `username == "Antonette"`, `email == "Shanna@melissa.tv"`

### Risks
- RestAssured's JSON deserialization requires field names to match the JSON response — the JsonPlaceholder API uses standard field names, so no custom mapping needed.
- Moving test classes to new packages requires updating imports in the test classes and `testng.xml` references.

# Delivery Steps

### ✓ Step 1: Add RestAssured dependency and reorganize test folders
Add RestAssured to pom.xml and move existing UI tests into `src/tests/ui/`.
- Add `<restassured.version>5.3.0</>` property and RestAssured dependency to `pom.xml`
- Create `src/tests/ui/` package, move `GoogleSearchTest.java` and `SmitherySearchTest.java` there
- Update package declarations and imports in both moved test classes
- Verify `mvn clean compile` succeeds with the new dependency and package structure

### ✓ Step 2: Create ApiBaseTest and User POJO model
Create the API testing foundation: base class and response model.
- Create `src/api/base/ApiBaseTest.java` with `@BeforeSuite` to set RestAssured base URI to `https://jsonplaceholder.typicode.com`
- Create `src/api/models/User.java` POJO with fields `id`, `name`, `username`, `email` and getters/setters
- Verify compilation succeeds

### ✓ Step 3: Implement JsonPlaceholder API test and update testng.xml
Create the API test and wire everything together in testng.xml and README.
- Create `src/tests/api/JsonPlaceholderTest.java` extending `ApiBaseTest`
- Implement `testGetUserById()` that calls `GET /users`, finds user with `id: 2`, and asserts `name`, `username`, and `email`
- Update `testng.xml` to reference `tests.ui.GoogleSearchTest`, `tests.ui.SmitherySearchTest`, and `tests.api.JsonPlaceholderTest`
- Update `README.md` with the new project structure, API test case, and RestAssured dependency
- Run `mvn clean test` to verify all 3 tests execute (2 UI + 1 API)