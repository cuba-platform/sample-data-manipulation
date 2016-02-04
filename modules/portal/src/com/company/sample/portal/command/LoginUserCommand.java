/*
 * Copyright (c) 2016 data-manipulation
 */
package com.company.sample.portal.command;

/**
 * @author krivopustov
 */
@SuppressWarnings({"UnusedDeclaration"})
public class LoginUserCommand {

    private String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}