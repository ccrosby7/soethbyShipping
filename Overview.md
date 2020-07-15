## Short Overview of the soethby-shipping Application

In short, I created this application with the mind set of it being a backend microservice. 
In that respect, I chose to go with the JHipster generator for a skeleton which creates a base Java Spring boot application.
I chose Jhipster as it provides a wide range of options for authentication, database and caching support, 
allowing for the application to slide into any microservice based application with little to no effort in terms of authentication
or database support.
  
While this was not to be a comprehensive solution, I approached it from the angle of an initial proof of concept, so extensibility long term was key.
I achieved this by leveraging a dev and production config as well as having stubbed ends for redis support and 
an initial dockerfile, allowing the application to be compiled and run on any machine with docker installed.

Testing is all provided by those written by myself as well as additional integration tests 
written by the Jhipster generator hitting all available endpoints.

Thank you for your time and this opportunity to show my abilities as a developer.
