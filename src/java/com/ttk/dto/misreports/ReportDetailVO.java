
/** @ (#) ReportDetailVO.java July 31, 2008
 * Project     : TTK Healthcare Services
 * File        : ReportDetailVO.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: July 31, 2008
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */



package com.ttk.dto.misreports;

import com.ttk.dto.BaseVO;

public class ReportDetailVO extends BaseVO {
	private String strUserId="";
	private String strUserName="";
	private String strUserLocation="";
	private String strTTKBranchName="";
	private String strInsCompanyName="";
	private String strInsDoBOCode="";
	private String strAgentCode="";
	private String strGroupPolicyNo="";
	private String strsType="";
	private String strStatus="";
	private String strStartDate = "";
    private String strEndDate = "";
    private String strReportId="";
    private String strReportName="";
    private String strReportType="";
    private String strReportLink="";
    private Long lReportSeqId=null;
    private String streType="";
    private String strsDomiciOption="";
    private String strtInwardRegister="";
    private String strsHospitalName="";
    private String strsClaimsType="";
    private String strInvestAgencyName="";
	private String strEnrolmentId="";
	private String strCorInsurer="";
	
	//XML Dashboards count
	private String sStartDate="";
	private String sEndDate="";
	private String xmlDashboards="";
	
	// e-claim XML downloading and bifurcation
	private Integer shafafiya1;
	private Integer shafafiya2;
	private Integer shafafiya3;
	private Integer shafafiya4;
	private Integer shafafiya5;
	private Integer shafafiya6;
	
	
	private Integer dhpo1;
	private Integer dhpo2;
	private Integer dhpo3;
	private Integer dhpo4;
	private Integer dhpo5;
	private Integer dhpo6;
	
	
	
	private Integer total1;
	private Integer total2;
	private Integer total3;
	private Integer total4;
	private Integer total5;
	private Integer total6;
	
	
	
	
	
	private Integer eclaimVingsTotal1;
	private Integer eclaimVingsTotal2;
	private Integer eclaimVingsTotal3;
	
	
	private Integer eclaimVingsDHPO1;
	private Integer eclaimVingsDHPO2;
	private Integer eclaimVingsDHPO3;
	
	
	private Integer eclaimVingsShafafiya1;
	private Integer eclaimVingsShafafiya2;
	private Integer eclaimVingsShafafiya3;
	
	
	
	//e-preauthorization XML downloading and bifurcation
	
	
	
	private Integer preauthDownloadTotal1;
	private Integer preauthDownloadTotal2;
	private Integer preauthDownloadTotal3;
	private Integer preauthDownloadTotal4;
	private Integer preauthDownloadTotal5;
	
	
	private Integer ePreauthVingsTotal1;
	private Integer ePreauthVingsTotal2;
	private Integer ePreauthVingsTotal3;
	
	
	//e-preauthorization XML processing and uploading
	private Integer preauthUploadTotal1;
	private Integer preauthUploadTotal2;
	private Integer preauthUploadTotal3;
	private Integer preauthUploadTotal4;

	
	//Remittance Advice Generation and Uploading
		private Integer remittanceUploadTotal1;
		private Integer remittanceUploadTotal2;
		private Integer remittanceUploadTotal3;
		private Integer remittanceUploadTotal4;
		
		private Integer remittanceUploadDhpo1;
		private Integer remittanceUploadDhpo2;
		private Integer remittanceUploadDhpo3;
		private Integer remittanceUploadDhpo4;
		
		private Integer remittanceUploadShafafiya1;
		private Integer remittanceUploadShafafiya2;
		private Integer remittanceUploadShafafiya3;
		private Integer remittanceUploadShafafiya4;
		
		
		
		// Member Register XML Endorsements and Uploads
		
		private Integer allInsuranceTotal1;
		private Integer allInsuranceTotal2;
		private Integer allInsuranceTotal3;
		private Integer allInsuranceTotal4;
		
		private Integer orientalInsTotal1;
		private Integer orientalInsTotal2;
		private Integer orientalInsTotal3;
		private Integer orientalInsTotal4;
		
		
		private Integer ascanaInsTotal1;
		private Integer ascanaInsTotal2;
		private Integer ascanaInsTotal3;
		private Integer ascanaInsTotal4;
		
		
		
		
		
		// Ageing Summary of all the pending status XMLs
		
		private Integer ageingsummarytotal1;
		private Integer ageingsummarytotal2;
		private Integer ageingsummarytotal3;
		private Integer ageingsummarytotal4;
		private Integer ageingsummarytotal5;
		private Integer ageingsummarytotal6;
		private Integer ageingsummarytotal7;
		private Integer ageingsummarytotal8;
		
		private Integer ageingsummary24hrs1;
		private Integer ageingsummary24hrs2;
		private Integer ageingsummary24hrs3;
		private Integer ageingsummary24hrs4;
		private Integer ageingsummary24hrs5;
		private Integer ageingsummary24hrs6;
		private Integer ageingsummary24hrs7;
		private Integer ageingsummary24hrs8;
		
		private Integer ageingsummary48hrs1;
		private Integer ageingsummary48hrs2;
		private Integer ageingsummary48hrs3;
		private Integer ageingsummary48hrs4;
		private Integer ageingsummary48hrs5;
		private Integer ageingsummary48hrs6;
		private Integer ageingsummary48hrs7;
		private Integer ageingsummary48hrs8;
		
		private Integer ageingsummary96hrs1;
		private Integer ageingsummary96hrs2;
		private Integer ageingsummary96hrs3;
		private Integer ageingsummary96hrs4;
		private Integer ageingsummary96hrs5;
		private Integer ageingsummary96hrs6;
		private Integer ageingsummary96hrs7;
		private Integer ageingsummary96hrs8;
		
		private Integer ageingsummaryAbove96hrs1;
		private Integer ageingsummaryAbove96hrs2;
		private Integer ageingsummaryAbove96hrs3;
		private Integer ageingsummaryAbove96hrs4;
		private Integer ageingsummaryAbove96hrs5;
		private Integer ageingsummaryAbove96hrs6;
		private Integer ageingsummaryAbove96hrs7;
		private Integer ageingsummaryAbove96hrs8;
		
	
		
