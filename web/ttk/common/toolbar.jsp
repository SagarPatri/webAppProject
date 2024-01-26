<%@ page import="com.ttk.common.TTKCommon, com.ttk.dto.common.*" %>
<%
    Toolbar toolBar = null;
    toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
    //toolBar.getWebBoard().setCurrentId(TTKCommon.checkNull(request.getParameter("webBoard")));
    String strQueryString=toolBar.getQueryString();
    String strURL=toolBar.getDocUrl();
    StringBuffer strQuery= new StringBuffer();
    strQuery = strQuery.append(strURL).append(strQueryString);
%>

<%
     if(toolBar.getConflictIcon().isVisible())
     {
    	 if((TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveTab(request).equals("Authorization")) || 
    		(TTKCommon.getActiveLink(request).equals("Claims") && TTKCommon.getActiveTab(request).equals("Settlement")))
	 	{
%>
  	<a href="#" id="discrepancies" onclick="javascript:onDiscrepancySubmit();"><img src="/ttk/images/ErrorIcon.gif" alt="Discrepancies" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;&nbsp;
    <img src="/ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;	  	    		
<%
  		} //end of if((TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveTab(request).equals("Authorization")) || (TTKCommon.getActiveLink(request).equals("Claims") && TTKCommon.getActiveTab(request).equals("Settlement")))
        else
  		{
%>
	<img src="/ttk/images/ErrorIcon.gif" alt="Discrepancies" width="16" height="16" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
	<img src="/ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
<%  
  		}//end of else
	}//end of if(toolBar.getConflictIcon().isVisible())
%>
<%  
	if(toolBar.getDocViewIcon().isVisible()) 
	{ 
%>
	<a href="#" id="docviewer" onclick="javascript:onDocumentViewer('<%=strQuery%>');"><img src="/ttk/images/DocViewIcon.gif" alt="Launch Document Viewer" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;&nbsp;
	<img src="/ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
<%  
	} 
%>
<% 
	if(toolBar.getWebBoard().isVisible())  
	{ 
%>
	Web&nbsp;Board:&nbsp;
	<select name="webBoard" id="webboard" class="webBoardList" onChange="javascript:onWebboardSelect()">
<%
     CacheObject toolCacheObject = null;
     if(toolBar != null && toolBar.getWebBoard().getWebboardList().size() > 0)
     {
		String strDesc = "";
		String strId = "";
		for(int i=0; i < toolBar.getWebBoard().getWebboardList().size(); i++)
		{
			toolCacheObject = (CacheObject)toolBar.getWebBoard().getWebboardList().get(i);
			strId = toolCacheObject.getCacheId();
			strDesc = toolCacheObject.getCacheDesc();
	%>
		<option VALUE="<%= strId %>" <%= toolBar.getWebBoard().getCurrentId().equals(strId) ? "selected" : "" %>><%= strDesc %></option>
	<%
			strDesc = "";
			strId = "";
		}//end of for
     }//end of if(toolBar != null && toolBar.getWebBoard().getWebboardList().size() > 0)
%>
	</select>
	<SCRIPT  type="text/javascript">
	function onWebboardSelect()
	{
		if(!TrackChanges())
		{
			// Reset the webboard to previous selected.
			var sel = document.getElementById("webboard");
			for (var i=0;i<sel.options.length;i++)
			{
				sel.options[i].selected = sel.options[i].defaultSelected;
			}
			return false;
		}
	    //alert(document.forms[1].action);
	    //if(document.forms[1].action != null && document.forms[1].action != '')
	    if(bAction)
	    {
	    	document.forms[1].mode.value = "doChangeWebBoard";
	    	document.forms[1].submit();
	    }
	}
function onDocumentViewer(documentviewer)
       {
              
              //var URL="ttkpro/getquery.html?"+documentviewer;
              //window.open(documentviewer);
              //added for KOC-1257
               var w = screen.availWidth - 10;
         var h = screen.availHeight - 49;
         var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
      if (window.ActiveXObject)  // IE
           {
      
       window.open(documentviewer,'',features,target="_blank");
           }
       else
       {
        alert("Please login with Internet Explorer6.0 and above for DMS");
       // return false;
       }
     }


function onDocumentViewerHyundaiBuffer(documentviewer)
{
       
       //var URL="ttkpro/getquery.html?"+documentviewer;
       //window.open(documentviewer);
       //added for KOC-1257
        var w = screen.availWidth - 10;
  var h = screen.availHeight - 49;
  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
if (window.ActiveXObject)  // IE
    {

window.open(documentviewer,'',features,target="_blank");
    }
else
{
 alert("Please login with Internet Explorer6.0 and above for viewing purpose");
// return false;
}
}	
function onDiscrepancySubmit()
	{
		if(!TrackChanges()) return false;
		document.forms[1].mode.value="doDiscrepancies";
		document.forms[1].child.value="Discrepancy";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
	}//end of function onDiscrepancySubmit()
	</SCRIPT>
	<%   }//end of if(toolBar.currentWebBoard().isVisible())
%>



