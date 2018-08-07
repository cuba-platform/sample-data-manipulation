# Data Manipulation

This example project outlines various ways of programmatic data manipulation in CUBA applications.

- Programmatic creation, updating and deletion of entities on the middleware and on the client tier. See the following methods of the [CustomerBrowse](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/gui/src/com/company/sample/gui/customer/CustomerBrowse.java) screen controller:
    - `onCreateInService()` - sends customer data to a middleware [service](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/core/src/com/company/sample/service/CustomerServiceBean.java) method.
    - `onCreateInController()` - replicates the logic of the midlleware service right in the screen controller.

- Execution of queries on the middleware and on the client tier. See the following methods of the `CustomerBrowse` screen controller:
    - `calculateDiscountInService()` - delegeates to a middleware [service](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/core/src/com/company/sample/service/CustomerServiceBean.java) method.
    - `calculateDiscountInScreen()` - executes query in the screen controller via [DataManager](https://doc.cuba-platform.com/manual-6.6/dataManager.html).
    
- Providing uniqueness by using database-level unique constraints. The `SAMPLE_CUSTOMER` table has the `IDX_SAMPLE_CUSTOMER_UNQ_EMAIL` constraint by `EMAIL` column, so the database throws an error on attempt to create two customers with the same email. The [main message pack](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/web/src/com/company/sample/web/messages.properties) of the `web` module contains a message with the `IDX_SAMPLE_CUSTOMER_UNQ_EMAIL` key which will be automatically picked up by the platform to display a user-friendly message. See [cuba.uniqueConstraintViolationPattern](https://doc.cuba-platform.com/manual-6.6/app_properties_reference.html#cuba.uniqueConstraintViolationPattern) application property for how to adjust this mechanism for your database.

- Enforcing complex rules using entity listeners. [OrderEntityListener](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/core/src/com/company/sample/listener/OrderEntityListener.java) throws an exception on attempt to create an order if there are already two or more orders for the same customer on the same day.

- Handling exceptions coming from the middleware. [TooManyOrdersExceptionHandler](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/gui/src/com/company/sample/gui/exception/TooManyOrdersExceptionHandler.java) shows a notification on unhandled [TooManyOrdersException](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/global/src/com/company/sample/exception/TooManyOrdersException.java). See [Client-Level Exception Handlers](https://doc.cuba-platform.com/manual-6.6/exceptionHandlers.html) for details.

- Using the [REST API](https://doc.cuba-platform.com/manual-6.6/rest_api_v2.html) to control the application from an external tool. See the project in the `clients/rest-client` directory. To create IntelliJ IDEA project files, open the command line in this directory and run `gradle idea`.
    - The `RestClient.createCustomer()` method creates an entity by sending JSON to the standard REST API CRUD method.
    - The `RestClient.createCustomerViaService()` method creates an entity by sending its attributes to the middleware service.

Based on CUBA Platform 6.9.1

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support, and reporting problems coressponding to this sample.
