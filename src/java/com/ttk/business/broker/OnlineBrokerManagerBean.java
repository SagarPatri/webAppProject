/**
 *   @ (#) BrokerManagerBean.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerManagerBean.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */
package com.ttk.business.broker;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.broker.OnlineBrokerDAOFactory;
import com.ttk.dao.impl.broker.OnlineBrokerDAOImpl;
import com.ttk.dto.brokerlogin.BrokerVO;
import com.ttk.dto.common.CacheObject;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)
public class OnlineBrokerManagerBean implements OnlineBrokerManager {

	 private OnlineBrokerDAOFactory brokerDAOFactory = null;
	    private OnlineBrokerDAOImpl brokerDAO = null;

	    /**
	     * This method returns the instance of the data access object to initiate the required tasks
	     * @param strIdentifier String object identifier for the required data access object
	     * @return BaseDAO object
	     * @exception throws TTKException
	     */
	    private BaseDAO getBrokerDAO(String strIdentifier) throws TTKException
	    {
	        try
	        {
	            if (brokerDAOFactory == null)
	            	brokerDAOFactory = new OnlineBrokerDAOFactory();
	            if(strIdentifier!=null)
	            {
	                return brokerDAOFactory.getDAO(strIdentifier);
	            }//end of if(strIdentifier!=null)
	            else
	                return null;
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "error."+strIdentifier+".dao");
	        }//end of catch(Exception exp)
	    }//End of getBrokerDAO(String strIdentifier)
	
	
	@Override
	public String[] getPolicyDetails(String brokerID)throws TTKException {
		brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
		return brokerDAO.getPolicyDetails(brokerID);
	}
	@Override
	public ArrayList<BrokerVO> getLogDetails(String brokerID) throws TTKException {
		brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
		return brokerDAO.getLogDetails(brokerID);
	}
	
	 @Override
	 public ArrayList<Object> getPolicyList(ArrayList<Object> searchList) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getPolicyList(searchList);
		}
	 @Override
	 public ArrayList<Object> getMemberList(ArrayList<Object> searchList) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getMemberList(searchList);
		}
	 
	 @Override
	 public HashMap<String,String> getSummaryViewDetails(String brokerCode,String groupType,Long groupSeqID,String summaryMode) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getSummaryViewDetails(brokerCode,groupType,groupSeqID,summaryMode);
		}
	 @Override
	 public HashMap<String,Object> getDetailedViewDetails(String enrolmentID,String summaryMode) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getDetailedViewDetails(enrolmentID,summaryMode);
		}
	 @Override
	 public HashMap<String,Object> getClaimDetails(String claimNO) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getClaimDetails(claimNO);
		}
	 @Override
	 public HashMap<String,Object> getTob(Long policySeqID) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getTob(policySeqID);
		}
	 @Override
	 public HashMap<String,Object> getEndorsements(String broCode,String enrolID) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getEndorsements(broCode,enrolID);
		}
	 
	 @Override
	 public HashMap<String,Object> getPreauthDetails(String preAuthNO) throws TTKException{
			brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getPreauthDetails(preAuthNO);
		}
	 public ArrayList<CacheObject> getCorporateList(String brokerCode) throws TTKException{
		 brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getCorporateList(brokerCode);
	 }
	 public ArrayList<CacheObject> getPolicyNumberList(Long corporateSeqID) throws TTKException{
		 brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getPolicyNumberList(corporateSeqID);
	 } 
	 public ArrayList<CacheObject> getPolicyPeriodList(Long policySeqID) throws TTKException{
		 this.getBrokerDAO("broker");
			return brokerDAO.getPolicyPeriodList(policySeqID);
	 }
	 public ArrayList<CacheObject> getINDPolicyNumberList(String brokerCode) throws TTKException{
		 brokerDAO=(OnlineBrokerDAOImpl)this.getBrokerDAO("broker");
			return brokerDAO.getINDPolicyNumberList(brokerCode);
	 }
	 public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException
	{
		 brokerDAO=(OnlineBrokerDAOImpl) this.getBrokerDAO("broker");
		return brokerDAO.getClaimTatData(alInputParams);
	}
	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException
	{
		brokerDAO=(OnlineBrokerDAOImpl) this.getBrokerDAO("broker");
		return brokerDAO.getPreAuthTatData(alInputParams);
	}
}
