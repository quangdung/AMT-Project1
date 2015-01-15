AMT Project
============

Authors : Nguyen-Phuong Le & Quang-Dung Ngo

---
# Introduction

This project has been created in the context of the AMT course of the HEIG-VD, Yverdon-les-Bains, Switzerland.

Its purpose is to create a Web API that can manage observations data coming from different sensors of all types (as in a web of things scenario, sensors coming from different devices) and deal with them to create facts that reflect some statistics out of the observations.

![web_of_things](./images/web_of_things.png)

The scenario we have chosen to illustrate in this project is as follow : 
- Theres is different organizations, each one composed of users, that are tied with different sensors.
- Those sensors can detect different observations (composed of a value and a time of observation) that arrive at different times and that are eventually stored into a database.
- Once an observation has been detected by a sensor, different facts are created to reflect some statistics matching the totality of all observations.

You can install this platform to test how all the generated observations are handled through it and see the generated facts.

---
# Domain modeling

Every observation can be detected by only one sensor and every sensor can detect several observations.

Each Sensor is associated with one organization and each organization can have several sensors associated with it.

Each organization is composed of several users.

Each fact can be tied to one organization and every organization can have several facts tied to it.

There is 2 types of facts. One that only counts the total number of observations per sensor and another that contains all stats per day.

![Domain_model](./images/domain_model.png)

---

# REST API Documentation
If it's the first time, run : `npm install`

Run :

- `grunt dev`
or
- `grunt dev --private=true`  to show private blocks

Go to adress [http://localhost:8080/AMT_Project/](http://localhost:8080/AMT_Project/ "Go to the page")

---

# Online testing

If you want to test an already functional version of our API, head over to 
http://quangdung.github.io/amt-project1/

---

# Known issues

We were not able to send observations with a continuous thread at given intervals.
It is currently possible to send a number of predefined observations and see that they are correctly created, as well as the facts tied to them. It is however not yet possible to send an observation on a regular basis. This problem does not concern the implementation of the API itself but rather a misunderstanding of the mechanism of Threads. This will be investigated and a solution will be found for the next version.

### Version
0.0.2

