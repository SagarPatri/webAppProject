
package com.ttk.business.webservice.haad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CheckForNewPriorAuthorizationTransactionsResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "checkForNewPriorAuthorizationTransactionsResult",
    "errorMessage"
})
@XmlRootElement(name = "CheckForNewPriorAuthorizationTransactionsResponse")
public class CheckForNewPriorAuthorizationTransactionsResponse {

    @XmlElement(name = "CheckForNewPriorAuthorizationTransactionsResult")
    protected int checkForNewPriorAuthorizationTransactionsResult;
    protected String errorMessage;

    /**
     * Gets the value of the checkForNewPriorAuthorizationTransactionsResult property.
     * 
     */
    public int getCheckForNewPriorAuthorizationTransactionsResult() {
        return checkForNewPriorAuthorizationTransactionsResult;
    }

    /**
     * Sets the value of the checkForNewPriorAuthorizationTransactionsResult property.
     * 
     */
    public void setCheckForNewPriorAuthorizationTransactionsResult(int value) {
        this.checkForNewPriorAuthorizationTransactionsResult = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

}
