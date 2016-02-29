# Data Manipulation

This example project outlines various ways of programmatic data manipulation:

- Programmatic creation of an entity in a middleware service. See the `CustomerServiceBean` class.

- Using the REST API to control the application from an external tool. See the project in the `clients/rest-client` directory. To create IntelliJ IDEA project files, open the command line in this directory and run `gradle idea`.
    - The `RestClient.createCustomer()` method creates an entity by sending JSON to the standard REST API method `commit`.
     - The `RestClient.createCustomerViaService()` method creates an entity by sending its attributes to the application service.

Based on [CUBA Platform](https://www.cuba-platform.com/) 6.0.8
