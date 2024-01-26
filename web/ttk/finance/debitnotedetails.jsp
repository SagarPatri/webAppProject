<%
/** @ (#) debitnotedetails.jsp Sep 12, 2007
 * Project    	 : TTK Healthcare Services
 * File       	 : debitnotedetails.jsp
 * Author     	 : Chandrasekaran J
 * Company    	 : Span Systems Corporation
 * Date Created	 : Sep 12, 2007
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/debitdetails.js"></script>
<%
	boolean viewmode=true;
	boolean vieDate=true;
	boolean viewAmt=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		vieDate=false;
	}
	pageContext.setAttribute("debitType",Cache.getCacheObject("debitType"));
	pageContext.setAttribute("debitTypeDraft",Cache.getCacheObject("debitTypeDraft"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SaveDebitNoteAction.do" >
	<logic:notEmpty name="frmDebitNoteDetails" property="debitNoteSeqID">
		<% vieDate=true; %>
	</logic:notEmpty>
	
		<logic:match name="frmDebitNoteDetails" property="debitType" value="DFL">
		<% viewmode=true; %>
         </logic:match>	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
    	<td width="90%"><bean:write name="frmDebitNoteDetails" property="caption"/></td>
    	<td align="right" class="webBoard">
    		<logic:notEmpty name="frmDebitNoteDetails" property="debitNoteSeqID">
            	<a href="#" onClick="javascript:onAssociateClaims()"><img src="ttk/images/EditIcon.gif" alt="Associate Claims" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;<img src="ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;
            </logic:notEmpty>
    	</td>
      </tr>
    </table>
    <!-- E N D : Page Title -->
    <html:errors/>
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
	<div class="contentArea" id="contentArea">
	<fieldset><legend>General</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
        		<td class="formLabel" width="12%" nowrap >Debit Note:</td>
        		<td width="30%" class="textLabelBold" nowrap><bean:write property="debitNoteNbr" name="frmDebitNoteDetails"/></td>
        		<td width="20%" class="formLabel" nowrap>Draft Date:<span class="mandatorySymbol"> *</span></td>
        		<td width="38%" nowrap>
        			<html:text property="debitNoteDate" styleClass="textBox textDate" maxlength="10" disabled="true"/>
        		<!-- 	<logic:empty name="frmDebitNoteDetails" property="debitNoteSeqID">
						<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].debitNoteDate',document.forms[1].debitNoteDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
					</logic:empty> -->
        		</td>
        	
        			<logic:match  name="frmDebitNoteDetails" property="debitType" value="DFL">
        			<td width="20%" class="formLabel" nowrap>Final Date:<span class="mandatorySymbol"> *</span></td>
        		<td width="38%" nowrap>
        			<html:text property="finalDate" styleClass="textBox textDate" maxlength="10" disabled="true"/>
        	   </td>
        	   </logic:match>
        
        	 	
        	<logic:match  name="frmDebitNoteDetails" property="debitType" value="DDT">
        			<td width="20%" class="formLabel" nowrap>Final Date:</td>
        		<td width="38%" nowrap>
        			<html:text property="finalDate" styleClass="textBox textDate textBoxDisabled"  value="" readonly="true" maxlength="10" />
        			</td>
        	 </logic:match>
        	 
        	 <logic:empty name="frmDebitNoteDetails" property="debitNoteSeqID">
        	     <td width="20%" class="formLabel" nowrap>Final Date:</td>
        		<td width="38%" nowrap>
        			<html:text property="finalDate" styleClass="textBox textDate textBoxDisabled"  value="" readonly="true" maxlength="10" />
        			</td>
        	 </logic:empty>
        	 
       
        	</tr>
        	<tr>
            	<td class="formLabel" nowrap>Debit Type:<span class="mandatorySymbol"> *</span></td>
            	
				<logic:empty name="frmDebitNoteDetails" property="debitNoteSeqID">
					<td>
	            		<html:select property="debitNoteTypeID" name="frmDebitNoteDetails" styleClass="selectBox selectBoxMedium" onchange="setDebitType()">
		            		<html:options collection="debitTypeDraft" property="cacheId" labelProperty="cacheDesc"/>
			        	</html:select>
	        		</td>
				</logic:empty>
				
				<logic:notEmpty name="frmDebitNoteDetails" property="debitNoteSeqID">
			       <logic:notEmpty name="flag"  scope="session">
					<td>
	            		<html:select property="debitNoteTypeID" styleId="debitNoteTypeID" name="frmDebitNoteDetails" styleClass="selectBox selectBoxMedium"      onchange="setDebitType()">
		            		<html:options collection="debitType" property="cacheId" labelProperty="cacheDesc"/>
			        	</html:select>
	        		</td>
				 </logic:notEmpty>
			        <logic:empty name="flag"  scope="session">
			       <td>
	            		<html:select property="debitNoteTypeID" styleId="debitNoteTypeID" name="frmDebitNoteDetails"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>"   onchange="setDebitType()">
		            		<html:options collection="debitType" property="cacheId" labelProperty="cacheDesc"/>
			        	</html:select>
	        		</td>
	        		</logic:empty>
				</logic:notEmpty>
				
				

          		<td class="formLabel" nowrap>Debit Note Amt. (OMR):</td>
        	    <td>
        	    	<html:text property="debitNoteAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode || viewAmt %>"/>
        	    </td>
            </tr>
            <tr>
           <td class="formLabel" nowrap>Description:<span class="mandatorySymbol"> *</span></td>
          <logic:empty name="frmDebitNoteDetails" property="debitNoteSeqID">
           <td colspan="3">
    		<html:textarea property="remarks" styleClass="textBox textAreaLong" />
    		</td>
    		</logic:empty>	
    	  <logic:notEmpty name="frmDebitNoteDetails" property="debitNoteSeqID">
    	 <logic:notEmpty name="flag"  scope="session">
    		<td colspan="3">
    		<html:textarea property="remarks" styleClass="textBox textAreaLong"/>
    		 </td>
    	 </logic:notEmpty>
    			    
    	 <logic:empty name="flag"  scope="session">
    	 <td colspan="3">
        <html:textarea property="remarks" styleClass="textBox textAreaLong"   disabled="<%=viewmode%>" />
    	 </td>
    	</logic:empty>
    	 </logic:notEmpty>
    	 </tr>
            <tr>
            	<logic:match name="frmDebitNoteDetails" property="debitNoteTypeID" value="DFL">
	            	<td class="formLabel" nowrap>Report Type:</td>
    	        	<td>
    	        		<select name="reportTypeID" class="selectBox" id="reporttypeid">
			    			<!-- <option value="76ColPend">76 Column Pending Report</option> -->
		        			<option value="FinPenRpt">Claims Pending Report</option>
							<!-- Added for Debit Note CR 1163 -->
							<!-- <option value="IbmDakshRpt">AMHI DN Report</option> -->
		   				</select>
   	        		</td>
    	        </logic:match>
    	        
    	        <logic:match value="DDT" name="frmDebitNoteDetails" property="debitNoteTypeID"  >
    	      <td class="formLabel" nowrap>Report Type:</td>
    	     
    	      <td>
	            		<html:select property="reportTypeID" name="frmDebitNoteDetails" styleClass="selectBox selectBoxMedium" >
		            		<option value="">Select from list</option>
		            		<option value="ASCANA">Ascana Report</option>
		        			<option value="NLGICNETWORK">NLGIC-Network-Report</option>
		        			<option value="NLGICRI">NLGIC-RI-Report</option>
							<option value="TAKAFUL">Takaful Report</option>
							<option value="ORIENTAL">Oriental Report</option>
			        	</html:select>
	        		</td>
    	      </logic:match> 
    	        
    	        
    	    </tr>
		</table>
	</fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T :  Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
      	<logic:match name="frmDebitNoteDetails" property="debitNoteTypeID" value="DFL">
	      	 <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
	      </logic:match>
      	  <%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 %>
    	 			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDraftReport();"><u>G</u>enerate <u>R</u>eport</button>&nbsp;
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	      <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 %>
	      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>

      </td>
    </tr>
   </table>
   <!-- E N D :  Buttons -->

	<logic:notEmpty name="frmFloatAccDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="debitNoteSeqID"/>
		<!-- Added for Debit Note CR 1163 -->
	<html:hidden property="floatSeqID"/>
	</div>
</html:form>