		private Integer ageingVingsDhpoTotal1;
		private Integer ageingVingsDhpo24hrs;
		private Integer ageingVingsDhpo48hrs;
		private Integer ageingVingsDhpo96hrs;
		private Integer ageingVingsDhpoAbove96hrs;
		
		private Integer ageingVingsShafafiyaTotal1;
		private Integer ageingVingsShafafiya24hrs;
		private Integer ageingVingsShafafiya48hrs;
		private Integer ageingVingsShafafiya96hrs;
		private Integer ageingVingsShafafiyaAbove96hrs;
		
		
		private Integer webPortalEperauthDhpoTotal;
		private Integer webPortalEperauthDhpo24hrs;
		private Integer webPortalEperauthDhpo48hrs;
		private Integer webPortalEperauthDhpo96hrs;
		private Integer webPortalEperauthDhpoAbove96hrs;
	
	
	
	
	
	
	
	
	
	public Integer getAgeingVingsDhpoTotal1() {
			return ageingVingsDhpoTotal1;
		}

		public void setAgeingVingsDhpoTotal1(Integer ageingVingsDhpoTotal1) {
			this.ageingVingsDhpoTotal1 = ageingVingsDhpoTotal1;
		}

		public Integer getAgeingVingsDhpo24hrs() {
			return ageingVingsDhpo24hrs;
		}

		public void setAgeingVingsDhpo24hrs(Integer ageingVingsDhpo24hrs) {
			this.ageingVingsDhpo24hrs = ageingVingsDhpo24hrs;
		}

		public Integer getAgeingVingsDhpo48hrs() {
			return ageingVingsDhpo48hrs;
		}

		public void setAgeingVingsDhpo48hrs(Integer ageingVingsDhpo48hrs) {
			this.ageingVingsDhpo48hrs = ageingVingsDhpo48hrs;
		}

		public Integer getAgeingVingsDhpo96hrs() {
			return ageingVingsDhpo96hrs;
		}

		public void setAgeingVingsDhpo96hrs(Integer ageingVingsDhpo96hrs) {
			this.ageingVingsDhpo96hrs = ageingVingsDhpo96hrs;
		}

		public Integer getAgeingVingsDhpoAbove96hrs() {
			return ageingVingsDhpoAbove96hrs;
		}

		public void setAgeingVingsDhpoAbove96hrs(Integer ageingVingsDhpoAbove96hrs) {
			this.ageingVingsDhpoAbove96hrs = ageingVingsDhpoAbove96hrs;
		}

		public Integer getAgeingVingsShafafiyaTotal1() {
			return ageingVingsShafafiyaTotal1;
		}

		public void setAgeingVingsShafafiyaTotal1(Integer ageingVingsShafafiyaTotal1) {
			this.ageingVingsShafafiyaTotal1 = ageingVingsShafafiyaTotal1;
		}

		public Integer getAgeingVingsShafafiya24hrs() {
			return ageingVingsShafafiya24hrs;
		}

		public void setAgeingVingsShafafiya24hrs(Integer ageingVingsShafafiya24hrs) {
			this.ageingVingsShafafiya24hrs = ageingVingsShafafiya24hrs;
		}

		public Integer getAgeingVingsShafafiya48hrs() {
			return ageingVingsShafafiya48hrs;
		}

		public void setAgeingVingsShafafiya48hrs(Integer ageingVingsShafafiya48hrs) {
			this.ageingVingsShafafiya48hrs = ageingVingsShafafiya48hrs;
		}

		public Integer getAgeingVingsShafafiya96hrs() {
			return ageingVingsShafafiya96hrs;
		}

		public void setAgeingVingsShafafiya96hrs(Integer ageingVingsShafafiya96hrs) {
			this.ageingVingsShafafiya96hrs = ageingVingsShafafiya96hrs;
		}

		public Integer getAgeingVingsShafafiyaAbove96hrs() {
			return ageingVingsShafafiyaAbove96hrs;
		}

		public void setAgeingVingsShafafiyaAbove96hrs(
				Integer ageingVingsShafafiyaAbove96hrs) {
			this.ageingVingsShafafiyaAbove96hrs = ageingVingsShafafiyaAbove96hrs;
		}

		public Integer getWebPortalEperauthDhpoTotal() {
			return webPortalEperauthDhpoTotal;
		}

		public void setWebPortalEperauthDhpoTotal(Integer webPortalEperauthDhpoTotal) {
			this.webPortalEperauthDhpoTotal = webPortalEperauthDhpoTotal;
		}

		public Integer getWebPortalEperauthDhpo24hrs() {
			return webPortalEperauthDhpo24hrs;
		}

		public void setWebPortalEperauthDhpo24hrs(Integer webPortalEperauthDhpo24hrs) {
			this.webPortalEperauthDhpo24hrs = webPortalEperauthDhpo24hrs;
		}

		public Integer getWebPortalEperauthDhpo48hrs() {
			return webPortalEperauthDhpo48hrs;
		}

		public void setWebPortalEperauthDhpo48hrs(Integer webPortalEperauthDhpo48hrs) {
			this.webPortalEperauthDhpo48hrs = webPortalEperauthDhpo48hrs;
		}

		public Integer getWebPortalEperauthDhpo96hrs() {
			return webPortalEperauthDhpo96hrs;
		}

		public void setWebPortalEperauthDhpo96hrs(Integer webPortalEperauthDhpo96hrs) {
			this.webPortalEperauthDhpo96hrs = webPortalEperauthDhpo96hrs;
		}

		public Integer getWebPortalEperauthDhpoAbove96hrs() {
			return webPortalEperauthDhpoAbove96hrs;
		}

		public void setWebPortalEperauthDhpoAbove96hrs(
				Integer webPortalEperauthDhpoAbove96hrs) {
			this.webPortalEperauthDhpoAbove96hrs = webPortalEperauthDhpoAbove96hrs;
		}

