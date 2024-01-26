/**
 * @ (#) GradingManagerBean.java Sep 20, 2005
 * Project      :
 * File         : GradingManagerBean.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 20, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M
 * Modified date :  Oct 11, 2005
 * Reason        :  Added methods
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
import com.ttk.dao.impl.empanelment.GradingDAOImpl;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.GradingVO;
import com.ttk.dto.empanelment.InfraStructureVO;
import com.ttk.dto.onlineforms.OnlineFacilityVO;

import javax.ejb.*;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class GradingManagerBean implements GradingManager{

    private EmpanelmentDAOFactory empanelmentDAOFactory = null;
    private GradingDAOImpl gradingDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getEmpanelmentDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if(empanelmentDAOFactory == null)
                empanelmentDAOFactory = new EmpanelmentDAOFactory();

            if(strIdentifier!=null)
            {
                return empanelmentDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end getEmpanelmentDAO(String strIdentifier)

    /**
     * This method adds or updates the grading details
     * The method also calls other methods on DAO to insert/update the grading details to the database
     * @param gradingVO GradingVO object which contains the grading details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGrading(GradingVO gradingVO) throws TTKException
    {
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.addUpdateGrading(gradingVO);
    }//end of addUpdateGrading(GradingVO gradingVO)

    /**
     * This method returns the GradingVO, which contains all the details about the grading details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the details about the grading details
     * @exception throws TTKException
     */
    public GradingVO getGradingInfo(long lHospSeqId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getGradingInfo(lHospSeqId);
    }//end of getGradingInfo(long lHospSeqId)

    /**
     * This method adds or updates the grading general details
     * The method also calls other methods on DAO to insert/update the grading general details to the database
     * @param gradingVO GradingVO object which contains the grading general details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGradingGeneral(GradingVO gradingVO) throws TTKException {
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.addUpdateGradingGeneral(gradingVO);
    }//end of addUpdateGradingGeneral(GradingVO gradingVO)

    /**
     * This method returns the GradingVO, which contains all the grading general details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the grading general details
     * @exception throws TTKException
     */
    public GradingVO getGradingGeneralInfo(long lHospSeqId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getGradingGeneralInfo(lHospSeqId);
    }//end of getGradingGeneralInfo(long lHospSeqId)

    /**
     * This method adds or updates the grading infrastructure details
     * The method also calls other methods on DAO to insert/update the grading infrastructure details to the database
     * @param infraStructureVO InfraStructureVO object which contains the grading infrastructure details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateInfraStructureDetails(InfraStructureVO infraStructureVO) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.addUpdateInfraStructureDetails(infraStructureVO);
    }//end of addUpdateInfraStructureDetails(InfraStructureVO infraStructureVO)

    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException
     */
    public InfraStructureVO getInfraStructureInfo(long lHospSeqId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getInfraStructureInfo(lHospSeqId);
    }//end of getInfrastructureInfo(long lHospSeqId)
    
    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException
     */
    public InfraStructureVO getProvInfraStructureInfo(String strUserId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getProvInfraStructureInfo(strUserId);
    }//end of getInfrastructureInfo(String String)

    
    /**
     * This method adds or updates the grading services details
     * The method also calls other methods on DAO to insert/update the grading services details to the database
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(GradingServicesVO gradingServicesVO) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.addUpdateServices(gradingServicesVO);
    }//end of addUpdateServices(GradingServicesVO gradingServicesVO)
    
    /**added for intx
     * This method adds or updates the grading services details
     * The method also calls other methods on DAO to insert/update the grading services details to the database
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(OnlineFacilityVO onlineFacilityVO) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.addUpdateServices(onlineFacilityVO);
    }//end of addUpdateServices(OnlineFacilityVO onlineFacilityVO)

    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException
     */
    public ArrayList getServices(String strServiceId,long lHospSeqId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getServices(strServiceId,lHospSeqId);
    }//end of getServices(String strServiceId,long lHospSeqId)
    
    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException
     */
    public ArrayList getProvServices(String strServiceId,String UserId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.getProvServices(strServiceId,UserId);
    }//end of getServices(String strServiceId,long lHospSeqId)

    
    /**
     * This method adds or updates the grading overriding details
     * The method also calls other methods on DAO to insert/update the grading overriding details to the database
     * @param long lHospSeqId which contains the Hospital Sequence Id
     * @return grade type id  String, contains generated Grade Type Id
     * @exception throws TTKException
     */
    public String generateGrade(long lHospSeqId) throws TTKException{
        gradingDAO = (GradingDAOImpl)this.getEmpanelmentDAO("grading");
        return gradingDAO.generateGrade(lHospSeqId);
    }//end of generateGrade(long lHospSeqId)

}//end of GradingManagerBean
