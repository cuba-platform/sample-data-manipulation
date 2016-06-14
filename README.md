# Data Manipulation

This example project outlines various ways of programmatic data manipulation in [CUBA](https://www.cuba-platform.com) applications:

- Programmatic creation, updating and deletion of entities in a middleware service. See the `CustomerServiceBean` class.

- Programmatic creation and updating entities on the client tier. See the `CustomerBrowse` screen controller.

- Using the REST API to control the application from an external tool. See the project in the `clients/rest-client` directory. To create IntelliJ IDEA project files, open the command line in this directory and run `gradle idea`.
    - The `RestClient.createCustomer()` method creates an entity by sending JSON to the standard REST API method `commit`.
    - The `RestClient.createCustomerViaService()` method creates an entity by sending its attributes to the application service.

Based on CUBA Platform 6.2.0