//This is the script file containing the methods for
//mofiying the links and logout methods.

//for modifying the links
var wellRef;

function wellnessOpen()
{
	 wellRef= window.open("/ttk/common/wellnesssinglesignon.jsp");
}
function modifyLinks(type, name)
{
	
	if(!TrackChanges()) return false;
	if(type == 'Link')
		document.forms[0].leftlink.value = name;
	else if(type == 'SubLink')
		document.forms[0].sublink.value = name;
	else if(type == 'Tab')
		document.forms[0].tab.value = name;
	document.forms[0].action="/ttkAction.do?mode=doDefault";
	document.forms[0].submit();
	
}//end of modifyLinks(type, name)

//for modifying the links
function modifyOnlineLinks(type, name)
{
	if(!TrackChanges()) return false;
	if(type == 'Link')
		document.forms[0].leftlink.value = name;
	else if(type == 'SubLink')
		document.forms[0].sublink.value = name;
	else if(type == 'Tab')
		document.forms[0].tab.value = name;
	document.forms[0].action="/ttkAction.do?mode=doOnlineDefault";
	document.forms[0].submit();
}//end of modifyOnlineLinks(type, name)

//for modifying the links
function modifyIpruLinks(type, name)
{
	if(!TrackChanges()) return false;
	if(type == 'Link')
		document.forms[0].leftlink.value = name;
	else if(type == 'SubLink')
		document.forms[0].sublink.value = name;
	else if(type == 'Tab')
		document.forms[0].tab.value = name;
	document.forms[0].action="/ttkAction.do?mode=doIpruDefault";
	document.forms[0].submit();
}//end of modifyIpruLinks(type, name)

//method to regular user logout process
function logout()
{
	if(!TrackChanges()) return false;
    document.forms[0].action="/LoginAction.do";
    document.forms[0].mode.value="doLogoff";
    document.forms[0].submit();
}//end of logout()

function onlineLogout(){
	
	if(!TrackChanges()) return false;
    document.forms[0].action="/LoginAction.do";
    document.forms[0].mode.value="doOnlineLogoff";
    document.forms[0].submit();
}//end of logout()
function inslogout()
{
	if(!TrackChanges()) return false;
    document.forms[0].action="/insOnlineAccessAction.do";
    document.forms[0].mode.value="doLogoff";
    document.forms[0].submit();
}//end of logout()
//Changes Added for Password Policy CR KOC 1235
//method to regular user logout process for browser window close
function logoutwindow(event)
{
	var keyCd, altKy, mouseBtn, ctlKy, shiftKy;
	var Browser = navigator.appName;
	var Micro = Browser.indexOf("Microsoft");
	var IE = false;
	var Xwidth = window.document.body.offsetWidth - window.event.clientX;
    var YHeight = window.event.clientY;
		
	if (Micro >= 0)
	{
	    IE = true;
	}
	function getKeyCode(event)
	{
	    if (IE == true)
	    {
	        keyCd = event.keyCode;
	        altKy = event.altKey;
	        ctlKy = event.ctlKey;
	        shiftKy = event.shiftKey;
	    }
	}
	    if (IE == true)
	    {
	        //Browser Closes through ALT + F4 buttons check with ALT + SPACE + C 
	        if (event.altKey == true && event.keyCode == 115)
	        {
	        	document.forms[0].action="/LoginAction.do";
	            document.forms[0].mode.value="doLogoff";
	            document.forms[0].submit();
	        }
	        //Browser Closes through Browser Close (X) button
	        else if (Xwidth <= 30 && YHeight < 0)
	        {
	            document.forms[0].action="/LoginAction.do";
	            document.forms[0].mode.value="doLogoff";
	            document.forms[0].submit();
	        }
	        //Browser Closes through CTRL + F4 and CTRL + W buttons
	        else if (event.ctrlKey == true && event.keyCode == 0)
	        {
	            document.forms[0].action="/LoginAction.do";
	            document.forms[0].mode.value="doLogoff";
	            document.forms[0].submit();
	        }
	        //Browser Closes through ALT + SPACE + C buttons
	        else if (keyCd == 32 && altKy == true)
	        {
	            document.forms[0].action="/LoginAction.do";
	            document.forms[0].mode.value="doLogoff";
	            document.forms[0].submit();
	        }
	        //Browser Closes through Mouse right Click in Task bar
	        //else if (window.event.clientX > 1000)
	        //{
	        //	document.forms[0].action="/LoginAction.do";
	        //	document.forms[0].mode.value="doLogoff";
	        //	document.forms[0].submit();
	        //}
	    }
}
//End changes for Password Policy CR KOC 1235

//method to logout the Online user
function onlineLogout()
{
var req;	
	 if (wellRef != null)
	  {
		 if (!wellRef.closed) {
			 wellRef.close();
		    } 
	 
	}
	if(!TrackChanges()) return false;

	if (window.XMLHttpRequest)  // Non-IE browsers
    {
      req = new XMLHttpRequest();
      req.onreadystatechange = processLogout;
      try{
    	    req.open("GET", "/LogoutOnlineAccessAction.do?mode=doLogoff", true);
	    }
	    catch (e)
	    {
	    }
	    req.send(null);
    }//end of if (window.XMLHttpRequest)
    else if (window.ActiveXObject)  // IE
    {
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req)
      {
        req.onreadystatechange = processLogout;
        req.open("GET","/LogoutOnlineAccessAction.do?mode=doLogoff", true);
        req.send();
      }
    }//end of else if (window.ActiveXObject)
	if ((navigator.userAgent.indexOf("Firefox"))!=-1) {
		window.open('/ttk/Logout.jsp','_self','');
			}
    window.close();
  }//end of onlineLogout()

  function processLogout()
  {
    if (req.readyState == 4) // Complete
    {
      //nothing to do here
    }//end of if (req.readyState == 4)
  }//end of processStateChange()
  
  //method to logout the Online user
function ipruLogout()
{
	if(!TrackChanges()) return false;

	if (window.XMLHttpRequest)  // Non-IE browsers
    {
      req = new XMLHttpRequest();
      req.onreadystatechange = processIpruLogout;
      try{
    	    req.open("GET", "/LogoutIpruAction.do?mode=doLogoff", true);
	    }
	    catch (e)
	    {
	    }
	    req.send(null);
    }//end of if (window.XMLHttpRequest)
    else if (window.ActiveXObject)  // IE
    {
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req)
      {
        req.onreadystatechange = processIpruLogout;
        req.open("GET","/LogoutIpruAction.do?mode=doLogoff", true);
        req.send();
      }
    }//end of else if (window.ActiveXObject)
    window.close();
  }//end of ipruLogout()

  function processIpruLogout()
  {
    if (req.readyState == 4) // Complete
    {
      //nothing to do here
    }//end of if (req.readyState == 4)
  }//end of processIpruLogout()