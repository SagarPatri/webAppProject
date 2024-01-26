<%
/** @ (#) associatepolicylist.jsp Oct 25, 2007
 * Project     : TTK Healthcare Services
 * File        : associatepolicylist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: OCt 25, 2007
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/associatepolicylist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	pageContext.setAttribute("ttkBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("associatedList",Cache.getCacheObject("debitAssoc"));
%>
<html:form action="/AssociatePolicyAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="50%"><bean:write name="frmInvoice" property="caption"/></td>
            <td width="50%" align="right" class="webBoard">&nbsp;</td>
        </tr>
    </table>
    <!-- E N D : Page Title -->
    <div class="contentArea" id="contentArea">
    <!-- S T A R T : Form Fields -->
    <table align="center" class="searchContainer " border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td width="20%" nowrap>Insurance Company :&nbsp; </td>
            <td width="30%" nowrap class="textLabelBold">
              <bean:write name="frmInvoice" property="insComp"/>
              <a href="#" onClick="javascript:onInsuranceComp();"><img src="/ttk/images/EditIcon.gif" alt="Select Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;
              <a href="#" onClick="javascript:onClear('Insurance')"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;
          	</td>
          	<td width="20%" nowrap >Company Code: &nbsp;</td>
            <td width="30%" nowrap class="textLabelBold">
	        	<bean:write name="frmInvoice" property="insCompCode"/>
	            <html:hidden property="insSeqID"/>
            </td>
    	</tr>
    </table>
    <table align="center" class="searchContainer " border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td width="15%" nowrap>Enrollment Type:<br></td>
    		<td  nowrap width="18%">
    			<label>
    				<html:checkbox property="sIndividualType" value="Y" />
    				Individual
    			</label>	
    		</td>
    		<td  nowrap width="21%">
    			<label>
    				<html:checkbox property="sIndasGroupType" value="Y" />
    				Individual as Group
    			</label>	
    		</td>	
    		<td  nowrap width="21%">
    			<label>
    				<html:checkbox property="sCorporateType" value="Y" />
    				Corporate
    			</label>	
    		</td>	
    		<td  nowrap width="25%">
    			<label>
    				<html:checkbox property="sNonCorpType" value="Y" />
    				Non Corporate
    			</label>	
    		</td>		
    	</tr>
    </table>
    <table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td nowrap>Vidal Health Branch:<br>
				<html:select property="sbranch"  styleClass="selectBox selectBoxMedium">
				<html:option value="">Any</html:option>
	  			<html:options collection="ttkBranch"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
        	</td>
        	<td nowrap>List:<br>
				<html:select property="associatedList"  styleClass="selectBox selectBoxMedium">
		  			<html:options collection="associatedList"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
        	</td>
        	<td nowrap>From Date:<br>
        		<html:text name="frmInvoiceGeneral" property="fromDate" styleClass="textBox textDate" disabled="true" />
        	</td>
        	<td nowrap>To Date:<br>
        		<html:text name="frmInvoiceGeneral" property="toDate" styleClass="textBox textDate" disabled="true" />
        	</td>
        	<td nowrap>Include Previous Schemes:<br>
        		<html:checkbox name="frmInvoiceGeneral" property="includeOldYN" value="Y" disabled="true"/>
        	</td>
        	<td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        	<td colspan="2" width="100%">&nbsp;</td>
    	</tr>
    </table>
    <div id="mainContentAreaI" >
    <!-- S T A R T : Grid -->
    	<ttk:HtmlGrid name="tableDataAssociatePolicy"/>
    <!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" nowrap align="right" colspan="2" >
			<logic:notMatch property="statusTypeID" name="frmInvoiceGeneral" value="DFL">
			<%
		    if(TTKCommon.isDataFound(request,"tableDataAssociatePolicy"))
		     {
		     %>
				<logic:match  property="associatedList" name="frmInvoice" value="DBU">
					<button type="button" name="Button1" accesskey="o" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExcludeAll('Associate')">Ass<u>o</u>ciate All</button>&nbsp;
		      		<button type="button" name="Button1" accesskey="A" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExclude('Associate')"><u>A</u>ssociate</button>&nbsp;
		    	</logic:match>
		    	
		    	<logic:match property="associatedList" name="frmInvoice" value="DBA">
		    		<button type="button" name="Button2" accesskey="x" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExcludeAll('Exclude')">E<u>x</u>clude All</button>&nbsp;
		     		<button type="button" name="Button2" accesskey="E" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExclude('Exclude')"><u>E</u>xclude</button>&nbsp;
		    	</logic:match>
		    <%
		   		 }
		    %>
		    </logic:notMatch>
		    <button type="button" name="Button3" accesskey="C" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>&nbsp;		      
			</td>
		</tr>
		<tr>
			<ttk:PageLinks name="tableDataAssociatePolicy"/>
		</tr>
	</table>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="identifier" VALUE="">
	<input type="hidden" name="child" value="">	
	<INPUT TYPE="hidden" NAME="sIndividualType" VALUE="N">
	<INPUT TYPE="hidden" NAME="sIndasGroupType" VALUE="N">
	<INPUT TYPE="hidden" NAME="sCorporateType" VALUE="N">
	<INPUT TYPE="hidden" NAME="sNonCorpType" VALUE="N">
	<INPUT TYPE="hidden" NAME="sIncludeOld" VALUE="N">
</html:form>