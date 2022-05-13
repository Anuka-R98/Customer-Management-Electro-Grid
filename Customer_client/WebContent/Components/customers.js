//Hide the alerts on page load
$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});

// SAVE ======================================================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation-------------------
	var status = validateCustomerForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }
	
	// If valid------------------------
	var type = ($("#hidCusSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
		{
			url : "CustomersAPI",
			type : type,
			data : $("#formCus").serialize(),
			dataType : "text",
			complete : function(response, status)
			{
				onCustomerSaveComplete(response.responseText, status);
			}
		});
	
});

// UPDATE--------------------------------------------------------------------------------------------
$(document).on("click", ".btnUpdate", function(event)
{		
	//getting the values from the grid and set them to the form elements, including the hidden element
	
	 $("#hidCusSave").val($(this).data("cid"));
	 $("#customerName").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#customerAddress").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#customerNIC").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#customerEmail").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#customerPNO").val($(this).closest("tr").find('td:eq(4)').text());
});

//DELETE--------------------------------------------------------------------------------------------
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
		{
			url : "CustomersAPI",
			type : "DELETE",
			data : "cID=" + $(this).data("cid"),
			dataType : "text",
			complete : function(response, status)
			{
				onCustomerDeleteComplete(response.responseText, status);
			}
		});
});


// CLIENT-MODEL================================================================
function validateCustomerForm()
{
	// Name--------------------------------------
	if ($("#customerName").val().trim() == "")
	 {
		return "Insert Customer Name.";
	 }
	// Address-----------------------------------
	if ($("#customerAddress").val().trim() == "")
	 {
		return "Insert Customer Address.";
	 }
	// NIC---------------------------------------
	if ($("#customerNIC").val().trim() == "")
	 {
		return "Insert Customer NIC.";
	 }
	// Email-------------------------------------
	if ($("#customerEmail").val().trim() == "")
	 {
		return "Insert Customer Email.";
	 }
	// Phone-------------------------------------
	if ($("#customerPNO").val().trim() == "")
	 {
		return "Insert Customer Phone number.";
	 }
	return true;
}

function onCustomerSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divCusGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidCusSave").val("");
	$("#formCus")[0].reset();
}

function onCustomerDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divCusGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}