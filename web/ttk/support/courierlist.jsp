<%
/**
 * @ (#) courierlist.jsp 26th May 2006
 * Project      : TTK HealthCare Services
 * File         : courierlist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 26th May 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/support/courierlist.js"></script>

<%
	pageContext.setAttribute("sCourierName",Cache.getCacheObject("courierName"));
	pageContext.setAttribute("sOfficeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sCourierType",Cache.getCacheObject("courierType"));
%>
<html:form action="/CourierSearchAction">
	<!-- S T A R T : Page Title -->

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Couriers</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td nowrap>Courier Type:<br>
        		<html:select property="sCourierType" styleClass="selectBox selectBoxMedium">
	        		<html:options collection="sCourierType" property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
        	</td>
        	<td nowrap>Courier No.:<br>
        		<html:text property="sCourierNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<!-- Docket / POD -->
        	<td nowrap> Consignment No.:<br>
        		<html:text property="sDocketNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Courier Name :<br>
            	<span class="textLabel">
            		<html:select property="sCourierName" styleClass="selectBox selectBoxMedium" >
	  					<html:option value="">Any</html:option>
	  					<html:optionsCollection name="sCourierName" label="cacheDesc" value="cacheId" />
           			</html:select>
            	</span>
            </td>
		</tr>
		<tr>
	    	<td nowrap>Vidal Health Branch:<br>
	        	<html:select property="sOfficeInfo" styleClass="selectBox selectBoxMedium">
	    			<html:option value="">Any</html:option>
	        		<html:options collection="sOfficeInfo" property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
	    	</td>
	        <td nowrap>Start Date:<br>
		   		<html:text property="sStartDare" styleClass="textBox textDate" maxlength="10"/>
		  		<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmCourier.sStartDare',document.frmCourier.sStartDare.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
		  	</td>
		  	<td nowrap>End Date:<br>
		   		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
		  		<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmCourier.sEndDate',document.frmCourier.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
		  	</td>
	        <td nowrap width="100%" valign="bottom">
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
	        </td>
		</tr>
   </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
	    <td width="73%" align="right">
	     <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReportLink();"><u>R</u>eport</button>&nbsp;
        <%
        
        if(TTKCommon.isAuthorized(request,"Add"))
			{
		%>      
				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd(document.forms[1].sCourierType.value);"><u>A</u>dd</button>&nbsp;
   		<%
	   		}//end of if(TTKCommon.isAuthorized(request,"Add"))
	   		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	   		{
		%>
        		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
	 	<%
    		}//end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
    	%>
    	   
    	</td>
    	
    	
		<ttk:PageLinks name="tableData"/>
     	</tr>
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="paramType" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->