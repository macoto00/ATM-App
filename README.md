# ATM Demo project

The task is to design and develop an application for ATM operation. Limit the scope to a single scenario, for example, cash withdrawal.

Elaborate and research the details based on own considerations. However, ensure the realistic functionality of the ATM.

Development Task:

The application should be:

1) Built using technologies based on own preferences.

2) Constructed using a microservices architecture, specifically consisting of a frontend service (referred to as FE) and a data service (referred to as BE).

3) Utilize a REST API between FE and BE, defined using the OpenAPI Specification in the latest version.

# Description of the Development process

I've decided to complete this task more robust. The desired functionality consists of:

1) A good looking FE with registration & login, About page, Contact page.
2) User must verify email (email sender from BE will send verification link).
3) Verified & logged user can create an account & can deposit and withdraw means of payment. It is also possible to send means of payment to other accounts.
4) User can change password or reset forgotten password || update profile.
5) User can see Account money balance and history of transactions.
6) User can delete Account.

## Backend 

Using Java, Spring Boot, Gradle, Application Variables, MySQL Database.

Using GIT and GitHub as version controll system. Specifically GitHub Desktop && || Command line Commands. 

For security is used Authentication and Authorisation. Using JWT Token stored in Cookies. 

BE was continuously tested using Postman.

## Frontend

Thinking about using Angular Framework or just create HTML, CSS, JS in static folder for faster development. I will see later. 

Decided to build App on Angular - just to remember things and for example. Also using Bootstrap Framework. - Later on I'd like to rebuild FE on React for learning purpouse. 

# Future ongoing Development

1) Unit tests
2) Joining tables relations
3) FE & BE connection via @CrossOrigin
4) FE on React
5) Password reset
6) Password change
7) Mailer
8) Verify email
9) Transactions Model Class
10) UserAccount Model class
11) Admin Role
