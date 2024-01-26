<%
/**
 * @ (#) clauses.jsp 9th July 2007
 * Project      : TTK HealthCare Services
 * File         : clauses.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 9th July 2006
 *
 * @author       :Chandrasekaran J
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

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/clauses.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("ClauseFor",Cache.getCacheObject("clausefor"));
pageContext.setAttribute("ClauseSubType",Cache.getCacheObject("clausesubtype"));

%>

<html:form action="/ClauseAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td><bean:write name="frmClauses" property="caption"/></td>
    	<td align="right" class="webBoard"></td>
  	</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
<div class="contentArea" id="contentArea">
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
	<ttk:HtmlGrid name="tableDataClause" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->


	<!-- S T A R T : Buttons -->
	<br>
	<table class="buttonsSavetolistGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="right" nowrap class="formLabel">
        <%
	    		if(TTKCommon.isDataFound(request,"tableDataClause") && TTKCommon.isAuthorized(request,"Delete"))
				{
		%>
        <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
        </td>
     	<%
        		}
        %>
      </tr>
    </table>
    
	<fieldset>
    <legend>Clause Details</legend>
    	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     	 <tr>
        	<td width="20%" class="indentedLabels" nowrap align="left">Clause Number: <span class="mandatorySymbol">*</span> <br>
        	<html:text property="clauseNbr"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%= viewmode %>" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        	</td>
        	<td align="left" width="40%" class="indentedLabels" nowrap>Clause Description: <span class="mandatorySymbol">*</span> <br>
	    	<html:textarea  property="clauseDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
	    	</td>
			<%-- 	<td class="formLabel">select clauses&nbsp;
                      <input type="text" name="clauseList" id="clauseList" style="search8" class="textBox textBoxSmall" value="<bean:write name="frmClauses" property="clauseList"/>"><A NAME="clauses" ID="clauses" HREF="#" onClick="javascript:openList('clauseList','REL');" ><img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="select relations" border="0" align="absmiddle"></a><span class="mandatorySymbol">*</span> 
                  </td>--%>
                 <%-- added as per SHORTFALL CR--%>
                 	<%--<html:options collection="ClauseFor"  property="cacheId" labelProperty="cacheDesc"/>	--%>  	
                <td align="left" width="20%" class="indentedLabels" nowrap> Clause For : <br>
                  	<html:select property="clauseFor"  styleClass="selectBox selectBoxSmall"  onchange="showhideSubClause(this);" disabled="<%= viewmode %>">
	  			  				<html:options collection="ClauseFor"  property="cacheId" labelProperty="cacheDesc"/>
	  					
		    	</html:select>
		    	</td>
		    	
		    	<%--  <td id="policyprodClauseSubtype"  class="indentedLabels" width="20%" nowrap style="display:">Clause SubType :<br>  
		        	<html:select property="clauseSubType"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		        	      	      <html:options collection="ClauseSubType"  property="cacheId" labelProperty="cacheDesc"/>
		        		
		    	     </html:select>
		    	</td>--%>
		    	<%--    Added by satya as per KOC 1179 shortfall CR     --%>
		    	  			<logic:equal property="clauseFor"  name="frmClauses" value="SRT" >
		    	 <td id="policyprodClauseSubtype" class="indentedLabels" width="20%" nowrap style="display:" >Clause SubType :<br>  	 
		        	<html:select property="clauseSubType"  styleClass="selectBox selectBoxSmall" disabled="<%= viewmode %>">
		        	<html:options collection="ClauseSubType"  property="cacheId" labelProperty="cacheDesc"/>
		        		
		    	</html:select>
		    		</td>
		    	</logic:equal> 
		    			
		    	<logic:notEqual property="clauseFor" name="frmClauses" value="SRT">
		    	 <td id="policyprodClauseSubtype"  class="indentedLabels" width="20%" nowrap  style="display:none;">Clause SubType :<br>  	 
		        	<html:select property="clauseSubType"  styleClass="selectBox selectBoxSmall" disabled="<%= viewmode %>">
		        	<html:options collection="ClauseSubType"  property="cacheId" labelProperty="cacheDesc"/>
		        	<%--<html:option value="INT">INTIMATION</html:option>
	  					<html:option value="SUB">SUBMISSION</html:option>
	  					<html:option value="COMB">COMBINATION</html:option>--%>
		    	</html:select>
		    		</td>
		    	
		    	</logic:notEqual>
		    	
		    <%--	<html:options collection="ClauseSubType"  property="cacheId" labelProperty="cacheDesc"/>--%>
		    	 <%-- added as per SHORTFALL CR--%>
      	</tr>
      	</table>
	</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
      	  <%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 %>
					<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	  	 			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	      <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 %>
	      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
	<!-- E N D : Buttons -->
	<html:hidden property="clauseSeqID"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden property="caption"/>
	<logic:notEmpty name="frmClauses" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</div>
</html:form>
