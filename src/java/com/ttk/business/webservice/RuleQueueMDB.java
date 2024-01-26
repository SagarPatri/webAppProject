/**
 * @ (#) RuleQueueMDB.java Aug 09, 2006
 * Project       : TTK HealthCare Services
 * File          : RuleQueueMDB.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Aug 09, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.webservice;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.exception.TTKException;


/*@MessageDriven(
        activateConfig ={
                @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
                @ActivationConfigProperty(propertyName="destination", propertyValue="queue/ruleEngine")
                }
        )*/
//added for jboss upgradation
@MessageDriven(
		activationConfig={
				@javax.ejb.ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"), 
				@javax.ejb.ActivationConfigProperty(propertyName="destination", propertyValue="queue/ruleEngine")
				}
		)
//added for jboss upgradation

public class RuleQueueMDB implements MessageListener
{
   private static Logger log = Logger.getLogger( RuleQueueMDB.class );
   public void onMessage(Message recvMsg)
   {
        log.debug("RuleQueueMDB onMessage");
     // Added for Blocking Rule Validation as per Sreeraj's advise on 26/02/2008
        
//        try
//        {
//            if(recvMsg instanceof ObjectMessage)
//            {
//                ObjectMessage objmsg = (ObjectMessage)recvMsg;
//                ArrayList alErrors = new ArrayList();
//
//                PolicyVO policyVO = (PolicyVO)objmsg.getObject();
//                
//                if(policyVO.getPolicySeqID()>0)
//                {
//                    PolicyManager policyObject=this.getPolicyManagerObject();
//                    Document policyRule = policyObject.validateEnrollment("E",policyVO.getPolicyGroupSeqID());
//                    RuleXMLHelper ruleXmlHelper=new RuleXMLHelper();
//
//                    //Merge the policy rule with Base rule to add display nodes
//                    Document baseRuleDoc=TTKCommon.getDocument("MasterBaseRules.xml");
//                    policyRule=ruleXmlHelper.mergeDisplayNodes(policyRule,baseRuleDoc);
//
//                    ValidationRuleManager validationRuleManager = this.getValidationRuleManagerObject();
//
//                    //Execute the policy level rule only for Individual and Ind. Policy as Group
//                    if(policyVO.getEnrollmentType().equals("IND") || policyVO.getEnrollmentType().equals("ING"))
//                    {
//                        alErrors = validationRuleManager.executePolicyValidation(policyRule);
//
//                        if(alErrors!=null && alErrors.size()>0)
//                        {
//                            //Save the error
//                            validationRuleManager.saveRuleErrors(alErrors);
//
//                            //Update status as Rule failed
//                            validationRuleManager.updateValidationStatus("P",policyVO.getPolicySeqID(),"F");
//                        }//end of if(alErrors!=null && alErrors.size()>0)
//                    }//end of if(policyVO.getEnrollmentType().equals("IND") || policyVO.getEnrollmentType().equals("ING"))
//
//                    //Execute the enrollment validation
//                    alErrors = validationRuleManager.executeEnrollmentValidation(policyRule);
//                    if(alErrors!=null && alErrors.size()>0)
//                    {
//                        //Save the error
//                        validationRuleManager.saveRuleErrors(alErrors);
//
//                        //Update status as Rule failed
//                        validationRuleManager.updateValidationStatus("E",policyVO.getPolicyGroupSeqID(),"F");
//                    }//end of if(alErrors!=null && alErrors.size()>0)
//                }//end of if(policyVO.getPolicySeqID()>0)
//            }//end of if(recvMsg instanceof ObjectMessage)
//        }//end of try
//        catch (JMSException jMSException)
//        {
//            jMSException.printStackTrace();
//        }//end of catch (JMSException jMSException)
//        catch (TTKException ttkException)
//        {
//            log.debug("Error in rule execute !!");
//        }//end of catch (DocumentException documentException)
        // End of Addition for Blocking Rule Validation
   }//end of onMessage(Message recvMsg)

   /**
    * Returns the PolicyManager session object for invoking methods on it.
    * @return policyManager session object which can be used for method invokation
    * @exception throws TTKException
    */
   private PolicyManager getPolicyManagerObject() throws TTKException
   {
       PolicyManager policyManager = null;
       try
       {
           if(policyManager == null)
           {
               InitialContext ctx = new InitialContext();
               policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
           }//end if(policyManager == null)
       }//end of try
       catch(Exception exp)
       {
           throw new TTKException(exp, "policydetail");
       }//end of catch
       return policyManager;
   }//end getPolicyManagerObject()
   /**
    * Returns the PolicyManager session object for invoking methods on it.
    * @return policyManager session object which can be used for method invokation
    * @exception throws TTKException
    */
   private ValidationRuleManager getValidationRuleManagerObject() throws TTKException
   {
       ValidationRuleManager validationRuleManager = null;
       try
       {
           if(validationRuleManager == null)
           {
               InitialContext ctx = new InitialContext();
               validationRuleManager = (ValidationRuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/ValidationRuleManagerBean!com.ttk.business.webservice.ValidationRuleManager");
           }//end if(policyManager == null)
       }//end of try
       catch(Exception exp)
       {
           throw new TTKException(exp, "policydetail");
       }//end of catch
       return validationRuleManager;
   }//end getValidationRuleManagerObject()
}//end of RuleQueueMDB
