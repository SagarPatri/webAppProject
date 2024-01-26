<%
/** @ (#) RuleData.jsp Apr 25th, 2006
 * Project       : TTK Healthcare Services
 * File          : RuleData.jsp
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : 25th Apr 2006
 * @author 		 : Unni V Mana
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
 %>
 <%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<script language="javascript" src="/ttk/scripts/enrollment/ruledata.js"></script>
<html>
<head>
<title>Vidal Health - Rule Data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="javascript" src="../scripts/utils.js"></script>
<script language="JavaScript" SRC="../scripts/calendar/calendar.js"></SCRIPT>
<link href="../styles/Default.css" media="screen" rel="stylesheet"></link>
<script type="text/javascript">
var strDataChanged=false;
var nCounter=0;
var txtArray = new Array(100);
for (i=0; i <txtArray.length; i++)
txtArray[i]=new Array(1);

  function SetState(obj_checkbox, obj_text)
    {  if(obj_checkbox.checked)
       { 
	   obj_text.disabled = false;
	   obj_text.className = "textBox textBoxSmall";
       }
       else
       { 
	   obj_text.disabled = true;
	   obj_text.className = "textBox textBoxSmallDisabled";
       }
    }

function onSave()
{

/*	if(!getCheckBox())
	{
		alert("Please Check at least one Coverage option");
		document.forms[1].dataChange.value="yes";	
		return false;
	} */
	//when a new policy rule defined
	document.forms[1].dataChange.value="true";
	//end of new policy rule
	document.forms[1].mode.value="save";
	document.forms[1].action = "/RuleDataAction.do";
    document.forms[1].method="post";
    document.forms[1].submit();
}


function getCheckBox()
{
	var NumElements=document.forms[1].elements.length;
	var chkStatus=false;
	for(n=0; n<NumElements;n++)
	{
			if(document.forms[1].elements[n].type=="checkbox")
			{
				var tt = document.forms[1].elements[n].name;
					if(document.forms[1].elements[n].checked==true)
					return true;
			}
	}//end of for
	return chkStatus;
}//end of getCheckBox

function checkDate()
{
	if(document.forms[1].dtFrom.value == document.forms[1].hiddenFromDate.value)
	{
		document.forms[1].dataChange.value="false";
	}
	else
	{
		document.forms[1].dataChange.value="true";
	}
}

function getTextBoxCount(str)
{
		var mCount=0;
		for(k=0; k<txtArray.length;k++)
		{
				try{
				var str2 = txtArray[k][0].split("-");
				if(str2[1]==str) 
				{
					mCount++;
				}
				}catch(er)
				{
				}
		}
		return mCount;
}

function getDefaultValue(obj_textbox,num)
{
	txtArray[nCounter][0]=obj_textbox.value+"-"+obj_textbox.name;
	txtArray[nCounter][1]=num;
	nCounter++;
}
function setDeafultValue()
{
		var NumElements=document.forms[1].elements.length;
		for(n=0; n<NumElements;n++)
		{
			if(document.forms[1].elements[n].type=="checkbox" && !document.forms[1].elements[n].checked) 
			{
					//getting the checkbox name
					var strChkName		=	document.forms[1].elements[n].name;
					var checkArray		= 	strChkName.split("@");
					var strNewCheckBox	=	checkArray[1]+checkArray[2];
					var strTextBoxName	=	strNewCheckBox+"textbox";
					printFromArray(strTextBoxName);
			} //end if
		}//end for
}//end of function
function printFromArray(str)
{
		var m=-1;
		for(p=0; p<txtArray.length;p++)
		{
				try{
				var str2 = txtArray[p][0].split("-");
				if(str2[1]==str) 
				{
					var count = getTextBoxCount(str);
					if(eval(count)==1)	
					{
						document.forms[1].elements[str].value=str2[0];
					}
					else
					{
						m++;
//						document.forms[1].elements[str][m].value=str2[0];
						var index =  txtArray[p][1];
						document.forms[1].elements[str][index].value=str2[0];
					}
				}
				}catch(er)
				{
				}
		}
}

</script>
</head>
<body> 
<!-- S T A R T : Header --> 
<!-- E N D : Header --> 
<!-- S T A R T : Main Container Table --> 
<table width="100%" id="mainContainerTable" class="mainContainerTable" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td width="" class="leftNavigationBand" nowrap valign="top">
	<!-- S T A R T : Left Navigation Menu --> 
    <!-- E N D : Left Navigation Menu --> </td> 
    <td width="100%" align="center" valign="top">
	<!-- S T A R T : Tab Navigation -->
	<!-- E N D : Tab Navigation -->
	<!-- S T A R T : Content/Form Area -->	
	<!-- S T A R T : Page Title -->
	<html:form action="/RuleDataAction.do">
	<html:errors/>
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">List of all Selected Rules  </td>     
    <td align="right" class="webBoard">&nbsp;
	<%@ include file="/ttk/common/toolbar.jsp"%>
	</td>
  </tr>
</table>
	<!-- E N D : Page Title --> 

			<%
			if(request.getAttribute("msg") !=null)
			{
			%>
	        	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;<%= TTKCommon.checkNull((String)request.getAttribute("msg")) %>!</td>
		      </tr>
		    </table>
		<%
		}
		%>
		<!-- S T A R T : Grid -->
		<!-- E N D : Grid -->
<!-- S T A R T : Page Title -->
<!-- E N D : Page Title --> 
<!-- S T A R T : Form Fields -->
	<%=((String) request.getAttribute("ruledatahtml")==null)?"":(String) request.getAttribute("ruledatahtml")%>
<!-- S T A R T : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE="save">
<INPUT TYPE="hidden" NAME="dataChange" VALUE="">
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center"><input type="button" name="Button2" value="Save" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"  onClick="onSave('RuleDataAction.do')">&nbsp;<input type="button" name="Button" value="Reset" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"></td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</html:form>
    <!-- E N D : Form Fields -->
	<!-- E N D : Content/Form Area -->	</td> 
  </tr> 
</table> 
</div>
<!-- E N D : Main Container Table --> 
<!-- S T A R T : Footer -->
<!-- E N D : Footer -->
</body>
</html>