	public Integer getePreauthVingsTotal1() {
			return ePreauthVingsTotal1;
		}

		public void setePreauthVingsTotal1(Integer ePreauthVingsTotal1) {
			this.ePreauthVingsTotal1 = ePreauthVingsTotal1;
		}

		public Integer getePreauthVingsTotal2() {
			return ePreauthVingsTotal2;
		}

		public void setePreauthVingsTotal2(Integer ePreauthVingsTotal2) {
			this.ePreauthVingsTotal2 = ePreauthVingsTotal2;
		}

		public Integer getePreauthVingsTotal3() {
			return ePreauthVingsTotal3;
		}

		public void setePreauthVingsTotal3(Integer ePreauthVingsTotal3) {
			this.ePreauthVingsTotal3 = ePreauthVingsTotal3;
		}

	public Integer getEclaimVingsTotal1() {
		return eclaimVingsTotal1;
	}

	public void setEclaimVingsTotal1(Integer eclaimVingsTotal1) {
		this.eclaimVingsTotal1 = eclaimVingsTotal1;
	}

	public Integer getEclaimVingsTotal2() {
		return eclaimVingsTotal2;
	}

	public void setEclaimVingsTotal2(Integer eclaimVingsTotal2) {
		this.eclaimVingsTotal2 = eclaimVingsTotal2;
	}

	public Integer getEclaimVingsTotal3() {
		return eclaimVingsTotal3;
	}

	public void setEclaimVingsTotal3(Integer eclaimVingsTotal3) {
		this.eclaimVingsTotal3 = eclaimVingsTotal3;
	}

	public Integer getEclaimVingsDHPO1() {
		return eclaimVingsDHPO1;
	}

	public void setEclaimVingsDHPO1(Integer eclaimVingsDHPO1) {
		this.eclaimVingsDHPO1 = eclaimVingsDHPO1;
	}

	public Integer getEclaimVingsDHPO2() {
		return eclaimVingsDHPO2;
	}

	public void setEclaimVingsDHPO2(Integer eclaimVingsDHPO2) {
		this.eclaimVingsDHPO2 = eclaimVingsDHPO2;
	}

	public Integer getEclaimVingsDHPO3() {
		return eclaimVingsDHPO3;
	}

	public void setEclaimVingsDHPO3(Integer eclaimVingsDHPO3) {
		this.eclaimVingsDHPO3 = eclaimVingsDHPO3;
	}

	public Integer getEclaimVingsShafafiya1() {
		return eclaimVingsShafafiya1;
	}

	public void setEclaimVingsShafafiya1(Integer eclaimVingsShafafiya1) {
		this.eclaimVingsShafafiya1 = eclaimVingsShafafiya1;
	}

	public Integer getEclaimVingsShafafiya2() {
		return eclaimVingsShafafiya2;
	}

	public void setEclaimVingsShafafiya2(Integer eclaimVingsShafafiya2) {
		this.eclaimVingsShafafiya2 = eclaimVingsShafafiya2;
	}

	public Integer getEclaimVingsShafafiya3() {
		return eclaimVingsShafafiya3;
	}

	public void setEclaimVingsShafafiya3(Integer eclaimVingsShafafiya3) {
		this.eclaimVingsShafafiya3 = eclaimVingsShafafiya3;
	}

	
		

	public Integer getAgeingsummarytotal1() {
			return ageingsummarytotal1;
		}

		public void setAgeingsummarytotal1(Integer ageingsummarytotal1) {
			this.ageingsummarytotal1 = ageingsummarytotal1;
		}

		public Integer getAgeingsummarytotal2() {
			return ageingsummarytotal2;
		}

		public void setAgeingsummarytotal2(Integer ageingsummarytotal2) {
			this.ageingsummarytotal2 = ageingsummarytotal2;
		}

		public Integer getAgeingsummarytotal3() {
			return ageingsummarytotal3;
		}

		public void setAgeingsummarytotal3(Integer ageingsummarytotal3) {
			this.ageingsummarytotal3 = ageingsummarytotal3;
		}

		public Integer getAgeingsummarytotal4() {
			return ageingsummarytotal4;
		}

		public void setAgeingsummarytotal4(Integer ageingsummarytotal4) {
			this.ageingsummarytotal4 = ageingsummarytotal4;
		}

		public Integer getAgeingsummarytotal5() {
			return ageingsummarytotal5;
		}

		public void setAgeingsummarytotal5(Integer ageingsummarytotal5) {
			this.ageingsummarytotal5 = ageingsummarytotal5;
		}

		public Integer getAgeingsummarytotal6() {
			return ageingsummarytotal6;
		}

		public void setAgeingsummarytotal6(Integer ageingsummarytotal6) {
			this.ageingsummarytotal6 = ageingsummarytotal6;
		}

		public Integer getAgeingsummarytotal7() {
			return ageingsummarytotal7;
		}

		public void setAgeingsummarytotal7(Integer ageingsummarytotal7) {
			this.ageingsummarytotal7 = ageingsummarytotal7;
		}

		public Integer getAgeingsummarytotal8() {
			return ageingsummarytotal8;
		}

		public void setAgeingsummarytotal8(Integer ageingsummarytotal8) {
			this.ageingsummarytotal8 = ageingsummarytotal8;
		}

		public Integer getAgeingsummary24hrs1() {
			return ageingsummary24hrs1;
		}

		public void setAgeingsummary24hrs1(Integer ageingsummary24hrs1) {
			this.ageingsummary24hrs1 = ageingsummary24hrs1;
		}

		public Integer getAgeingsummary24hrs2() {
			return ageingsummary24hrs2;
		}

		public void setAgeingsummary24hrs2(Integer ageingsummary24hrs2) {
			this.ageingsummary24hrs2 = ageingsummary24hrs2;
		}

		public Integer getAgeingsummary24hrs3() {
			return ageingsummary24hrs3;
		}

