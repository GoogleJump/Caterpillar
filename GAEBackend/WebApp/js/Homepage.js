var root;

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
strip.addClass('navbar-collapse collapse');
strip.css({
	'background-color':'#B1F2EF',
	'top':'100px',
	'position':'absolute',
	'width':'100%',
	'height':'40px',
	'left':'0px'

});
overlay.append(strip);
 
ownerDiv = $(document.createElement('div'));
$navbar.append(ownerDiv);
ownerDiv.css({
	'height':'100%',
	'width':'100px',
	'left':'10px',
	'position':'absolute',
});
owner = $(document.createElement('label'));
ownerDiv.append(owner);
owner.text("Caterpillar's");
owner.css({
	'display':'block',
	'margin':'auto',
	'font-size':'5em',
});

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
	'left':'450px',
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


// /***Side bar****/
// sidebar = $(document.createElement('div'));
// sidebar.addClass("btn-group-vertical");
// overlay.append(sidebar);
// sidebar.css({
// 	'left':'0px',
// 	'top':'65px',
// 	'width':'225px',
// 	// 'height':'300px',
// 	'background-color':'#012000',
// 	'border-radius':'4px'
// });

// btn1 = $(document.createElement('button'));
// sidebar.append(btn1);
// btn1.text('Home');
// btn1.addClass('btn btn-default');
// btn1.css({
// 	'background-color':'#5A9491',
// 	'border-color':'#012000'
// });
// btn2 = $(document.createElement('button'));
// sidebar.append(btn2);
// btn2.text('Profile');
// btn2.addClass('btn btn-default');
// btn2.css({
// 	'background-color':'#5A9491',
// 	'border-color':'#012000'
// });
// btn3 = $(document.createElement('button'));
// sidebar.append(btn3);
// btn3.text('Add New Entry');
// btn3.addClass('btn btn-default');
// btn3.css({
// 	'background-color':'#5A9491',
// 	'border-color':'#012000'
// });
// btn4 = $(document.createElement('button'));
// sidebar.append(btn4);
// btn4.text('View by Trips');
// btn4.addClass('btn btn-default');
// btn4.css({
// 	'background-color':'#5A9491',
// 	'border-color':'#012000'
// });
// btn5 = $(document.createElement('button'));
// sidebar.append(btn5);
// btn5.text('Map Mode');
// btn5.addClass('btn btn-default');
// btn5.css({
// 	'background-color':'#5A9491',
// 	'border-color':'#012000'
// });


/********Horizontal version******/
nav = $(document.createElement('ul'));
nav.addClass('nav nav-pills');
strip.append(nav);

lihome=$(document.createElement('li'));
lihome.addClass("active");
lihome.css({
	'border-radius':'4px'
});
nav.append(lihome);
home = $(document.createElement('a'));
home.text('Home');
lihome.append(home);
home.css({
	// 'background-color':'#504552',
});

liProfile=$(document.createElement('li'));
// liProfile.addClass("active");
liProfile.css({
	'border-radius':'4px',
});
nav.append(liProfile);
profile = $(document.createElement('a'));
profile.text('Profile');
liProfile.append(profile);
profile.css({
	// 'background-color':'#504552',
});

liAdd=$(document.createElement('li'));
// liProfile.addClass("active");
liAdd.css({
	'border-radius':'4px',
});

nav.append(liAdd);
add = $(document.createElement('a'));
add.text('Add New Entry');
liAdd.append(add);
add.css({
	// 'background-color':'#504552',
});

liTrips=$(document.createElement('li'));
// liProfile.addClass("active");
liTrips.css({
	'border-radius':'4px',
});

nav.append(liTrips);
viewtrips = $(document.createElement('a'));
viewtrips.text('View Trips');
liTrips.append(viewtrips);
viewtrips.css({
	// 'background-color':'#504552',
});

liMap=$(document.createElement('li'));
// liProfile.addClass("active");
liMap.css({
	'border-radius':'4px',
});
nav.append(liMap);
viewmap = $(document.createElement('a'));
viewmap.text('View Map');
liMap.append(viewmap);
viewmap.css({
	// 'background-color':'#504552',
});




