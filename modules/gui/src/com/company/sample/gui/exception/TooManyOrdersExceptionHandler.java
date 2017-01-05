package com.company.sample.gui.exception;

import com.company.sample.exception.TooManyOrdersException;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.exception.AbstractGenericExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Exception handler displaying a user-friendly message when TooManyOrdersException occurs.
 */
@Component("sample_TooManyOrdersExceptionHandler")
public class TooManyOrdersExceptionHandler extends AbstractGenericExceptionHandler {

    public TooManyOrdersExceptionHandler() {
        super(TooManyOrdersException.class.getName());
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, WindowManager windowManager) {
        windowManager.showNotification("Too many orders for a customer",
                throwable != null ? throwable.getMessage() : null,
                Frame.NotificationType.WARNING);
    }
}