		public void setAgeingsummary24hrs3(Integer ageingsummary24hrs3) {
			this.ageingsummary24hrs3 = ageingsummary24hrs3;
		}

		public Integer getAgeingsummary24hrs4() {
			return ageingsummary24hrs4;
		}

		public void setAgeingsummary24hrs4(Integer ageingsummary24hrs4) {
			this.ageingsummary24hrs4 = ageingsummary24hrs4;
		}

		public Integer getAgeingsummary24hrs5() {
			return ageingsummary24hrs5;
		}

		public void setAgeingsummary24hrs5(Integer ageingsummary24hrs5) {
			this.ageingsummary24hrs5 = ageingsummary24hrs5;
		}

		public Integer getAgeingsummary24hrs6() {
			return ageingsummary24hrs6;
		}

		public void setAgeingsummary24hrs6(Integer ageingsummary24hrs6) {
			this.ageingsummary24hrs6 = ageingsummary24hrs6;
		}

		public Integer getAgeingsummary24hrs7() {
			return ageingsummary24hrs7;
		}

		public void setAgeingsummary24hrs7(Integer ageingsummary24hrs7) {
			this.ageingsummary24hrs7 = ageingsummary24hrs7;
		}

		public Integer getAgeingsummary24hrs8() {
			return ageingsummary24hrs8;
		}

		public void setAgeingsummary24hrs8(Integer ageingsummary24hrs8) {
			this.ageingsummary24hrs8 = ageingsummary24hrs8;
		}

		public Integer getAgeingsummary48hrs1() {
			return ageingsummary48hrs1;
		}

		public void setAgeingsummary48hrs1(Integer ageingsummary48hrs1) {
			this.ageingsummary48hrs1 = ageingsummary48hrs1;
		}

		public Integer getAgeingsummary48hrs2() {
			return ageingsummary48hrs2;
		}

		public void setAgeingsummary48hrs2(Integer ageingsummary48hrs2) {
			this.ageingsummary48hrs2 = ageingsummary48hrs2;
		}

		public Integer getAgeingsummary48hrs3() {
			return ageingsummary48hrs3;
		}

		public void setAgeingsummary48hrs3(Integer ageingsummary48hrs3) {
			this.ageingsummary48hrs3 = ageingsummary48hrs3;
		}

		public Integer getAgeingsummary48hrs4() {
			return ageingsummary48hrs4;
		}

		public void setAgeingsummary48hrs4(Integer ageingsummary48hrs4) {
			this.ageingsummary48hrs4 = ageingsummary48hrs4;
		}

		public Integer getAgeingsummary48hrs5() {
			return ageingsummary48hrs5;
		}

		public void setAgeingsummary48hrs5(Integer ageingsummary48hrs5) {
			this.ageingsummary48hrs5 = ageingsummary48hrs5;
		}

		public Integer getAgeingsummary48hrs6() {
			return ageingsummary48hrs6;
		}

		public void setAgeingsummary48hrs6(Integer ageingsummary48hrs6) {
			this.ageingsummary48hrs6 = ageingsummary48hrs6;
		}

		public Integer getAgeingsummary48hrs7() {
			return ageingsummary48hrs7;
		}

		public void setAgeingsummary48hrs7(Integer ageingsummary48hrs7) {
			this.ageingsummary48hrs7 = ageingsummary48hrs7;
		}

		public Integer getAgeingsummary48hrs8() {
			return ageingsummary48hrs8;
		}

		public void setAgeingsummary48hrs8(Integer ageingsummary48hrs8) {
			this.ageingsummary48hrs8 = ageingsummary48hrs8;
		}

		public Integer getAgeingsummary96hrs1() {
			return ageingsummary96hrs1;
		}

		public void setAgeingsummary96hrs1(Integer ageingsummary96hrs1) {
			this.ageingsummary96hrs1 = ageingsummary96hrs1;
		}

		public Integer getAgeingsummary96hrs2() {
			return ageingsummary96hrs2;
		}

		public void setAgeingsummary96hrs2(Integer ageingsummary96hrs2) {
			this.ageingsummary96hrs2 = ageingsummary96hrs2;
		}

		public Integer getAgeingsummary96hrs3() {
			return ageingsummary96hrs3;
		}

		public void setAgeingsummary96hrs3(Integer ageingsummary96hrs3) {
			this.ageingsummary96hrs3 = ageingsummary96hrs3;
		}

		public Integer getAgeingsummary96hrs4() {
			return ageingsummary96hrs4;
		}

		public void setAgeingsummary96hrs4(Integer ageingsummary96hrs4) {
			this.ageingsummary96hrs4 = ageingsummary96hrs4;
		}

		public Integer getAgeingsummary96hrs5() {
			return ageingsummary96hrs5;
		}

		public void setAgeingsummary96hrs5(Integer ageingsummary96hrs5) {
			this.ageingsummary96hrs5 = ageingsummary96hrs5;
		}

		public Integer getAgeingsummary96hrs6() {
			return ageingsummary96hrs6;
		}

		public void setAgeingsummary96hrs6(Integer ageingsummary96hrs6) {
			this.ageingsummary96hrs6 = ageingsummary96hrs6;
		}

		public Integer getAgeingsummary96hrs7() {
			return ageingsummary96hrs7;
		}

		public void setAgeingsummary96hrs7(Integer ageingsummary96hrs7) {
			this.ageingsummary96hrs7 = ageingsummary96hrs7;
		}

		public Integer getAgeingsummary96hrs8() {
			return ageingsummary96hrs8;
		}

		public void setAgeingsummary96hrs8(Integer ageingsummary96hrs8) {
			this.ageingsummary96hrs8 = ageingsummary96hrs8;
		}

		public Integer getAgeingsummaryAbove96hrs1() {
			return ageingsummaryAbove96hrs1;
		}

		public void setAgeingsummaryAbove96hrs1(Integer ageingsummaryAbove96hrs1) {
			this.ageingsummaryAbove96hrs1 = ageingsummaryAbove96hrs1;
		}

		public Integer getAgeingsummaryAbove96hrs2() {
			return ageingsummaryAbove96hrs2;
		}

