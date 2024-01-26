
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
 *         &lt;element name="GetMemberPolicesResult" type="{http://schemas.datacontract.org/2004/07/Shared.Models}MemberInsuranceResponse" minOccurs="0"/>
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
    "getMemberPolicesResult"
})
@XmlRootElement(name = "GetMemberPolicesResponse")
public class GetMemberPolicesResponse {

    @XmlElementRef(name = "GetMemberPolicesResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<MemberInsuranceResponse> getMemberPolicesResult;

    /**
     * Gets the value of the getMemberPolicesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MemberInsuranceResponse }{@code >}
     *     
     */
    public JAXBElement<MemberInsuranceResponse> getGetMemberPolicesResult() {
        return getMemberPolicesResult;
    }

    /**
     * Sets the value of the getMemberPolicesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MemberInsuranceResponse }{@code >}
     *     
     */
    public void setGetMemberPolicesResult(JAXBElement<MemberInsuranceResponse> value) {
        this.getMemberPolicesResult = value;
    }

}
