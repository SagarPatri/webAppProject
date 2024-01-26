package com.ttk.business.preauth.ruleengine;

public class ActionFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public IAction getActionFactory(String strIdentifier)
	{
		IAction iaction=null;
		if(strIdentifier.equals("ruleengineaction"))
		    iaction=new RuleEngineAction();
        else if(strIdentifier.equals("validationaction"))
            iaction=new PolicyRuleEngineAction();
        else if(strIdentifier.equals("DisplayError"))
            iaction=new PolicyRuleEngineAction();
        else if(strIdentifier.equals("computeoverallclp"))
            iaction=new ComputeOverallConfidenceLevel();
		return iaction;
	}
}
