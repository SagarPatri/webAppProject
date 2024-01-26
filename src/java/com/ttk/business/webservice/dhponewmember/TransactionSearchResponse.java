
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionSearchResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionSearchResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusCode" type="{http://schemas.datacontract.org/2004/07/Shared.Enums}ResultStatus"/>
 *         &lt;element name="TransactionsList" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}ArrayOfMemberRegisterDTO"/>
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
@XmlType(name = "TransactionSearchResponse", namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", propOrder = {
    "statusCode",
    "transactionsList",
    "message",
    "errorReport"
})
public class TransactionSearchResponse {

    @XmlElement(name = "StatusCode", required = true)
    protected String statusCode;
    @XmlElement(name = "TransactionsList", required = true, nillable = true)
    protected ArrayOfMemberRegisterDTO transactionsList;
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
     * Gets the value of the transactionsList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMemberRegisterDTO }
     *     
     */
    public ArrayOfMemberRegisterDTO getTransactionsList() {
        return transactionsList;
    }

    /**
     * Sets the value of the transactionsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMemberRegisterDTO }
     *     
     */
    public void setTransactionsList(ArrayOfMemberRegisterDTO value) {
        this.transactionsList = value;
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
