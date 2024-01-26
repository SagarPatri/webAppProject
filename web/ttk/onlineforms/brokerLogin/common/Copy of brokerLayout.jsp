<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile" errorPage="/ttk/common/error.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
<head>
<title>Vidal Healthcare</title>
<script type="text/javascript" src="/ttk/scripts/utils.js"></script>
<script type="text/javascript" src="/ttk/scripts/trackdatachanges.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/common/healthcarelayout.js"></SCRIPT>
<link href="/ttk/styles/OnlineDefault.css" media="screen" rel="stylesheet"></link>
<style type="text/css">
  
	#onlineMenus   {
            margin:0;
            margin-left: -3px;
            margin-right: -5px;
            padding:0px;
            list-style:none;
            background-color: #91C85F;
            height:28px; 
            width:100%;
            border:solid 1px #91C85F;
            border-width:1px 1px 1px 0px;
            font-family:Arial, Helvetica, sans-serif;
        }
	#onlineMenus li{
            display:inline; 
            position:relative; 
            float:left; 
            margin-right: -5px;
        }
	#onlineMenus li a { 
            display:block; 
            float:left; 
            height:29px; 
            line-height:29px; 
            padding:0 25px; 
            text-decoration:none; 
            color:#FFFFFF; 
            font-weight:normal; 
            font-size:12px; 
            border-left:solid 1px white; 
        }
	#onlineMenus li a:hover {
            color: blue; 
            background: white; 
        }
	
	#onlineMenus li ul { 
            margin:0;            
            padding:0 5px; 
            line-height:none; 
            position:absolute; 
            top:29px; left:0; 
            border:solid 1px yellowgreen; 
            border-width:0px 1px 1px 1px; 
            width:180px; display:none; 
            background:#FFFFFF;
        }
	#onlineMenus li:hover ul { display:block}
	
	#onlineMenus li ul li { 
            display:block;  
            border-bottom:solid 1px #dbdcd9; 
            width:98%;
            padding:0 0 0 10px;
            background:url(/ttk/images/arrow.gif) no-repeat 3px 12px; 
        }
	#onlineMenus li ul li:last-child { border-bottom:0px;}
	#onlineMenus li ul a { 
            border-width:0px; 
            color:#909090; 
            padding:0 5px 0 0; 
            background-color:transparent;
        }
	
	#onlineMenus li ul li a:hover { 
            color: blue;
            background-color: whitesmoke;
        }
        #onlineMenus li ul li:hover { 
        background-color: whitesmoke;
        border-right: solid 1px #91C85F;
        }
.imageLeft{
float: left;
background-image: url('/ttk/images/Insurance/content.png');
background-repeat: repeat-y;
width: 100%;
height: 100%;
}
.imageRight{
float: right;
background-image: url('/ttk/images/Insurance/content.png');
background-repeat: repeat-y;
width: 100%;
height: 100%;
}
</style>
<script type="text/javascript">
var bAction = true;
ClientReset = true;
var JS_SecondSubmit=false;
</script>
<script type="text/javascript">
function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
	
}//end of disableCtrlModifer(evt)


var myClock = document.getElementById("clock");
function renderTime () {
    var currentTime = new Date();
    var h = currentTime.getHours();
    var m = currentTime.getMinutes();
    var s = currentTime.getSeconds();

    if (h < 10) {
        h = "0" + h;
    }


    if (m < 10) {
        m = "0" + m;
    }


    if (s < 10) {
        s = "0" + s;
    }

    //myClock.textContent = h + ":" + m + ":" + s;
    myClock.innerText = h + ":" + m + ":" + s;

    setTimeout(renderTime, 1000);
}
renderTime();
</script>

</head>
<!-- Changes Added onunload in body for Password Policy CR KOC 1235 -->
<body onunload="javascript:logoutwindow(event);" onLoad="javascript:resizeDocument();TrackFormData('webBoard');handleFocus();window.history.forward(1);" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<logic:empty name="UserSecurityProfile">
	<script type="text/javascript">
	var varLoginType='<%=session.getAttribute("loginType")%>';
	  openFullScreenMode("/ttk/onlineaccess.jsp?sessionexpired=true&loginType="+varLoginType);
	</script>
</logic:empty>
<table style="width:100%;border:1px solid #FFFFFF;padding:0px;border-spacing: 0px;">
	<tr>
		<td>		
			<tiles:insert attribute="header" ignore="true"/>
		</td>
	</tr>
	<tr>
		<td>		
			<tiles:insert attribute="menuLinks" ignore="true"/>
		</td>
	</tr>
	<tr>
		<td>		
		<table style="width: 100%;height: 100%;margin:0;overflow: scroll;margin-top: -3px;margin-bottom: -3px;margin-left: -3px;margin-right: -5px;">
		<tr>
		<td style="background-image: url('/ttk/images/Insurance/content.png');">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td style="vertical-align: top;width: 100%;"><tiles:insert attribute="bodyContent" ignore="true"/></td>
		<td style="background-image: url('/ttk/images/Insurance/content.png');">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td style="width: 100%;height: 18px;text-align: center;background-color: #F8F8F8;">
			<tiles:insert attribute="footer" ignore="true"/>
		</td>
	</tr>
</table>
</body>
</html>