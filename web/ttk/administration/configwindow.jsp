<!DOCTYPE html>
<html>
<%/**
 * @ (#) configwindow.jsp June 28th 2017
 * Project       :Vidal Health TPA Services
 * File          : clinicianTypeCopayDetails.jsp
 * Author        : Nagababu K
 * Company       : Span Systems Corporation
 * Date Created  : June 28th 2017
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<head>
<title><%=request.getParameter("windowTitle") %></title>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script>

function save()
{
	objform = document.forms[0];
	var checkid="checkid";
	var strCheckedVal="";
	var vDisplayID=  document.forms[0].displayID.value;
	var vBoxType=document.forms[0].boxType.value;
	if(vBoxType=='RD')  //If showing radio box
	{
		for(var i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("^(radid){1}[0-9]{1,}$"); 
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true)
				{
					strCheckedVal=objform.elements[i].value;
					break;
				}//end of if(objform.elements[i].checked == true)
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		
		if(strCheckedVal=='')
		{
			alert('Select atleast one record');
			return false;
		}//end of if(strCheckedVal=='')
	}//end of if(showRadio=='Y')
	else
	{
		for(var i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("("+checkid+"){1}[0-9]*");
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true)
					strCheckedVal=strCheckedVal+objform.elements[i].value+"|";
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		if(strCheckedVal=="|") // if no check box is selected
			strCheckedVal="";
		else 				 // remove the last '|'
			strCheckedVal=strCheckedVal.substring(0,strCheckedVal.lastIndexOf('|'));
	}//end of else

	if (window.opener && !window.opener.closed){
			window.opener.document.getElementById(vDisplayID).value = strCheckedVal;
			
			var clrIDs=document.getElementById("clearFieldIDs").value;
			
     if(clrIDs!=''){
    	 var clrIDsArr=clrIDs.split("|");
    	 for(var n=0;n<clrIDsArr.length;n++){
    		 if(clrIDsArr[n].length>0){
     		 window.opener.document.getElementById(clrIDsArr[n]).value = '';
    		 }
        	 }
    		}

	}
	self.close();
}//end of save()

function oncheck(strId)
{
	var i	=	0;
	var bFlag=true;
	if(strId =='checkall')
		bFlag = document.getElementById("checkall").checked;
	var checkid="checkid";
	objform = document.forms[0];
	for(var i=0;i<objform.length;i++)
	{
		var regexp=new RegExp("("+checkid+"){1}[0-9]*");
		if(regexp.test(objform.elements[i].id))
		{
			if(strId =='checkall')
				objform.elements[i].checked = bFlag;
			else if(objform.elements[i].checked != true)
			{
				bFlag=false;
				break;
			}//end of else if(objform.elements[i].checked != true)
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)
 	document.getElementById("checkall").checked = bFlag ;
}//end of oncheck(strId)
</script>
<SCRIPT type="JavaScript/text" SRC="/ttk/scripts/validation.js"></SCRIPT>
<body>

<!-- S T A R T : Content/Form Area -->
<form name="frmMember" method="post" action="/CorporateMemberAction.do">

	<div class="contentArea" id="contentArea">
	
	<ttk:ConfigWindowDetails/>
</div>
<input type="hidden" name="displayID" value="<%=TTKCommon.checkNull(request.getParameter("displayID")) %>">
<input type="hidden" name="boxType" value="<%=TTKCommon.checkNull(request.getParameter("boxType")) %>">
<input type="hidden" id="clearFieldIDs" name="clearFieldIDs" value="<%=TTKCommon.checkNull(request.getParameter("clearFieldIDs")) %>">
<!-- E N D : Buttons -->
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</form>
</body>
</html>