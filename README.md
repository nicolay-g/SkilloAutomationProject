# SkilloAutomationProject
 This repo contains the code of the project after completing the Skillo automation course.

**Tests description**

The project contains 7 tests which cover the basic functionality provided by the website as follows:
  - **_login (both a positive and a negative test)_** - includes filling in of Username, Password and Remember me controls with valid/invalid credentials to check if the login functionality behaves as expected.
  - **_post creation_** - creates a post with image and description and added validations. 
  - **_post update_** - edits the post from above (test dependency) by adding a comment to it and validating it's stored correctly 
  - **_post deletion_** - deletes the post from above (test dependency) and validates the post is not present anymore.
  - **_user profile update_** - updates user profile by adding Public info to it which is validated afterward.
  - **_search for user(s)_** - searches for users who match the search criteria and checks if the correct results are returned.

For all the tests data providers are used to minimize the need of adding test data within the tests themselves.

**_NOTE:_** The tests for trying to log in with invalid credentials and the search one are **failing** because of existing bugs on the http://training.skillo-bg.com:4200/posts/all site, thus these tests found existing bugs.

**Tests execution and reports**

Prior to running the tests the following prerequisites are needed:
 - JDK 21
 - maven 3.9.6
 - IntelliJ IDEA 2023.3.4 (Community Edition)
 - Chrome browser


The tests can be run via:
 - Intellij terminal with the following command: _**mvn clean test**_ which runs all the tests as defined in the **_testng.xml_** configuration file
 - Using the **SkilloSiteTestsConfiguration** test configuration added to the project

In both cases a report is created and stored in the **src\test\resources\reports** project folder.

In case of failing tests screenshots are added to the **src\test\resources\screenshots** project folder.

Images used for post creation are stored to **src\test\resources\upload** project folder.

**Code Design and Implementation** 

The website pages and major page containers (dialogs and sections) are represented by separate classes following the page object model (**POM**) pattern and **Page Factory**.

The project is build by on **Selenium WebDriver** web framework and **TestNG** for performing automated UI tests for web applications.

The test methods (test scenarios) are implemented by creation and usage of objects from the POM classes to define the scenario steps.







