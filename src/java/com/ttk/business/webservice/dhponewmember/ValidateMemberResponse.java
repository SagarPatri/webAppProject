
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="ValidateMemberResult" type="{http://schemas.datacontract.org/2004/07/Shared.Models}ValidateMemberFileNoResponse" minOccurs="0"/>
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
    "validateMemberResult"
})
@XmlRootElement(name = "ValidateMemberResponse")
public class ValidateMemberResponse {

    @XmlElementRef(name = "ValidateMemberResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ValidateMemberFileNoResponse> validateMemberResult;

    /**
     * Gets the value of the validateMemberResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ValidateMemberFileNoResponse }{@code >}
     *     
     */
    public JAXBElement<ValidateMemberFileNoResponse> getValidateMemberResult() {
        return validateMemberResult;
    }

    /**
     * Sets the value of the validateMemberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ValidateMemberFileNoResponse }{@code >}
     *     
     */
    public void setValidateMemberResult(JAXBElement<ValidateMemberFileNoResponse> value) {
        this.validateMemberResult = value;
    }

}
