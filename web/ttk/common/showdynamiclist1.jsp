<%
/**
 * @ (#) showdynamiclist.jsp May 16th 2017
 * Project      : Project X
 * File         : showdynamiclist.jsp
 * Author       : Nagababu K
 * Company      : RCS Technologies
 * Date Created : May 16th 2017
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script>

function save()
{
	objform = document.forms[0];
	var checkid="checkid";
	var strCheckedVal="";
	var controlname=  document.forms[0].strControlName.value;
	var showRadio=document.forms[0].showRadio.value;
	if(showRadio=='Y')  //If showing radio box
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
	if (window.opener && !window.opener.closed)
			window.opener.document.getElementById(controlname).value = strCheckedVal;	
	self.close();
}//end of save()

function checkPreSelect(iCount)
{
	var iTotalChecked = 0;
	var showRadio=document.forms[0].showRadio.value;
	var controlId=document.forms[0].strControlName.value;
	if(showRadio=='Y')		//for showing radio box
	{
		var selVal= window.opener.document.getElementById(controlId).value;
		for(var i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("radid"+i);
			if(objCheck.value == selVal)
			{
				objCheck.checked= true;
				break;
			}//end of if(objCheck.value == selVal)
		}//end of for(i=0;i<objform.length;i++)
	}//end of if(showRadio=='Y')
	else	//for showing check box
	{
		for(var i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("checkid"+i);
			if(isSelected(objCheck.value)==1)
			{
				objCheck.checked = true;
				iTotalChecked++;
			}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
		}//end of for(i=0;i<iCount;i++)
		if(iTotalChecked==iCount)
			document.getElementById("checkall").checked = true;
	}//end of else
}//end of checkPreSelect(iCount)

function isSelected(strId)
{
	var controlname = document.forms[0].strControlName.value;
	var strPreSelected = window.opener.document.getElementById(controlname).value;
	var temp = new Array();
	temp = strPreSelected.split('|');
	for(var j=0;j<temp.length;j++)
		if(temp[j]==strId)
		  return 1;
	return 0;
}//end of isSelected(strId)

function oncheck(strId){
	
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

<%
		//String strTypeId = TTKCommon.checkNull((String)request.getParameter("typeId"));
		String strControlName = TTKCommon.checkNull((String)request.getParameter("controlName"));
		String strControlValue = TTKCommon.checkNull((String)request.getParameter("controlVal"));
		String strShowRadio=TTKCommon.checkNull((String)request.getParameter("showRadio"));
		String strDescTitle=TTKCommon.checkNull((String)request.getParameter("descTitle"));
		
			int iRowCount = 0;
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CorporateMemberAction.do" >
<title>List of - <%=strDescTitle%></title>
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="gridWithCheckBox" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td width="5%" ID="listsubheader" CLASS="gridHeader">
		<%if(!strShowRadio.equals("Y"))
		{
		%>
			<input name="checkall" title="Check All" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall')">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
        </td>
		<td width="95%" ID="listsubheader" CLASS="gridHeader"> <%=strDescTitle%> &nbsp;</td>
	</tr>
	<logic:notEmpty name="dataList" scope="request">
		<logic:iterate id="dataList" name="dataList" scope="request">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
				<td width="5%" align="center">
				<%
				if(strShowRadio.equals("Y")){
				%>
				<input type="radio" id="radid<%=iRowCount%>" name="optRadio" value="<bean:write name="dataList" property="cacheId" />">
				<%
				}else{
				%>
				<input type="checkbox" id="checkid<%=iRowCount%>" onclick="javascript:oncheck('');" name="optSelect" value="<bean:write name="dataList" property="cacheId" />">
				<%}%>				
				</td>
				<td width="95%" class="textLabelBold"><bean:write name="dataList" property="cacheDesc" /></td>
			</tr>
			<%iRowCount++;%>
		</logic:iterate>
	</logic:notEmpty>
	  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
			  <%if(iRowCount!=0) { %>
				<input type="button" name="Button" value="Save" class="buttons" onClick="javascript:save();">&nbsp;
				<%
			  }%>
			<input type="button" name="Button" value="Close" class="buttons" onClick="javascript:self.close();">
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>

</div>

<input type="hidden" name="strControlName" value="<%=strControlName%>">
<input type="hidden" name="showRadio" value="<%=strShowRadio%>">
<script>
	checkPreSelect(<%=iRowCount%>);
</script>

</html:form>
