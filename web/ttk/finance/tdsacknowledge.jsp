<%
/**
 * @ (#)  tdsacknowledge.jsp Aug 11, 2009
 * Project      : TTKPROJECT
 * File         : tdsacknowledge.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Aug 11, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
pageContext.setAttribute("tdsInsuranceInfo",Cache.getCacheObject("tdsInsuranceInfo"));
pageContext.setAttribute("tdsAckInfo",Cache.getCacheObject("tdsAckInfo"));
%>
<script language="javascript">
function finendyear(financeYear)
{
	var i = 1;
	i = i+ parseInt(financeYear);
 	if(i >1)
	{
		document.forms[1].finYearTo.value = i;
  	}//end of if(i >1)
}
</script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/tdsacknowledge.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/TDSAcknowledgeAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>List of Acknowledgement Completed </td>
	  	</tr>
	</table>
	<div class="contentArea" id="contentArea">
		<html:errors/>
		<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      		<tr>
      			<td nowrap>Insurance Company:<br>
        			<html:select property="insuranceComp" styleId="select10" styleClass="selectBox selectBoxLargest">
              			<html:option value="">Any</html:option>
              			<html:optionsCollection name="tdsInsuranceInfo" value="cacheId" label="cacheDesc"/>              
              		</html:select>
              	</td>	 
        		<td nowrap>Financial Year:<br>
            		<html:text name="frmTDSAcknowledge" property="financeYear"  styleClass="textBox textBoxSmall" maxlength="4" onblur="javascript:finendyear(document.forms[1].financeYear.value);"/>
           			- <html:text property="finYearTo" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled" />
            	</td>
				<td nowrap>Quarter:<br>
             		<html:select property="tdsQuater" styleId="select10" styleClass="selectBox selectBoxMedium">
              			<html:option value="">Any</html:option>
              			<html:optionsCollection name="tdsAckInfo" value="cacheId" label="cacheDesc"/>              
              		</html:select>
              	</td>		  
        		<td valign="bottom" nowrap align="left" width="100%">
        			<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        		</td>        		
      		</tr>
    	</table>
		<!-- E N D : Page Title --> 
	
		<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
		<!-- E N D : Grid -->
    
    	<!-- S T A R T : Buttons and Page Counter -->
		<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        	<td width="27%">&nbsp;</td>
	        	<td width="73%" align="right">
	        	<%
			    	if(TTKCommon.isAuthorized(request,"Acknowledgement Details"))
					{
				%>
	        		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddAcknowledge();"><u>A</u>cknowledgement Information</button>&nbsp;</td>
	        	<%
	        		}//end of if(TTKCommon.isAuthorized(request,"Acknowledgement Details"))
	        	%>
	      	</tr>
	      	<ttk:PageLinks name="tableData"/>
    	</table>
	</div>
    <!-- E N D : Buttons and Page Counter -->

    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>