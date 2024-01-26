package com.ttk.dto.maintenance;

import com.ttk.dto.BaseVO;

public class GeneralAnnouncementVO extends BaseVO {

	private Long GenAnnounSeqID = null; //SEQ_ID
	private String AnnouncementMes = null; //DESCRIPTION

	public Long getGenAnnounSeqID() {
		return GenAnnounSeqID;
	}

	public void setGenAnnounSeqID(Long genAnnounSeqID) {
		GenAnnounSeqID = genAnnounSeqID;
	}

	public String getAnnouncementMes() {
		return AnnouncementMes;
	}

	public void setAnnouncementMes(String announcementMes) {
		AnnouncementMes = announcementMes;
	}

}
