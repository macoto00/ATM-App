## === App is in Development Process ===

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

1) A good looking FE with registration & login, About page, Dashboard, Other Menus, Contact page.
2) User must verify email (email sender from BE will send verification link).
3) Verified & logged user can create an account & can deposit and withdraw means of payment. It is also possible to send means of payment to other accounts.
4) User can change password or reset forgotten password || update profile.
5) User can see Account money balance and history of transactions.
6) User can delete Account.

## Backend 

Developing BE with IntelliJ IDEA Ultimate.

Using Java, Spring Boot, Gradle, Application Variables, MySQL Database.

Using GIT and GitHub as version controll system. Specifically GitHub Desktop && || Command line Commands. 

For security is used Authentication and Authorisation. Using JWT Token stored in Cookies. 

BE was continuously tested using Postman.

## Frontend

Developing FE with Visual Studio Code.

Thinking about using Angular Framework or just create HTML, CSS, JS in static folder for faster development. I will see later. 

Decided to build App on Angular - just to remember things and for example. Also using Bootstrap Framework. - Later on I'd like to rebuild FE on React for learning purpouse. 

## Images

Dashboard screen for logged user with active menu.

![Snímek obrazovky 2023-09-22 101014](https://github.com/macoto00/ATM-App/assets/117540231/310ea951-00f5-42ef-aa1a-a09d7ebc1258)

Dashboard screen for logged user with deactived menu.

![Snímek obrazovky 2023-09-22 101028](https://github.com/macoto00/ATM-App/assets/117540231/7ec84c80-ee26-485d-888d-f357a7102f39)

Profile Page.

![Snímek obrazovky 2023-09-22 101112](https://github.com/macoto00/ATM-App/assets/117540231/1d954649-87a0-4fc3-84c3-04c5f21668a7)

Contact Page.

![Snímek obrazovky 2023-09-22 101953](https://github.com/macoto00/ATM-App/assets/117540231/0f9152c5-9296-4749-9152-5f1a67d2f36e)

Register.

![Snímek obrazovky 2023-09-22 101210](https://github.com/macoto00/ATM-App/assets/117540231/5c02c9d8-8df1-45ba-8f1b-c7de9db6b378)

Login.

![Snímek obrazovky 2023-09-22 101220](https://github.com/macoto00/ATM-App/assets/117540231/7f662107-bb1a-43c2-b6ab-fa301bf4de93)

Mobile Screens. (App is fully responsive)

![Snímek obrazovky 2023-09-22 101303](https://github.com/macoto00/ATM-App/assets/117540231/44be9fd9-6247-481b-9bc4-3f55f2804075)

![Snímek obrazovky 2023-09-22 101336](https://github.com/macoto00/ATM-App/assets/117540231/9d66e20c-19fd-4ba5-9f56-371519e94f51)

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
