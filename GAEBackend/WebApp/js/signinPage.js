
var root;
// path = "signin.html";
// $.ajax({
//     async: false,
//     cache: false,
//     url: "../html/"+"signin.html",
//     success: function (data) {
//         root = $(data);
//     },
//     error: function (err) {
//         console.log("url = " + path);
//         console.log("error: "+err.statusText);
//         root = null;
//     },
//     dataType: 'html'
// });
// console.log("Used!!");
overlay = $(document.getElementById("body"));//root.find("div");
/*-----initial the navigation bar---------*/
navbar = document.createElement("div");
navbar.setAttribute("role","navigation");
$navbar= $(navbar);
$navbar.addClass("navbar navbar-default navbar-fixed-top");
overlay.append(navbar);
$(document.getElementsByClassName('navbar-default')).css({
	'background-color':'#5A9491'
});
$(document.getElementsByClassName('navbar-fixed-top')).css({
	'height':'100px'
});

strip = $(document.createElement('div'));
strip.css({
	'background-color':'#B1F2EF',
	'top':'100px',
	'position':'absolute',
	'width':'100%',
	'height':'15px',
	'left':'0px'

});
overlay.append(strip);

//logo on the right
logoDiv = $(document.createElement("div"));
$navbar.append(logoDiv);
logoDiv.css({
	'height':'100%',
	// 'width': '20%',
	'left':'960px',
	'position':'absolute'
});	
logo = $(document.createElement('img'));
logo.attr('src','../images/stamp.png');
// logo.addClass('img-responsive');
logoDiv.append(logo);
logo.css({
	'display':'block',
	// 'margin':'auto',
	// 'right':'220px',
	'top':'0px',
	'z-index':'1000000',
	'position':'relative',
	// 'width':'100%',
	'height':'200%',
});
whiteDiv = $(document.createElement("div"));
$navbar.append(whiteDiv);
whiteDiv.css({
	'height':'100%',
	// 'width': '20%',
	'left':'560px',
	'position':'absolute'
});	
whitelogo = $(document.createElement('img'));
whitelogo.attr('src','../images/whitelogo.png');
whiteDiv.append(whitelogo);
whitelogo.css({
	'top':'0px',
	// 'left':'100px',
	'position':'relative',
	'height':'100%'
});

// signin option:
signinDiv = $(document.createElement('div'));
$navbar.append(signinDiv);
signinDiv.css({
	'height':'100%',
	'max-width':'600px',
	'left':'0%',
	'top':'0%',
	'position':'absolute',
	'padding-left':'5px',
});
username = $(document.createElement('input'));
username.attr('type','email');
username.attr('placeholder','Email address');
username.attr('required');
username.attr('autofocus');
signinDiv.append(username);
username.css({
	'bottom':'25px',
	'background-color':'#FFFCD4',
	'text-color':'black',
	'position':'absolute'
});
password = $(document.createElement('input'));
password.attr('placeholder','Password');
password.attr('required');
password.attr('type','password');
signinDiv.append(password);
password.css({
	'bottom':'25px',
	'background-color':'#FFFCD4',
	'position':'absolute',
	'left':'180px'
});
signinbtn = $(document.createElement('button'));
signinbtn.addClass('btn btn-default btn-primary btn-block');
signinbtn.attr('type','submit');
signinbtn.text('Sign In');
signinbtn.css({
	'left':'350px',
	'bottom':'20px',
	'background-color':'#B1F2EF',
	'position':'absolute',
	'width':'65px',
});
signupbtn = $(document.createElement('button'));
signupbtn.addClass('btn btn-default btn-primary btn-block');
signupbtn.text('Sign Up');
signupbtn.css({
	'left':'430px',
	'background-color':'#B1F2EF',
	'bottom':'20px',
	'position':'absolute',
	'width':'65px',
});
signinDiv.append(signinbtn);
signinDiv.append(signupbtn);

description= $(document.createElement('div'));
description.css({
	'position':'absolute',
	'top':'150px',
	'left':'60px',
	'height':'400px',
	'width':'400px',
	'background-color':'#FFFCD4',
	'border':'solid 2px gray',
	'border-radius':'5px'

});
overlay.append(description);


