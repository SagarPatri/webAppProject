
	function onAdd()
	{
		document.forms[1].mode.value="doAdd";
		document.forms[1].child.value="Buffer Details";
   		document.forms[1].action="/BufferDetailAction.do";
		document.forms[1].submit();	
	}//end of onAdd

	function edit(rownum)
	{ 
	    document.forms[1].mode.value="doViewBuffer";
	    document.forms[1].child.value="Buffer Details";
	    document.forms[1].rownum.value=rownum;       
	    document.forms[1].action = "/BufferDetailAction.do";
	    document.forms[1].submit();
	}//end of edit(rownum)
	
	//function to sort based on the link selected
	function toggle(sortid)
	{
	    document.forms[1].mode.value="doBufferAmount";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/BufferAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)
	
	//function to display the selected page
	function pageIndex(pagenumber)
	{ 
		document.forms[1].mode.value="doBufferAmount";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/BufferAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)
	
	//function to display previous set of pages
	function PressBackWard()
	{ 
	    document.forms[1].mode.value ="doBackward"; 
	    document.forms[1].action = "/BufferAction.do";
	    document.forms[1].submit();     
	}//end of PressBackWard()
	
	//function to display next set of pages
	function PressForward()
	{ 
	    document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/BufferAction.do";
	    document.forms[1].submit();     
	}//end of PressForward()
	
	
	  //added for hyundai buffer by rekha on 20.06.2014
	function onLevelConfiguration()
	{
	
		document.forms[1].mode.value="doViewProductLevelConfiguration";
		document.forms[1].action="/BufferDetailAction.do";
	    document.forms[1].submit();
	}//end of onLevelConfiguration()
		

	//end added for hyundai buffer by rekha on 20.06.2014	