# Cat Breed and Facts Testing Project

This project contains automated test suites for testing Cat Facts API  (https://catfact.ninja/ ). The tests are designed to validate the functionality and response data of the APIs.

## Features

### Cat Breed API Test

**Feature:** Tests the Cat Breed API to ensure the number of breeds returned in the response matches the number requested.

#### Scenarios:
- Verify that the response contains the exact number of breeds requested.

### Facts about Cats API Test

**Feature:** Tests the Facts about Cats API to ensure that the facts returned adhere to the length constraints and match the expected data format.

#### Scenarios:
- Verify that the length of the fact provided does not exceed the limit set in the request.
- Verify that the number and length of the facts received do not exceed the limits set in the request.

## Prerequisites

- **Maven:** Ensure Maven is installed on your system. This project uses Maven to manage dependencies and run tests.
- **JDK:** This project requires Java JDK 17 or higher.
- **Jenkins (optional):** If you wish to integrate these tests into a CI/CD pipeline, Jenkins is required. (MOCK, here just showcase such possibility)

## Plugins Required

- **Cucumber for Java:** Used running tests in human friendly way, ensures proper work of scenarios .
- **Gherkin:** Prerequisite for cucumber plugin.
- **Jenkins Pipeline(not active in this case):** If using Jenkins, ensure the Jenkins Pipeline is set up for continuous integration(mock in this case, there is a JenkinsFile but it is a MOCK, there just to give insight how would it look)

## Setup

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Ensure all the prerequisites are installed.

## Running Tests

### Locally
Note: To run via command line make sure that maven is installed to local machine, if not it is still possible to run via cucumber scenarios.

To run the test suite, go to recourses bundle and choose feature file which you desire to run, then press on green triangles OR execute the following command in the root directory of the project:
```bash
mvn clean verify
