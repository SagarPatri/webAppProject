
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
 *         &lt;element name="GenerateMemberIDResult" type="{http://schemas.datacontract.org/2004/07/Shared.Models}GenerateMamberResponse" minOccurs="0"/>
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
    "generateMemberIDResult"
})
@XmlRootElement(name = "GenerateMemberIDResponse")
public class GenerateMemberIDResponse {

    @XmlElementRef(name = "GenerateMemberIDResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<GenerateMamberResponse> generateMemberIDResult;

    /**
     * Gets the value of the generateMemberIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link GenerateMamberResponse }{@code >}
     *     
     */
    public JAXBElement<GenerateMamberResponse> getGenerateMemberIDResult() {
        return generateMemberIDResult;
    }

    /**
     * Sets the value of the generateMemberIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link GenerateMamberResponse }{@code >}
     *     
     */
    public void setGenerateMemberIDResult(JAXBElement<GenerateMamberResponse> value) {
        this.generateMemberIDResult = value;
    }

}
