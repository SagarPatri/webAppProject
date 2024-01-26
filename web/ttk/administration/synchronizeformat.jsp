<%@page import="com.ttk.dto.administration.RuleSynchronizationVO"%>
<%@page import="com.ttk.action.table.TableData"%>
<%@page import="com.ttk.dto.BaseVO"%>
<%@page import="com.ttk.dto.common.CacheObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%
/**
 * @ (#) showlistintx.jsp Aug 13th 2006
 * Project      : TTK HealthCare Services
 * File         : synchronizeformat.jsp
 * Author       : Rishi Sharma
 * Company      : RCS technologies
 * Date Created : 21st May 2015
 *
 * @author       :Rishi Sharma
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.WebBoardHelper,com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>


<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/administration/productsynclist.js"></SCRIPT>


<%!    String chkoptt=""; %>

<%
chkoptt=request.getParameter("chkoptt");
   
		String strTypeId = TTKCommon.checkNull((String)request.getParameter("typeId"));
		String strControlName = TTKCommon.checkNull((String)request.getParameter("controlName"));
		String strControlValue = TTKCommon.checkNull((String)request.getParameter("controlVal"));
		String strShowRadio=TTKCommon.checkNull((String)request.getParameter("showRadio"));
		String strCondParam=TTKCommon.checkNull((String)request.getParameter("condParam"));
		
		String strIdTile = "";
		String strDescTitle ="";
		int iRowCount = 0;

 Collection<Object> resultList = new ArrayList<Object>();
        
 CacheObject voBean = new CacheObject();
 voBean.setCacheId("GEN");
 //voBean.setCacheDesc("General");
 voBean.setCacheDesc("General [Configuration]");
CacheObject voBean1 = new CacheObject();
voBean1.setCacheId("GEN BNF");
voBean1.setCacheDesc("General [Card benifits]"); 
CacheObject voBean2 = new CacheObject();
voBean2.setCacheId("PROD");
voBean2.setCacheDesc("Product Rules");
CacheObject voBean3 = new CacheObject();
voBean3.setCacheId("CARD");
voBean3.setCacheDesc("Card Rules");

CacheObject voBean4 = new CacheObject();
voBean4.setCacheId("VAT");
voBean4.setCacheDesc("VAT");


resultList.add(voBean);
resultList.add(voBean1);
resultList.add(voBean2);
resultList.add(voBean3);
resultList.add(voBean4);

	if (strTypeId.equals("Pages to be Synchronized"))
		{
	pageContext.setAttribute("dataList",resultList);
	strIdTile = "";
	strDescTitle = "Pages to be Synchronized";
		}
		
		//ended
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ProductListSyncAction.do" onsubmit="return save();" method="post">
<title>List of - <%=strDescTitle%></title>
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="gridWithCheckBox" border="0" cellspacing="1" cellpadding="0">
	<tr>
	<td width="90%" ID="listsubheader" CLASS="gridHeader"> <%=strDescTitle%> &nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap">
		<%if(!strShowRadio.equals("Y"))
		{
		%>
			Check All<input name="checkall" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall')">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
        <%=strIdTile%></td>
		
	</tr>
	<logic:notEmpty name="dataList">
		<logic:iterate id="dataList" name="dataList">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			 	<td width="90%" class="textLabelBold"><bean:write name="dataList" property="cacheDesc" /></td>
				<td width="10%" align="center" nowrap="nowrap">
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
				
			</tr>
			<%iRowCount++;%>
		</logic:iterate>
	</logic:notEmpty>
	  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
			  <%if(iRowCount!=0) { 
			 %>
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
<input type="hidden" name="chkoptt" id="chkoptt" value="<%=chkoptt%>">
<input type="hidden" name="mode" id="mode" value="">
<input type="hidden" name="strControlName"  value="<%=strControlName%>">

<input type="hidden" name="showRadio" value="<%=strShowRadio%>">
<input type="hidden" name="strTypeId" value="<%=strTypeId %>" >
<input type="hidden" name="txtBoxVals" value="<%=request.getParameter("controlVal") %>" >
<script>
	checkPreSelect(<%=iRowCount%>);
</script>
<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</html:form>
