package org.yarlithub.dia.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yarlithub.dia.repo.DBExecutor;
import org.yarlithub.dia.repo.object.Garden;

@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class RegistrationController {

    HttpSession session;

    @RequestMapping(value = "/registerMe", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request,HttpServletResponse response) {
			Garden gn=new Garden();
			gn.setGarden_name(request.getParameter("gardenName"));
			gn.setPassword(request.getParameter("password"));
			DBExecutor.addNewGarden(gn);

            gn=DBExecutor.getGardenByName(request.getParameter("gardenName"));
            session = request.getSession();
            session.setAttribute("gardenId", gn.getId());
            session.setAttribute("gardenName", gn.getGarden_name());
			return "gardenHome" ;
		}

}
