## Work Order Manager Application</h2>
### Capstone Project (PROJ 354) completed at SAIT for Summer Semester 2021
### Description:
Inspection, servicing, and repairs of industrial equipment and infrastructure is a problem, as these assets can be remote and often require many specialize people to facilitate their maintenance.  Managing these work orders through traditional means is inefficient and often meant that information was prone to being lost, forgotten, or just not communicated. 

We have created an application that solves the issues with managing these work orders. Our application provides an easily navigable interface for users to create, find, and update work order information. This results in real time updates to the work orders and an increase in the overall efficiency of the work order management system.


### Features: 

* Secure and encrypted login/authentication of users
* Different privileges for the different user role types 
* Create, read, update, and delete/lock of work order notifications, work orders, and operations
* Responsive design
* An easy navigable UI
* Filter, sort, and search data tables
* Display the location and address of the work orders
* Database tables: Work Order, Order Type, Status, Work Center, Operations, Notifications, Notification Type, Cause, Damage, Plant, Technical Object, Users, Roles

### Internal Structure:

* Java based web application
* Apache Tomcat web container
* MySQL database accessed with JPA to provide object-relational mapping
* Argon2 with Password4j to securely store and check user passwords
* OWASP HTML Java input sanitation to prevent XSS attacks


### This project was completed in a team by the following authors: 
Z. Paul Weleschuk, Trevor E., Saied G. Jaehan K., Maria P., Rylan C.
 
\
&nbsp;

![fig1](https://github.com/ZPaulWeleschuk/Capstone-Project/blob/main/Documentation/demo-images/adminPage.jpg)
Figure 1: Administrator page where the admin can create new work order notification and add new users. This page also displays all notifications, work orders, operations, and all users. These tables have both search and sort data functionality, as well as delete. The admin can also reset user passwords and lock accounts.

\
&nbsp;
![fig2](https://github.com/ZPaulWeleschuk/Capstone-Project/blob/main/Documentation/demo-images/WorkOrder.jpg)
Figure 2: Work Order page displaying the work order and notification as well as the corresponding operations within the work order. Operations can be edited to reflect the work done on that specific operation.

\
&nbsp;
![fig3](https://github.com/ZPaulWeleschuk/Capstone-Project/blob/main/Documentation/demo-images/location.jpg)
Figure 3: Map displaying the location of the work order.

\
&nbsp;
![fig4](https://github.com/ZPaulWeleschuk/Capstone-Project/blob/main/Documentation/demo-images/loginScreen.jpg)
Figure 4: Secure login screen that prevents XSS and SQL injection attacks. Features salted and hashed password encryption.
