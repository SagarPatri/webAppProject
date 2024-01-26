/**
 * @ (#) GradingManager.java Sep 15, 2005
 * Project      : 
 * File         : GradingManager.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 15, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M
 * Modified date :  Oct 11, 2005
 * Reason        :  Added methods
 */

package com.ttk.business.empanelment;

import javax.ejb.*;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.GradingVO;
import com.ttk.dto.empanelment.InfraStructureVO;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.onlineforms.OnlineFacilityVO;

import java.util.ArrayList;


@Local
public interface GradingManager {
    
    /**
     * This method adds or updates the grading details   
     * The method also calls other methods on DAO to insert/update the grading details to the database 
     * @param gradingVO GradingVO object which contains the grading details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGrading(GradingVO gradingVO) throws TTKException;
    
    /**
     * This method returns the GradingVO, which contains all the details about the grading details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the details about the grading details
     * @exception throws TTKException 
     */
    public GradingVO getGradingInfo(long lHospSeqId) throws TTKException;
    
    /**
     * This method adds or updates the grading general details   
     * The method also calls other methods on DAO to insert/update the grading general details to the database 
     * @param gradingVO GradingVO object which contains the grading general details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGradingGeneral(GradingVO gradingVO) throws TTKException;
    
    /**
     * This method returns the GradingVO, which contains all the grading general details 
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the grading general details
     * @exception throws TTKException 
     */
    public GradingVO getGradingGeneralInfo(long lHospSeqId) throws TTKException;
    
    /**
     * This method adds or updates the grading infrastructure details   
     * The method also calls other methods on DAO to insert/update the grading infrastructure details to the database 
     * @param infraStructureVO InfraStructureVO object which contains the grading infrastructure details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateInfraStructureDetails(InfraStructureVO infraStructureVO) throws TTKException;
    
    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException 
     */
    public InfraStructureVO getInfraStructureInfo(long lHospSeqId) throws TTKException;
    
    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException 
     */
    public InfraStructureVO getProvInfraStructureInfo(String strUserId) throws TTKException;
    
    
    /**
     * This method adds or updates the grading services details   
     * The method also calls other methods on DAO to insert/update the grading services details to the database 
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(GradingServicesVO gradingServicesVO) throws TTKException;
    
    /**
     * This method adds or updates the grading services details   
     * The method also calls other methods on DAO to insert/update the grading services details to the database 
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(OnlineFacilityVO onlineFacilityVO) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException 
     */
    public ArrayList getServices(String strServiceId,long lHospSeqId) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException 
     */
    public ArrayList getProvServices(String strServiceId,String UserId) throws TTKException;
    
    /**
     * This method adds or updates the grading overriding details   
     * The method also calls other methods on DAO to insert/update the grading overriding details to the database 
     * @param long lHospSeqId which contains the Hospital Sequence Id 
     * @return grade type id  String, contains generated Grade Type Id 
     * @exception throws TTKException
     */
    public String  generateGrade(long lHospSeqId) throws TTKException;
    
 }//end of GradingManager
