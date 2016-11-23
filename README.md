# Data Manipulation

This example project outlines various ways of programmatic data manipulation in CUBA applications.

- Programmatic creation, updating and deletion of entities on the middleware and on the client tier. See the following methods of the [CustomerBrowse](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/gui/src/com/company/sample/gui/customer/CustomerBrowse.java) screen controller:
    - `onCreateInService()` - sends customer data to a middleware [service](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/core/src/com/company/sample/service/CustomerServiceBean.java) method.
    - `onCreateInController()` - replicates the logic of the midlleware service right in the screen controller.

- Execution of queries on the middleware and on the client tier. See the following methods of the `CustomerBrowse` screen controller:
    - `calculateDiscountInService()` - delegeates to a middleware [service](https://github.com/cuba-platform/sample-data-manipulation/blob/master/modules/core/src/com/company/sample/service/CustomerServiceBean.java) method.
    - `calculateDiscountInScreen()` - executes query in the screen controller via [DataManager](https://doc.cuba-platform.com/manual-6.3/dataManager.html).

- Using the [REST API](https://doc.cuba-platform.com/manual-6.3/rest_api_v2.html) to control the application from an external tool. See the project in the `clients/rest-client` directory. To create IntelliJ IDEA project files, open the command line in this directory and run `gradle idea`.
    - The `RestClient.createCustomer()` method creates an entity by sending JSON to the standard REST API CRUD method.
    - The `RestClient.createCustomerViaService()` method creates an entity by sending its attributes to the middleware service.

Based on CUBA Platform 6.3.4
