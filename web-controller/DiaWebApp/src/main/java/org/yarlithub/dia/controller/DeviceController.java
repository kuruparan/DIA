package org.yarlithub.dia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yarlithub.dia.repo.DataLayer;
import org.yarlithub.dia.repo.object.Device;
import org.yarlithub.dia.repo.object.Schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

        Device device;
        device=DataLayer.getDeviceByName(request.getParameter("deviceName"));
        if(device.getSchedule()!=null){
            String [] ss=device.getSchedule().split(":");
            String [] temSS;
            Schedule schedule;
            List<Schedule> schedules=new ArrayList<Schedule>();
            for(String s:ss){
                if(s.contains("-")){
                    schedule=new Schedule();
                    temSS=s.split("-");
                    schedule.setFrom(temSS[0]);
                    schedule.setTo(temSS[1]);
                    schedules.add(schedule);
                }
            }
            char[] daySche=ss[0].toCharArray();
            model.addAttribute("schedules", schedules);
            model.addAttribute("daySche", daySche);
        }
        model.addAttribute("device", device);
        return "deviceHome";
    }

    @RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
    public String updateSchedule(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        String ss1=request.getParameter("days");
        String[] ss2=request.getParameterValues("start");
        String[] ss3=request.getParameterValues("end");
        String shedule=null;

        if(ss1!=null){
            ss1=ss1.replace("b","");
           shedule=ss1;
        }
        if(ss2!=null&ss2!=null) {
            for (int n = 0; n < ss2.length; n++) {
                ss2[n]=ss2[n].replace("start:","");
                ss3[n]=ss3[n].replace("end:","");
                shedule+=":"+ss2[n]+"-"+ss3[n];
            }
        }
        Device device=DataLayer.getDeviceByName(request.getParameter("device"));
        device.setSchedule(shedule);
        DataLayer.updateNewDevice(device);
        //model.addAttribute("message", shedule);
        //return "index";
        List<Device> devices = DataLayer.getDevicesByGardenId(device.getGardenId());
        model.addAttribute("devices", devices);
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
			device.setGardenId(Integer.parseInt(request.getParameter("gardenId")));
			device.setDeviceName(request.getParameter("deviveName"));
			device.setPin(request.getParameter("pin"));
			device.setId(DBExecutor.reserveNewDevice());
			DBExecutor.updateNewDevice(device);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/gardenHome.jsp");
			view.forward(request, response);
		}*/

}