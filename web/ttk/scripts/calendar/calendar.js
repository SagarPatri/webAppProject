isIE = (document.all)? true:false

//This function gets X position of the event(Mouse Click)
function mouseX(e) {
	if (!isIE) {var mouseX=e.pageX; var mouseY=e.pageY}
	if (isIE) {var mouseX=event.x; var mouseY=event.y}
	return(mouseX);
}

//This function gets Y position of the event(Mouse Click)
function mouseY(e) {
	if (!isIE) {var mouseX=e.pageX; var mouseY=e.pageY}
	if (isIE) {var mouseX=event.x; var mouseY=event.y}
	return(mouseY);
}

//This function writes iFrame to the document
function writeCalendarIFrame(){
	//document.write("<iframe id='calendar' name='calendar' src='../calendar.html' scrolling='no' frameborder='0' allowtransparency='true' style='position:absolute;top:200px;left:200px;width:250px;height:100px; z-index: 1000;'></iframe>");
	document.write("<iframe id='calendar' name='calendar' src='/ttk/calendar.html' scrolling='no' frameborder='0'></iframe>");
}

writeCalendarIFrame();




var weekend = [0,6];
var gNow = new Date();
var ggWinCal;
var calendarText = "";
var hideCal = 0;
var xOffset = -90;
var yOffset = 10;

// Default Date Format
var gDefaultDateFormat = 'DD/MM/YYYY';
var gDefaultDateFormat1 = 'YYYY/DD/MM';

isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
isIE = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;

Calendar.Months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

// Non-Leap year Month days..
Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// Leap year Month days..
Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];


// Code modified to capture the date value ( p_day has been included)
function Calendar(p_item, p_WinCal, p_day, p_month, p_year, p_format) {
	if ((p_month == null) && (p_year == null))	return;

	if (p_WinCal == null)
		this.gWinCal = ggWinCal;
	else
		this.gWinCal = p_WinCal;

	if (p_month == null) {
		this.gMonthName = null;
		this.gMonth = null;
		this.gYearly = true;
	} else {
		this.gMonthName = Calendar.get_month(p_month);
		this.gMonth = new Number(p_month);
		this.gYearly = false;
	}

	// Code added to capture the date value
	this.gDay = p_day;
	this.gYear = p_year;
	this.gFormat = p_format;

	// Customize your Calendar here.....
	this.gTextColor = "#000000";
	this.gMainTableBgColor = "#DFE4EB";
	this.gHeaderBgColor = "#677BA8"; // The Year Display
	this.gHeaderFontColor = "#FFFFFF";
	this.gHeaderFontFace = "Verdana, Arial, Helvetica, sans-serif";
	this.gHeaderFontSize = "2";
	this.gLinkColor="#000000";

	// Display of Weekdays ( S M T W T F S )
	this.gDayFontFace = "Verdana, Arial, Helvetica, sans-serif";
	this.gDayFontSize = "2";

	// Display of Dates ( 1 - 31 )
	this.gDateFontFace = "Verdana, Arial, Helvetica, sans-serif";
 	this.gDateFontSize = "1";
	this.gDateBgColor = "#FFFFFF";
	this.gWeekendBgColor = "#EEEEEE";

	this.gSelectedDateFontColor = "#FF0000";

	// Images used in the Calendar
	this.gImgYearDcr = "/ttk/images/First.gif";
	this.gImgYearInc = "/ttk/images/Last.gif";
	this.gImgMonDcr = "/ttk/images/Prev.gif";
	this.gImgMonInc = "/ttk/images/Next.gif";


	this.gReturnItem = p_item;


}

Calendar.get_month = Calendar_get_month;
Calendar.get_daysofmonth = Calendar_get_daysofmonth;
Calendar.calc_month_year = Calendar_calc_month_year;
Calendar.print = Calendar_print;

function Calendar_get_month(monthNo) {
	return Calendar.Months[monthNo];
}

