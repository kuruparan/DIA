<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix= "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap-clockpicker.min.css">

<script type="text/javascript">
     function addSchedule(){
        var table = document.getElementById("scheduleTable");
        var row = table.insertRow(0);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell2.innerHTML ="-";


        var m1 = document.createElement("input");
        m1.setAttribute('type', 'text');
        m1.setAttribute('name', 'start');
        m1.setAttribute('style','width: 74px');
        m1.setAttribute('value', "start:"+document.getElementById("startTime").value);
        m1.readOnly=true;
        cell1.appendChild(m1);

        var m2 = document.createElement("input");
        m2.setAttribute('type', 'text');
        m2.setAttribute('name', 'end');
        m2.setAttribute('style','width: 74px');
        m2.setAttribute('value', "end:"+document.getElementById("endTime").value);
        m2.readOnly=true;
        cell3.appendChild(m2);

        }

      function submitAllForm(){
        var av=document.getElementsByName("day");
        var addon="b";
            for (e = 0; e < av.length; e++)
            {
                if (av[e].checked == true)
                {
                    addon +="1";
                }
                else{
                    addon += "0";
                }
            }
        document.getElementById("deviceId").value="<%=request.getParameter("deviceName")%>";
        document.getElementById("daysId").value=addon;
        document.getElementById("timeSchedule").submit();

      }

      function load(){

      var av=document.getElementsByName("day");

      <c:forEach items="${daySche}" var="dayS" varStatus="counter">
      var avx=${dayS};
        if(avx!="0"){
            av["${counter.index}"].checked=true;
       }
      </c:forEach>

      var sts=${device.currentStatus};
       if(sts=="1"){
          document.getElementById("myToggleButtonOFF").style.display="none";
          document.getElementById("myToggleButtonON").style.display="block";
      }else{
          document.getElementById("myToggleButtonON").style.display="none";
          document.getElementById("myToggleButtonOFF").style.display="block";
      }
      
      document.getElementById("ModeSelectId").selectedIndex = ${device.operationMode};
    }

      function clickToggle(x){
        var url,xhr,stss;

        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        if(x=="0"){
            document.getElementById("myToggleButtonOFF").style.display="none";
            document.getElementById("myToggleButtonON").style.display="block";
            url="${contextPath}/changeStatus?deviceName=${device.deviceName}&status=ON";
        }else{
            document.getElementById("myToggleButtonON").style.display="none";
            document.getElementById("myToggleButtonOFF").style.display="block";
            url="${contextPath}/changeStatus?deviceName=${device.deviceName}&status=OFF";
        }
              
        xhr = new XMLHttpRequest();
        xhr.open('GET',url, true);
        xhr.send();
      }
      
      function changeMode(x){
        var url;

        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        url="${contextPath}/changeMode?deviceName=${device.deviceName}&operationMode="+x;
         
        xhr = new XMLHttpRequest();
        xhr.open('GET',url, true);
        xhr.send();
      }

</script>

</head>
<body style="margin-top:60px" onload="load()">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- Fixed navbar -->
<div id="dic_bubble" class="selection_bubble fontSize13 noSelect"
     style="z-index: 9999; border: 1px solid rgb(74, 174, 222); visibility: hidden;"></div>
<div class="navbar navbar-default navbar-inverse  navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">YIT DIA</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a href="#">Separated link</a></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
             <ul class="nav navbar-nav navbar-right">
                                        <li class="active"><a href="#"><c:out value="${sessionScope.gardenName}"/></a></li>
                                        <li class="active"><a href="${contextPath}/signOut">Sign Out</a></li>
                        </ul>
        </div>

    </div>
</div>
<!-- /Fixed navbar -->

<br/>
<br/>

<div class="container">



<!-- /container -->
<a href="index.html"></a>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->




<div class="row">

    <div class="panel panel-success col-sm-6">
      <div class="panel-heading">Current Status</div>

      <br/>
      <div class="col-sm-4">Water follow</div>
      <button class="btn btn-default col-sm-offset-5" id="myToggleButtonON" onclick="clickToggle(1)">ON</button>
      <button class="btn btn-default col-sm-offset-5" id="myToggleButtonOFF"  onclick="clickToggle(0)">OFF</button>
      <br/>
      <br/>

      <div class="col-sm-4">
          Mode
      </div>


      <div class="col-sm-4 col-sm-offset-4">
	      <div class="row-fluid">
	          <select class="selectpicker span2" id="ModeSelectId" onchange="changeMode(this.selectedIndex)">
	    		<option>Normal Mode</option>
	    		<option>Alert Mode</option>
	    		<option>Intelligent Mode</option>
	  		  </select>
	  	  </div>
      </div>
      <br/>
      <br/>
    </div>

    <div class="panel panel-default col-sm-4 col-sm-offset-1">
      <div class="panel-heading">
        <h3 class="panel-title">Status Report</h3>
      </div>
      <div class="panel-body">
        Temprature    32C<br/><br/>
        Moisture      52%<br/><br/>
        weather       Possiblity of raining<br/><br/>
      </div>
    </div>
