# Jsonlenium
[![CircleCI](https://circleci.com/gh/mapserver2007/Jsonlenium/tree/master.svg?style=svg)](https://circleci.com/gh/mapserver2007/Jsonlenium/tree/master)
[![Test Coverage](https://api.codeclimate.com/v1/badges/fa780bc872652811102c/test_coverage)](https://codeclimate.com/github/mapserver2007/Jsonlenium/test_coverage)  
Jsonlenium is a tool to perform E2E tests using JSON files.

## Preparation
Java is necessary to execute (Java 8 or later). Please install it.

## Usage
```
$> git clone https://github.com/mapserver2007/Jsonlenium.git
$> cd Jsonlenium
$> .\gradlew uiTest -Ddriver=phantomjs -Dpath=C:\jsonlenium_test_files\test.json
$> .\gradlew uiParallelTest -Ddriver=phantomjs -Dpath=C:\jsonlenium_test_files\test.json -Ddivision=5
```

## Tasks
Available Gradle tasks:

| Task           | Desctiption                                                                                                  |
|----------------|--------------------------------------------------------------------------------------------------------------|
| uiTest         | Execute the E2E test.                                                                                        |
| uiParallelTest | Execute the E2E test in parallel.                                                                            |
| uiTestCompile  | It is implicitly called by a uiTest and uiParallelTest task, therefore do not need to explicitly execute it. |
| cleanAll       | Delete the compiled files.<br>When updating the JSON test file, it needs to be executed.                     |
| unitTest       | Execute unit test.                                                                                          |
| coverageTest   | Execute unit test and coverage mesurement.                                                                   |

## Specifiable parameters

| Parameter  | Description                                                                                                                                                                                                                                             | Example                 | Required |
|------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|----------|
| -Ddriver   | Driver used for execution (WebDriver).<br>phantomjs/chrome/headlesschrome can be specified.                                                                                                                                                             | -Ddriver=chrome         | ◯        |
| -Dpath     | JSON test file path or directory path.                                                                                                                                                                                                                  | -Dpath=C:\test.json     | ◯        |
| -Ddivision | Number of partitions to allocate to one process when executing tests in parallel.<br>Increasing the value of the division as the total number of tests increases increases the execution speed.<br>Only for uiParallelTest task.<br>Default value is 5. | -Ddivision=5            | -        |
| -Dtimeout  | Maximum response time of running test (sec).<br>If the response becomes longer than the timeout time, it ends as an NG test.<br>Timeout is unlimited if not specified.                                                                                  | -Dtimeout=300           | -        |
| -i         | Output the log as standard output (info level).                                                                                                                                                                                                         | -i                      | -        |
| -d         | Output the log as standard output (debug level).                                                                                                                                                                                                        | -d                      | -        |

## Unit test in each JDK version
| JDK name | OS | build version | testing result |
|-----------------|-----|---------------------------|----------------|
| OpenJDK | mac | 1.8 (1.8.0_192) | ok |
| AdoptOpenJDK | mac | 1.8 (1.8.0_202) | ok |
| amazon-corretto | mac | 1.8 (Corretto-8.202.08.2) | ok |
| amazon-corretto | mac | 11.0 | ng |

## License
Licensed under the MIT
http://www.opensource.org/licenses/mit-license.php
