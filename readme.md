# Group gr2230 repository 
 
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2230/gr2230/-/tree/master/2230/src/main/java.git)


## Welcome to our calendar app for personal schedules!

This project uses maven for building and running.

To run, cd into the *2230* ⚠️ **Det skal vel være fxui nå?** ⚠️ folder, and use the appripriate compile and run commands (first "mvn compile", then "mvn javafx:run").

##### User stories
To se a list of relevant user stories for this project see [here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2230/gr2230/-/blob/readmeBranch/2230/userStories.md).

##### Project description 
To se a full description of the project and its functionality, look at [this](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2230/gr2230/-/blob/readmeBranch/2230/README.md) readme file.


### Modularization

This project consists of a full three layer modularization, divided into the folders *core*, *data* and *fxui*.
The domain layer (main logic classes) for the project will be kept in the *core* folder, while the user interface layer (ui and controller classes) will be kept in the *fxui* folder. 
The *data* folder will act as our server in this release, and will store the data for calendars and appointments.


### File overview

In the *2230* folder you will find the *core*, *data* and *fxui* folders, *docs* folder for documentation, and the parent **pom** file for this project.

Each of the *core*, *data* and *fxui* folders contains a *src* folder, a child **pom** file and a specific **README** file. The *main* and *test* folders lies within each of the *src* folders, and the **test** classes for the each of the modules will be kept within the *test* folders. The *main* folder contains the main classes and data for each of the modules, alongside the specific **module-info** file.

Within the *main* folder of *core*, you will find the *calendarApp* folder. This folder, in turn, holds the core folder with **CalendarApp** file and *images* folder for the user stories, and a *json* folder for the **CalendarSaveHandler** file. 

Within the *main* folder of *data*, you will find the *savedCalendars* folder. This folder will hold all the saved calendars for the project. 


### File overview in list format

- 2230
    - **core**
        - README.md
        - pom.xml
        - src
            - main/java
                - module-info.java
                - calendarApp
                    - core
                        - images
                            - images for the user story (.png)
                        - CalendarApp.java
                        - README.md
                    - json
                        - CalendarSaveHandler.java
            - test/java/CalendarApp
                - core
                    - CalendarAppTest.java
                - json
                    - CalendarSaveHandlerTest.java
    - **data**
        - README.md
        - pom.xml
        - src/main/java
            - module-info.java
            - CalendarApp/data/savedCalendars
                - a number of saved calendars (.json)
    - **fxui**
        - README.md
        - pom.xml
        - src/main
            - java
                - module-info.java
                - calendarApp/ui
                    - CalendarController.java
            - resources/CalendarApp/ui
                - Calendar.FXML
    - docs
        - release1.md
        - release2.md
    - pom.xml

> Every individual file is marked with the appropriate filename extension, everything else in the list is to be considered as folders


### Architecture
[Link to the classDiagram image and architecture image]

##### Class diagram
[Link to the classDiagram image and architecture image]

Click here to see the code for the [class diagram]() or [architecture diagram]().