</div>


<div class="row">
<div class="panel panel-default col-sm-12">
  <div class="panel-heading">
    <h3 class="panel-title">Status Report</h3>
  </div>
  <div class="panel-body">


    <div class="col-sm-6">
        <br/>
        <br/>
        <br/>

              <div class="col-sm-3 col-sm-offset-0">
                  Start time :<br/><br/><br/>
                  End time :
              </div>

              <div class="col-sm-5 col-sm-offset-2">
                  <div class="form-group ">
		<div class="input-group clockpicker">
			<input type="text" id="startTime" class="form-control" value="09:30">
			<span class="input-group-addon">
				<span class="glyphicon glyphicon-time"></span>
			</span>
		</div>
	</div>
              </div>

              <div class="col-sm-5 col-sm-offset-2">
                    	<div class="form-group ">
		<div class="input-group clockpicker">
			<input type="text" id="endTime" class="form-control" value="09:30">
			<span class="input-group-addon">
				<span class="glyphicon glyphicon-time"></span>
			</span>
		</div>
	</div>



              </div>
         <button type="button" onclick="addSchedule()" class="btn btn-default col-sm-offset-2">Add this time interval</button>

    	    </div>

  <div class="col-sm-3">
  <form id="timeSchedule" action="updateSchedule" method="post">
    <table id="scheduleTable">

        <tbody>
            <c:forEach items="${schedules}" var="schedule" >
                            <tr>
                                <td><input type="text" name="start" style="width: 74px" value='start:<c:out value="${schedule.from}"/>' readonly ></td>
                                <td>-</td>
                                <td><input type="text" name="end" style="width: 74px" value='end:<c:out value="${schedule.to}"/>' readonly ></td>
                            </tr>
             </c:forEach>
        </tbody>
        <input type="hidden" name="days" id="daysId"/>
        <input type="hidden" name="device" id="deviceId"/>

    </table>
    </form>
  </div>

  <div class="col-sm-3 col-sm-offset-0">Schedule Active Days
      <form id="daySchedule">
            <br/>
            <input type="checkbox" name="day" value="mo">Monday <br/>
            <input type="checkbox" name="day" value="tu">Tuesday <br/>
            <input type="checkbox" name="day" value="tu">Wednesday<br/>
            <input type="checkbox" name="day" value="tu">Thursday<br/>
            <input type="checkbox" name="day" value="tu">Friday<br/>
            <input type="checkbox" name="day" value="tu">Saturday<br/>
            <input type="checkbox" name="day" value="tu">Sunday<br/>
        </form>
  </div>
<br/>
<br/>
<br/>
  <button type="button" onclick="submitAllForm()" class="btn btn-default col-sm-offset-2">Update Schedule</button>
  </div>
</div>
</div>


</div>























<!-- Include all compiled plugins (below), or include individual files as needed -->
<<!-- Include all compiled plugins (below), or include individual files as needed -->
 <script type="text/javascript" src="resources/js/bootstrap/jquery-1.11.js"></script>
 <script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
 <script type="text/javascript" src="resources/js/bootstrap/bootstrap-clockpicker.min.js"></script>

<script type="text/javascript">
$('.clockpicker').clockpicker()
	.find('input').change(function(){
		console.log(this.value);
	});
var input = $('#single-input').clockpicker({
	placement: 'bottom',
	align: 'left',
	autoclose: true,
	'default': 'now'
});

// Manually toggle to the minutes view
$('#check-minutes').click(function(e){
	// Have to stop propagation here
	e.stopPropagation();
	input.clockpicker('show')
			.clockpicker('toggleView', 'minutes');
});
if (/mobile/i.test(navigator.userAgent)) {
	$('input').prop('readOnly', true);
}
</script>
<script type="text/javascript">
var button = $('#myToggleButton');
button.on('click', function () {
  $(this).toggleClass('active');
});
</script>
</body>
</html>
