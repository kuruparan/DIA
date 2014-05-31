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
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="resources/js/html5shiv.js"></script>
    <script src="resources/js/respond.min-1.4.2.js"></script>
    <![endif]-->
</head>
<body style="margin-top:60px">

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
        <!--/.nav-collapse -->
    </div>
</div>
<!-- /Fixed navbar -->


<div class="container">
    <dir class="row">
        <h1> Welcome to Garden</h1>
        <div class="col-sm-2 col-sm-offset-10">
                <form action="goToAddDevice" method="get">
                    <input type="hidden" name="gardenId" value="<%request.getParameter("gardenId");%>"/>
                    <button style="margin:5%" type="submit" class="btn btn-lg btn-success col-sm-12">Add Device</button>
                </form>
        </div>
    </dir>
    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Devices</div>

  <!-- Table -->
<table class="table">
        <thead>
          <tr>
            <th>Device Name</th>
            <th>Pin</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
        	<c:forEach items="${devices}" var="device" >
                <tr> 
                    <th><c:out value="${device.device_name}" /></th>
                    <th><c:out value="${device.pin}" /></th>
                    
                </tr>
            </c:forEach>
        
         <!--  <tr>
            <td>1</td>
            <td><a href="device.html">01:23:45:67:89:ab</a></td>
            <td>0776851425</td>
            

          </tr>
          <tr>
            <td>2</td>
            <td><a href="device.html">01-23-33-67-45-ab</a></td>
            <td>0777152486</td>
          </tr>
          <tr>
            <td>3</td>
            <td><a href="device.html">01-75-88-67-89-ef</a></td>
            <td>0775214856</td>
          </tr> -->
        </tbody>
      </table>

</div>
    </div>

</div>
<!-- /container -->
<a href="index.html"></a>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="resources/js/jquery-1.11.0.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="resources/js/bootstrap/bootstrap.min.js"></script>
</body>
</html>

