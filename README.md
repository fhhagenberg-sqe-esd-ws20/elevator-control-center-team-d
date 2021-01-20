# Elevator Control Center

### Members
- Dominic Zopf (S1910567012)
- Sajan Cherukad (S1910567014)


### Instructions to download and run ECC (latest release)   

#### Prerequisites   
- [x] Java 13 SDK (e.g. Oracle or OpenJDK).

#### Download
1. Open the latest release v1.0

2. Open the associated assets

3. Download the jar file: ecc-team-d-1.0-jar-with-dependencies.jar

#### Run

1. Open a terminal (cmd, shell) and navigate to the folder where the downloaded jar file is located

2. Start the application with the following command: java -jar ecc-team-d-1.0-jar-with-dependencies.jar   


### Download Repository
```
git clone https://github.com/fhhagenberg-sqe-esd-ws20/elevator-control-center-team-d.git
```


### Instructions build Application

This maven project is already set up for JavaFx based GUI applications. It also contains a small example application - `App.java`.

1. Import this git repository into your favourite IDE.

1. Make sure, you can run the sample application without errors.
	- Either run it in your IDE
	- Via command line, run it with `mvn clean javafx:run`.

You can build your project with maven with

```
mvn clean package
```

The resulting archive (`.jar` file) is in the `target` directory.


### Test concept

Generally we used unit tests and testfx tests to ensure the product quality of our product. Most of the automated tests are 
white box tests. We used the coverage information from jacoco to look for missing test cases. We used Mockito to create 
mocks for the modules which are used from the module under test. We also used dependency injection to test all modules.
The written tests can be divided into the following parts:

- Model tests: Unit tests to verify the functionality
- Controller tests: Unit tests to verify the functionality
	- Reconnection tests to test the correct beavior in a case of a remote error
- GUI tests: Tests with the TestFx framework
	- End-To-End tests from the GUI to the interface and in the other direction

To ensure the code quality we used also the static analysis tool sonar cloud. Github actions was used to execute the tests when 
a pull-request or a push to the master branch has been carried out. On every automated build a jacoco report was created and 
stored as an artifact. For reviews we used GitHub pull-requests. 
