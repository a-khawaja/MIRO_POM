## MIRO Automation Task Framework
The framework right now consists of Web bases UI tests and can be extended to API's also, making it an integrated one to run both the tests.
Also it can be improved on various other aspects as we move further.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites
What things you need to install the software and how to install them
```
Maven
Java
Eclipse/IntelliJ
```

## Steps to execute the framework in your local


1. Checkout the code in your local using:
   **git clone "Link to the Github repo shared via email"**
2. Once checked out, import the project as "maven project" in eclipse/STS/IntelliJ
3. Once import is sucessfully, you can run pom.xml
4. In order to run all tests suite files or feature-wise suite file(s) for a particular component manually.
    - **To run all features at once, value to be passed is 'all'**
        - `Run the TestNG.xml file`

<h3><b>Note:</b></h3>


*  Values for the parameters passed when running from maven commandline:

   | **Parameter Name** | **Description** | **Values** |
       | ------ | ------ | ------ |
   | env | Which environment the tests to run on | At the moment only live env for Miro |
   | browser | Which browser to run | chrome/firefox |
   | feature | Which class test cases to trigger | select from TestNG.xml |


<b>Note:</b> Drivers have been taken care of automatically and therefore  no speical change is needed at your end. It will be taken care of automatically by the pom dependency.</font>


## Project Structure
As the project is maven based, have followed 'standard' maven project structure as below:

```
src/main/java       - Consists of Page classes & methods
src/main/resources  - Consists of resources utilised by across (such as log4j.properties, setup.properties etc.)   
src/main/test       - Consists of tests to be executed
```
## IMPORTANT POINTS

1. The framework at the moment has not been designed for the pipeline. To make it suitable for the pipeline, slight modifications in the pom are needed to let it run as a maven command. If needed, changes could be provided.
2. Miro does NOT accept "yopmail" domain, therefore, the Sign up test case has been limited till the EmailCode page.
3. Sign up via Third party integration are limited to landing on the respective pages, as I do not have test accounts for all these applications. 
4. There are many other combinations possible that could be added to this suite, but only limited have been taken care of for the task purpose. 
5. Please pay special attention to the sign up Happy paths. At the moment, Miro does not allow the same email to be resued even if it is not verified and therefore the test data has to be changed on running for the second time (if needed)
6. If any special change is needed, I would be happy to do so. 
7. The framework is data driven and all of the data and dependencies have been taken care of automatically. Simply run the TestNG file and all the test results will be executed autoamtically.
8. The framework is platform and browser independent. But the tests have been run on Chrome and Windows.


## Built With
* Dependency Management - [Maven](https://maven.apache.org/)
* Web framework used    - Selenium WebDriver + PageFactory Model
* API Framework used    - [RestAssured](http://rest-assured.io/)
* Testing tool          - [TestNG](https://testng.org/doc/)
* Extent Reporting      - [Extent Reports](https://extentreports.com/)
* Logging               - [Log4J](https://logging.apache.org/log4j/2.x/)

	