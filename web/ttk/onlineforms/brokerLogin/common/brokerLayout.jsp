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
            height:26px; 
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
            color:#000000; 
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
        
        
  #brokerContainerBody{
                border: 0;
                padding: 0;
                margin: 0;
                background-color: white;
            }  
            #brokerContainerTable{
                border: 1px solid greenyellow;
                border-collapse: collapse;
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
            }
            #brokerHeader{
                border-bottom: 1px solid greenyellow;
                border-collapse: collapse;
                width: 100%;
                height: 55px;            
            }
            #brokerDropDownMenus{
                border-bottom: 1px solid greenyellow;
                border-collapse: collapse;
                width: 100%;
                height: 28px;
                text-align: left;
                color: aliceblue;
                font-size: 15px;
                background-color: #91C85F;
            }
            #brokerSubContainerTable{
                border-collapse: collapse;
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
            }
            #brokerLeftImg{
                border-collapse: collapse;
                height: 550px;
                vertical-align: top;
                background-color: azure;
                background-image: url("/ttk/images/Insurance/content.png");
            }
            #brokerRightImg{
                border-collapse: collapse;
                height: 550px;
                vertical-align: top;
                background-color: azure;
                background-image: url("/ttk/images/Insurance/content.png");
            }            
            #brokerMidBody{
                width: 90%;
                height: 100%;
                vertical-align: top;               
            }
            #brokerFooter{
                border-top: 1px solid greenyellow;
                border-collapse: collapse;
                width: 100%;
                height: 20px;
                text-align: center;
                color: brown;
                font-size: 15px;
                background-color: cornsilk;
            }
            #brokerDivMidBody{
            padding:0;
            margin:0;
            border:0;
             height: 550px;
             overflow: scroll;
            }    
</style>
<script type="text/javascript">
var bAction = true;
ClientReset = true;
var JS_SecondSubmit=false;
</script>
<script type="text/javascript">
function modifyOnlineTabs(leftLink1,subLink1,tab1){
	if(!TrackChanges()) return false;
		document.forms[0].leftlink.value = leftLink1;	
		document.forms[0].sublink.value = subLink1;	
		document.forms[0].tab.value = tab1;
	document.forms[0].action="/ttkAction.do?mode=doOnlineDefault";
	document.forms[0].submit();
}
function brokerLogout(){
	document.forms[0].action="/LogoutOnlineAccessAction.do?mode=doLogoff";
	document.forms[0].submit();
}
function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
	
}//end of disableCtrlModifer(evt)
</script>

</head>
<!-- Changes Added onunload in body for Password Policy CR KOC 1235 -->
<body id="brokerContainerBody" onunload="javascript:logoutwindow(event);" onLoad="javascript:resizeDocument();TrackFormData('webBoard');handleFocus();window.history.forward(1);" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<table id="brokerContainerTable">
            <tr>
                <td id="brokerHeader"><tiles:insert attribute="header" ignore="true"/></td>
            </tr>
            <tr>
                <td id="brokerDropDownMenus"><tiles:insert attribute="menuLinks" ignore="true"/></td>
            </tr>
            <tr>
                <td>
                    <table id="brokerSubContainerTable">
                        <tr>
                            <td  id="brokerLeftImg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td  id="brokerMidBody">
                            <div id="brokerDivMidBody">
                            <tiles:insert attribute="bodyContent" ignore="true"/>
                            </div>
							</td>
                            <td  id="brokerRightImg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        </tr>
                    </table>

                </td>
            </tr>
            <tr>
                <td id="brokerFooter">&COPY;Vidal&nbsp;2015</td>
            </tr>
        </table>
</body>
</html>