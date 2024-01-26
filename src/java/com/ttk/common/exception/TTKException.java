/**
 * @ (#) TTKException.java July 29, 2005
 * Project      :
 * File         : TTKException.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : July 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.exception;

import gnu.inet.ftp.ServerResponseException;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.jms.JMSException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.naming.NamingException;

import jxl.JXLException;
import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;

/**
 * This class is a wrapper class for all the exceptions thrown in the TTK web
 * application. It retrieves the actual exception object and processes it to set
 * the appropriate error message key/code.
 */
public class TTKException extends Exception {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 8802223732478084001L;

	private static Logger log = Logger.getLogger(TTKException.class);
    protected Throwable rootCause = null;
    private String strMessage = null;
    private String strIdentfier = null;
    private String dynamicErrorDesc = "";

	public TTKException() {
        super();
	}// end of constructor TTKException()

	public TTKException(String strMessage, String dynamicErrorDesc) {
        super();
		this.strMessage = strMessage;
		this.dynamicErrorDesc = dynamicErrorDesc;
	}// end of constructor TTKException()

    /**
     * Sets the message code based on the appropriate flow.
     *
	 * @param rootCause
	 *            Throwable the throwable object which contains the actual
	 *            exception
	 * @param strIdentifier
	 *            String an indicator to identify the current flow
     */
	public TTKException(Throwable rootCause, String strIdentifier) {
        log.debug("Inside TTKException....");
        this.rootCause = rootCause;
        this.setIdentfier(strIdentifier);
        rootCause.printStackTrace();
       
        //get the appropriate exception and build the error keys
        //if any SQLException comes,it should be handled in this block.
       if(rootCause instanceof SQLException ){
        String excClassName=rootCause.getClass().getSimpleName();
	      if("PSQLException".equals(excClassName)){ 
       	 this.setMessage(getPGState());
	       }  else if("BatchUpdateException".equals(excClassName)){ 
         	 this.setMessage(getPGState());
	   }        
       }else if (rootCause instanceof NullPointerException) {										// later...
        	this.setMessage("error.nullpointer");
		}// end of else if(rootCause instanceof NullPointerException)
		else if (rootCause instanceof NamingException) {
			this.setMessage("error.naming." + this.getIdentfier());
		}// end of else if(rootCause instanceof NamingException)
		else if (rootCause instanceof FileNotFoundException) {
            this.setMessage("error.file");
		}// end of else if(rootCause instanceof FileNotFoundException)
		else if (rootCause instanceof JMSException) {
        	this.setMessage("error.jms");
		}// end of else if(rootCause instanceof JMSException)
		else if (rootCause instanceof MessagingException) {
			this.setMessage("error.messaging");
		}// end of else if(rootCause instanceof MessagingException)
		else if (rootCause instanceof SendFailedException) {
			this.setMessage("error.sendmailfailed");
		}// end of else if(rootCause instanceof SendFailedException)
		else if (rootCause instanceof UnknownHostException) {
        	this.setMessage("error.unknownhost");
		}// end of else if(rootCause instanceof SendFailedException)
		else if (rootCause instanceof ServerResponseException) {
			this.setMessage("error.serverresponse");
		}// end of else if(rootCause instanceof ServerResponseException)
		else if (rootCause instanceof ConnectException) {
			this.setMessage("error.connect");
		}// end of else if(rootCause instanceof ServerResponseException)
		else if (rootCause instanceof MalformedURLException) {
			this.setMessage("error.hylafaxserver");
		}// end of else if(rootCause instanceof ServerResponseException)
		else if (rootCause instanceof JRException) {
			this.setMessage("error.jrexp");
		}// end of else if(rootCause instanceof JRException)
		else if (rootCause instanceof JXLException) {
			this.setMessage("error.jxlexp");
		}// end of else if(rootCause instanceof JXLException)
		else if (rootCause instanceof TTKException) {
			if ("error.dublicate.crm.code".equals(((TTKException) rootCause)
					.getMessage())) {
				this.setMessage("error.dublicate.crm.code");
				this.setDynamicErrorDesc(((TTKException) rootCause)
						.getDynamicErrorDesc());
			} else if (this.getIdentfier() == null
					|| this.getIdentfier().equals(""))
				this.setMessage("error.general");// set the general message if
													// no appropriate identifier
													// is found
            else
				this.setMessage("error." + this.getIdentfier());
		}// end of else if(rootCause instanceof TTKException)
		else if (rootCause instanceof RuntimeException) {
			if (this.getIdentfier() != null
					|| this.getIdentfier().equals(
							"error.datemismatch.enrollement11"))
                this.setMessage("error.datemismatch.enrollement");
		} else
            this.setMessage("error.general");
	}// end of TTKException(Throwable rootCause, String strIdentifier)

    public TTKException(String message) {
  		// TODO Auto-generated constructor stub
         	super(message);
         	if("error.ibanno".equals(message))
            this.setMessage("error.ibanno");
         	else if("error.enrollment.member.required".equals(message))
         		this.setMessage("error.enrollment.member.required");
         	//else if("error.enrollment.gdrfafilenumber.required".equals(message))
         	//	this.setMessage("error.enrollment.gdrfafilenumber.required");
			else if("error.invalid.memberid".equals(message))
         		this.setMessage("error.invalid.memberid");
     }

    /**
     * Sets the message code based on the appropriate flow
     *
	 * @param strMessage
	 *            String an indicator to identify the appropriate message
     */
	public void setMessage(String strMessage) {
        this.strMessage = strMessage;
	}// end of setMessage(String strMessage)

    /**
     * Gets the message code based on the appropriate flow
     *
     * @return strMessage String the message code
     */
	public String getMessage() {
        return strMessage;
	}// end of getMessage()

    /**
     * Sets the identifier based on the appropriate flow
     *
	 * @param strIdentfier
	 *            String an indicator to identify the appropriate flow
     */
	public void setIdentfier(String strIdentfier) {
        this.strIdentfier = strIdentfier;
	}// end of setIdentfier(String strIdentfier)

    /**
     * Gets the flow identifier
     *
     * @return strIdentfier String the flow identifier
     */
	public String getIdentfier() {
        return strIdentfier;
	}// end of getIdentfier()

