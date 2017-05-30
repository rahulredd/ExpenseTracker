$(document).ready(function() {

	hide();
	
	$("#startDate").datepicker({
		maxDate : new Date(),
		dateFormat: "yy-mm-dd"
	});

	$("#endDate").datepicker({
		maxDate : new Date(),
		dateFormat: "yy-mm-dd"
	});

	var username = $("#username").val();

	getLastWeeksExpense(username);

	$("#backToExpenses").click(function() {
		back();
	});

	$("#listExpensesButton").click(function() {
		getExpenses(username);
	});
	
	$("#updateExpense").click(function() {
		updateExpense(username);
	});
	
	$("#deleteExpense").click(function() {
		deleteExpense(username);
	});
	
	$("#logout").click(function() {
		logout();
	});
	setTimeout(timeOut, 600000);
});

function getExpenses(username) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();

	var data = {
			username : username,
			startDate : startDate,
			endDate : endDate
	};
	console.log(startDate);
	if (startDate == '' || endDate == '') {
		alert("Select the dates");
	} else if( (new Date(startDate).getTime() > new Date(endDate).getTime())) {
		alert("Check the Dates");
	} else {
		$.ajax({
			type: "GET",
			url: "ExpenseController",
			data: data,
			dataType : "json",
			success : function(rdata) {
				$("#backToExpenses").show();
				if (rdata.length > 0) {
				  $("#listExpensesForm").hide();
				  //$("#addExpenses").hide();
				 // $("#displayTable").load("./displayExpensesTable.html");
				  createTable(rdata);
				 show();
				 $("#addExpenses").hide();
				} else {
					alert("No data found");
				}
			},
			error : function(rdata, textStatus, error) {
				console.log(rdata.status);
				alert("No expenses");
			}
		});
	}
}

function createTable(data) {
 $("#displayExpensesTable").append('<tr><th> S.no </th> <th> Id </th> <th> Amount </th> <th> Item </th> <th> Date </th>  </tr>')
 var total = 0;
 var num = 1;
	$(data).each(function(index, element) {
		total += element.amount;
    $('#displayExpensesTable').append('<tr><td>' + num + '</td><td>' + element.id + '</td><td> ' + element.amount + ' </td> <td> ' + element.item + ' </td> <td> ' + element.date+ '</td></tr>'); 
    num++;
 })
 $("#displayExpensesTable").append('<tr><td> Total </td> <td>' + total +'</td></tr>');
 $("#displayExpensesTable").show();
 
}

function getLastWeeksExpense(username) {
	var data = {
			username : username
	}
	$.ajax({
		type: "GET",
		url: "WeeklyExpenses",
		data: data,
		dataType : "json",
		success : function(rdata) {
			$("#lastWeekTotal").text("$"+rdata[0]);
		},
		error : function(rdata, textStatus, error) {
			$("#lastWeekTotal").text("0");
		}
	});
}

function logout() {
	$.ajax({
		type: "GET",
		url: "Logout",
		success : function(rdata) {
		},
		error : function(rdata, textStatus, error) {
		}
	});
	$("#userLoginPage").hide();
	$("#logout").hide();
	window.location.replace("login.html");

}

function updateExpense(username) {
	var id = $("#updateId").val();
	var amount = $("#updateAmount").val();
	
	var data = {
			id : id,
			username : username,
			amount : amount
	};
	
	if (id == '' || amount == '') {
		alert("Enter the fields");
	} else {
		$.ajax({
			type: "POST",
			url: "UpdateExpense",
			data: data,
			dataType : "text",
			success : function(rdata) {
				if (rdata == "1") {
					alert("Update successful");
					back();
					getLastWeeksExpense(username);
			} else {
				alert("fail");
			}
			  $("#updateId").val('');
			  $("#updateAmount").val('');
			},
			error : function(rdata, textStatus, error) {
				console.log(rdata.status);
				alert("Cannot update expense");
			}
		});
	}
}

function deleteExpense(username) {
	var id = $("#deleteId").val();
	
	var data = {
			id : id,
			username : username
	};
	
	if (id == '') {
		alert("Enter id to delete");
	} else {
		$.ajax({
			type: "POST",
			url: "DeleteExpense",
			data: data,
			dataType : "text",
			success : function(rdata) {
				if (rdata == "1") {
						alert("Delete successful");
						back();
						getLastWeeksExpense(username);
				} else {
					alert("fail");
				}
				$("#deleteId").val('')
			},
			error : function(rdata, textStatus, error) {
				console.log(rdata.status);
				alert("Cannot delete expense");
			}
		});
	}
	
}

function hide() {
	$("#backToExpenses").hide();
	$("#displayExpensesTable").hide();
	$("#deleteExpense").hide();
	$("#updateExpense").hide();
	$("#udpateTable").hide();
	//$("#updateId").hide();
	//$("#updateAmount").hide();
	$("#txId").hide();
	$("#txAmount").hide();
	$("#deleteTxId").hide();
}

function show() {
	 $("#displayExpensesTable").show();
	  $("#updateExpense").show();
	  $("#deleteExpense").show();
	  $("#udpateTable").show();
	  $("#txId").show();
		$("#txAmount").show();
		$("#deleteTxId").show();
}

function back() {
	$("#listExpensesForm").show();
	$("#addExpenses").show();
	$("#displayExpensesTable tr").remove('');
	hide();
}

function timeOut() {
	alert("Time Out");
	logout();
}

