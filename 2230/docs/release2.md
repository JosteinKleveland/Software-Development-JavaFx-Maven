# Documentation - Release 2

### Getting started with "the second sprint"

In the first of our weekly meetings (Tuesday at 14:15-16:00), we reflected on the previous sprint, discussed the requirements and expectations for the second release, and began expanding our backlog of issues. Additionally, we discussed how far to evolve the application. We concluded to implement functionality for the user to be able to make "appointments" in his/her calendar, and remove unwanted appointments. Becoming wiser from the previous sprint, we were more efficient in dividing responsibility and starting the implementation. Our first and highest priority was to modularize the project and set up the framework properly with respect to our future implementations.

### Workflow

Encountering peculiar issues in attempts to run the application after modularizing the project, we decided to work pairwise with different elements of the release. With Gitpod being down, Jostein and Endre worked together locally on implementing the logic for the user story, while William and Simen continued to work with POM and module-info files in relation to the modularization. 

In between our "daily scrum" meetings (which are set for every Tuesday and Friday), we held each other updated and communicated through messenger. A few meetings were done digitally, with one group member working abroad (traveling) and the other sick at home. Nonetheless, going through the usual meeting process - updating each other on work done, disussing potential issues, and map and  divide upcoming work - the digital meeting went very well. 

In addition to working together twice a week for two hours (which included the "daily scrum" meeting), we aimed at baseline of 4 hours of individual work every week. At different times, workhours increased for each member - in attempts to complete the assigned issues. 

With feedback from the previous release, we enhanced our workflow as well as understanding of future expectations. We started utilizing branching more frequently, introducing pairwork, and basing all tasks on issues. We have also begun to relate issues to each other. 

### Code quality - testing - tools and their settings

To check and document code quality for release 2, we have chosen to implement three different tools. These tools include Jacoco, for test coverage, CheckStyle, for syntax and the like, and SpotBugs, for potential bugs in the structure.

These tools were implemented one at a time, so that we always had control over which parts of the code were approved by the different tools. At the same time, we also had the opportunity to correct errors and remarks that appeared during the implementation and testing. The code was finally systematically reviewed, module by module, tool by tool, until it ran successfully.

Along the way, we discovered one thing in particular that can only be improved in a future implementation of a larger data module. Spotbugs seems to work well in data (folder) too, but it crashes if we don't have any .java files there. We have tried running it with a very simple Test.java file with only one variable and a getter, and then we get success. As the data folder is only a precursor to the server to be implemented at a later date, SpotBugs is omitted in this module at this time.

When it comes to the settings of the tools, we have not implemented major changes specifically for the project. In particular, as already mentioned, we have omitted SpotBugs from the data module. In addition, we have chosen to use Google's built-in .xml for CheckStyle, which contains a code style we are already well familiar with. This means that we can code the project as we have learned so far, while at the same time that CheckStyle keeps track of whether we follow the rules we intend to implement.

### Choice of persistence 

We chose the Jackson library for persistance with JSON because it is considered to be one of fastest and most practical JSON processors for Java, and because it is used in the todo-list example - which acts as a great guide, and has several persistence elements in common with our application.  

Once a user creates a Calendar, a file is stored as, and with, the calendar name. Consequently, when the user adds or removes appointments, the structure of the appointment data is stored in an "appointments" array in the respective json file. An appointment in the json file is structured with a title (string), weekday (string), startHour (int), startMin (int), stopHour (int), and stopMin (int). 

As it is very practical to be able to use our calendar across devices, we have decided to implement implicit storage, where the application communicates with a server to upload/download the calendar. However, for this release, we are still operating with documentmetaphor (desktop) persistence. 

### Delivery delay

With the circumstancs of our situation, with gitpod being down for an extended period of time in the beginning, group members being sick and working abroad, and a few pom errors consuming time, we decided to request an extension of the deadline for this release for a couple of days. Also to ensure the holistic quality of our delivery - with updated tests, controller, and user experience. 

### Road ahead

With our increase in knowledge, experience, and valuable feedback, we aim to continue to emphasize and improve on documenting, utilizing branches, create and relate issues, and striving for quality of code. Despite the obstacles mentioned in the previous paragrahp, our workflow has been working very well, and is something we intend to carry on to the next sprint. 