function Calendar_get_daysofmonth(monthNo, p_year) {
	/*
	Check for leap year ..
	1.Years evenly divisible by four are normally leap years, except for...
	2.Years also evenly divisible by 100 are not leap years, except for...
	3.Years also evenly divisible by 400 are leap years.
	*/
	if ((p_year % 4) == 0) {
		if ((p_year % 100) == 0 && (p_year % 400) != 0)
			return Calendar.DOMonth[monthNo];

		return Calendar.lDOMonth[monthNo];
	} else
		return Calendar.DOMonth[monthNo];
}



function Calendar_print() {
	ggWinCal.print();
}

function Calendar_calc_month_year(p_Month, p_Year, incr) {
	/*
	Will return an 1-D array with 1st element being the calculated month
	and second being the calculated year
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();

	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year,10) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month,10) - 1;
			ret_arr[1] = parseInt(p_Year,10);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year,10) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month,10) + 1;
			ret_arr[1] = parseInt(p_Year,10);
		}
	}

	return ret_arr;
}

// This is for compatibility with Navigator 3, we have to create and discard one object before the prototype object exists.
new Calendar();

Calendar.prototype.getMonthlyCalendarCode = function() {
	var vCode = "";
	var vHeader_Code = "";
	var vData_Code = "";

	// Begin Table Drawing code here..
	vHeader_Code = this.cal_header();
	vData_Code = this.cal_data();
	vCode = vCode + vHeader_Code + vData_Code;

	return vCode;
}

Calendar.prototype.show = function() {
	var vCode = "";

	// Show navigation buttons
	var prevMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, -1);
	var prevMM = prevMMYYYY[0];
	var prevYYYY = prevMMYYYY[1];

	var nextMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, 1);
	var nextMM = nextMMYYYY[0];
	var nextYYYY = nextMMYYYY[1];

	this.wwrite("<TABLE WIDTH='147' BORDER='0' CELLSPACING='1' CELLPADDING='0' BGCOLOR='"+ this.gMainTableBgColor +"'><TR><TD>");

	// Code modofied to capture the date value (gDay being included)
	var iframeDivObj = parent.frames["calendar"].document.getElementById("calendarObj");

	this.wwrite("<TABLE WIDTH='147' BORDER=0 CELLSPACING=1 CELLPADDING=1 ALIGN='CENTER'><TR><TD WIDTH='10%' ALIGN='CENTER' BGCOLOR='"+ this.gHeaderBgColor +"'>");
	this.wwrite("<A HREF=\"" +
		"javascript:parent.calendarText = ''; parent.Build('" + this.gReturnItem + "', '" + this.gDay + "','" + this.gMonth + "', '" + (parseInt(this.gYear,10)-1) + "', '" + this.gFormat + "'); \"><IMG SRC='"+ this.gImgYearDcr +"' WIDTH='5' HEIGHT='8' BORDER='0'><\/A></TD><TD WIDTH='10%' ALIGN='CENTER' BGCOLOR='"+ this.gHeaderBgColor +"'>");
	this.wwrite("<A HREF=\"" +
		"javascript:parent.calendarText = ''; parent.Build(" + "'" + this.gReturnItem + "', '" + this.gDay + "','" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" + ");" + " \"><IMG SRC='"+ this.gImgMonDcr +"' WIDTH='4' HEIGHT='8' BORDER='0'><\/A></TD><TD  WIDTH='60%' ALIGN='CENTER' BGCOLOR='"+ this.gHeaderBgColor +"'><FONT FACE='"+ this.gHeaderFontFace +"' SIZE='"+ this.gHeaderFontSize +"' COLOR='"+ this.gHeaderFontColor +"'>");
	this.wwriteA(this.gMonthName + " " + this.gYear);
	this.wwrite("</FONT></TD><TD WIDTH='10%' ALIGN=center BGCOLOR='"+ this.gHeaderBgColor +"'>");
	this.wwrite("<A HREF=\"" +
		"javascript:parent.calendarText = ''; parent.Build(" + "'" + this.gReturnItem + "', '" + this.gDay + "','" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +	");" + " \"><IMG SRC='"+ this.gImgMonInc +"' WIDTH='4' HEIGHT='8' BORDER='0'><\/A></TD><TD WIDTH='10%' ALIGN='CENTER' BGCOLOR='"+ this.gHeaderBgColor +"'>");
	this.wwrite("<A HREF=\"" +
		"javascript:parent.calendarText = ''; parent.Build(" + "'" + this.gReturnItem + "', '" + this.gDay + "','" + this.gMonth + "', '" + (parseInt(this.gYear,10)+1) + "', '" + this.gFormat + "'" +	");" + " \"><IMG SRC='"+ this.gImgYearInc +"' WIDTH='5' HEIGHT='8' BORDER='0'><\/A></TD></TR></TABLE>");

	// Get the complete calendar code for the month..
	vCode = this.getMonthlyCalendarCode();
	this.wwrite(vCode);

	this.wwrite("</TABLE>");
}

Calendar.prototype.wwrite = function(wtext) {
	//this.gWinCal.document.writeln(wtext);
	calendarText += wtext;
}

Calendar.prototype.wwriteA = function(wtext) {
	//this.gWinCal.document.write(wtext);
	calendarText += wtext;
}

Calendar.prototype.cal_header = function() {
	var vCode = "";

	vCode = vCode + "<TABLE WIDTH='147' BORDER='0' CELLSPACING='0' CELLPADDING='2'>";
	vCode = vCode + "<TR ALIGN='CENTER'>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>S</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>M</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>T</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>W</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>T</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>F</FONT></TD>";
	vCode = vCode + "<TD><FONT FACE='"+ this.gDayFontFace +"' SIZE='"+ this.gDayFontSize +"'>S</FONT></TD>";
	vCode = vCode + "</TR>";
	vCode = vCode + "</TABLE>";

	return vCode;
}

Calendar.prototype.cal_data = function() {
	var vDate = new Date();
	vDate.setDate(1);
	vDate.setMonth(this.gMonth);
	vDate.setFullYear(this.gYear);

	var vFirstDay=vDate.getDay();
	var vDay=1;
	var vLastDay=Calendar.get_daysofmonth(this.gMonth, this.gYear);
	var vOnLastDay=0;
	var vCode = "";

	/*
	Get day for the 1st of the requested month/year..
	Place as many blank cells before the 1st day of the month as necessary.
	*/
	vCode = vCode + "<TABLE WIDTH='147' BORDER='0' CELLSPACING='1' CELLPADDING='0'>";
	vCode = vCode + "<TR>";
	for (i=0; i<vFirstDay; i++) {
		vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(i) + "><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"'><B>&nbsp;</B></FONT></TD>";
	}


	// Write rest of the 1st week
	// changeFlag has been included to trigger the onChange event in the concerned screens
	for (j=vFirstDay; j<7; j++) {
		vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(j) + "><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"'><B>" +
			"<A HREF='#' " +
				"onClick=\"self.parent.document." + this.gReturnItem + ".value='" +
				this.format_data(vDay) +
				"'; self.parent.document." + this.gReturnItem + ".focus(); if(self.parent.changeFlag) self.parent.changeFlag='true'; parent.document.getElementById('calendar').style.display = 'none'; self.parent.calendarText = ''; self.parent.hideCal = 0; \">" +
				this.format_day(vDay) +
			"</A>" +
			"</B></FONT></TD>";
		vDay=vDay + 1;
	}
	vCode = vCode + "</TR>";

	// Write the rest of the weeks
	// changeFlag has been included to trigger the onChange event in the concerned screens
	for (k=2; k<7; k++) {
		vCode = vCode + "<TR>";

		for (j=0; j<7; j++) {
if(vDay!="&nbsp;"){
			vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(j) + "><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"'><B>" +
				"<A HREF='#' " +
					"onClick=\"self.parent.document." + this.gReturnItem + ".value='" +
					this.format_data(vDay) +
					"'; self.parent.document." + this.gReturnItem + ".focus();if(self.parent.changeFlag) self.parent.changeFlag='true'; parent.document.getElementById('calendar').style.display = 'none'; self.parent.calendarText = ''; self.parent.hideCal = 0; \">" +
				this.format_day(vDay) +
				"</A>" +
				"</B></FONT></TD>";
}else{
			vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(j) + "><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"' color='#aaaaaa'>" +
				this.format_day(vDay) +
				"</FONT></TD>";
}

			if(vDay != "&nbsp;")
				vDay=vDay + 1;

			if (vDay > vLastDay) {
				vDay = "&nbsp;"
				/*vOnLastDay = 1;
				break;*/
			}
		}

		/*if (j == 6)
			vCode = vCode + "</TR>";
		if (vOnLastDay == 1)
			break;*/
	}

	/*// Fill up the rest of last week with proper blanks, so that we get proper square blocks
	for (m=1; m<(7-j); m++) {
		if (this.gYearly)
			vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(j+m) +
			"><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"'><B>&nbsp;</B></FONT></TD>";
		else
			vCode = vCode + "<TD ALIGN='CENTER' WIDTH='21' HEIGHT='21'" + this.write_weekend_string(j+m) +
			"><FONT FACE='"+ this.gDateFontFace +"' SIZE='"+ this.gDateFontSize +"'><B>&nbsp;</B></FONT></TD>";
	}*/
	vCode = vCode + "</TABLE>";
	return vCode;
}

