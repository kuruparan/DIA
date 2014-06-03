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

    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
        
        <h1>Register your Device</h1>
        <p>
			you can add your device by filling these essentials to get our sevices
        </p>
        </div>
        
        <form method="post" action="addDevice">
		
            <div class="col-sm-6 col-sm-offset-5">
                <input type="text" name="deviceName" class="form-control input-lg col-sm-4"
                       placeholder="Enter your Decice Name here">
            </div>
			<div class="col-sm-6 col-sm-offset-5" style="margin-top: 1%">
				<input type="text" name="pin" class="form-control input-lg col-sm-4"
					placeholder="Enter your Pin here">
			</div>


			<div class="form-group">
				<div class="col-sm-offset-5 col-sm-10">

					<div class="col-sm-4 col-sm-offset-1">
						<button style="margin: 10%" type="submit"
							class="btn btn-lg btn-success col-sm-12">Add</button>
					</div>
				</div>
			</div>
		</form>
    

</div>
<!-- /container -->


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="resources/js/jquery-1.11.0.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="resources/js/bootstrap/bootstrap.min.js"></script>
</body>
</html>


