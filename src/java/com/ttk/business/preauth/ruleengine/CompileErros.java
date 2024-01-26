package com.ttk.business.preauth.ruleengine;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.dto.webservice.ErrorLogVO;


public class CompileErros
{
    /**
     *  This method process the given rule document and returns the action message where message in not "OK"
     *
     * @param document Document rule.
     * @return alMessage ArrayList containing error message
     */
    public static ArrayList compile(Document document)
    {
        ArrayList<Object> alMessage = new ArrayList<Object>();
        try {
            List listAcion = document.selectNodes("//coverage[@allowed='3' and @selected='YES']/action");
            for(int iAction=0;iAction<listAcion.size();iAction++)
            {
                if(!((Element)listAcion.get(iAction)).valueOf("@message").equals("OK"))
                {
                    alMessage.add(((Element)listAcion.get(iAction)).valueOf("@message"));
                }//end of if(!((Element)listAcion.get(iAction)).valueOf("@message").equals("OK"))
            }//end of for(int iAction=0;iAction<listAcion.size();iAction++)
        }//end of try
        catch (Exception fault) {
            fault.printStackTrace();
        }//end of catch
        return alMessage;
    }//end of compile(Document document)

    /**
     *  This method process the given rule document and returns the action message where message in not "OK"
     *
     * @param document Document rule.
     * @return alMessage ArrayList containing error message
     */
    public static ArrayList<Object> compile(Document document,ErrorLogVO errorLogVO)
    {
        ArrayList<Object> alMessage = new ArrayList<Object>();
        try
        {
            //select the coverages for which Validations has been applied
            List listCoverage = document.selectNodes("//coverage[@allowed='3' and @selected='YES']");
            String strErrorID = null;
            Element eleAction = null;
            ErrorLogVO errorLogVOLoc = null;
            String strMessage = "";
            //boolean bValidRel = true;
            for(int iCovergeCnt=0;iCovergeCnt<listCoverage.size();iCovergeCnt++)
            {
                /*String strAutoSel = ((Element)listCoverage.get(iCovergeCnt)).valueOf("@autoselect");
                bValidRel = true;


                //check for the valid relation if relation code is presenet both in rule XML and VO
                if(!TTKCommon.checkNull(strAutoSel).equals("") && strAutoSel.indexOf("REL=")>=0 && !TTKCommon.checkNull(errorLogVO.getMemberRelation()).equals(""))
                {
                    //if relationship is defined in autoselect, coverate is applicable to that relation only
                    bValidRel = false;
                    String strRel = strAutoSel.substring(strAutoSel.indexOf("REL=")+4,strAutoSel.length());
                    String strRelations[] = strRel.split(",");
                    for(int iRelation=0;iRelation<strRelations.length;iRelation++)
                    {
                        if(strRelations[iRelation].equals(errorLogVO.getMemberRelation()))
                            bValidRel = true;
                    }//end of  for(int iRelation=0;iRelation<strRelations.length;iRelation++)
                }//end of if(strAutoSel.indexOf("REL=")>=0)
                */

                eleAction = ((Element)listCoverage.get(iCovergeCnt)).element("action");
                strErrorID = ((Element)listCoverage.get(iCovergeCnt)).valueOf("@id");
                strMessage = TTKCommon.checkNull(eleAction.valueOf("@Message"));
                if(!strMessage.equals("") && !strMessage.equals("OK"))
                {
                    errorLogVOLoc = new ErrorLogVO();
                    errorLogVOLoc.setErrorLogSeqID(new Long(0));
                    errorLogVOLoc.setOfficeSeqID(errorLogVO.getOfficeSeqID());
                    errorLogVOLoc.setInsSeqID(errorLogVO.getInsSeqID());
                    errorLogVOLoc.setAbbrevationCode(errorLogVO.getAbbrevationCode());
                    errorLogVOLoc.setInsCompCode(errorLogVO.getInsCompCode());
                    errorLogVOLoc.setBatchNbr(errorLogVO.getBatchNbr());
                    errorLogVOLoc.setPolicyNbr(errorLogVO.getPolicyNbr());
                    errorLogVOLoc.setEndorsementNbr(errorLogVO.getEndorsementNbr());
                    errorLogVOLoc.setEmployeeNbr(errorLogVO.getEmployeeNbr());
                    errorLogVOLoc.setPolicyHolder(errorLogVO.getPolicyHolder());
                    errorLogVOLoc.setEnrollmentID(errorLogVO.getEnrollmentID());
                    errorLogVOLoc.setEnrollmentNbr(errorLogVO.getEnrollmentNbr());
                    errorLogVOLoc.setErrorMessage(TTKCommon.replaceSingleQots(eleAction.valueOf("@Message")));
                    errorLogVOLoc.setAddedBy(new Long(1));
                    errorLogVOLoc.setErrorNbr(strErrorID);
                    errorLogVOLoc.setErrorType("SCU"); //Softcopy upload error
                    alMessage.add(errorLogVOLoc);
                }//end of if(!((Element)listAcion.get(iAction)).valueOf("@message").equals("OK"))
            }//end of for(int iAction=0;iAction<listAcion.size();iAction++)
        }//end of try
        catch (Exception fault) {
            fault.printStackTrace();
        }//end of catch
        return alMessage;
    }//end of compile(Document document)
}//end of CompileErros
