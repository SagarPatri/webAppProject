
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusCode" type="{http://schemas.datacontract.org/2004/07/Shared.Enums}ResultStatus"/>
 *         &lt;element name="TransactionInformation" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}MemberRegisterDTO"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorReport" type="{http://schemas.datacontract.org/2004/07/Shared.Models}ArrayOfErrorValidaions"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionResponse", namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", propOrder = {
    "statusCode",
    "transactionInformation",
    "message",
    "errorReport"
})
public class TransactionResponse {

    @XmlElement(name = "StatusCode", required = true)
    protected String statusCode;
    @XmlElement(name = "TransactionInformation", required = true, nillable = true)
    protected MemberRegisterDTO transactionInformation;
    @XmlElement(name = "Message", required = true, nillable = true)
    protected String message;
    @XmlElement(name = "ErrorReport", required = true, nillable = true)
    protected ArrayOfErrorValidaions errorReport;

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the transactionInformation property.
     * 
     * @return
     *     possible object is
     *     {@link MemberRegisterDTO }
     *     
     */
    public MemberRegisterDTO getTransactionInformation() {
        return transactionInformation;
    }

    /**
     * Sets the value of the transactionInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberRegisterDTO }
     *     
     */
    public void setTransactionInformation(MemberRegisterDTO value) {
        this.transactionInformation = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the errorReport property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfErrorValidaions }
     *     
     */
    public ArrayOfErrorValidaions getErrorReport() {
        return errorReport;
    }

    /**
     * Sets the value of the errorReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfErrorValidaions }
     *     
     */
    public void setErrorReport(ArrayOfErrorValidaions value) {
        this.errorReport = value;
    }

}
