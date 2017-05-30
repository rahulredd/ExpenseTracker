$(document).ready(function() {
	$("#login").show();
	//$("#registrationForm").hide();

	$("#register").click(function() {
		$("#login").hide();
		$("#registrationForm").show();
	});

	$("#loginButton").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		console.log(password);
		if (username == '' || password == '') {
			alert("Please fill all fields");
		} else if ((password.length) < 0) {
			alert("Password should atleast 8 characters");
		} else {
			var data = {
					username : username,
					password : password
			};
			$.ajax({
				type: "POST",
				url: "Login",
				data: data,
				dataType : "text",
				success : function(rdata) {
					if (rdata == "1") {
						$("#login").hide();
						$("#userLoginPage").load("./userPage.html");
						$("#displayTable").load("./displayExpensesTable.html");
						$("#userLoginPage").show();
						$("#displayExpensesTable").show();
						$("#backToExpenses").show();
					} else {
						alert("fail");
					}
				},
				error : function(rdata, textStatus, error) {
					alert("Invalid username or password");
				}
			});
		}
	});
});