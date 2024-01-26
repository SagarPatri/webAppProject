package com.ttk.business.preauth.ruleengine;




import org.dom4j.Node;

import com.ttk.common.exception.TTKException;

public interface IAction {
	public Node execute(Node doc) throws TTKException;
}
