package com.company.sample.gui.customer;

import com.company.sample.entity.Customer;
import com.company.sample.entity.CustomerGrade;
import com.company.sample.entity.Discount;
import com.company.sample.entity.ProductType;
import com.company.sample.gui.product_type.ProductTypeDialog;
import com.company.sample.service.CustomerService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomerBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<Customer, UUID> customersDs;

    @Inject
    private HBoxLayout fieldsBox;
    @Inject
    private TextField nameField;
    @Inject
    private TextField emailField;
    @Inject
    private LookupField gradeField;

    @Inject
    private CustomerService customerService;

    @Inject
    private DataManager dataManager;

    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        fieldsBox.setVisible(false);
        gradeField.setOptionsList(Arrays.asList(CustomerGrade.values()));
    }

    public void onCreateBtnClick(Component source) {
        fieldsBox.setVisible(true);
    }

    public void onCreateInService(Component source) {
        fieldsBox.setVisible(false);
        String name = nameField.getValue();
        String email = emailField.getValue();
        CustomerGrade grade = gradeField.getValue();

        customerService.createOrUpdateCustomer(name, email, grade);

        customersDs.refresh();
    }

    public void onCreateInController(Component source) {
        fieldsBox.setVisible(false);
        String name = nameField.getValue();
        String email = emailField.getValue();
        CustomerGrade grade = gradeField.getValue();

        // create a LoadContext
        LoadContext<Customer> loadContext = LoadContext.create(Customer.class)
                .setQuery(LoadContext.createQuery("select c from sample$Customer c where c.name = :name")
                        .setParameter("name", name))
                .setView(View.LOCAL);
        // load list using DataManager
        List<Customer> customers = dataManager.loadList(loadContext);

        if (customers.size() == 0) {
            // no customer with the given name, creating it
            Customer customer = metadata.create(Customer.class);
            customer.setName(name);
            customer.setEmail(email);
            customer.setGrade(grade);
            // persist the new instance
            dataManager.commit(customer);
        } else if (customers.size() == 1) {
            // a customer found, updating it
            Customer customer = customers.get(0);
            customer.setEmail(email);
            customer.setGrade(grade);
            // update the customer
            dataManager.commit(customer);
        } else {
            // more than one customer found, raising an error
            throw new IllegalStateException("More than one customer with name " + name);
        }

        customersDs.refresh();
    }

    public void onDeleteAllBtnClick(Component source) {
        showOptionDialog(
                "Warning",
                "All records created by you will be deleted. Continue?",
                MessageType.CONFIRMATION,
                new Action[] {
                        new DialogAction(DialogAction.Type.YES) {
                            @Override
                            public void actionPerform(Component component) {
                                customerService.deleteAll();
                                customersDs.refresh();
                            }
                        },
                        new DialogAction(DialogAction.Type.NO)
                }
        );
    }

    public void onCancelBtnClick(Component source) {
        fieldsBox.setVisible(false);
    }

    public void onShowDiscountBtnClick() {
        Customer customer = customersDs.getItem();
        if (customer == null) {
            showNotification("Select a customer", NotificationType.HUMANIZED);
            return;
        }
        // Open ProductType selection dialog
        ProductTypeDialog productTypeDialog = (ProductTypeDialog) openWindow(
                "product-type-dialog", WindowManager.OpenType.DIALOG);
        // Add listener which will be invoked when the dialog is closed with Window.COMMIT_ACTION_ID
        productTypeDialog.addCloseWithCommitListener(() -> {
            Double discount;
            ProductType productType = productTypeDialog.getProductType();
            if (productTypeDialog.getOption().equals(ProductTypeDialog.IN_SCREEN)) {
                discount = calculateDiscountInScreen(customer, productType);
            } else {
                discount = calculateDiscountInService(customer, productType);
            }
            showNotification("Discount is " + discount, NotificationType.HUMANIZED);
        });
    }

    private Double calculateDiscountInScreen(Customer customer, ProductType productType) {
        List<Discount> discounts = dataManager.loadList(LoadContext.create(Discount.class).setQuery(
                LoadContext.createQuery("select d from sample$Discount d where d.customerGrade = :grade and d.productType = :type")
                        .setParameter("grade", customer.getGrade())
                        .setParameter("type", productType)));
        if (discounts.isEmpty())
            return 0.0;
        else
            return discounts.get(0).getDiscount();
    }

    private Double calculateDiscountInService(Customer customer, ProductType productType) {
        return customerService.getDiscount(customer.getGrade(), productType);
    }
}