		public void setAgeingsummaryAbove96hrs2(Integer ageingsummaryAbove96hrs2) {
			this.ageingsummaryAbove96hrs2 = ageingsummaryAbove96hrs2;
		}

		public Integer getAgeingsummaryAbove96hrs3() {
			return ageingsummaryAbove96hrs3;
		}

		public void setAgeingsummaryAbove96hrs3(Integer ageingsummaryAbove96hrs3) {
			this.ageingsummaryAbove96hrs3 = ageingsummaryAbove96hrs3;
		}

		public Integer getAgeingsummaryAbove96hrs4() {
			return ageingsummaryAbove96hrs4;
		}

		public void setAgeingsummaryAbove96hrs4(Integer ageingsummaryAbove96hrs4) {
			this.ageingsummaryAbove96hrs4 = ageingsummaryAbove96hrs4;
		}

		public Integer getAgeingsummaryAbove96hrs5() {
			return ageingsummaryAbove96hrs5;
		}

		public void setAgeingsummaryAbove96hrs5(Integer ageingsummaryAbove96hrs5) {
			this.ageingsummaryAbove96hrs5 = ageingsummaryAbove96hrs5;
		}

		public Integer getAgeingsummaryAbove96hrs6() {
			return ageingsummaryAbove96hrs6;
		}

		public void setAgeingsummaryAbove96hrs6(Integer ageingsummaryAbove96hrs6) {
			this.ageingsummaryAbove96hrs6 = ageingsummaryAbove96hrs6;
		}

		public Integer getAgeingsummaryAbove96hrs7() {
			return ageingsummaryAbove96hrs7;
		}

		public void setAgeingsummaryAbove96hrs7(Integer ageingsummaryAbove96hrs7) {
			this.ageingsummaryAbove96hrs7 = ageingsummaryAbove96hrs7;
		}

		public Integer getAgeingsummaryAbove96hrs8() {
			return ageingsummaryAbove96hrs8;
		}

		public void setAgeingsummaryAbove96hrs8(Integer ageingsummaryAbove96hrs8) {
			this.ageingsummaryAbove96hrs8 = ageingsummaryAbove96hrs8;
		}

	public Integer getPreauthUploadTotal1() {
		return preauthUploadTotal1;
	}

	public void setPreauthUploadTotal1(Integer preauthUploadTotal1) {
		this.preauthUploadTotal1 = preauthUploadTotal1;
	}

	public Integer getPreauthUploadTotal2() {
		return preauthUploadTotal2;
	}

	public void setPreauthUploadTotal2(Integer preauthUploadTotal2) {
		this.preauthUploadTotal2 = preauthUploadTotal2;
	}

	public Integer getPreauthUploadTotal3() {
		return preauthUploadTotal3;
	}

	public void setPreauthUploadTotal3(Integer preauthUploadTotal3) {
		this.preauthUploadTotal3 = preauthUploadTotal3;
	}

	public Integer getPreauthUploadTotal4() {
		return preauthUploadTotal4;
	}

	public void setPreauthUploadTotal4(Integer preauthUploadTotal4) {
		this.preauthUploadTotal4 = preauthUploadTotal4;
	}

	public Integer getRemittanceUploadTotal1() {
		return remittanceUploadTotal1;
	}

	public void setRemittanceUploadTotal1(Integer remittanceUploadTotal1) {
		this.remittanceUploadTotal1 = remittanceUploadTotal1;
	}

	public Integer getRemittanceUploadTotal2() {
		return remittanceUploadTotal2;
	}

	public void setRemittanceUploadTotal2(Integer remittanceUploadTotal2) {
		this.remittanceUploadTotal2 = remittanceUploadTotal2;
	}

	public Integer getRemittanceUploadTotal3() {
		return remittanceUploadTotal3;
	}

	public void setRemittanceUploadTotal3(Integer remittanceUploadTotal3) {
		this.remittanceUploadTotal3 = remittanceUploadTotal3;
	}

	public Integer getRemittanceUploadTotal4() {
		return remittanceUploadTotal4;
	}

	public void setRemittanceUploadTotal4(Integer remittanceUploadTotal4) {
		this.remittanceUploadTotal4 = remittanceUploadTotal4;
	}

	public Integer getRemittanceUploadDhpo1() {
		return remittanceUploadDhpo1;
	}

	public void setRemittanceUploadDhpo1(Integer remittanceUploadDhpo1) {
		this.remittanceUploadDhpo1 = remittanceUploadDhpo1;
	}

	public Integer getRemittanceUploadDhpo2() {
		return remittanceUploadDhpo2;
	}

	public void setRemittanceUploadDhpo2(Integer remittanceUploadDhpo2) {
		this.remittanceUploadDhpo2 = remittanceUploadDhpo2;
	}

	public Integer getRemittanceUploadDhpo3() {
		return remittanceUploadDhpo3;
	}

	public void setRemittanceUploadDhpo3(Integer remittanceUploadDhpo3) {
		this.remittanceUploadDhpo3 = remittanceUploadDhpo3;
	}

	public Integer getRemittanceUploadDhpo4() {
		return remittanceUploadDhpo4;
	}

	public void setRemittanceUploadDhpo4(Integer remittanceUploadDhpo4) {
		this.remittanceUploadDhpo4 = remittanceUploadDhpo4;
	}

	public Integer getRemittanceUploadShafafiya1() {
		return remittanceUploadShafafiya1;
	}

	public void setRemittanceUploadShafafiya1(Integer remittanceUploadShafafiya1) {
		this.remittanceUploadShafafiya1 = remittanceUploadShafafiya1;
	}

	public Integer getRemittanceUploadShafafiya2() {
		return remittanceUploadShafafiya2;
	}

	public void setRemittanceUploadShafafiya2(Integer remittanceUploadShafafiya2) {
		this.remittanceUploadShafafiya2 = remittanceUploadShafafiya2;
	}

	public Integer getRemittanceUploadShafafiya3() {
		return remittanceUploadShafafiya3;
	}

