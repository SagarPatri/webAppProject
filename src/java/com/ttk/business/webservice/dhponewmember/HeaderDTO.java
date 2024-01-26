
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DispositionFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IntermediaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReceiverID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecordCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SenderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TPAID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderDTO", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", propOrder = {
    "dispositionFlag",
    "intermediaryID",
    "payerID",
    "receiverID",
    "recordCount",
    "senderID",
    "tpaid",
    "transactionDate"
})
public class HeaderDTO {

    @XmlElementRef(name = "DispositionFlag", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dispositionFlag;
    @XmlElementRef(name = "IntermediaryID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intermediaryID;
    @XmlElementRef(name = "PayerID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> payerID;
    @XmlElementRef(name = "ReceiverID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> receiverID;
    @XmlElement(name = "RecordCount")
    protected Integer recordCount;
    @XmlElementRef(name = "SenderID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> senderID;
    @XmlElementRef(name = "TPAID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tpaid;
    @XmlElementRef(name = "TransactionDate", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> transactionDate;

    /**
     * Gets the value of the dispositionFlag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDispositionFlag() {
        return dispositionFlag;
    }

    /**
     * Sets the value of the dispositionFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDispositionFlag(JAXBElement<String> value) {
        this.dispositionFlag = value;
    }

    /**
     * Gets the value of the intermediaryID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIntermediaryID() {
        return intermediaryID;
    }

    /**
     * Sets the value of the intermediaryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIntermediaryID(JAXBElement<String> value) {
        this.intermediaryID = value;
    }

    /**
     * Gets the value of the payerID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPayerID() {
        return payerID;
    }

    /**
     * Sets the value of the payerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPayerID(JAXBElement<String> value) {
        this.payerID = value;
    }

    /**
     * Gets the value of the receiverID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReceiverID() {
        return receiverID;
    }

    /**
     * Sets the value of the receiverID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReceiverID(JAXBElement<String> value) {
        this.receiverID = value;
    }

    /**
     * Gets the value of the recordCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the value of the recordCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRecordCount(Integer value) {
        this.recordCount = value;
    }

    /**
     * Gets the value of the senderID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSenderID() {
        return senderID;
    }

    /**
     * Sets the value of the senderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSenderID(JAXBElement<String> value) {
        this.senderID = value;
    }

    /**
     * Gets the value of the tpaid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTPAID() {
        return tpaid;
    }

    /**
     * Sets the value of the tpaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTPAID(JAXBElement<String> value) {
        this.tpaid = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTransactionDate(JAXBElement<String> value) {
        this.transactionDate = value;
    }

}
