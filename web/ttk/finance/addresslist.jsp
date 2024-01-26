<%
/**
 * @ (#) AddressSearchAction.java
 * Project       : TTK HealthCare Services
 * File          : addresslist.jsp
 * Author        : SendhilKumar V
 * Company       : Span Systems Corporation
 * Date Created  : May 16,2008
 *
 * @author       : SendhilKumar V
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/addresslist.js"></script>
<script language="JavaScript">
	var TC_Disabled = true;
</script>
<%
	String strWebBoardId = String.valueOf(TTKCommon.getWebBoardId(request));
	pageContext.setAttribute("WebBoardId", strWebBoardId);
	//start cr koc 1103 and 1105
	pageContext.setAttribute("paymentMethod", Cache.getCacheObject("paymentMethod1"));
	//end cr koc 1103 and 1105
	
%>
	
<html:form action="/AddressSearchAction.do">
	<logic:notEmpty name="GenerateXL" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 90;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("<bean:write name="GenerateXL" scope="request"/>",'',features);
		</script>
	</logic:notEmpty>
	
	<logic:notEmpty name="openreport">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 85;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("<bean:write name="openreport" scope="request"/>",'',features);
		</script>
	</logic:notEmpty>
	
	<logic:notEmpty name="CignaGenerateXL" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 90;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("<bean:write name="CignaGenerateXL" scope="request"/>",'',features);
		</script>
	</logic:notEmpty>

		
	<logic:match name="WebBoardId" value="null">
		<html:errors/>
	</logic:match>
	
	<logic:notMatch name="WebBoardId" value="null">
		<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="57%">List of Claims</td>
    			<td align="right" class="webBoard">
    				&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %>
    			</td>
 			</tr>
		</table>
		<!-- E N D : Page Title -->
		
		<div class="contentArea" id="contentArea">
			<!-- S T A R T : Search Box -->
			<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
      				<td nowrap>Claim Settlement No.:<br>
            			<html:text property="sClaimSettleNumber" styleClass="textBox textBoxMedium" maxlength="60"/>
         			</td>
		      		<td nowrap>Start Date:<br>
		      			<html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
		      			<a name="CalendarObjectstartDate" id="CalendarObjectstartDate" href="#" onClick="javascript:show_calendar('CalendarObjectstartDate','frmAddressLabel.startDate',document.frmAddressLabel.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		      				<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="startDate" width="24" height="17" border="0" align="absmiddle" ></a>
		      		</td>		
		      		<td nowrap>End Date:<br>
		      			<html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
		      			<a name="CalendarObjectendDate" id="CalendarObjectendDate" href="#" onClick="javascript:show_calendar('CalendarObjectendDate','frmAddressLabel.endDate',document.frmAddressLabel.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		      				<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="endDate" width="24" height="17" border="0" align="absmiddle" ></a>
		      		</td>
		      		<!-- start changes for cr koc 1103 and 1105 -->
                  <td nowrap> Payment Method:<br>
        	        <html:select property="paymethod" styleClass="selectBox selectBoxMedium">
				    <html:optionsCollection name="paymentMethod" label="cacheDesc" value="cacheId"/>
			        </html:select>
		</td>
		<!-- end changes for cr koc 1103 and 1105-->
			  		<td valign="bottom" nowrap width="100%">
			  			<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
			  				<img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
			  			</a>
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
	    			<td width="73%" nowrap>&nbsp;</td>
	    			<td align="right" nowrap>
	    			<%
    				if(TTKCommon.isDataFound(request,"tableData"))
    				{
	    			%>
	    			   <logic:match name="frmAddressLabel" property="paymethod" value="PCA">
	    				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddressLabel();">Generate <u>A</u>ddress Labels</button>&nbsp;
	    				</logic:match>
	    			<%
	    			}//end of if(TTKCommon.isDataFound(request,"tableData"))
	    			%>	
	    			</td>
	  			</tr>
			</table>
			<%
			if(TTKCommon.isDataFound(request,"tableData"))
			{
			%>
			<fieldset>
				<legend>Generate Covering Letter</legend>
				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
					<tr>
			    		<td align="center">
			    		<!-- start changes for cr koc 1105 -->
			    		<logic:match name="frmAddressLabel" property="paymethod" value="PCA">
    		     			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onComputaionSheet();">With <u>C</u>omputation Sheet</button>&nbsp;
   		    				<button type="button" name="Button" accesskey="w" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCoveringLetter();"><u>W</u>ithout Computation Sheet</button>&nbsp;
   		    				</logic:match>
   		    			
   		    			<logic:match name="frmAddressLabel" property="paymethod" value="EFT">
		    			<button type="button" name="Button" accesskey="w" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onEftComputaionSheet();"><u>W</u>ith Computation Sheet for EFT</button>&nbsp;
		    			</logic:match>
		    			<!-- end changes for cr koc 1105 -->
		    			</td>
			  		</tr>
				</table>
			</fieldset>
			<br>
			<%
	 		}//end of if(TTKCommon.isDataFound(request,"tableData"))
			%>
			<br>	
		</div>
	</logic:notMatch>
    <!-- E N D : Buttons and Page Counter -->
    
    <input type="hidden" name="rownum" value="">
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="sortId" value="">
	<input type="hidden" name="pageId" value="">
	<input type="hidden" name="child" value="">
	
</html:form>