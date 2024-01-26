<%
/** @ (#) courierdetail.jsp May 26, 2006
 * Project     : TTK Healthcare Services
 * File        : courierdetail.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: May 26, 2006
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
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/support/courierdetail.js"></script>
<%
  boolean viewmode=true;
  boolean viewtype=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }// end of if(TTKCommon.isAuthorized(request,"Edit"))
  String ampm[] = {"AM","PM"};
  String strStyle="display:none";
  String strStyle1="display:none";
  pageContext.setAttribute("courierName",Cache.getCacheObject("courierName"));
  pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
  pageContext.setAttribute("contentType",Cache.getCacheObject("contentType"));
  pageContext.setAttribute("courierStatus",Cache.getCacheObject("courierStatus"));
  pageContext.setAttribute("courierStatusDsp",Cache.getCacheObject("courierStatusDsp"));
  pageContext.setAttribute("courierType",Cache.getCacheObject("courierType"));
  pageContext.setAttribute("courierSource",Cache.getCacheObject("courierSource"));
  pageContext.setAttribute("departmentID",Cache.getCacheObject("departmentID"));
  //added for courier cr
  pageContext.setAttribute("courierDocType",Cache.getCacheObject("courierDocType"));
  pageContext.setAttribute("viewmode",new Boolean(viewmode));	
  pageContext.setAttribute("ampm",ampm);
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CourierDetailAction.do">
  <!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td> Courier Details</td>
    </tr>
  </table>
  <!-- E N D : Page Title -->
  <!-- S T A R T : Form Fields -->
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
  <logic:empty name="frmCourierDetails" property="courierSeqID" >
    <% viewtype=false; %>
  </logic:empty>
  <html:errors/>
    <fieldset>
      <legend>General</legend>
      <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <!--first row Courier type	Courier name-->
       
        <tr>
          <td width="18%" height="20" nowrap class="formLabel">Courier Type: <span class="mandatorySymbol">*</span></td>
          <td width="43%" class="textLabelBold">
         
               <html:select property="courierTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideCourierType()" disabled="<%= (viewmode || viewtype)%>">
                  <html:options collection="courierType" property="cacheId" labelProperty="cacheDesc"/>
              </html:select>
              </td>
             <td width="16%" nowrap class="formLabel">Consignment No.:<span class="mandatorySymbol">*</span> </td>
               <td width="23%" class="textLabel">
                <html:text property="docketPODNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
               </td>
                
                 
        </tr>
         <!--End first row Courier type	Courier name-->
         
          <!--2nd row consignment no	Courier No-->
        <tr>
             
          <td  width="18%" height="15" nowrap class="formLabel">Courier Name: <span class="mandatorySymbol">*</span></td>
              <td  width="43%" nowrap class="textLabel">
                <html:select property="courierCompSeqID" styleClass="selectBox selectBoxMedium" onchange="getCourierName()" disabled="<%= viewmode %>">
                <html:option value="">Select from list</html:option>
                <html:option value="-1">Others</html:option>
                <html:optionsCollection name="courierName" label="cacheDesc" value="cacheId" />
                   </html:select>
                   <logic:notEmpty name="frmCourierDetails" property="courierCompSeqID">
                     <logic:match name="frmCourierDetails" property="courierCompSeqID" value="-1">
                       <%strStyle="display:";%>
                     </logic:match>
                   </logic:notEmpty>
                   <html:text property="otherDesc" styleClass="textBox textBoxMedium" styleId="Others" maxlength="60" style="<%=strStyle%>" disabled="<%= viewmode %>"/>
               </td>
           <td width="16%" height="20" nowrap class="formLabel">Courier No.:</td>
           <td width="23%" class="textLabelBold"><bean:write name="frmCourierDetails" property="courierNbr"/></td>
         </tr>
         
        <!-- end 2nd row consignment no	Courier No-->  
        
         <!--3rd row Receiving Branch	Recd. date	Recd. Time-->
         
        <logic:match name="frmCourierDetails" property="courierTypeID" value="RCT">
          <tr id="Recipt" style="display">
        </logic:match>
        <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="RCT">
          <tr id="Recipt" style="display:none;">
        </logic:notMatch>
             <td width="18%"  nowrap class="formLabel">Receiving Branch: <span class="mandatorySymbol">*</span></td>
                 <td width="43%" class="textLabel">
                  <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                  <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId" />
                   </html:select>
                 </td>
                 <td width="16%" height="20" nowrap class="formLabel">Received Date / Time: <span class="mandatorySymbol">*</span></td>
                 <td width="23%" class="textLabel">
		               <table cellpadding="1" cellspacing="0">
		                <tr>
		                 <td><html:text property="receivedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/><logic:match name="viewmode" value="false"><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].receivedDate',document.forms[1].receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></logic:match>&nbsp;&nbsp;</td>
		                 <td><html:text property="receivedTime" styleClass="textBox textTime" maxlength="5" disabled="<%= viewmode %>"/>&nbsp;</td>
		                 <td><html:select property="receivedDay" styleClass="selectBox" disabled="<%= viewmode %>">
		                    <html:options name="ampm" labelName="ampm"/>
		                   </html:select></td>
		                </tr>
		               </table>
                </td>
              </tr>
              
          <logic:match name="frmCourierDetails" property="courierTypeID" value="DSP">
          <tr id="senbranch" style="display">
        </logic:match>
        <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="DSP">
          <tr id="senbranch" style="display:none;">
        </logic:notMatch>
             <td width="18%"  nowrap class="formLabel">Sender Branch: <span class="mandatorySymbol">*</span></td>
                 <td width="43%" class="textLabel">
                  <html:select property="officeSeqID1" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                  <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId" />
                   </html:select>
                 </td>
                   <td height="20" nowrap class="formLabel">Dispatch Date / Time: <span class="mandatorySymbol">*</span></td>
                 <td class="textLabel">
		                 <table cellpadding="1" cellspacing="0">
		                <tr>
		                 <td><html:text property="dispatchDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/>
		              		<logic:match name="viewmode" value="false">
		              		<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].dispatchDate',document.forms[1].dispatchDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;</logic:match></td>
		                 <td><html:text property="dispatchTime" styleClass="textBox textTime" maxlength="5" disabled="<%= viewmode %>"/>&nbsp;</td>
		                 <td><html:select property="dispatchDay" styleClass="selectBox" disabled="<%= viewmode %>">
		                    <html:options name="ampm" labelName="ampm"/>
		                   </html:select></td>
		                 </tr>
		               </table>
                   </td>
               </tr>
              <!--End 3rd row Receiving Branch	Recd. date	Recd. Time-->
              
           <!--4th row sender	sender Location-->    
           
           
                <logic:match name="frmCourierDetails" property="courierTypeID" value="RCT">
                   <tr id="senderDetails" style="display">
                </logic:match>
               <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="RCT">
                 <tr id="senderDetails" style="display:none;">
              </logic:notMatch>
		              <td width="18%" nowrap class="formLabel">From Address: <span class="mandatorySymbol">*</span></td>
		              <td width="43%" class="textLabel">
		                <html:text property="rctSen" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
		              </td>
		              <td width="16%"  nowrap class="formLabel">From Location: <span class="mandatorySymbol">*</span></td>
		              <td width="23%" class="textLabel">
		                <html:text property="rctSenLoc" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
		              </td>
               </tr>
                <logic:match name="frmCourierDetails" property="courierTypeID" value="DSP">
          <tr id="Dispatch" style="display">
           </logic:match>
           <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="DSP">
          <tr id="Dispatch" style="display:none;">
          </logic:notMatch>
           <td nowrap class="formLabel">Sender Name: <span class="mandatorySymbol">*</span></td>
              <td class="textLabel">
                <html:text property="rctSen1" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
             <td nowrap class="formLabel">Sender Department.: <span class="mandatorySymbol">*</span></td>
             <td class="textLabel">
                <html:select property="department" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			        <html:option value="">Select from list</html:option>
                  		<html:options collection="departmentID" property="cacheId" labelProperty="cacheDesc"/>
              		</html:select>
              </td>
              </tr>
               <!-- end 4th row sender	sender Location-->  
               
           
         
      
              
              <!--
          
           <logic:match name="frmCourierDetails" property="courierTypeID" value="RCT">
           <tr id="senderDetails" style="display">
            <td nowrap class="formLabel">Sender: <span class="mandatorySymbol">*</span></td>
              <td class="textLabel">
                <html:text property="rctSen" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
              <td nowrap class="formLabel">Sender Location.: <span class="mandatorySymbol">*</span></td>
              <td class="textLabel">
                <html:text property="rctSenLoc" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
          </tr>
        </logic:match>
         <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="RCT">
         <tr id="senderDetails" style="display:none;">
            <td nowrap class="formLabel">Sender: <span class="mandatorySymbol">*</span></td>
              <td class="textLabel">
                <html:text property="rctSen" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
              <td nowrap class="formLabel">Sender Location.: <span class="mandatorySymbol">*</span></td>
              <td class="textLabel">
                <html:text property="rctSenLoc" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
          </tr>
        
        </logic:notMatch>
        
      --></table>
    </fieldset>
    <logic:match name="frmCourierDetails" property="courierTypeID" value="DSP">
      <div id="Dispatch1" style="display">
    </logic:match>
    <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="DSP">
      <div id="Dispatch1" style="display:none;">
    </logic:notMatch>
    <fieldset>
      <legend>Contents</legend>
      
        <table  align="center"  class="formContainer" border="0" cellspacing="0" cellpadding="0">
          <tr>
               <td  width="18%"   class="formLabel">To Address:<span class="mandatorySymbol">*</span></td>
               <td  width="43%" class="textLabelBold">
                 <span class="textLabel">
                     <html:text property="couAddressTo" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
                 </span>&nbsp;
               </td>
              
             <td  width="16%"  class="formLabel">To Location:<span class="mandatorySymbol">*</span></td>
               <td  width="23%" class="textLabelBold">
                 <span class="textLabel">
                     <html:text property="dspToaddress" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%= viewmode %>"/>
                 </span>&nbsp;
               </td>
               </tr>
          <tr>
            <td width="18%" height="20" nowrap class="formLabel">Content Type: <span class="mandatorySymbol">*</span></td>
            <td width="43%" nowrap class="textLabel">
              <html:select property="contentTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideContentType()" disabled="<%= viewmode %>">
                  <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="contentType" label="cacheDesc" value="cacheId" />
                     </html:select>
                </td>
                <td class="formLabel" width="16%"></td>
              <td class="textLabelBold" width="23%"></td>
          </tr>
          <logic:match name="frmCourierDetails" property="contentTypeID" value="HOP">
            <tr id="Hospital" style="display">
          </logic:match>
          <logic:notMatch name="frmCourierDetails" property="contentTypeID" value="HOP">
            <tr id="Hospital" style="display:none;">
          </logic:notMatch>
                <td height="20" class="formLabel">Hospital Name:</td>
                <td class="textLabelBold"><bean:write name="frmCourierDetails" property="hospName"/>&nbsp;&nbsp;</td>
                <td class="formLabel">Empanelment No.: <span class="mandatorySymbol">*</span></td>
                <td class="textLabelBold" ><bean:write name="frmCourierDetails" property="empanelmentNbr"/>&nbsp;&nbsp;&nbsp;
                <logic:match name="viewmode" value="false">
                <a href="#" onClick="javascript:selectHospital();"><img src="/ttk/images/EditIcon.gif" alt="Select Hospital" width="16" height="16" border="0" align="absmiddle"></a>
                </logic:match></td>
              </tr>
            <logic:notMatch name="frmCourierDetails" property="contentTypeID" value="ECD">
            <tr id="Enrollment" style="display:none;">
          </logic:notMatch>
          <logic:match name="frmCourierDetails" property="contentTypeID" value="ECD">
            <tr id="Enrollment" style="display">
          </logic:match>
                <td height="20" class="formLabel">Batch No.:</td>
                <td class="textLabelBold"><bean:write name="frmCourierDetails" property="cardBatchNbr"/>&nbsp;&nbsp;</td>
                <td class="formLabel" >Batch Date: <span class="mandatorySymbol">*</span></td>
                <td class="textLabelBold" ><bean:write name="frmCourierDetails" property="batchDate"/>&nbsp;&nbsp;&nbsp;
                <logic:match name="viewmode" value="false">
                	<a href="#" onClick="onCardBatch()"><img src="/ttk/images/EditIcon.gif" alt="Select Card Batch No." width="16" height="16" border="0" align="absmiddle"></a>
                </logic:match>
                </td>
              </tr>
              
             
               <tr>
              <td nowrap class="formLabel">Content Description:</td>
              <td  colspan="3" nowrap class="textLabel">
                  <html:textarea property="contentDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
                </td>
            </tr>
        </table>
    </fieldset>
    </div>
    <logic:match name="frmCourierDetails" property="courierTypeID" value="RCT">
      <div id="Recipt1" style="display">
    </logic:match>
    <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="RCT">
      <div id="Recipt1" style="display:none;">
    </logic:notMatch>
    <fieldset>
      <legend>Contents</legend>
        <table  align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	        
	              <!-- 5th row Courier addresed to	Department-->  
            <tr>
               <td  width="18%"   class="formLabel">Courier Addressed To:<span class="mandatorySymbol">*</span></td>
               <td  width="43%" class="textLabelBold">
                 <span class="textLabel">
                     <html:text property="rctCouAddressTo" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
                 </span>&nbsp;
               </td>  
                <td width="16%"  nowrap class="formLabel">Department: <span class="mandatorySymbol">*</span></td>
              <td  width="23%"  class="textLabel">
                <html:select property="rctdepartment" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			        <html:option value="">Select from list</html:option>
                  		<html:options collection="departmentID" property="cacheId" labelProperty="cacheDesc"/>
              		</html:select>
              </td>
              </tr>
             
                <!-- end 5th row Courier addresed to	Department-->   
                
                <!-- 6th row Document type	No. of docs  -->
                <tr><!--
                 <td  width="18%"  nowrap class="formLabel">Document Type:</td>
			        <td  width="43%" class="textLabelBold">
	                 <span class="textLabel">
	                     <html:text property="rctDocType" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
	                 </span>&nbsp;
	               </td> 
	              -->
	              <td  width="18%" height="15" nowrap class="formLabel">Document Type: <span class="mandatorySymbol">*</span></td>
                  <td  width="43%" nowrap class="textLabel">
                <html:select property="rctDocType" styleClass="selectBox selectBoxMedium" onchange="getDocumentName()" disabled="<%= viewmode %>">
                <html:option value="">Select from list</html:option>
                <!--
                <html:optionsCollection name="courierDocType" label="cacheDesc" value="cacheId" />
                   -->
                   <html:options collection="courierDocType" property="cacheId" labelProperty="cacheDesc"/>
                   </html:select>
                    <logic:notEmpty name="frmCourierDetails" property="rctDocType">
                     <logic:match name="frmCourierDetails" property="rctDocType" value="OTRS">
                       <%strStyle1="display:";%>
                     </logic:match>
                     </logic:notEmpty><!--
                  <logic:notMatch name="frmCourierDetails" property="rctDocType" value="OTRS"></logic:notMatch>
                   --><html:text property="courierDocType" styleClass="textBox textBoxMedium" styleId="DocType" maxlength="60" style="<%=strStyle1%>" disabled="<%= viewmode %>"/>
                  
                   
               </td>
               
               
               
                 <td  width="16%"   class="formLabel">No. of Docs:</td>
	               <td  width="23%" class="textLabelBold">
	                 <span class="textLabel">
	                     <html:text property="nbrOfDocs" styleClass="textBox textBoxMedium" maxlength="5" disabled="<%= viewmode %>"/>
	                 </span>&nbsp;
	               </td>
	              
	               
	               </tr>
              
                <!-- end 6th row Document type	No. of docs  -->
                
                
                <!-- 7th row Phone no	Email id-->
                 <tr>
                  <td  width="18%"   class="formLabel">Phone No:</td>
                   <td  width="43%" class="textLabelBold">
	                 <span class="textLabel">
	                     <html:text property="rctPhoneNbr" styleClass="textBox textBoxMedium" maxlength="10" disabled="<%= viewmode %>"/>
	                 </span>&nbsp;
               		</td>
                 	<td  width="16%"  class="formLabel">EmailId:</td>
	               <td  width="23%" class="textLabelBold">
	                 <span class="textLabel">
					<html:text property="rctEmailID" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
	   	             </span>&nbsp;
	               </td>
                  
               </tr>
               <!--end  7th row Phone no	Email id-->
                <!--  8th row Receiver name 	Source--> 
               <tr>
               <td  width="18%"  nowrap class="formLabel">Receiver Name: </td>
              <td   width="43%"  class="textLabel">
                <html:text property="rctRecName" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
              </td>
		        <td width="16%"   nowrap class="formLabel">Source:</td>
		        <td  width="23%"  class="textLabelBold">
			        <html:select property="sourceRcvdTypeId" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			        <html:option value="">Select from list</html:option>
                  		<html:options collection="courierSource" property="cacheId" labelProperty="cacheDesc"/>
              		</html:select>
		        </td>
		    </tr>
		    <!-- end  8th row Receiver name 	Source--> 
           
            <!--  <tr>
              
                <td width="18%" height="20" nowrap class="formLabel">No. of Claims:</td>
                <td width="43%" class="textLabelBold">
                  <span class="textLabel">
                     <html:text property="nbrOfClaims" styleClass="textBox textBoxMedium" maxlength="5" disabled="<%= viewmode %>"/>
                  </span>
                </td>
                 <td height="20" nowrap class="formLabel">ClaimType:</td>
		         <td class="textLabelBold">
			        <html:select property="rctClaimType" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			        <html:option value="">Select from list</html:option>
                  		<html:options collection="courierSource" property="cacheId" labelProperty="cacheDesc"/>
              		</html:select>
		        </td>
           
              </tr>
             <tr>
               <td  width="18%"   class="formLabel">Courier addresed to:<span class="mandatorySymbol">*</span></td>
               <td  width="43%" class="textLabelBold">
                 <span class="textLabel">
                     <html:text property="rctCouAddressTo" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%= viewmode %>"/>
                 </span>&nbsp;
               </td>
              
             <td  width="16%"  class="formLabel">To Address:<span class="mandatorySymbol">*</span></td>
               <td  width="23%" class="textLabelBold">
                 <span class="textLabel">
                     <html:text property="dspToaddress" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%= viewmode %>"/>
                 </span>&nbsp;
               </td>
               </tr>
                 -->
            </table>
    </fieldset>
    </div>
 <logic:match name="frmCourierDetails" property="courierTypeID" value="RCT">
      <div id="Recipt2" style="display">
    </logic:match>
      <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="RCT">
    <div id="Recipt2" style="display:none;">
    </logic:notMatch>
    <fieldset>
      <legend>Others</legend>
      <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td width="18%" class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
          <td width="43%" nowrap>
                  <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="courierStatus" label="cacheDesc" value="cacheId" />
                   </html:select>
                </td>
                <td class="formLabel" width="16%"></td>
            <td class="textLabelBold" width="23%"></td>
        </tr> 
          
          
          <tr>
         
            <td width="18%" class="formLabel">Remarks:</td>
            <td width="81%" colspan="3" class="textLabel">
              <html:textarea  property="rcptRemarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
            </td>
          </tr>
          <tr>
          <td  width="16%" nowrap class="formLabel">Signature: </td>
           
          </tr>
      </table>
    </fieldset>
    </div>
    <logic:match name="frmCourierDetails" property="courierTypeID" value="DSP">
      <div id="Dispatch2" style="display">
    </logic:match>
    <logic:notMatch name="frmCourierDetails" property="courierTypeID" value="DSP">
      <div id="Dispatch2" style="display:none;">
    </logic:notMatch>
    <fieldset>
      <legend>Others</legend>
      <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       <tr>
           <td width="18%" class="formLabel">POD Received Date: </td>
        			<td width="43%">
        				<html:text property="podReceDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectPODDate" id="CalendarObjectPODDate" href="#" onClick="javascript:show_calendar('CalendarObjectPODDate','forms[1].podReceDate',document.forms[1].podReceDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
                <td class="formLabel" width="16%"></td>
            <td class="textLabelBold" width="23%"></td>
        </tr>
        <tr>
          <td width="18%" class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
          <td width="43%" nowrap>
                  <html:select property="statusTypeID1" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="courierStatusDsp" label="cacheDesc" value="cacheId" />
                   </html:select>
                </td>
                <td class="formLabel" width="16%"></td>
            <td class="textLabelBold" width="23%"></td>
        </tr>
        <logic:match name="frmCourierDetails" property="contentTypeID" value="HOP">
	        <tr id="deldate" style="display">
	    </logic:match>
	     <logic:notMatch name="frmCourierDetails" property="contentTypeID" value="HOP">
 	        <tr id="deldate" style="display:none;">
	     </logic:notMatch>
	          <td class="formLabel">Delivery Date / Time:</td>
	          <td class="textLabel">
	            <html:text property="deliveryDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].deliveryDate',document.forms[1].deliveryDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	            <html:text property="deliveryTime" styleClass="textBox textTime" maxlength="5" disabled="<%= viewmode %>"/>&nbsp;
	            <html:select property="deliveryDay" styleClass="selectBox" disabled="<%= viewmode %>">
	                  <html:options name="ampm" labelName="ampm"/>
	                   </html:select>
	             </td>
	             <td class="formLabel">POD No.: </td>
	            <td class="textLabel">
	              <html:text property="proofDeliveryNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
	            </td>
	        </tr>
        <tr>
            <td class="formLabel">Remarks:</td>
            <td colspan="3" class="textLabel">
              <html:textarea  property="dispatchRemarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
            </td>
            </tr>
         <tr>
         <td  width="16%" nowrap class="formLabel">Signature: </td>
           
            
         </tr>
      </table>
    </fieldset>
    </div>
  <!-- E N D : Form Fields -->
  <!-- S T A R T : Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
          <%
        if(TTKCommon.isAuthorized(request,"Edit"))
        {
          %>

            <logic:notEmpty name="frmCourierDetails" property="courierSeqID" >
              <logic:match name="frmCourierDetails" property="contentTypeID" value="ECD">
	              <button type="button" name="Button" accesskey="d" id="courier" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReport();"><u>D</u>own Load</button>&nbsp;
              </logic:match>
              <logic:notMatch name="frmCourierDetails" property="contentTypeID" value="ECD">
	              <button type="button" name="Button" accesskey="d" id="courier" style="display:none" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReport();"><u>D</u>own Load</button>&nbsp;
              </logic:notMatch>
          </logic:notEmpty><!--
           <a href="#" onClick="javascript:PressBackWard()"><img src="/ttk/images/Prev.gif\" alt="Previous Page" width="4" height="8" border="0"></a>
            --><button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button><!--&nbsp;
            <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd(document.forms[1].courierTypeID.value);"><u>A</u>dd Next</button>&nbsp;
			--><button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button><!--&nbsp;
            &nbsp;&nbsp;<a href="#" onClick="javascript:PressForward()"><img src="/ttk/images/Next.gif" alt="Next Page" width="4" height="8" border="0"></a>
          --><%
          }//end of checking for Edit permission
          %>
          <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
           
        </td>
      </tr>
  </table>
  <!-- E N D : Buttons -->
  </div>
  <INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
  <INPUT TYPE="hidden" NAME="paramType" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="tab" VALUE="">
  <html:hidden property="hospSeqID"/>
  <html:hidden property="courierSeqID"/>
  <html:hidden property="cardBatchSeqID"/>
  <logic:notEmpty name="frmCourierDetails" property="frmChanged">
    <script> ClientReset=false;TC_PageDataChanged=true;</script>
  </logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->