	public void setRemittanceUploadShafafiya3(Integer remittanceUploadShafafiya3) {
		this.remittanceUploadShafafiya3 = remittanceUploadShafafiya3;
	}

	public Integer getRemittanceUploadShafafiya4() {
		return remittanceUploadShafafiya4;
	}

	public void setRemittanceUploadShafafiya4(Integer remittanceUploadShafafiya4) {
		this.remittanceUploadShafafiya4 = remittanceUploadShafafiya4;
	}

	public Integer getAllInsuranceTotal1() {
		return allInsuranceTotal1;
	}

	public void setAllInsuranceTotal1(Integer allInsuranceTotal1) {
		this.allInsuranceTotal1 = allInsuranceTotal1;
	}

	public Integer getAllInsuranceTotal2() {
		return allInsuranceTotal2;
	}

	public void setAllInsuranceTotal2(Integer allInsuranceTotal2) {
		this.allInsuranceTotal2 = allInsuranceTotal2;
	}

	public Integer getAllInsuranceTotal3() {
		return allInsuranceTotal3;
	}

	public void setAllInsuranceTotal3(Integer allInsuranceTotal3) {
		this.allInsuranceTotal3 = allInsuranceTotal3;
	}

	public Integer getAllInsuranceTotal4() {
		return allInsuranceTotal4;
	}

	public void setAllInsuranceTotal4(Integer allInsuranceTotal4) {
		this.allInsuranceTotal4 = allInsuranceTotal4;
	}

	public Integer getOrientalInsTotal1() {
		return orientalInsTotal1;
	}

	public void setOrientalInsTotal1(Integer orientalInsTotal1) {
		this.orientalInsTotal1 = orientalInsTotal1;
	}

	public Integer getOrientalInsTotal2() {
		return orientalInsTotal2;
	}

	public void setOrientalInsTotal2(Integer orientalInsTotal2) {
		this.orientalInsTotal2 = orientalInsTotal2;
	}

	public Integer getOrientalInsTotal3() {
		return orientalInsTotal3;
	}

	public void setOrientalInsTotal3(Integer orientalInsTotal3) {
		this.orientalInsTotal3 = orientalInsTotal3;
	}

	public Integer getOrientalInsTotal4() {
		return orientalInsTotal4;
	}

	public void setOrientalInsTotal4(Integer orientalInsTotal4) {
		this.orientalInsTotal4 = orientalInsTotal4;
	}

	public Integer getAscanaInsTotal1() {
		return ascanaInsTotal1;
	}

	public void setAscanaInsTotal1(Integer ascanaInsTotal1) {
		this.ascanaInsTotal1 = ascanaInsTotal1;
	}

	public Integer getAscanaInsTotal2() {
		return ascanaInsTotal2;
	}

	public void setAscanaInsTotal2(Integer ascanaInsTotal2) {
		this.ascanaInsTotal2 = ascanaInsTotal2;
	}

	public Integer getAscanaInsTotal3() {
		return ascanaInsTotal3;
	}

	public void setAscanaInsTotal3(Integer ascanaInsTotal3) {
		this.ascanaInsTotal3 = ascanaInsTotal3;
	}

	public Integer getAscanaInsTotal4() {
		return ascanaInsTotal4;
	}

	public void setAscanaInsTotal4(Integer ascanaInsTotal4) {
		this.ascanaInsTotal4 = ascanaInsTotal4;
	}

	
	
	public Integer getPreauthDownloadTotal1() {
		return preauthDownloadTotal1;
	}

	public void setPreauthDownloadTotal1(Integer preauthDownloadTotal1) {
		this.preauthDownloadTotal1 = preauthDownloadTotal1;
	}

	public Integer getPreauthDownloadTotal2() {
		return preauthDownloadTotal2;
	}

	public void setPreauthDownloadTotal2(Integer preauthDownloadTotal2) {
		this.preauthDownloadTotal2 = preauthDownloadTotal2;
	}

	public Integer getPreauthDownloadTotal3() {
		return preauthDownloadTotal3;
	}

	public void setPreauthDownloadTotal3(Integer preauthDownloadTotal3) {
		this.preauthDownloadTotal3 = preauthDownloadTotal3;
	}

	public Integer getPreauthDownloadTotal4() {
		return preauthDownloadTotal4;
	}

	public void setPreauthDownloadTotal4(Integer preauthDownloadTotal4) {
		this.preauthDownloadTotal4 = preauthDownloadTotal4;
	}

	public Integer getPreauthDownloadTotal5() {
		return preauthDownloadTotal5;
	}

	public void setPreauthDownloadTotal5(Integer preauthDownloadTotal5) {
		this.preauthDownloadTotal5 = preauthDownloadTotal5;
	}

	
	
	
	
	
	
	

	public Integer getShafafiya1() {
		return shafafiya1;
	}

	public void setShafafiya1(Integer shafafiya1) {
		this.shafafiya1 = shafafiya1;
	}

	public Integer getShafafiya2() {
		return shafafiya2;
	}

	public void setShafafiya2(Integer shafafiya2) {
		this.shafafiya2 = shafafiya2;
	}

	public Integer getShafafiya3() {
		return shafafiya3;
	}

	public void setShafafiya3(Integer shafafiya3) {
		this.shafafiya3 = shafafiya3;
	}

	public Integer getShafafiya4() {
		return shafafiya4;
	}

	public void setShafafiya4(Integer shafafiya4) {
		this.shafafiya4 = shafafiya4;
	}

	public Integer getShafafiya5() {
		return shafafiya5;
	}

	public void setShafafiya5(Integer shafafiya5) {
		this.shafafiya5 = shafafiya5;
	}

	public Integer getShafafiya6() {
		return shafafiya6;
	}

	public void setShafafiya6(Integer shafafiya6) {
		this.shafafiya6 = shafafiya6;
	}

	public Integer getDhpo1() {
		return dhpo1;
	}

	public void setDhpo1(Integer dhpo1) {
		this.dhpo1 = dhpo1;
	}

