/*
 * Copyright (c) 2016 data-manipulation
 */
package com.company.sample.portal.controllers;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.portal.security.PortalSessionProvider;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author krivopustov
 */
@Controller
public class PortalController {

    @Inject
    private DataService dataService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        if (PortalSessionProvider.getUserSession().isAuthenticated()) {
            LoadContext<User> l = LoadContext.create(User.class);
            l.setQueryString("select u from sec$User u");
            model.addAttribute("users", dataService.loadList(l));
        }
        return "index";
    }
}
