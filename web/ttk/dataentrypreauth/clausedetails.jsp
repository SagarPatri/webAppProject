<%
/** @ (#) clausedetails.jsp 9th July 2007
 * Project     : TTK Healthcare Services
 * File        : clausedetails.jsp
 * Author      : Srikanth H.M
 * Company     : Span Systems Corporation
 * Date Created: 9th July 2007
 *
 * @author Srikanth H. M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/clausedetails.js"></script>

<%
	boolean viewmode=true;
	String disable="disabled";
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		disable="";
	}
%>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/SelectRejectoinClause.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td >Clause Details <bean:write name="frmRejectionClauses" property="caption"/> </td>
	  	</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Success Box -->
	<div class="contentArea" id="contentArea">
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
	<!-- E N D : Success Box -->
	<html:errors/>
	<!-- S T A R T : Form Fields -->

			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<%
					if(TTKCommon.getActiveLink(request).equals("Claims"))
					 {
				%>
				<logic:notEmpty name="frmRejectionClauses" property="listclauses" >
				       <tr>
					        <td width="10%" class="formLabel">Header</td>
					        <td class="formLabelBold"></td>
					        <td class="formLabel" align="right" style="padding-right:10px; "></td>
				       </tr>
				       <tr>
				         <td colspan="3" valign="top" class="formLabel"><html:textarea property="rejHeaderInfo" styleClass="textBox textAreaLargeMedium" style="width:99%; " disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea></td>
					  </tr>

		       </logic:notEmpty>

		       <%
		       		}
		       %>
				<logic:notEmpty name="frmRejectionClauses" property="listclauses" >
				<logic:iterate id="clause" name="frmRejectionClauses" property="listclauses" >
				      <tr>
				        <td width="10%" class="formLabel">Clause No.:</td>
				        <td class="formLabelBold"><bean:write name="clause" property="clauseNbr"/></td>
				        <td class="formLabel" align="right" style="padding-right:10px; ">
				        <logic:match name="clause" property="selectedYN" value="Y">
					        <input type="checkbox" name="applicable" value="<bean:write name="clause" property="clauseSeqID"/>" checked>
				        </logic:match>
				        <logic:notMatch name="clause" property="selectedYN" value="Y">
					        <input type="checkbox" name="applicable" value="<bean:write name="clause" property="clauseSeqID"/>">
				        </logic:notMatch>
				        Applicable</td>
				      </tr>
				      <tr>
				        <td colspan="3" valign="top" class="formLabel"><textarea name="Description1" class="textBox textAreaLargeMedium" disabled="true" style="width:99%; "><bean:write name="clause" property="clauseDesc"/></textarea></td>
				      </tr>
			    </logic:iterate>
			    </logic:notEmpty>
			  	<%
					if(TTKCommon.getActiveLink(request).equals("Claims"))
					 {
				%>
				<logic:notEmpty name="frmRejectionClauses" property="listclauses" >

			   <tr>
			        <td width="10%" class="formLabel">Footer</td>
			        <td class="formLabelBold"></td>
			        <td class="formLabel" align="right" style="padding-right:10px; "></td>
		       </tr>
		       <tr>
				    <td colspan="3" valign="top" class="formLabel"><html:textarea property="rejFooterInfo" styleClass="textBox textAreaLargeMedium" style="width:99%; " disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea></td>
			   </tr>
			   <tr>
					<td width="15%" nowrap class="formLabel">Letter to be generated for : <span class="mandatorySymbol">*</span></td>
					<td>
						<html:select property="letterTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<logic:notEmpty name="frmRejectionClauses" property="nhcpLetterType" >
									<html:optionsCollection property="nhcpLetterType" label="cacheDesc" value="cacheId" />
						    </logic:notEmpty>
				 		</html:select>
					</td>
				</tr>
		       </logic:notEmpty>
		    <%
		    	}
		    %>
		    </table>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
    	<td width="100%" align="center">
	    <%
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				if(TTKCommon.getActiveLink(request).equals("Claims"))
				{
				%>
				<logic:notEmpty name="frmRejectionClauses" property="listclauses" >
				<button type="button" name="Button3" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Letter</button>&nbsp;
				</logic:notEmpty>
				<%
				}
		        %>
		<logic:notEmpty name="frmRejectionClauses" property="listclauses" >
	    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
				
		</logic:notEmpty>
	    <%
	   		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	 	%>
		 		<button type="button" name="close" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
    	</td>
	  </tr>
	</table>
</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="clauseIds" VALUE="">
	<html:hidden property="seqId"/>
	</html:form>
<!-- E N D : Content/Form Area -->