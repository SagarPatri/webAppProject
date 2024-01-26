function onSelectedView(argBtnMode) {
	document.forms[1].mode.value="goSelectedView";
	document.forms[1].btnMode.value=argBtnMode;
	document.forms[1].submit();	
}