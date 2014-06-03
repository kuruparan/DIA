package org.yarlithub.dia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yarlithub.dia.repo.DataLayer;
import org.yarlithub.dia.repo.object.Device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
//Maps the request after host:port/dia/
public class DeviceController {
    HttpSession session;

    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    public String addDevice(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        Device device = new Device();
        device=DataLayer.getDeviceByName(request.getParameter("deviceName"));
        if(device.getPin().equals(request.getParameter("pin"))){
            session = request.getSession();
            device.setGardenId((Integer) session.getAttribute("gardenId"));
            DataLayer.updateNewDevice(device);

            List<Device> devices = DataLayer.getDevicesByGardenId(device.getGardenId());
            model.addAttribute("devices", devices);
            return "gardenHome";
        } else{
            return "addDevice" ;
        }


    }
    @RequestMapping(value = "/deviceHome", method = RequestMethod.GET)
    public String goToDevice(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        Device device = new Device();
        device=DataLayer.getDeviceByName(request.getParameter("deviceName"));
            DataLayer.updateNewDevice(device);
            model.addAttribute("device", device);
            return "deviceHome";
    }

    @RequestMapping(value = "/goToAddDevice", method = RequestMethod.GET)
    public String goTo() {
        return "addDevice";
    }


    /*public class DeviceController extends HttpServlet{
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
		{
			Device device =new Device(); 
			device.setGardenId(Integer.parseInt(request.getParameter("gardenId")));
			device.setDeviceName(request.getParameter("deviveName"));
			device.setPin(request.getParameter("pin"));
			device.setId(DBExecutor.reserveNewDevice());
			DBExecutor.updateNewDevice(device);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/gardenHome.jsp");
			view.forward(request, response);
		}*/

}