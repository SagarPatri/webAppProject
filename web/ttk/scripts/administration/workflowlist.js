//java script for the workflowlist screen in the administration of workflow.

function onEventDetails(eventSeqID,workflowName)
{  
	document.forms[1].eventSeqID.value=eventSeqID;
	document.forms[1].caption.value=workflowName;
	document.forms[1].mode.value="doViewWorkflow";
	document.forms[1].child.value="EventDetails";
	document.forms[1].action="/WorkflowAction.do";
	document.forms[1].submit();
}//end of function onEventDetails(eventSeqID)