	public Integer getDhpo2() {
		return dhpo2;
	}

	public void setDhpo2(Integer dhpo2) {
		this.dhpo2 = dhpo2;
	}

	public Integer getDhpo3() {
		return dhpo3;
	}

	public void setDhpo3(Integer dhpo3) {
		this.dhpo3 = dhpo3;
	}

	public Integer getDhpo4() {
		return dhpo4;
	}

	public void setDhpo4(Integer dhpo4) {
		this.dhpo4 = dhpo4;
	}

	public Integer getDhpo5() {
		return dhpo5;
	}

	public void setDhpo5(Integer dhpo5) {
		this.dhpo5 = dhpo5;
	}

	public Integer getDhpo6() {
		return dhpo6;
	}

	public void setDhpo6(Integer dhpo6) {
		this.dhpo6 = dhpo6;
	}

	public Integer getTotal1() {
		return total1;
	}

	public void setTotal1(Integer total1) {
		this.total1 = total1;
	}

	public Integer getTotal2() {
		return total2;
	}

	public void setTotal2(Integer total2) {
		this.total2 = total2;
	}

	public Integer getTotal3() {
		return total3;
	}

	public void setTotal3(Integer total3) {
		this.total3 = total3;
	}

	public Integer getTotal4() {
		return total4;
	}

	public void setTotal4(Integer total4) {
		this.total4 = total4;
	}

	public Integer getTotal5() {
		return total5;
	}

	public void setTotal5(Integer total5) {
		this.total5 = total5;
	}

	public Integer getTotal6() {
		return total6;
	}

	public void setTotal6(Integer total6) {
		this.total6 = total6;
	}