    /**
     * Gets the throwable object which contains the actual exception object
     *
     * @return rootCause Throwable the exception object
     */
	public Throwable getRootCause() {
        return rootCause;
	}// end of getRootCause()

    public void setDynamicErrorDesc(String dynamicErrorDesc) {
		this.dynamicErrorDesc = dynamicErrorDesc;
	}

    public String getDynamicErrorDesc() {
		return dynamicErrorDesc;
	}
    private String methodName = "getSQLState";
	private String getPGState() {
		String errorKey = "error.database";
    	
        Method getNameMethod;
        String SQLState="";
		try {
			getNameMethod = rootCause.getClass().getMethod(methodName);
			  SQLState = (String) getNameMethod.invoke(rootCause); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// explicit cast
			 System.out.println("getSQLState================"+SQLState);
				if("V0002".equals(SQLState)){
					errorKey="error.usermanagement.login.userinvalid";
			}
			else if ("V0001".equals(SQLState)) {
				errorKey = "error.usermanagement.login.useraccountlock";
			} 			
			else if ("V0005".equals(SQLState)) {
				errorKey = "error.usermanagement.login.useripaddress";
			} else if ("V0008".equals(SQLState)) {
				errorKey = "error.administration.usermanagement.ttkuser.dtofresgn";
			} else if ("V0009".equals(SQLState)) {
				errorKey = "error.administration.usermanagement.ttkuser.inactivate";
			} else if ("V0010".equals(SQLState)) {
				errorKey = "error.administration.usermanagement.ttkuser.activate";
			} else if ("V0011".equals(SQLState)) {
				errorKey = "error.intx.professionalID";
			} else if ("V0012".equals(SQLState)) {
				errorKey = "error.intx.provider.registeredWithAnother";
			} 
			else if ("V0013".equals(SQLState)) {
				errorKey = "error.hospital.status.renew";
			} 
			else if ("V0014".equals(SQLState)) {
				errorKey = "error.myprofile.changepassword.oldpasswordmismatch";
			} 
			else if ("V0015".equals(SQLState)) {
				errorKey = "error.myprofile.changepassword.identicalpasswords";
			} 
			else if ("V0016".equals(SQLState)) {
				errorKey = "error.administration.usermanagement.ttkuser.activation";
			} 
			
			else if ("V0017".equals(SQLState)) {
				 errorKey = "error.empanelment.insurance.abbreviation";
			} 
			else if ("V0018".equals(SQLState)) {
				errorKey = "error.empanelment.insurance.empdate";

			} else if ("V0020".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.startdate";

			} 
			
			else if ("V0021".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.productenddate";

				}
			else if ("V0022".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.productalreadyassociated";

				}
			else if ("V0023".equals(SQLState)) {
				errorKey = "error.empal.hos.associate";

				}
			else if ("V0024".equals(SQLState)) {
				errorKey = "error.administration.usermanagement.ttkuser.inactivation";

				}
			else if ("V0025".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.startdate";

				}
			else if ("V0026".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.productenddate";

				}
			else if ("V0027".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.productalreadyassociated";

				}else if("23503".equals(SQLState)){
					errorKey="error.references";
				}else if("V0019".equals(SQLState)){
					errorKey="security.add.duplicate";
				}else if("V0024".equals(SQLState)){
					errorKey="error.administration.usermanagement.ttkuser.inactivation";
		        }else if("V0015".equals(SQLState)){
			     errorKey="error.myprofile.changepassword.identicalpasswords";
		       }else if("V0014".equals(SQLState)){
			       errorKey="error.myprofile.changepassword.oldpasswordmismatch";
			} else if ("V0070".equals(SQLState)) {
				errorKey="error.intx.provider.not.registered";
			} else if ("V0071".equals(SQLState)) {
				errorKey="error.intx.provider.not.registered";
			} else if ("V0072".equals(SQLState)) {
				errorKey="error.wrong.bankname";
			} else if ("V0073".equals(SQLState)) {
				errorKey="error.hospital.validation.stopempanel";
			}
			else if ("V0028".equals(SQLState)) {
				errorKey = "error.insurance.productdetail.productstartdate";

			}
			else if ("V0029".equals(SQLState)){
				errorKey = "error.empanelment.groupregn.notifytype";
			}
			else if( "V0040".equals(SQLState))  {
				errorKey="error.dynamic.massage";
			}
				else if("V0038".equals(SQLState)){
					errorKey="error.administration.productrule.rulestartdate";
				}
				else if("V0039".equals(SQLState)){
					errorKey="error.administration.productrule.exists";
				}
				else if("V0250".equals(SQLState)){
					errorKey="error.preauth.preauthdetails.review";
				}
				else if("V0032".equals(SQLState)){
					errorKey="error.administration.product.duplicateprodcode";
				}
				else if("V0034".equals(SQLState)){
					errorKey="error.administration.productdetail.productassociated";
				}
				else if("V0035".equals(SQLState)){
					errorKey="error.administration.productdetail.producthonoured";
				}
		       else if("V0074".equals(SQLState)){
		    	   errorKey="error.empanelment.validation.empaneldate";
				 } else if("V0075".equals(SQLState)){
					 errorKey="error.hospital.validation.validateddate";
				 }else if("V0076".equals(SQLState)){
					 errorKey="error.hospital.validation.markeddate";
				 }else if("V0077".equals(SQLState)){
					 errorKey="error.empanelment.status.tdsprocessing";
				 }else if("V0078".equals(SQLState)){
					 errorKey="error.hospital.status.renew";
				 }
					 else if("V0079".equals(SQLState)){
						 errorKey="error.hospital.status.contact";
				 }else if("V0080".equals(SQLState)){
					 errorKey="error.hospital.status.validation";
				 }else if("V0081".equals(SQLState)){
					 errorKey="error.hospital.status.payorder";
				 }else if("V0082".equals(SQLState)){
					 errorKey="error.hospital.status.account";
				 }else if("V0083".equals(SQLState)){
					 errorKey="error.intx.EmpanelFinanceReview"; 
				 }else if("V0084".equals(SQLState)){
					 errorKey="error.hospital.status.empanel";
				 }else if("V0085".equals(SQLState)){
					 errorKey="error.hospital.status.changestatus";
				 }else if("V0086".equals(SQLState)){
					 errorKey="error.hospital.status.disempanel";
				 }else if("V0088".equals(SQLState)){
					 errorKey="error.intx.Online.medical.code"; 
				 }else if("V0087".equals(SQLState)){
					 errorKey="error.hospital.grading.general";
				 }else if("V0089".equals(SQLState)){
					 errorKey="error.intx.professionalID";
				 }else if("V0090".equals(SQLState)){
					 errorKey="error.intx.professionalID.Duplicate";
				 }else if("V0091".equals(SQLState)){
					 errorKey="error.intx.provider.registeredWithAnother";
				 }
				 else if("V0089".equals(SQLState)){
					 errorKey="error.intx.professionalID";
				 }else if("V0090".equals(SQLState)){
					 errorKey="error.intx.professionalID.Duplicate";
				 }else if("V0091".equals(SQLState)){
					 errorKey="error.intx.provider.registeredWithAnother";
				 }else if("V0092".equals(SQLState)){
					 errorKey="error.date.validation";
				 }else if("V0093".equals(SQLState)){
					 errorKey="error.duplicatate.records";
				 }else if("V0094".equals(SQLState)){
					 errorKey="error.ProRataPremium.duplicate";
				 }
				else if("V0052".equals(SQLState)){
	                errorKey="error.usermanagement.role.changeusertype";
				}
				else if("V0253".equals(SQLState)){
	                errorKey="error.Premium.config.overlap";
				}
				else if("V0254".equals(SQLState)){
	                errorKey="error.preauth.claimexist";
				}
				else if("V0255".equals(SQLState)){
	                errorKey="error.cancel.rulecannotmodified";
				}
				else if("V0256".equals(SQLState)){
	                errorKey="error.inwardentry.batchpolicy.uncompleted";
				}
				else if("V0033".equals(SQLState)){
					errorKey="error.admin.product.general";
				}
				else if("V0036".equals(SQLState)){
					errorKey="error.rule.alreadyadded";
				 }
				else if("V0037".equals(SQLState)){
					errorKey="error.invalidbenefittype";
				 }
				 else if("V0096".equals(SQLState)){
					 errorKey="error.inwardentry.batchdetails.changeinsurancecompnay";
				 }
				 else if("V0097".equals(SQLState)){
					 errorKey="error.inwardentry.batchpolicy.completed";
				 }
				 else if("V0098".equals(SQLState)){
					 errorKey="error.enrollment.policydetails.productname";
				 }
				 else if("V0099".equals(SQLState)){
					 errorKey="error.inwardentry.policydetails.policyinfo";
				 }
				 else if("V0100".equals(SQLState)){
					 errorKey="error.inwardEntry.enrollment.batchscheme";
				 }
				 else if("V0101".equals(SQLState)){
					 errorKey="error.cancsuspawaitrenewal.modificationrestriction";
				 }
				 else if("V0102".equals(SQLState)){
					 errorKey="error.selectrenewal.forsameproduct";
				 }
				 else if("V0103".equals(SQLState)){
					 errorKey="error.enrollment.policydetails.ProdDOBOchange";
				 }
				 else if("V0106".equals(SQLState)){
					 errorKey="error.policy.policydetails.productchange";
				 }
				 else if("V0107".equals(SQLState)){
					 errorKey="error.inwardentry.policydetails.policytype";
				 }
				 else if("V0108".equals(SQLState)){
					 errorKey="error.enrollment.policydetails.DOBOchange";
				 }
				 else if("V0109".equals(SQLState)){
					 errorKey="error.enrollment.policydetails.RenewDOBOchange";
				 }
				 else if("V0110".equals(SQLState)){
					 errorKey="error.inwardentry.policydetails.policy";
				 }
				 else if("V0111".equals(SQLState)){
					 errorKey="error.inwardentry.policydetails.endorsement";
				 }
				 else if("V0113".equals(SQLState)){
					 errorKey="error.inwardentry.batchpolicy.policycategorysuffix";
				 }
				 else if("V0114".equals(SQLState)){
					 errorKey="enrollment.add.duplicate";
				 }
				 else if("V0115".equals(SQLState)){
					 errorKey="error.webservice.policyexists";
				 }
				 else if("V0116".equals(SQLState)){
					 errorKey="error.inwardentry.batchpolicies.policytype";
				 }
				 else if("V0117".equals(SQLState)){
					 errorKey="error.enrollment.group.change";
				 }
				 else if("V0118".equals(SQLState)){
					 errorKey="error.inwardentry.batchpolicy.uncompleted";
				 }
				else if("V0259".equals(SQLState)){
					errorKey="error.intx.activityAlreadyExist";
				}else if("V0251".equals(SQLState)){
					errorKey="error.cardrule.modificationrestriction";
				}
			/*	 else if ("V0179".equals(SQLState)) {
					 errorKey="error.endorsement.members.edit";		
		         }*/
				 else if ("V0186".equals(SQLState)) {
					 errorKey="error.enrollment.search.delete";		
		         }
				 else if ("V0187".equals(SQLState)) {
		                errorKey="error.endorsement.search.deletepartialendorsement";		
		         }
				 else if ("V0188".equals(SQLState)) {
					 errorKey="error.endorsement.search.delete";		
		         }
				 else if ("V0189".equals(SQLState)) {
					 errorKey="error.endorsement.members.adddelete";		
		         }
				 else if ("V0190".equals(SQLState)) {
					 errorKey="error.endorsement.members.edit";		
		         }
			/*	 else if ("V0191".equals(SQLState)) {
					 errorKey="error.enrollment.premium.defaultsuminsured";		
		         }*/
				/*	else if ("V0192".equals(SQLState)) {
				errorKey = "error.duplicate.emirate.id";
			}*/
				/*else if ("V0194".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium";//duplicate
      		}*/
				 else if ("V0193".equals(SQLState)) {
					 errorKey="error.enrollment.members.suminsured";		
		         }
				/* else if ("V0195".equals(SQLState)) {
					 errorKey="error.endorsement.search.deletepartialendorsement";		
		         }*/
				 else if ("V0196".equals(SQLState)) {
					 errorKey="error.endorsement.member.addpremium";		
		         }
				 else if("V0282".equals(SQLState)){
		                errorKey="error.admin.insurance.Aprover.closed";
				 }
				 else if("V0283".equals(SQLState)){
					 errorKey="error.intx.tariffGrossDiscountAmount";
				 }
				 else if("V0284".equals(SQLState)){
		                errorKey="error.suspension";
				 }
				 else if("V0285".equals(SQLState)){
					 errorKey="error.intx.activityAlreadyExist"; 
				 }
				 else if("V0286".equals(SQLState)){
		                errorKey="error.administration.policy.webconfiglinks";
				 }
				 else if("V0287".equals(SQLState)){
		                errorKey="error.administration.policy.webconfiglinksimage";
				 }
				 else if("V0288".equals(SQLState)){
		                errorKey="error.administration.policies.buffer.allocation";
				 }
				 else if("V0289".equals(SQLState)){
		                errorKey="error.administration.policy.bufferzero";
				 }
				 else if("V0290".equals(SQLState)){
		                errorKey="error.administration.policy.bufferdetails";
				 }
				 else if("V0291".equals(SQLState)){
		                errorKey="error.administration.domamount";
				 }
				 else if("V0292".equals(SQLState)){
		                errorKey="error.cancel.rulecannotmodified";
				 }
				 else if("V0293".equals(SQLState)){
					    errorKey="error.preauth.claimexist";
				 }
				 else if("V0315".equals(SQLState)){
					 errorKey="error.hospital.grading.general";
				 }
				 else if("V0095".equals(SQLState)){
		                errorKey="error.admin.hospital.associated";
				 }
				 else if("V0353".equals(SQLState)){
					 errorKey="error.provider.oldLicense.history.duplicate";
				 }
				 else if("V0354".equals(SQLState)){
					 errorKey="error.intx.PreEmpanelment.Credentials";
				 }
		
			
			else if ("V0041".equals(SQLState)) {
				errorKey = "error.empanelment.groupregistration.changegrouptype";
			}
			else if ("V0043".equals(SQLState)) {
				errorKey = "error.empanelment.insurance.empdate";
			}
			else if ("V0044".equals(SQLState)) {
				errorKey = "error.wrong.bankname";
			}
			else if ("V0045".equals(SQLState)) {
				errorKey = "error.swiftCode.Mandatory";
			}
			else if ("V0047".equals(SQLState)) {
				errorKey = "error.hospital.status.renew";
			}
			else if ("V0048".equals(SQLState)) {
				errorKey = "error.hospital.status.contact";
			}
			else if ("V0049".equals(SQLState)) {
				errorKey = "error.partnerNotEmpnaled.validation";
			}
			else if ("V0050".equals(SQLState)) {
				errorKey = "error.partner.accountsRequired";
			}
			else if ("V0051".equals(SQLState)) {
				errorKey = "error.intx.PartnerEmpanelFinanceReview";
			}
			else if ("V0052".equals(SQLState)) {
				errorKey = "error.partner.status.for";
			}
			else if ("V0053".equals(SQLState)) {
				errorKey = "error.partner.disempaneled";
			}
			else if("V0054".equals(SQLState)){
				errorKey="broker.add.duplicate";
			}
			else if("V0055".equals(SQLState)){
				errorKey="error.empanelment.partner.partnerSave";
			}
			
			else if ("23503".equals(SQLState)) {
				errorKey = "message.savedSuccessfully";
			}
			else if ("V0056".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.details";
			}
			else if ("V0057".equals(SQLState)) {
				errorKey = "error.webservice.policyexists";
			}
			else if ("V0058".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
			}
			else if ("V0059".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium";
			}
			else if ("V0060".equals(SQLState)) {
				errorKey = "error.endorsement.members.edit";
			}
			else if ("V0151".equals(SQLState)) {
				errorKey = "error.policy.restrictmodification";
			}
			else if ("V0152".equals(SQLState)) {
				errorKey = "error.enrollment.domiciliary.domiciliarytype";
			}
			else if ("V0153".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.policystartdateproduct";
			}
			else if ("V0154".equals(SQLState)) {
				errorKey = "error.claims.claimdetails.reassociateenrolid";
			}
			else if ("V0155".equals(SQLState)) {
				errorKey = "error.preauthclaim.reassociate";
			}
			else if ("V0156".equals(SQLState)) {
				errorKey = "error.claims.settlement.policynbrchange";
			}
			else if ("V0157".equals(SQLState)) {
				errorKey = "error.claims.settlement.policyperiod";
			}
			else if ("V0158".equals(SQLState)) {
				errorKey = "error.claims.settlement.associateenrollmentid";
			}
			else if ("V0159".equals(SQLState)) {
				errorKey = "error.preauth.authsettle.cancelmember";
			}
			else if ("V0160".equals(SQLState)) {
				errorKey = "error.inwardentry.policydetails.policyinfo";
			}
			else if ("V0161".equals(SQLState)) {
				errorKey = "error.policy.datemismatch";
			}
			else if ("V0162".equals(SQLState)) {
				errorKey = "error.renewalpolicyperiod.modificationrestriction";
			}
			else if ("V0163".equals(SQLState)) {
				errorKey = "error.renewalpol.activeneforeexppolicy";
			}
			else if ("V0164".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.renewvalidityperiod";
			}
			else if ("V0165".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.policysubtype";
			}
			else if ("V0166".equals(SQLState)) {
				errorKey = "error.enrollment.policydetail.termstatuschange";
			}
			else if ("V0167".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.renewvalidityperiod";
			}
			else if ("V0168".equals(SQLState)) {
				errorKey = "error.enrollment.group.change";
			}
			else if ("V0169".equals(SQLState)) {
				errorKey = "error.endorsement.policydetails.policycancelled";
			}else if ("V0170".equals(SQLState)) {
				errorKey = "error.member.deleterestriction";
			}
			else if ("V0171".equals(SQLState)) {
				errorKey = "error.endorsement.members.edit";
			}
			else if ("V0172".equals(SQLState)) {
				errorKey = "error.member.deleterestriction";
			}
			else if ("V0173".equals(SQLState)) {
				errorKey = "error.webservice.dupempnbr";
			}
			else if ("V0174".equals(SQLState)) {
				errorKey = "error.endorsement.policydetails.policycancelled";
			}
			else if ("V00175".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
			}else if ("V0176".equals(SQLState)) {
				errorKey = "error.webservice.dupcertificatenbr";
			}
			else if ("V0177".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium";
			}
	/*		else if ("V0178".equals(SQLState)) {
				errorKey = "error.claims.settlement.save";
			}*/
			else if ("V0179".equals(SQLState)) {
				errorKey = "error.endorsement.members.edit";
			}
			else if ("V0180".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.renewalpolicy";
			}
			else if ("V0181".equals(SQLState)) {
				errorKey = "error.member.deleterestriction";
			}
			else if ("V0182".equals(SQLState)) {
				errorKey = "error.policy.restrictmodification";
			}
			else if ("V0183".equals(SQLState)) {
				errorKey = "error.enrollment.broker.no.user";
			}
			else if ("V0184".equals(SQLState)) {
				errorKey = "error.enrollment.broDetail.del.not.allowed";
			}
				
			else if ("V0185".equals(SQLState)) {
				errorKey = "error.rules.not.completed";
			}
			else if ("V2000".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
			}
			/*else if ("V0186".equals(SQLState)) {
				errorKey = "error.productsynchronize.notpolicyruleconfiguration";
			}*/
    		else if ("V0187".equals(SQLState)) {
			    errorKey = "error.webservice.policydomicilaryamount";
	    	}
			else if ("V0188".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
			}
    		else if ("V0189".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium";
			}
    		else if ("V0190".equals(SQLState)) {
				errorKey = "error.endorsement.members.edit";
			}
    		else if ("V0191".equals(SQLState)) {
    			errorKey = "error.webservice.dupcustomercode";
    		}
    		else if ("V0192".equals(SQLState)) {
				 errorKey="error.endorsement.members.edit";		
	         }
    	/*	else if ("V0193".equals(SQLState)) {
				errorKey = "error.duplicate.globalnet.member.Id";
      		}*/
    		 else if ("V0194".equals(SQLState)) {
				 errorKey="error.inwardentry.batchComplete.delete";	
	         }
    		else if ("V0195".equals(SQLState)) {
				errorKey = "error.enrollment.addmember.dateofmarriage";
      		}
    		else if ("V0196".equals(SQLState)) {
				errorKey = "error.endorsement.policydetails.policycancelled";
      		}
    		else if ("V0197".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
      		}
    		else if ("V0198".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.period";
      		}
		    else if ("V0199".equals(SQLState)) {
				errorKey = "error.member.deleterestriction";
      		}
      		else if ("V0200".equals(SQLState)) {
				errorKey = "error.policy.restrictmodification";
      		}
      		else if ("V0201".equals(SQLState)) {
				errorKey = "error.invalid.memberid";
      		}
      		else if ("V0202".equals(SQLState)) {
				errorKey = "error.claim.general.curencyrate";
      		}
      		else if ("V0203".equals(SQLState)) {
				errorKey = "error.preauth.preauthdetails.review";
      		}
      		else if ("V0204".equals(SQLState)) {
				errorKey = "patient.covered.member";
      		}
      		else if ("V0205".equals(SQLState)) {
				errorKey = "services.performed.prior.effective";
      		}
      		else if ("V0206".equals(SQLState)) {
				errorKey = "error.services.performed.after.last";
      		}
      		else if ("V0207".equals(SQLState)) {
				errorKey = "clinician.not.exist";
      		}
      		else if ("V0208".equals(SQLState)) {
				errorKey = "clinician.already.exist";
      		}
      		else if ("V0209".equals(SQLState)) {
				errorKey = "duplicate.preauth.request";
      		}
      		else if ("V0210".equals(SQLState)) {
				errorKey = "error.endors.enhancement.underprocess";
      		}
      		else if ("V0211".equals(SQLState)) {
				errorKey = "enhancement.date.not.lessthan.original.date";
      		}
      		else if ("V0212".equals(SQLState)) {
				errorKey = "please.complete.parent.preauth.first";
      		}
      		else if ("V0213".equals(SQLState)) {
				errorKey = "error.preauth.preauthdetails.review";
      		}
      		else if ("V0214".equals(SQLState)) {
				errorKey = "primary.diagnosis.already.exists";
      		}
      		else if ("V0215".equals(SQLState)) {
				errorKey = "add.atleast.one.diagnosis";
      		}
      		else if ("V0216".equals(SQLState)) {
				errorKey = "diagnosis.code.already.exists";
      		}
      		else if ("V0217".equals(SQLState)) {
				errorKey = "you.cannot.delete.diagnosis.details";
      		}
      		else if ("V0218".equals(SQLState)) {
				errorKey = "error.claims.rules.not.configured";
      		}
      		/*else if ("V0219".equals(SQLState)) {
				errorKey = "error.claims.total.amount.should.not.exceed";
      		}*/
      		else if ("V0220".equals(SQLState)) {
				errorKey = "error.preauth.settlement.sufficientbalance";
      		}
      		else if ("V0221".equals(SQLState)) {
				errorKey = "error.preauth.general.uploadToDhpo";
      		}	
//      		else if ("V0501".equals(SQLState)) {
//				errorKey = "errors.member.save";
//      		}
      		else if ("V0502".equals(SQLState)) {
				errorKey = "error.endorsement.members.add";
      		}
      		else if ("V0503".equals(SQLState)) {
				errorKey = "error.endorsement.member.dateofinception";
      		}
      		else if ("V0504".equals(SQLState)) {
				errorKey = "error.weblogin.createself";
      		}
      		else if ("V0505".equals(SQLState)) {
				errorKey = "error.dependentinspdate.afterselfinspdate";
      		}
      		else if ("V0506".equals(SQLState)) {
				errorKey = "error.enrollment.members.relationship";
      		}
      		else if ("V0507".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.deletesuminsured";
      		}
      		else if ("V0508".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium";
      		}
      		else if ("V0509".equals(SQLState)) {
				errorKey = "error.enrollment.member.delete";
      		}
      		else if ("V0510".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.paclaim";
      		}
      		else if ("V0511".equals(SQLState)) {
				errorKey = "error.enrollment.members.cancelmember";
      		}
      		else if ("V0512".equals(SQLState)) {
				errorKey = "error.endorsement.member.addpremium"; //need to check...
      		}
      		else if ("V0513".equals(SQLState)) {
				errorKey = "error.enrollment.member.policycancellation";
      		}
      		else if ("V0514".equals(SQLState)) {
				errorKey = "error.expdelcanc.awaitedrenewal";
      		}
      		else if ("V0515".equals(SQLState)) {
				errorKey = "error.endorsement.members.deletelimit";
      		}
      		else if ("V0516".equals(SQLState)) {
				errorKey = "error.endorsement.members.edit";
			}
			else if ("V0517".equals(SQLState)) {
				errorKey = "error.enrollment.members.cancelmember";
			}
			else if ("V0518".equals(SQLState)) {
				errorKey = "error.invalid.emirateid";
			}
			else if ("V0519".equals(SQLState)) {
				errorKey = "error.enrollment.broDetail.del.not.allowed";
			}
			else if ("V0520".equals(SQLState)) {
				errorKey = "error.enrl.renwal.not.allowed";
			}
			else if ("V0521".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.review";
			}
			else if ("V0522".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.finalconfirm";
			}
			else if ("V0523".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.finalconfirmpremium";
			}
			else if ("V0524".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.reviewmemberinfo";
			}
			else if ("V0525".equals(SQLState)) {
				errorKey = "error.endorsement.policydetails.endorsementnotcomplete";
			}
			else if ("V0526".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.renewal";
			}
			else if ("V0527".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.suminsured";
			}
			else if ("V0528".equals(SQLState)) {
				errorKey = "error.preauth.shortfall.reminderdate";
			}
			else if ("V0529".equals(SQLState)) {
				errorKey = "close.opened.shortfall.before.raise";
			}
			else if ("V0530".equals(SQLState)) {
				errorKey = "error.preauth.preauthdetails.review";
			}
			else if ("V0531".equals(SQLState)) {
				errorKey = "error.preauth.shortfall.reminder";
			}
			else if ("V0532".equals(SQLState)) {
				errorKey = "you.cannot.delete.shortfall.details";
			}
			else if ("V0533".equals(SQLState)) {
				errorKey = "you.cannot.delete.observation.claims.status.progress";
			}
			else if ("V0534".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.suminsuredpremium";
			}
			else if ("V0535".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.premiumgreaterthanplan";
			}
			else if ("V0536".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.polmemcancel";
			}
			else if ("V0537".equals(SQLState)) {
				errorKey = "error.enrollment.members.effectivedate";
			}
			else if ("V0538".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.defautlsuminsured";
			}
			else if ("V0539".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.suminsuredplan";
			}
			else if ("V0540".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.additionalsuminsured";
			}			
			else if ("V0541".equals(SQLState)) {
				errorKey = "error.enrollment.members.bonuslimit";
			}
			else if("V0310".equals(SQLState)){
				errorKey="error.preauth.claims.max.copay.suminsured.restricted.amount";
			}
			else if("V0305".equals(SQLState)){
				errorKey="error.finance.bankaccount.tdsprocessing";
			}
			else if("V0306".equals(SQLState)){
				errorKey="error.finance.bankaccount.bankbalanceexists";
			}
			else if("V0307".equals(SQLState)){
				errorKey="error.finance.bankaccount.bankaccountcloseddate";
			}
			else if("V0308".equals(SQLState)){
				errorKey="error.finance.floataccount.closeddate";
			}
			else if("V0309".equals(SQLState)){
				errorKey="error.finance.bankaccount.changebank";
			}
			else if("V0302".equals(SQLState)){
				errorKey="error.finance.bankaccount.chequenumber";
			}
			else if("V0303".equals(SQLState)){
				errorKey="error.finance.bankaccount.chequeseries";
			}
			else if("V0304".equals(SQLState)){
				errorKey="error.finance.bankaccount.issuedChequeUpdate";
			}
			else if("V0300".equals(SQLState)){
				errorKey="error.finance.bankaccount.trandate";
			}
			else if("V0301".equals(SQLState)){
				errorKey="error.finance.transaction.withdrawn";
			}
			else if("V0311".equals(SQLState)){
				errorKey="error.finance.banks.bankslist";
			}
			else if ("V0312".equals(SQLState)){
                errorKey="error.finance.account.delete";
			}
			else if ("V0313".equals(SQLState)){
				errorKey="error.finance.bankaccount.search";
			}
			else if ("V0314".equals(SQLState)){
				errorKey="error.finance.bankaccount.chequeseries.delete";
			}
			else if ("V0317".equals(SQLState)){
				errorKey="error.finance.floataccount.closedbankaccount";
			}
			else if ("V0318".equals(SQLState)){
				errorKey="error.finance.floataccount.changeaccount";
			}
			else if ("V0319".equals(SQLState)){
				errorKey="error.finance.floataccount.floatbalanceexists";
			}
			else if ("V0320".equals(SQLState)){
				errorKey="error.finance.floataccount.closeddate";
			}
			else if ("V0321".equals(SQLState)){
				errorKey="error.finance.bankaccount.bankaccountcloseddate";
			}
			else if ("V0322".equals(SQLState)){
				errorKey="error.finance.account.delete";
			}
			else if ("V0323".equals(SQLState)){
				errorKey="error.finance.floataccount.deletegroup";
			}
			else if ("V0324".equals(SQLState)){
				errorKey="error.finance.floataccount.associategroup";
			}
			else if ("V0325".equals(SQLState)){
				errorKey="error.floataccount.debitnote.final";
			}
			else if ("V0326".equals(SQLState)){
				errorKey="error.floataccount.debitnote.debittransaction";
			}
			else if ("V0327".equals(SQLState)){
				errorKey="error.finance.floataccount.trandate";
			}
			else if ("V0328".equals(SQLState)){
				errorKey="error.productsynchronize.notpolicyruleconfiguration";
			}
			else if ("V0329".equals(SQLState)){
				errorKey="error.finance.floataccount.floatbalance";
			}
			else if ("V0330".equals(SQLState)){
				errorKey="error.finance.floataccount.transaction";
			}
			else if ("V0331".equals(SQLState)){
				errorKey="error.finance.floataccount.floatnegativebalance";
			}
			else if ("V0332".equals(SQLState)){
				errorKey="error.finance.transaction.reverse";
			}
			else if ("V0333".equals(SQLState)){
				errorKey="error.finance.floataccount.closedbankaccount";
			}
			else if ("V0334".equals(SQLState)){
				errorKey="error.selffund.validateEnrollID";
			}
			else if ("V0335".equals(SQLState) || "V0338".equals(SQLState) ){
				errorKey="error.finance.chequeprint";
			}
			else if ("V0336".equals(SQLState) || "V0339".equals(SQLState)){
				errorKey="error.finance.floataccount.paidclaims";
			}
			else if ("V0337".equals(SQLState)){
				errorKey="error.finance.floataccount.bank.advicepayment";
			}
			/*else if ("V0338".equals(SQLState)){
				errorKey="error.finance.bankaccount.chequeseries";
			}
			else if ("V0339".equals(SQLState)){
				errorKey="error.finance.bankaccount.chequeseries";
			}*/
			else if ("V0340".equals(SQLState)){
				errorKey="error.finance.chequeinformation.date.greater.or.less.systemdate";
			}
			else if ("V0341".equals(SQLState)){
				errorKey="error.finance.chequeinformation.date";
			}
			else if ("V0342".equals(SQLState)){
				errorKey="error.finance.chequeinformation.chequestatus";
			}
			else if ("V0344".equals(SQLState) || "V0349".equals(SQLState)){
				errorKey="error.finance.chequeinformation.stalecheque";
			}
			else if ("V0345".equals(SQLState) || "V0350".equals(SQLState)){
				errorKey="error.finance.chequeinformation.voidcheque";
			}
			else if ("V0346".equals(SQLState) || "V0351".equals(SQLState)){
				errorKey="error.finance.chequeinformation.clearedcheque";
			}
			else if ("V0347".equals(SQLState)){
				errorKey="error.finance.bankaccount.user.add.duplicate";
			}
			else if ("V0348".equals(SQLState)){
				errorKey="error.finance.chequeinformation.chequestatus";
			}
			else if ("V0352".equals(SQLState)){
				errorKey="error.finance.chequeinformation.date.greater.or.less.systemdate";
			}
			else if ("V0362".equals(SQLState)){
				errorKey="error.finance.bankaccount.banknegativebalance";
			}
			else if ("V0363".equals(SQLState)){
				errorKey="error.finance.transaction.reverse";
			}
			else if ("V0364".equals(SQLState)){
				errorKey="error.finance.floataccount.closedbankaccount";
			}
			else if ("V0365".equals(SQLState)){
				errorKey="error.finance.chequeprint";
			}
			else if ("V0366".equals(SQLState)){
				errorKey="error.finance.floataccount.paidclaims";
			}
			else if ("V0367".equals(SQLState)){
				errorKey="error.finance.floataccount.chequenumber";
			}
			else if ("V0368".equals(SQLState)){
				errorKey="error.finance.floataccount.Payments";
			}
			else if ("V0369".equals(SQLState)){
				errorKey="error.finance.floataccount.cheque";
			}
			else if ("V0222".equals(SQLState)) {
				errorKey = "you.cannot.delete.activity.claim.status.progress";
			}
			else if ("V0542".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.addmodpolicyinfo";
			}
			else if ("V0543".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.cancelmember";
			}
			else if ("V0544".equals(SQLState)) {
				errorKey = "error.endors.effective.date";
			}
			else if ("V0545".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.changeinfo";
			}
			else if ("V0546".equals(SQLState)) {
				errorKey = "error.endorsement.completed";
			}
			else if ("V0547".equals(SQLState)) {
				errorKey = "error.endorsement.endorsement.keyfieldchange";
			}
			else if ("V0548".equals(SQLState)) {
				errorKey = "error.enrollment.member.reducedsuminsured";
			}
			else if ("V0549".equals(SQLState)) {
				errorKey = "error.enrollment.members.approvecardprint";
			}
			else if ("V0550".equals(SQLState)) {
				errorKey = "error.enrollment.members.cardprintnotallowed";
			}
			else if ("V0567".equals(SQLState)) {
				errorKey = "error.productsynchronize.notpolicyruleconfiguration";
			}
			else if ("V0175".equals(SQLState)) {
				errorKey = "error.endorsement.members.adddelete";
			}
			else if ("V0568".equals(SQLState)) {
				errorKey = "error.duplicate.emirate.id";
			}
			else if ("V0569".equals(SQLState) ) {
				errorKey = "error.duplicate.globalnet.member.Id";
			}
			else if ("V0570".equals(SQLState)) {
				errorKey = "All Fields are Mandatory";
			}
				
			else if ("V0355".equals(SQLState)) {
				errorKey = "error.weblogin.policyno";
			}
			else if ("V0356".equals(SQLState)) {
				errorKey = "error.weblogin.enrollmentid";
			}
			else if ("V0357".equals(SQLState)) {
				errorKey = "error.usermanagement.login.useraccountlock";
			}
			else if ("V0358".equals(SQLState)) {
				errorKey = "error.usermanagement.login.userinvalid";
			}
			else if ("V0359".equals(SQLState)) {
				errorKey = "error.usermanagement.onlineaccess.citbanklogin";
			}
			else if ("V0360".equals(SQLState)) {
				errorKey = "error.usermanagement.login.empaneluserinvalid";
			}
			else if ("V0361".equals(SQLState)) {
				errorKey = "error.usermanagement.login.invalidlogin";
			}
			else if ("V0370".equals(SQLState)) {
				errorKey = "error.finance.floataccount.paymentspaymentadviceassociateclaim.uploadclaimsettlementnumber";
			}
			else if ("V0371".equals(SQLState)) {
				errorKey = "error.invalid.memberid.and.emirateid";
			}
			else if ("V0372".equals(SQLState)) {
				errorKey = "error.invalid.memberid";
			}
			else if ("V0373".equals(SQLState)) {
				errorKey = "error.invalid.emirateid";
			}
			else if ("V0374".equals(SQLState)) {
				errorKey = "error.intx.ActiveVidalId";
			}
			else if ("V0375".equals(SQLState)) {
				errorKey = "error.policy.inactive";
			}
			else if ("V0376".equals(SQLState)) {
				errorKey = "error.dateofAdimissionnotbetween.policyperiod";
			}
			else if ("V0377".equals(SQLState)) {
				errorKey = "error.members.networkType.eligible";
			}
			
			else if ("V0223".equals(SQLState)){
                errorKey="error.finance.floataccount.transaction";
			}
			else if ("V0224".equals(SQLState)) {
				errorKey="error.preauth.network.not.matching";
			}
			else if ("V0225".equals(SQLState)){
				  errorKey="error.check.auth.benefit.type.not.matching";
			}
			else if ("V0226".equals(SQLState)){
				  errorKey="error.check.auth.maternity.gpla.values.not.matching";
			}
			else if ("V0227".equals(SQLState)){
                  errorKey="error.inwardentry.claimsdetails.ammendment";
			}
			else if ("V0228".equals(SQLState)){
                  errorKey="error.inwardentry.claimdetails.ammendment";
                  
			}
			else if ("V0229".equals(SQLState)){
				  errorKey="error.inwardentry.claimsdetails.ammendmentbill";
			}
			else if ("V0230".equals(SQLState)){
				  errorKey="error.entered.Claims.count.shouldbe.equal";
			}
			else if ("V0231".equals(SQLState)){
				  errorKey="error.claims.total.amount.should.not.exceed";
			}
			else if ("V0549".equals(SQLState)) {
                errorKey = "error.enrollment.members.approvecardprint";
            }
            else if ("V0550".equals(SQLState)) {
                errorKey = "error.enrollment.members.cardprintnotallowed";
            }
            else if	("V0294".equals(SQLState)){
				errorKey="error.claims.rules.not.configured";
            }
            else if("V0297".equals(SQLState)){
				errorKey="error.primary.icd.chronic";
            }
            else if("V0298".equals(SQLState)){
				errorKey="error.per.one.medical.condition";
            }            
            else if("V0234".equals(SQLState)){
				errorKey="error.calculate.before.save";
            }
            else if("V0235".equals(SQLState)){
				errorKey="error.provider.not.empanelled";
            }
            else if("V0219".equals(SQLState)){
            	errorKey="close.opened.shortfall.before.complete";
            }
            else if("V0233".equals(SQLState)){
				errorKey="error.claim.presents.finance.cannot.override";
            }
            else if("V0236".equals(SQLState)){
            	errorKey="subbatch.auditstatus.checked.cannotoverride";
            }
            else if("V0237".equals(SQLState)){
				errorKey="error.preauth.preauthdetails.overideclaim";
            }
            else if ("V0580".equals(SQLState)) {
                errorKey = "error.endorsement.members.edit";
            } 
            else if ("V0238".equals(SQLState)) {
                errorKey = "error.claims.settlement.enterhospitaladdress";
            } 
            else if ("V0239".equals(SQLState)) {
                errorKey = "error.claims.settlement.correctpayeeaddress";
            } 
            else if ("V0240".equals(SQLState)) {
                errorKey = "error.claims.settlement.correctcorporateaddress";
            } 
				
			else if ("V0571".equals(SQLState)) {
				errorKey = "error.dependentinspdate.afterselfinspdate";
			}
			else if ("V0572".equals(SQLState)) {
				errorKey = "error.expdeleteawaited.modificationrestriction";
			}
			else if ("V0573".equals(SQLState)) {
				errorKey = "error.preauth.claimexist";
			}
			else if ("V0574".equals(SQLState)) {
				errorKey = "error.awaitedreneal.modificationrestriction";
			}
			else if ("V0575".equals(SQLState)) {
				errorKey = "error.inwardentry.batchpolicy.completed";
			}
			else if ("V0576".equals(SQLState)) {
				errorKey = "error.enrollment.policydetails.productname";
			}
			else if ("V0577".equals(SQLState)) {
				errorKey = "error.inwardEntry.enrollment.batchscheme";
			}
			else if ("V0578".equals(SQLState)) {
				errorKey = "error.inwardentry.policydetails.policy";
			}
			else if ("V0579".equals(SQLState)) {
				errorKey = "error.inwardentry.policydetails.endorsement";
			}
			else if ("V0801".equals(SQLState)) {
				errorKey = "error.mailID.present";
			}
					
			else if ("V0381".equals(SQLState)) {
				errorKey = "error.preauth.preauthdetails.review";
			}
			else if ("V0382".equals(SQLState)) {
				errorKey = "patient.covered.member";
			}
			else if ("V0383".equals(SQLState)) {
				errorKey = "enhancement.date.not.lessthan.original.date";
			}
			else if ("V0384".equals(SQLState)) {
				errorKey = "please.complete.parent.preauth.first";
			}
			else if ("V0385".equals(SQLState)) {
				errorKey = "error.selffund.validateOTPNumber";
			}
			else if ("V0386".equals(SQLState)) {
				errorKey = "add.atleast.one.diagnosis";
			}
			else if ("V0387".equals(SQLState)) {
				errorKey = "PT2";
			}
			else if ("V0388".equals(SQLState)) {
				errorKey = "error.final.premium.screen";
			}
			else if ("V0678".equals(SQLState)) {
				errorKey = "error.endors.bulk.upload";
			}
			else if ("V0700".equals(SQLState)) {
				errorKey = "error.member.not.eligible.for.treatment";
			}
			else if ("V0390".equals(SQLState)) {	
				errorKey = "error.floataccount.debitnote.deposit";
			}
			else if("V0241".equals(SQLState)) {
				errorKey ="error.hospital.address.completeclaim";
			}
			else if("P5000".equals(SQLState)){
				errorKey="error.ailment.dentalBenefitType";
			}
			else if("P5002".equals(SQLState)){
				errorKey="error.clinician.consultation.not.matching";
			}
			else if("D0001".equals(SQLState)) {
				try {
                    getNameMethod = rootCause.getClass().getMethod("getMessage");
                    SQLState = (String) getNameMethod.invoke(rootCause);
                    } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                   }
                   setDynamicErrorDesc(SQLState);
                   errorKey ="error.dynamic.massage";
            }else if("V0257".equals(SQLState)){
            	errorKey="error.change.required";
            }
			else if("45122".equals(SQLState)) {
				errorKey ="error.preauth.claim.cannot.process";
			}
			else if("V0669".equals(SQLState)) {
				errorKey ="error.preauth.claim.cannot.process";
			}
                 else if("V0581".equals(SQLState)){
				errorKey="error.member.inception";
			}
            else if("V0582".equals(SQLState)){
     			errorKey="Duplicate.invocenumber";
     		}
		 return errorKey;
		 
    }

}// end of class TTKException