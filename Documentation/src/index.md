---
title: Welcome
template: layout.jade
menuIndex: 1

---

#Software-as-a-Service platform REST API that allow organizations to manage sensors.

Authors : Nguyen-Phuong Le, Quang-Dung Ngo

This project has been created for the AMT course of the HEIG-VD, under the supervision of Mr. Olivier Liechti.
It's purpose is to create an API which will manage differents sensors (which could, for example, measure the temperature of a room or detect the variations taking place in a particular area), and in turn, manage the observations captured on these sensors and turn them into facts which summarize the amount of informations given by those observations. 

The project is using JPA and a REST architecture to achieve its goals and will ultimately allow the following functionnalities:
  - Create, Read, Update and Delete organizations, sensors, users and observations
  - Display the different entries from the database in a convenient manner.
  - Allow for different users to use the API and store their informations and tie them to an organization, in order to sort them more easily

The database is composed of :
  - Organizations, defined by their id number and their names
  - Sensors, defined by their id number, name, description, type, visibility and the organization to which they are tied to.
  - Users, defined by their id number, first name, last name, email, password, the organization to which they are tied to and if they are the main contact of that organization
  - Observations, defined by their id number, name, value, creation date and the sensor to which they are tied to.
  - Facts, which come in two forms:
    - Facts tied to a particular sensor
    - Facts tied to a particular sensor, as well as a particular date
    

### Version
0.0.2


