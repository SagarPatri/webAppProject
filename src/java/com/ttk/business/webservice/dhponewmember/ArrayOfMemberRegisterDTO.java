
package com.ttk.business.webservice.dhponewmember;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMemberRegisterDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMemberRegisterDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MemberRegisterDTO" type="{http://schemas.datacontract.org/2004/07/Shared.DTOs}MemberRegisterDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMemberRegisterDTO", namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", propOrder = {
    "memberRegisterDTO"
})
public class ArrayOfMemberRegisterDTO {

    @XmlElement(name = "MemberRegisterDTO", nillable = true)
    protected List<MemberRegisterDTO> memberRegisterDTO;

    /**
     * Gets the value of the memberRegisterDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the memberRegisterDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemberRegisterDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MemberRegisterDTO }
     * 
     * 
     */
    public List<MemberRegisterDTO> getMemberRegisterDTO() {
        if (memberRegisterDTO == null) {
            memberRegisterDTO = new ArrayList<MemberRegisterDTO>();
        }
        return this.memberRegisterDTO;
    }

}
