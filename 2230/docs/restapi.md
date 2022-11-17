# CalendarApp API

## Base endpoint

Methods:

* GET - retrieves a calendar from the server
  * URI: host:port/calendar
  <http://localhost:8080/calendar>
  * parameters: none
  * available: jetty, springboot
  * returns JSON with the calendar

```json
{
    "calendarName":"calendar",
    "appointments":[
        {"appointmentName":"name","appointmentDescription":"description","dayOfTheWeek":"MONDAY","startHour":00,"stopHour":10,"startMin":00,"stopMin":00}
    ]
    }
}
```

## calendar

<http://localhost:8080/calendar/{name}>

Methods:

* GET - retrieves the specified calendar content from the server
  * URI: host:port/calendar/{name}
 <http://localhost:8080/calendar/calendar>
  * parameters: none
  * available: jetty, springboot
  * returns JSON with the calendar content

```json
{
    "calendarName":"calendar",
    "appointments":[
        {"appointmentName":"name","appointmentDescription":"description","dayOfTheWeek":"MONDAY","startHour":00,"stopHour":10,"startMin":00,"stopMin":00}
    ]
    }
}
```

* PUT creates a calendar with the desired name if it does not exist or updates the existing calendar otherwise
  * URI: host:port/calendar/{name}
  * parameters:
    * body -  application/json; charset=UTF-8
    * calendar content

```json
{
    "calendarName":"calendar",
    "appointments":[
        {"appointmentName":"name","appointmentDescription":"description","dayOfTheWeek":"MONDAY","startHour":00,"stopHour":10,"startMin":00,"stopMin":00}
    ]
    }
}
```

* available: jetty, springboot
* returns
  * Content-Type: application/json
  * json with boolean true on success

```json
   true
```
  
* DELETE - delete the calendar with the name {name}
  * URI: host:port/calendar/{name}
  * parameters: none
  * available: jetty, springboot
  * returns
    * Content-Type: application/json
    * json with boolean true on success

```json
   true
```

* POST - rename a calendar
  * URI: host:port/calendar/{name}/rename
  * parameters - form / body
    * application/x-www-form-urlencoded; charset=UTF-8
    * newName={newName}
  * available: jetty, springboot
  * returns
    * Content-Type: application/json
    * json with boolean true on success

```json
   true
```
