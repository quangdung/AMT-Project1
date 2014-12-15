AMT-Project1
============

Authors : Nguyen-Phuong Le & Quang-Dung Ngo

---

# Domain modeling

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

# Implementation choices
One of the implementation choices that we had to choose were the inheritance strategy.

We have chosen to go for the single table implementation because we don't have a lot of columns and it is practical to be able to work with only one table, especially when it comes to queries. 

# Known issues

We were not able to send observations with a continuous thread at given intervals.
It is currently possible to send a number of predefined observations and see that they are correctly created, as well as the facts tied to them. It is however not yet possible to send an observation on a regular basis. This problem does not concern the implementation of the API itself but rather a misunderstanding of the mechanism of Threads. This will be investigated and a solution will be found for the next version.

### Version
0.0.2

