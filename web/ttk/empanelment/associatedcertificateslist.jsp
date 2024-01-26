<%
/** @ (#) associatedcertificateslist.jsp
 * Project     : TTK Healthcare Services
 * File        : associatedcertificateslist.jsp
 * Author      : Swaroop Kaushik D.S
 * Company     : Span Systems Corporation
 * Date Created: May 05, 2010
 *
 * @author 		 :Swaroop Kaushik D.S
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
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/associatedcertificateslist.js"></script>
<%  
 
    boolean viewmode=true;
  
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
    String Caption=(String)session.getAttribute("caption");
%>
<html:form action="/AssociateCertificatesList.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Associate Certificate - <bean:write name="frmAssociateCertificates" property="caption"/></td>   
    		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title --> 
	<div style="width: 99%; float: right;">
	<div class="scrollableGrid" style="height:290px;">
	<!-- S T A R T : Success Box -->
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
	<!-- S T A R T : Form Fields -->
	<br>
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataAssociateCertificates" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
	</div>
	<!-- S T A R T : Buttons -->
	<br>
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right" nowrap class="formLabel">
			<%
	    		if(TTKCommon.isDataFound(request,"tableDataAssociateCertificates") && TTKCommon.isAuthorized(request,"Delete"))
				{
		    %>
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
			<%
        		}// end of if(TTKCommon.isDataFound(request,"tableDataLinkDetails") && TTKCommon.isAuthorized(request,"Delete"))
        	%>		
			</td>
		</tr>
	</table>
	<!-- END: Buttons -->
	<fieldset>
		<legend>Certificate Details</legend>
		<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			
			<tr  style="border:1px solid;">
				
          		<td width="19%"    align="left" nowrap>&nbsp;&nbsp;Description:<span class="mandatorySymbol">*</span></td>
          		<td width="18%"    align="left" nowrap>
          			<html:text property="description"  styleClass="textBox textBoxMedium" style="width75px;"  maxlength="250" readonly="<%=viewmode%>" />
          		</td>
          		 
          	          <td width="13%"  align="left" nowrap>&nbsp;&nbsp;Financial Year:<span class="mandatorySymbol">*</span></td>
          		      <td width="5%"   align="left" nowrap>
          			   <html:text property="financialYear"  styleClass="textBox textBoxTiny"  maxlength="4" readonly="<%=viewmode%>" onblur="javascript:finendyear();"  />
          			   - <html:text property="financialYearTo"  styleClass="textBox textBoxTiny textBoxDisabled"  maxlength="4" readonly="true" />
          		      </td>
          		     
          		
          		   <td  align="right" nowrap="nowrap" >
			    <span id="docPath">Path:</span><span class="mandatorySymbol">*</span>&nbsp;&nbsp;&nbsp;</td>
			     <td ><html:text property="certPath"  styleClass="textBox textBoxMedium"  maxlength="250" readonly="<%=viewmode%>" disabled="true"/>
			      <a name="Browseicon" id="BrowseIconID" href="#"  onClick="javascript:onBrowse();" onMouseOver="window.status='Browse';return true;" onMouseOut="window.status='';return true;"><img  src="/ttk/images/filebrowse.gif" alt="Browse" name="browseImg" align="absmiddle" width="20" height="15" border = "0"></a>
          		   </td>
          		   
          	      </tr>
          	      
          	       <tr>
          	       	<td  colspan="4" align="right">
          	       		<logic:match name="viewmode" value="false">
									<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;&nbsp;&nbsp;
									<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;
								</logic:match>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
          	       	</td>
          	       </tr>
			  </table>
		</fieldset>	
		
		</div>
		<!-- END : Form Fields -->
	<html:hidden property="certSeqId"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	
</html:form>

<!-- E N D : Content/Form Area -->