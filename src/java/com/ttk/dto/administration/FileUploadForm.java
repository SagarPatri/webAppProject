package com.ttk.dto.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author info@tutorials4u.net
 */
public class FileUploadForm extends org.apache.struts.action.ActionForm {

	private static final long serialVersionUID = 1L;

	private FormFile file;

    private String message;

    public FileUploadForm() {
        super();
    // TODO Auto-generated constructor stub
    }

    /**
     * @return the file
     */
    public FormFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(FormFile file) {
        this.file = file;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (file.getFileSize() == 0) {
            errors.add("file", new ActionMessage("error.file.required"));
        } else if (!file.getContentType().equals("application/vnd.ms-excel")) {
            errors.add("file", new ActionMessage("error.file.type"));
        }
        /**
         * If the file size is greater than 20kb.
         */
        else if (file.getFileSize() > 20480) {
            errors.add("file", new ActionMessage("error.file.size"));
        }
        return errors;
    }
}
