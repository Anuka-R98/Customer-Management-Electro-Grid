<%@ page import="com.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
	<html>
		<head>
		<meta charset="ISO-8859-1">
		<title>Customer Management</title>
		
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery.min.js"></script>
		<script src="Components/customers.js"></script>
		<script src="Components/validation.js"></script>
		
		</head>
	<body>
	
	 <nav class="navbar navbar-dark bg-dark" style="justify-content: center">
            <h3 style="color: white">ElectroGrid</h3>
     </nav>
     
    <br>
	<div class="container" style="max-width: 90%">
	<div style="text-align: center">
		<h1>Customer Management</h1><br></br>
	</div>
     
	<div class="row">
		<div class="col-4"> 

				<form id="formCus" name="formCus" method="post" action="customers.jsp">
					Customer Name:
					<input id="customerName" name="customerName" type="text"
					class="form-control form-control-sm">
					<br> Customer Address:
					<input id="customerAddress" name="customerAddress" type="text"
					class="form-control form-control-sm">
					<br> Customer NIC:
					<input id="customerNIC" name="customerNIC" type="text"
					class="form-control form-control-sm">
					<br> Customer Email:
					<input id="customerEmail" name="customerEmail" type="text"
					class="form-control form-control-sm">
					<br> Customer Phone Number:
					<input id="customerPNO" name="customerPNO" type="tel"
					class="form-control form-control-sm" pattern="+94[7-9]{2}-[0-9]{3}-[0-9]{4}" value="+94">
					<br>
					
					<input id="btnSave" name="btnSave" type="button" value="Save"
					class="btn btn-info btn-lg">
					
					<input type="hidden" id="hidCusSave" name="hidCusSave" value="">

			</form>

			<br>
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		
		<br>
		</div>
		<div id="divCusGrid" class="col-8" style="text-align: center">
		<br>
			 <%
				Customer cusObj = new Customer(); 
				out.print(cusObj.readCustomer());
			 %>
		</div>
	</div> </div> 
	
	</body>
	</html>