Calendar.prototype.format_day = function(vday) {
	// Code modified to capture the date value (the date in the text field is shown highlighted)
	if (vday == this.gDay)
		return ("<FONT COLOR='"+ this.gSelectedDateFontColor +"'>" + vday + "</FONT>");
	else
		return (vday);
}

Calendar.prototype.write_weekend_string = function(vday) {
	var i;

	// Return special formatting for the weekend day.
	for (i=0; i<weekend.length; i++) {
		if (vday == weekend[i])
			return (" BGCOLOR=\"" + this.gWeekendBgColor + "\"");
	}

	return (" BGCOLOR=\"" + this.gDateBgColor + "\"");
}

Calendar.prototype.format_data = function(p_day) {
	var vData;
	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = Calendar.get_month(this.gMonth).substr(0,3).toUpperCase();
	var vFMon = Calendar.get_month(this.gMonth).toUpperCase();
	var vY4 = new String(this.gYear);
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	switch (this.gFormat) {
		case "MM\/DD\/YYYY" :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
			break;
		case "MM\/DD\/YY" :
			vData = vMonth + "\/" + vDD + "\/" + vY2;
			break;
		case "MM-DD-YYYY" :
			vData = vMonth + "-" + vDD + "-" + vY4;
			break;
		case "MM-DD-YY" :
			vData = vMonth + "-" + vDD + "-" + vY2;
			break;

		case "DD\/MON\/YYYY" :
			vData = vDD + "\/" + vMon + "\/" + vY4;
			break;
		case "DD\/MON\/YY" :
			vData = vDD + "\/" + vMon + "\/" + vY2;
			break;
		case "DD-MON-YYYY" :
			vData = vDD + "-" + vMon + "-" + vY4;
			break;
		case "DD-MON-YY" :
			vData = vDD + "-" + vMon + "-" + vY2;
			break;

		case "DD\/MONTH\/YYYY" :
			vData = vDD + "\/" + vFMon + "\/" + vY4;
			break;
		case "DD\/MONTH\/YY" :
			vData = vDD + "\/" + vFMon + "\/" + vY2;
			break;
		case "DD-MONTH-YYYY" :
			vData = vDD + "-" + vFMon + "-" + vY4;
			break;
		case "DD-MONTH-YY" :
			vData = vDD + "-" + vFMon + "-" + vY2;
			break;

		case "DD\/MM\/YYYY" :
			vData = vDD + "\/" + vMonth + "\/" + vY4;
			break;
		case "DD\/MM\/YY" :
			vData = vDD + "\/" + vMonth + "\/" + vY2;
			break;
		case "DD-MM-YYYY" :
			vData = vDD + "-" + vMonth + "-" + vY4;
			break;
		case "DD-MM-YY" :
			vData = vDD + "-" + vMonth + "-" + vY2;
			break;
		default :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
	}

	return vData;
}

