<%
/**
 * @ (#)  configurationtds.jsp July 28, 2009
 * Project      : TTKPROJECT
 * File         : configurationtds.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 28, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        : 
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/administration/configurationtds.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TDSConfigurationAction.do" > 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td>TDS Configuration Details </td>     
    	</tr>
	</table>
	<!-- E N D : Page Title --> 

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
    		<tr>
    			<td class="fieldGroupHeader">TDS Categories: </td>
  			</tr>  
  			<tr>
	 			<td colspan="2">
					<ttk:TreeComponent name="treeData"/> 
				</td>
			</tr>
			<tr>
		    	<td colspan="2" height="25" align="right">&nbsp;</td>
	    	</tr>
	  		<tr>
		    	<td colspan="2" class="buttonsContainerGrid" align="right">&nbsp;</td>
	    	</tr>
	    	<%
			if(TTKCommon.isAuthorized(request,"Generate Report"))
    		{
			%>
	    	<tr>
	    		<td width="50%" align="right">
	     			<button type="button" name="Button2" accesskey="H" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="hospitalReport()"><u>H</u>ospital Report</button>&nbsp;			   		
				</td>
	  		
	  		<%
    		}
			%>
			<td>	
			 <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
			 </td>
			 </tr>
		</table>	
		<html:hidden property="mode" />
		<input type="hidden" name="child" value="">
    	<input type="hidden" name="selectedroot" value="">
    	<input type="hidden" name="selectednode" value="">
    	<input type="hidden" name="pageId" value="">
		<!-- E N D : Main Container Table -->
	</div>
</html:form>
