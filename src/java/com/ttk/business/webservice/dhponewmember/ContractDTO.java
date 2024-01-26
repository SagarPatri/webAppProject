
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeletionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DentalCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnrollmentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrossPremium" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="IPCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IntermediaryFee" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="MaternityCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetPremium" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="OPConsultationCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OPCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OpticalCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PharmacyCopay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PolicyID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PolicySequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductOrigin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TPAFee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TPAFeeType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TopUpPolicy" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractDTO", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", propOrder = {
    "deletionDate",
    "dentalCopay",
    "enrollmentDate",
    "grossPremium",
    "ipCopay",
    "intermediaryFee",
    "maternityCopay",
    "netPremium",
    "opConsultationCopay",
    "opCopay",
    "opticalCopay",
    "pharmacyCopay",
    "policyID",
    "policySequence",
    "productCode",
    "productID",
    "productOrigin",
    "tpaFee",
    "tpaFeeType",
    "topUpPolicy"
})
public class ContractDTO {

    @XmlElementRef(name = "DeletionDate", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> deletionDate;
    @XmlElementRef(name = "DentalCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dentalCopay;
    @XmlElementRef(name = "EnrollmentDate", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> enrollmentDate;
    @XmlElement(name = "GrossPremium")
    protected Float grossPremium;
    @XmlElementRef(name = "IPCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ipCopay;
    @XmlElement(name = "IntermediaryFee")
    protected Float intermediaryFee;
    @XmlElementRef(name = "MaternityCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> maternityCopay;
    @XmlElement(name = "NetPremium")
    protected Float netPremium;
    @XmlElementRef(name = "OPConsultationCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> opConsultationCopay;
    @XmlElementRef(name = "OPCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> opCopay;
    @XmlElementRef(name = "OpticalCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> opticalCopay;
    @XmlElementRef(name = "PharmacyCopay", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pharmacyCopay;
    @XmlElementRef(name = "PolicyID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> policyID;
    @XmlElementRef(name = "PolicySequence", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> policySequence;
    @XmlElementRef(name = "ProductCode", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> productCode;
    @XmlElementRef(name = "ProductID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> productID;
    @XmlElement(name = "ProductOrigin")
    protected Integer productOrigin;
    @XmlElementRef(name = "TPAFee", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tpaFee;
    @XmlElement(name = "TPAFeeType")
    protected Integer tpaFeeType;
    @XmlElement(name = "TopUpPolicy")
    protected Integer topUpPolicy;

    /**
     * Gets the value of the deletionDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDeletionDate() {
        return deletionDate;
    }

    /**
     * Sets the value of the deletionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDeletionDate(JAXBElement<String> value) {
        this.deletionDate = value;
    }

    /**
     * Gets the value of the dentalCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDentalCopay() {
        return dentalCopay;
    }

    /**
     * Sets the value of the dentalCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDentalCopay(JAXBElement<String> value) {
        this.dentalCopay = value;
    }

    /**
     * Gets the value of the enrollmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * Sets the value of the enrollmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEnrollmentDate(JAXBElement<String> value) {
        this.enrollmentDate = value;
    }

    /**
     * Gets the value of the grossPremium property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getGrossPremium() {
        return grossPremium;
    }

    /**
     * Sets the value of the grossPremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setGrossPremium(Float value) {
        this.grossPremium = value;
    }

    /**
     * Gets the value of the ipCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIPCopay() {
        return ipCopay;
    }

    /**
     * Sets the value of the ipCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIPCopay(JAXBElement<String> value) {
        this.ipCopay = value;
    }

    /**
     * Gets the value of the intermediaryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getIntermediaryFee() {
        return intermediaryFee;
    }

    /**
     * Sets the value of the intermediaryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setIntermediaryFee(Float value) {
        this.intermediaryFee = value;
    }

    /**
     * Gets the value of the maternityCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMaternityCopay() {
        return maternityCopay;
    }

    /**
     * Sets the value of the maternityCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMaternityCopay(JAXBElement<String> value) {
        this.maternityCopay = value;
    }

    /**
     * Gets the value of the netPremium property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetPremium() {
        return netPremium;
    }

    /**
     * Sets the value of the netPremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetPremium(Float value) {
        this.netPremium = value;
    }

    /**
     * Gets the value of the opConsultationCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOPConsultationCopay() {
        return opConsultationCopay;
    }

    /**
     * Sets the value of the opConsultationCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPConsultationCopay(JAXBElement<String> value) {
        this.opConsultationCopay = value;
    }

    /**
     * Gets the value of the opCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOPCopay() {
        return opCopay;
    }

    /**
     * Sets the value of the opCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPCopay(JAXBElement<String> value) {
        this.opCopay = value;
    }

    /**
     * Gets the value of the opticalCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOpticalCopay() {
        return opticalCopay;
    }

    /**
     * Sets the value of the opticalCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOpticalCopay(JAXBElement<String> value) {
        this.opticalCopay = value;
    }

    /**
     * Gets the value of the pharmacyCopay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPharmacyCopay() {
        return pharmacyCopay;
    }

    /**
     * Sets the value of the pharmacyCopay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPharmacyCopay(JAXBElement<String> value) {
        this.pharmacyCopay = value;
    }

    /**
     * Gets the value of the policyID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPolicyID() {
        return policyID;
    }

    /**
     * Sets the value of the policyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPolicyID(JAXBElement<String> value) {
        this.policyID = value;
    }

    /**
     * Gets the value of the policySequence property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPolicySequence() {
        return policySequence;
    }

    /**
     * Sets the value of the policySequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPolicySequence(JAXBElement<String> value) {
        this.policySequence = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProductCode(JAXBElement<String> value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the productID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProductID() {
        return productID;
    }

    /**
     * Sets the value of the productID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProductID(JAXBElement<String> value) {
        this.productID = value;
    }

    /**
     * Gets the value of the productOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProductOrigin() {
        return productOrigin;
    }

    /**
     * Sets the value of the productOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProductOrigin(Integer value) {
        this.productOrigin = value;
    }

    /**
     * Gets the value of the tpaFee property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTPAFee() {
        return tpaFee;
    }

    /**
     * Sets the value of the tpaFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTPAFee(JAXBElement<String> value) {
        this.tpaFee = value;
    }

    /**
     * Gets the value of the tpaFeeType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTPAFeeType() {
        return tpaFeeType;
    }

    /**
     * Sets the value of the tpaFeeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTPAFeeType(Integer value) {
        this.tpaFeeType = value;
    }

    /**
     * Gets the value of the topUpPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTopUpPolicy() {
        return topUpPolicy;
    }

    /**
     * Sets the value of the topUpPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTopUpPolicy(Integer value) {
        this.topUpPolicy = value;
    }

}
