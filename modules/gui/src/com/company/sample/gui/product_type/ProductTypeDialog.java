package com.company.sample.gui.product_type;

import com.company.sample.entity.ProductType;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.Window;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductTypeDialog extends AbstractWindow {

    @Inject
    private LookupField productTypeField;

    @Inject
    private OptionsGroup optionsField;

    public static final String IN_SCREEN = "screen";
    public static final String IN_SERVICE = "service";

    @Override
    public void init(Map<String, Object> params) {
        productTypeField.setOptionsEnum(ProductType.class);

        Map<String, Object> options = new LinkedHashMap<>();
        options.put("Calculate in screen", IN_SCREEN);
        options.put("Calculate in service", IN_SERVICE);
        optionsField.setOptionsMap(options);
        optionsField.setValue(IN_SCREEN);
    }

    public ProductType getProductType() {
        return productTypeField.getValue();
    }

    public String getOption() {
        return optionsField.getValue();
    }

    public void onOk() {
        if (productTypeField.getValue() == null) {
            showNotification("Select a value", NotificationType.HUMANIZED);
        } else {
            close(Window.COMMIT_ACTION_ID);
        }
    }

    public void onCancel() {
        close(Window.CLOSE_ACTION_ID);
    }
}