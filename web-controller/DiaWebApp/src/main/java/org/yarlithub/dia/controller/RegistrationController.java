package org.yarlithub.dia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yarlithub.dia.repo.DataLayer;
import org.yarlithub.dia.repo.object.Garden;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class RegistrationController {

    HttpSession session;

    @RequestMapping(value = "/registerMe", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpServletResponse response) {
        Garden gn = new Garden();
        gn.setGardenName(request.getParameter("gardenName"));
        gn.setPassword(request.getParameter("password"));
        DataLayer.addNewGarden(gn);

        gn = DataLayer.getGardenByName(request.getParameter("gardenName"));
        session = request.getSession();
        session.setAttribute("gardenId", gn.getId());
        session.setAttribute("gardenName", gn.getGardenName());
        return "gardenHome";
    }
}
