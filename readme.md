# Group gr2230 repository 
 
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2230/gr2230/-/tree/master/2230/src/main/java.git)

#### Welcome to our calendar app for personal schedules!

The project uses maven for building and running.
To run, cd into the 2230 folder, and use the appripriate compile and run commands (first "mvn compile", then "mvn javafx:run").

##### File overview

In the 2230 folder you will find the source folder (src) and the POM file for this project. 

The main and test folders lies within the src folder.

The main folder contains both the resources folder, in which you will find the FXML file, and the java folder, in which the core of the project lies.

The app-, controller- and saveHandler-class lies within the core folder. This constitutes the majority of the app logic and controller elements of the project. Here, you will also find the savedCalendars folder, in which the saved calendars will be stored. 

The test classes for the project will be kept within the test folder.

##### File overview in list format

- 2230
    - src
        - main
            - java/CalendarApp/core
                - savedCalendars
                    - a number of saved calendars
                - CalendarApp.java
                - CalendarController.java
                - CalendarSaveHandler.java
            - resources/CalendarApp
                - Calendar.FXML
        - test/java/CalendarApp/core
            - CalendarAppTest.java
    - pom.xml