	public String getStrStartDate() {
		return strStartDate;
	}

	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}

	public String getsEndDate() {
		return sEndDate;
	}

	public void setsEndDate(String sEndDate) {
		this.sEndDate = sEndDate;
	}

	public String getXmlDashboards() {
		return xmlDashboards;
	}

	public void setXmlDashboards(String xmlDashboards) {
		this.xmlDashboards = xmlDashboards;
	}

	/**
	 * @return Returns the getEnrolmentId.
	 */
	public String getEnrolmentId() {
		return strEnrolmentId;
	}//end of public String getEnrolmentId()

	/**
	 * @param getEnrolmentId The getEnrolmentId to set.
	 */
	public void setEnrolmentId(String strEnrolmentId) {
		this.strEnrolmentId = strEnrolmentId;
	}//end of public void setStrAgentCode(String strAgentCode)

	/**
	 * @return Returns the strCorInsurer.
	 */
	public String getCorInsurer() {
		return strCorInsurer;
	}//end of public String getStrGroupPolicyNo()

	/**
	 * @param strCorInsurer The strCorInsurer to set.
	 */
	public void setCorInsurer(String strCorInsurer) {
		this.strCorInsurer = strCorInsurer;
	}// end of public void strCorInsurer(String strCorInsurer)
	/**
    
	/**
	 * Store the user id
	 * @param strUserId String the user id
	 */
	public void setUSER_ID(String strUserId)
	{
		this.strUserId = strUserId;
	}//end of setUSER_ID(String strUserId)

	/**
	 * Retrieve the user id
	 * @return strUserId String the user id
	 */
	public String getUSER_ID()
	{
		return strUserId;
	}//end of getUSER_ID()
	/**
     * Retrieve User Name
     * @return  strUserName String
     */
    public String getUserName() {
        return strUserName;
    }//end of getUserName()

    /**
     * Sets User Name
     * @param  strUserName String
     */
    public void setUserName(String strUserName) {
        this.strUserName = strUserName;
    }//end of setUserName(String strUserName)
    /**
     * Retrieve the Branch Name
     * @return  strBranchName String
     */
    public String getUserLocation() {
        return strUserLocation;
    }//end of getUserLocation()

    /**
     * Sets the Branch Name
     * @param  strBranchName String
     */
    public void setUserLocation(String strUserLocation) {
        this.strUserLocation = strUserLocation;
    }//end of setUserLocation(String strUserLocation)
    

	

	/**
	 * @return Returns the strTTKBranchName.
	 */
	public String getTTKBranchName() {
		return strTTKBranchName;
	}//end of public String getTTKBranchName()

	/**
	 * @param strTTKBranchName The strTTKBranchName to set.
	 */
	public void setTTKBranchName(String strTTKBranchName) {
		this.strTTKBranchName = strTTKBranchName;
	}//end of public void setTTKBranchName(String strTTKBranchName)

	

	

	/**
	 * @return Returns the strInsCompanyName.
	 */
	public String getInsCompanyName() {
		return strInsCompanyName;
	}//end of public String getInsCompanyName()

	/**
	 * @param strInsCompanyName The strInsCompanyName to set.
	 */
	public void setInsCompanyName(String strInsCompanyName) {
		this.strInsCompanyName = strInsCompanyName;
	}//end of public void setInsCompanyName(String strInsCompanyName)

	/**
	 * @return Returns the strInsDoBOCode.
	 */
	public String getInsDoBOCode() {
		return strInsDoBOCode;
	}//end of public String getInsDoBOCode()

	/**
	 * @param strInsDoBOCode The strInsDoBOCode to set.
	 */
	public void setInsDoBOCode(String strInsDoBOCode) {
		this.strInsDoBOCode = strInsDoBOCode;
	}//end of public void setInsDoBOCode(String strInsDoBOCode)

	/**
	 * @return Returns the strAgentCode.
	 */
	public String getAgentCode() {
		return strAgentCode;
	}//end of public String getStrAgentCode()

	/**
	 * @param strAgentCode The strAgentCode to set.
	 */
	public void setAgentCode(String strAgentCode) {
		this.strAgentCode = strAgentCode;
	}//end of public void setStrAgentCode(String strAgentCode)

	/**
	 * @return Returns the strGroupPolicyNo.
	 */
	public String getGroupPolicyNo() {
		return strGroupPolicyNo;
	}//end of public String getStrGroupPolicyNo()

	/**
	 * @param strGroupPolicyNo The strGroupPolicyNo to set.
	 */
	public void setGroupPolicyNo(String strGroupPolicyNo) {
		this.strGroupPolicyNo = strGroupPolicyNo;
	}// end of public void setStrGroupPolicyNo(String strGroupPolicyNo)
	/**Retrieve the sType
	 * @return Returns the sType.
	 */
	public String getSType() {
		return strsType;
	}//end of getSType()

	/**Sets the sType
	 * @param groupId The sType to set.
	 */
	public void setSType(String strsType) {
		this.strsType = strsType;
	}//end of setSType(String sType)
	/**Retrieve the sStatus
	 * @return Returns the sStatus.
	 */
	public String getSStatus() {
		return strStatus;
	}//end of getSStatus()

	/**Sets the sStatus
	 * @param  The sStatus to set.
	 */
	public void setSStatus(String strStatus) {
		this.strStatus = strStatus;
	}//end of setSStatus(String sStatus)
	/** This method returns the Start Date
     * @return Returns the strStartDate.
     */
    public String getStartDate() {
        return strStartDate;
    }//end of getStartDate()
    
    /** This method sets the Start Date
     * @param strStartDate The strStartDate to set.
     */
    public void setStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }//end of setStartDate(Date strStartDate)
    /** This method returns the End Date 
     * @return Returns the strEndDate.
     */
    public String getEndDate() {
        return strEndDate;
    }//end of getEndDate()
    
    /** This method sets the End Date 
     * @param strEndDate The strEndDate to set.
     */
    public void setEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }//end of setEndDate(Date strEndDate)

	/**
	 * @return Returns the strReportName.
	 */
	public String getReportName() {
		return strReportName;
	}//end of public String getReportName()

	/**
	 * @param strReportName The strReportName to set.
	 */
	public void setReportName(String strReportName) {
		this.strReportName = strReportName;
	}//end of public void setReportName(String strReportName)

	/**
	 * @return Returns the strReportId.
	 */
	public String getReportId() {
		return strReportId;
	}//end of public String getReportId()

	/**
	 * @param strReportId The strReportId to set.
	 */
	public void setReportId(String strReportId) {
		this.strReportId = strReportId;
	}//end of public void setReportId(String strReportId)

	/**
	 * @return Returns the strReportType.
	 */
	public String getReportType() {
		return strReportType;
	}//end of public String getReportType()

	/**
	 * @param strReportType The strReportType to set.
	 */
	public void setReportType(String strReportType) {
		this.strReportType = strReportType;
	}//end of public void setReportType(String strReportType) 

	/**
	 * @return Returns the strReportLink.
	 */
	public String getReportLink() {
		return strReportLink;
	}//end of public String getReportLink()

	/**
	 * @param strReportLink The strReportLink to set.
	 */
	public void setReportLink(String strReportLink) {
		this.strReportLink = strReportLink;
	}//end of public void setReportLink(String strReportLink)

	/**
	 * @return Returns the lReportSeqId.
	 */
	public Long getReportSeqId() {
		return lReportSeqId;
	}//end of public Long getReportSeqId()

	/**
	 * @param reportSeqId The lReportSeqId to set.
	 */
	public void setReportSeqId(Long reportSeqId) {
		lReportSeqId = reportSeqId;
	}//end of public void setReportSeqId(Long reportSeqId)

	/**
	 * @return Returns the streType.
	 */
	public String geteType() {
		return streType;
	}//end of public String geteType()

	/**
	 * @param streType The streType to set.
	 */
	public void seteType(String streType) {
		this.streType = streType;
	}//end of public void seteType(String streType)

	/**
	 * @return Returns the strsDomiciOption.
	 */
	public String getsDomiciOption() {
		return strsDomiciOption;
	}//end of public String getsDomiciOption()

	/**
	 * @param strsDomiciOption The strsDomiciOption to set.
	 */
	public void setsDomiciOption(String strsDomiciOption) {
		this.strsDomiciOption = strsDomiciOption;
	}//end of public void setsDomiciOption(String strsDomiciOption)

	/**
	 * @return Returns the strtInwardRegister.
	 */
	public String gettInwardRegister() {
		return strtInwardRegister;
	}//end of public String gettInwardRegister()

	/**
	 * @param strtInwardRegister The strtInwardRegister to set.
	 */
	public void settInwardRegister(String strtInwardRegister) {
		this.strtInwardRegister = strtInwardRegister;
	}//end of public void settInwardRegister(String strtInwardRegister)

	/**
	 * @return Returns the strsHospitalName.
	 */
	public String getsHospitalName() {
		return strsHospitalName;
	}//end of public String getsHospitalName()

	/**
	 * @param strsHospitalName The strsHospitalName to set.
	 */
	public void setsHospitalName(String strsHospitalName) {
		this.strsHospitalName = strsHospitalName;
	}//end of public void setsHospitalName(String strsHospitalName)

	/**
	 * @return Returns the strsClaimsType.
	 */
	public String getsClaimsType() {
		return strsClaimsType;
	}//end of public String getsClaimsType()

	/**
	 * @param strsClaimsType The strsClaimsType to set.
	 */
	public void setsClaimsType(String strsClaimsType) {
		this.strsClaimsType = strsClaimsType;
	}//end of public void setsClaimsType(String strsClaimsType)
	
	/**
	 * @return Returns the strInvestAgencyName.
	 */
	public String getInvestAgencyName() {
		return strInvestAgencyName;
	}//end of public String getInvestAgencyName()

	/**
	 * @param strInvestAgencyName The strInvestAgencyName to set.
	 */
	public void setInvestAgencyName(String strInvestAgencyName) {
		this.strInvestAgencyName = strInvestAgencyName;
	}//end of public void setInvestAgencyName(String strInvestAgencyName)

	public String getsStartDate() {
		return sStartDate;
	}

	public void setsStartDate(String sStartDate) {
		this.sStartDate = sStartDate;
	}

	

	
}//end of public class ReportDetailVO 
