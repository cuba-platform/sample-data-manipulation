/*
 * Copyright (c) 2016 data-manipulation
 */
package com.company.sample.portal.controllers;

import com.company.sample.portal.command.LoginUserCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author krivopustov
 */
@Controller
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        final LoginUserCommand loginUserCommand = new LoginUserCommand();
        model.addAttribute(loginUserCommand);

        return "login";
    }

}