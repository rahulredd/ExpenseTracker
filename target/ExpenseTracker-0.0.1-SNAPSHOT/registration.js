$(document).ready(function() {
	$("#login").hide();
	$("#registrationForm").show();
//	$("#userLoginPage").hide();
	
	$("#register").click(function() {
		$("#login").hide();
		$("#registrationForm").load("./register.html");
//		$("#registrationForm").show();
	});
	
	$("#backToLogin").click(function() {
		back();
	})
	
	$("#registrationRegister").click(function() {
		var username = $("#registerUsername").val();
		var email = $("#email").val();
		var password = $("#registerPassword").val();
		var cpassword = $("#cpassword").val();
		if (username == '' || email == '' || password == '' || cpassword == '') {
			alert("Please fill all fields...!!!!!!");
		} else if ((password.length) < 0) {
			alert("Password should atleast 8 character in length...!!!!!!");
		} else if (!(password).match(cpassword)) {
			alert("Your passwords don't match. Try again?");
		} else if (!isEmail(email)) {
			alert("Check email format");
		} else {
			var data = {
					username : username, 
					email : email,
					password : password
			};
			$.ajax({
				type: "POST",
				url: "Register",
				data: data,
				dataType : "text",
				success : function(rdata) {
					console.log(rdata);
					$("#registrationForm").hide();
					$("#login").show();
					alert("User created");
				},
				error : function(rdata, textStatus, error) {
					alert("Username already present");
				}
			});
		}
	});
});

function isEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}

function back() {
	window.location.replace("login.html");
}