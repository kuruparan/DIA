package org.yarlithub.dia.controller;

import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yarlithub.dia.repo.DBExecutor;
import org.yarlithub.dia.repo.object.Device;
import org.yarlithub.dia.repo.object.Garden;

@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class LoginController {

    HttpSession session;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		Garden gn=new Garden();
		gn=DBExecutor.getGardenByName(request.getParameter("gardenName"));
		if(gn.getPassword().equals(request.getParameter("password"))){
			List<Device> devices=DBExecutor.getDeviceByGardenId(gn.getId());
            model.addAttribute("devices", devices);
            session = request.getSession();
            session.setAttribute("gardenId", gn.getId());
            session.setAttribute("gardenName", gn.getGarden_name());
            return "gardenHome";
		}else{
			return "login";
		}
	}
    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String doLogOut(HttpServletRequest request) {
            session = request.getSession();
            session.invalidate();
            return "login";
    }


}
