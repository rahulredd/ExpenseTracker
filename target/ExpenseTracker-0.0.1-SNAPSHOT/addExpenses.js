$(document).ready(function() {

	$("#addExpensesButton").click(function() {
		
		var username = $("#username").val();
		var item = $("#item").val();
		var amount = $("#amount").val();
		var data = {
				username : username,
				item : item,
				amount : amount
		};
		if (item == '' || amount == '') {
			alert("Enter all fields");
		} else {
			$.ajax({
				type: "POST",
				url: "ExpenseController",
				data: data,
				dataType : "text",
				success : function(rdata) {
					//$("#userLoginPage").hide();
					alert("Successfully Added");
					$("#item").val('');
					$("#amount").val('') ;
					getLastWeeksExpense(username);
				},
				error : function(rdata, textStatus, error) {
					alert("Could not load");
				}
			});
			
		}
	});
});