// Code modified to capture the date value (p_day has been included)
function Build(p_item,  p_day,  p_month, p_year, p_format) {
	var p_WinCal = ggWinCal;

	// Code modified to capture the date value (p_day has been included)
	gCal = new Calendar(p_item, p_WinCal, p_day,  p_month, p_year, p_format);

	// Show function
	gCal.show();

	window.frames["calendar"].document.getElementById("calendarObj").innerHTML = calendarText;
}

function show_calendar() {
	calendarText = "";
	/*
		p_month : 0-11 for Jan-Dec; 12 for All Months.
		p_year	: 4-digit year
		p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
		p_item	: Return Item.
	*/

	p_item = arguments[1];

	// Added this piece of code to fix the error that is caused when the element name contains period, and the user
	// tries to navigate to other months or years.
	// The application must send the control as formname.elements['elementname'] to the calendar function in such cases.
	// The Calendar's Build function accepts the first paramenter as a string and hence the problem occurs when this code is not added.
	if((p = p_item.indexOf('[\'')) != -1 && (q = p_item.indexOf('\']')) != -1){
		var p_form = eval("document.forms['"+p_item.substring(0,p_item.indexOf("."))+"']");
		var p_controlname = p_item.substring(p+2,q);
		for(var i=0;i<p_form.elements.length;i++){
			if(p_form.elements[i].name==p_controlname) break;
		}
		// formname.elements['elementname'] is transformed to formname.elements[n]; where n is the index of the concerned element.
		p_item = p_form.name+".elements["+i+"]";
	}
	//--

	// Code added to capture the date value
	if (arguments[2] == "") 	{
		p_day = gNow.getDate();
		p_month = new String(gNow.getMonth());
		p_year = new String(gNow.getFullYear().toString());
	}
	else {
		var dateValue = arguments[2];
		var dateArray = dateValue.split( (dateValue.indexOf("/") != -1) ? "/" : "-");
		p_day = new Number(dateArray[0]);
		p_month = new Number(dateArray[1])-1;
		p_year = new String(dateArray[2]);

		if( isNaN(p_day) || p_day < 1 || p_day > 31 || isNaN(p_month) || p_month == -1 || p_month<0 || p_month > 11 || p_year.length != 4 || isNaN(p_year) ) {
			p_day = gNow.getDate();
			p_month = new String(gNow.getMonth());
			p_year = new String(gNow.getFullYear().toString());
		}

	}
	// ----------------------------------------------------------------------------

	if (arguments[3] == null || arguments[3] == "")
		p_format = gDefaultDateFormat;
	else if (arguments[3] == null || arguments[3] == "")
		p_format = gDefaultDateFormat1;
	else
		p_format = arguments[3];


	var mouseXPos = mouseX(arguments[4]);
	var mouseYPos = mouseY(arguments[4]);

	var docSize = getDocumentSize();
	calendarPos = mouseYPos+arguments[6];
	if(calendarPos>docSize.height){
		mouseYPos = mouseYPos - arguments[6] - 20;
	}
	if(mouseYPos<0){
		mouseYPos = mouseYPos + arguments[6] + 20;
	}

	Build(p_item, p_day,  p_month, p_year, p_format);

	var calendarObj = document.getElementById("calendar");
	var iframeDivObj = window.frames["calendar"].document.getElementById("calendarObj");
	calendarObj.style.height = iframeDivObj.offsetHeight + "px";
	calendarObj.style.top = mouseYPos + yOffset + "px";
	calendarObj.style.left = mouseXPos + xOffset + "px";
	calendarObj.style.width = arguments[5] + "px";
	calendarObj.style.height = arguments[6] - 3 + "px";
	calendarObj.style.display = "block";

	//self.parent.document.onclick=showHideCalendar;
	self.document.onclick=showHideCalendar;

}

//Hide iFrame Calendar
function showHideCalendar(){
	var calendarObj = document.getElementById("calendar");
	if(hideCal==1 && calendarObj.style.display == "block"){
		calendarObj.style.display = "none"
		hideCal = 0;
	}
	if(calendarObj.style.display == "block")
		hideCal = 1;
}

function scrollTest(){
	if(document.getElementById('contentArea'))
		document.getElementById('contentArea').onscroll = showHideCalendar;
}

window.setInterval("scrollTest()",10);



	// ------ E N D ----------------------------------------------------------------------


