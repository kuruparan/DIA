package org.yarlithub.dia.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;


import org.yarlithub.dia.repo.DBExecutor;
import org.yarlithub.dia.repo.object.Device;
import org.yarlithub.dia.repo.object.Garden;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class DeviceController {
    HttpSession session;

    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    public String addDevice(ModelMap model,HttpServletRequest request,HttpServletResponse response) {

        Device device =new Device();
        session = request.getSession();
        device.setGarden_id((Integer)session.getAttribute("gardenId"));
        device.setDevice_name(request.getParameter("deviceName"));
        device.setPin(request.getParameter("pin"));
        device.setId(DBExecutor.reserveNewDevice());
        DBExecutor.updateNewDevice(device);

        List<Device> devices=DBExecutor.getDeviceByGardenId(device.getGarden_id());
        model.addAttribute("devices", devices);
        request.setAttribute("gardenId", device.getGarden_id());
        /*model.addAttribute("message", "DIA Web Project + Spring 3 MVC - welcome()");*/

        //Spring uses InternalResourceViewResolver and return back index.jsp
        return "gardenHome";

    }
    @RequestMapping(value = "/goToAddDevice", method = RequestMethod.GET)
    public String goTo() {
         return "addDevice";
    }


    /*public class DeviceController extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
		{
			Device device =new Device(); 
			device.setGarden_id(Integer.parseInt(request.getParameter("gardenId")));
			device.setDevice_name(request.getParameter("deviveName"));
			device.setPin(request.getParameter("pin"));
			device.setId(DBExecutor.reserveNewDevice());
			DBExecutor.updateNewDevice(device);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/gardenHome.jsp");
			view.forward(request, response);
		}*/

}
