<%
/** @ (#) cardprinting.jsp Feb 23, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : cardprinting.jsp
 * Author     	 : Bhaskar Sandra
 * Company    	 : Span Systems Corporation
 * Date Created	 : Feb 23, 2006
 * @author 		 : Bhaskar Sandra
 * Modified by   : Raghavendra T M
 * Modified date : Mar 5, 2006
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/support/cardprinting.js"></SCRIPT>
<script>
var TC_Disabled = true;
var cardbatchpath = new Array(<bean:write name="frmCardPrinting" property="CardBatchPath" filter="false"/>);
</script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));	
	pageContext.setAttribute("strSubLink",TTKCommon.getActiveSubLink(request));
	pageContext.setAttribute("ttkbranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("printStatus",Cache.getCacheObject("printStatus"));
%>
<html:form action="/CardPrintingAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Batches</td>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
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
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Batch No.:<br>
        	<html:text property="BatchNbr" styleClass="textBox textBoxMedium" onkeypress="javascript:blockEnterkey(event.srcElement);" maxlength="60" />
        <td nowrap>Vidal Health Branch:<br>
	        <html:select property="CompanyName" styleClass="selectBox selectBoxMedium" >
	        	<html:option value="">Any</html:option>
		       	<html:options collection="ttkbranch" property="cacheId" labelProperty="cacheDesc"/>
	        </html:select>
        </td>
        <td valign="bottom" nowrap>Print Status:<br>
            <html:select property="PrintStatus" name="frmCardPrinting" styleClass="selectBox selectBoxMedium" >
            	<html:options collection="printStatus" property="cacheId" labelProperty="cacheDesc"/>
        	</html:select>
        </td>
		 <td width="100%" valign="bottom" nowrap>
		 	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		 </td>
      </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataCardBatch"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  <td width="100%" nowrap align="right" colspan="2" >
	  	<logic:match name="strSubLink"  value="Card Printing">
		  	 <logic:match value="BNP" name="frmCardPrinting" property="PrintStatus" >
				<%
					if(TTKCommon.isAuthorized(request,"Create Card Batch"))
			     	{
			    %>
				    <logic:match name="viewmode" value="false">
			  		 <button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCreateNewBatch();">Create <u>N</u>ew Batch</button>&nbsp;
			  		 </logic:match>
				<%
					}//end of if(TTKCommon.isAuthorized(request,"Add"))
					if(TTKCommon.isDataFound(request,"tableDataCardBatch"))
					{
			    %>
			    	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateDispatchList();">Generate <u>D</u>ispatch List</button>&nbsp;
			    	<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateLabels();">Generate <u>L</u>abels</button>&nbsp;
			    	<logic:match name="viewmode" value="false">
			    	<button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPrintComplete();"><u>P</u>rint Complete</button>&nbsp;
			    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancelBatch();"><u>C</u>ancel Batch</button>
			    	</logic:match>
				<%
			    	}
			    %>
			</logic:match>
			<logic:notMatch value="BNP" name="frmCardPrinting" property="PrintStatus" >
				<%  if(TTKCommon.isDataFound(request,"tableDataCardBatch"))
					{
			    %>
			    	<button type="button" name="Button" accesskey="D" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateDispatchList();">Generate <u>D</u>ispatch List</button>&nbsp;
			    	<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateLabels();">Generate <u>L</u>abels</button>&nbsp;
			    <%
			    	}
			    %>
			</logic:notMatch>
	   </logic:match>
		<logic:match name="strSubLink"  value="Courier">
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseList();"><u>C</u>lose</button>
		</logic:match>
		</td>
	  </tr>
    <ttk:PageLinks name="tableDataCardBatch" />
 	</table>
 	</div>
	<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="mode" value=""/>
	<html:hidden property="flag" />
	<input type="hidden" name="rownum" value=""/>
	<input type="hidden" name="pageId" value=""/>
	<input type="hidden" name="sortId" value=""/>
	<input type="hidden" name="child" value=""/>
	<logic:notEmpty name="CardLableURL" scope="request">
	         <script>
	             if(document.forms[1].flag.value == 'GL')
		         {
			        var w = screen.availWidth - 10;
					var h = screen.availHeight - 49;
					var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
					window.open("<bean:write name="CardLableURL" scope="request"/>",'',features);
			     }
		     </script>
	</logic:notEmpty>
	</html:form>
	<logic:notEmpty name="DispatchListURL" scope="request">
	         <script>
                var w = screen.availWidth - 10;
				var h = screen.availHeight - 49;
				var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
				window.open("<bean:write name="DispatchListURL" scope="request"/>",'',features);
			 </script>
	</logic:notEmpty>
	<!-- E N D : Content/Form Area -->