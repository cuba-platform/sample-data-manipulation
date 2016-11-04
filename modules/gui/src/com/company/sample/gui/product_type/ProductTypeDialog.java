/*
 * Copyright (c) 2016 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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