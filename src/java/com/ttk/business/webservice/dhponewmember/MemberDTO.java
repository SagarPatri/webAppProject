
package com.ttk.business.webservice.dhponewmember;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MemberDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MemberDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Contract" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}ContractDTO" minOccurs="0"/>
 *         &lt;element name="Establishment" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}EstablishmentDTO" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PhotoAttachment" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}PhotoAttachmentDTO" minOccurs="0"/>
 *         &lt;element name="Relation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RelationTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemberDTO", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", propOrder = {
    "contract",
    "establishment",
    "id",
    "photoAttachment",
    "relation",
    "relationTo"
})
public class MemberDTO {

    @XmlElementRef(name = "Contract", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<ContractDTO> contract;
    @XmlElementRef(name = "Establishment", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<EstablishmentDTO> establishment;
    @XmlElementRef(name = "ID", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> id;
    @XmlElementRef(name = "PhotoAttachment", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<PhotoAttachmentDTO> photoAttachment;
    @XmlElement(name = "Relation")
    protected Integer relation;
    @XmlElementRef(name = "RelationTo", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", type = JAXBElement.class, required = false)
    protected JAXBElement<String> relationTo;

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ContractDTO }{@code >}
     *     
     */
    public JAXBElement<ContractDTO> getContract() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ContractDTO }{@code >}
     *     
     */
    public void setContract(JAXBElement<ContractDTO> value) {
        this.contract = value;
    }

    /**
     * Gets the value of the establishment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EstablishmentDTO }{@code >}
     *     
     */
    public JAXBElement<EstablishmentDTO> getEstablishment() {
        return establishment;
    }

    /**
     * Sets the value of the establishment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EstablishmentDTO }{@code >}
     *     
     */
    public void setEstablishment(JAXBElement<EstablishmentDTO> value) {
        this.establishment = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setID(JAXBElement<String> value) {
        this.id = value;
    }

    /**
     * Gets the value of the photoAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PhotoAttachmentDTO }{@code >}
     *     
     */
    public JAXBElement<PhotoAttachmentDTO> getPhotoAttachment() {
        return photoAttachment;
    }

    /**
     * Sets the value of the photoAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PhotoAttachmentDTO }{@code >}
     *     
     */
    public void setPhotoAttachment(JAXBElement<PhotoAttachmentDTO> value) {
        this.photoAttachment = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRelation(Integer value) {
        this.relation = value;
    }

    /**
     * Gets the value of the relationTo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelationTo() {
        return relationTo;
    }

    /**
     * Sets the value of the relationTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelationTo(JAXBElement<String> value) {
        this.relationTo = value;
    }

}
