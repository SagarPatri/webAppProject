
package com.ttk.business.webservice.dhponewmember;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ttk.business.webservice.dhponewmember package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ErrorValidaionsDhpoField_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "DhpoField");
    private final static QName _ErrorValidaionsType_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "Type");
    private final static QName _ErrorValidaionsErrorText_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "ErrorText");
    private final static QName _ErrorValidaionsAdditionalReference_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "AdditionalReference");
    private final static QName _ErrorValidaionsTransaction_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "Transaction");
    private final static QName _ErrorValidaionsFieldValue_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "FieldValue");
    private final static QName _ErrorValidaionsRuleId_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "RuleId");
    private final static QName _ErrorValidaionsObjectName_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "ObjectName");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _ValidateMemberFileNoResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "ValidateMemberFileNoResponse");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _ArrayOfErrorValidaions_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "ArrayOfErrorValidaions");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _ContractDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ContractDTO");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _EstablishmentDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "EstablishmentDTO");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _ErrorValidaions_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "ErrorValidaions");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _MemberDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "MemberDTO");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _TransactionSearchResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "TransactionSearchResponse");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _MemberRegisterDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "MemberRegisterDTO");
    private final static QName _TransactionResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "TransactionResponse");
    private final static QName _MemberInsuranceResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "MemberInsuranceResponse");
    private final static QName _ArrayOfMemberRegisterDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ArrayOfMemberRegisterDTO");
    private final static QName _PersonDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PersonDTO");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _ArrayOfMemberPoliceDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ArrayOfMemberPoliceDTO");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _MemberPoliceDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "MemberPoliceDTO");
    private final static QName _UploadResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "UploadResponse");
    private final static QName _GenerateMamberResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Models", "GenerateMamberResponse");
    private final static QName _PhotoAttachmentDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PhotoAttachmentDTO");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _HeaderDTO_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "HeaderDTO");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _ResultStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.Enums", "ResultStatus");
    private final static QName _ValidateMemberResponseValidateMemberResult_QNAME = new QName("http://tempuri.org/", "ValidateMemberResult");
    private final static QName _SearchTransactionResponseSearchTransactionResult_QNAME = new QName("http://tempuri.org/", "SearchTransactionResult");
    private final static QName _GetMemberPolicesPwd_QNAME = new QName("http://tempuri.org/", "pwd");
    private final static QName _GetMemberPolicesSearchValue_QNAME = new QName("http://tempuri.org/", "searchValue");
    private final static QName _GetMemberPolicesLogin_QNAME = new QName("http://tempuri.org/", "login");
    private final static QName _GetMemberPolicesResponseGetMemberPolicesResult_QNAME = new QName("http://tempuri.org/", "GetMemberPolicesResult");
    private final static QName _HeaderDTOTPAID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "TPAID");
    private final static QName _HeaderDTOTransactionDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "TransactionDate");
    private final static QName _HeaderDTOReceiverID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ReceiverID");
    private final static QName _HeaderDTODispositionFlag_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "DispositionFlag");
    private final static QName _HeaderDTOIntermediaryID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "IntermediaryID");
    private final static QName _HeaderDTOSenderID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "SenderID");
    private final static QName _HeaderDTOPayerID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PayerID");
    private final static QName _UploadMemberRegisterResponseUploadMemberRegisterResult_QNAME = new QName("http://tempuri.org/", "UploadMemberRegisterResult");
    private final static QName _DownloadMemberTransactionTransactionId_QNAME = new QName("http://tempuri.org/", "transactionId");
    private final static QName _SearchTransactionFromDate_QNAME = new QName("http://tempuri.org/", "fromDate");
    private final static QName _SearchTransactionToDate_QNAME = new QName("http://tempuri.org/", "toDate");
    private final static QName _SearchTransactionMemberId_QNAME = new QName("http://tempuri.org/", "memberId");
    private final static QName _PhotoAttachmentDTOPhoto_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Photo");
    private final static QName _DownloadMemberTransactionResponseDownloadMemberTransactionResult_QNAME = new QName("http://tempuri.org/", "DownloadMemberTransactionResult");
    private final static QName _PersonDTOEmail_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Email");
    private final static QName _PersonDTOBirthCertificateID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "BirthCertificateID");
    private final static QName _PersonDTOFullName_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "FullName");
    private final static QName _PersonDTOGDRFAFileNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "GDRFAFileNumber");
    private final static QName _PersonDTOMember_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Member");
    private final static QName _PersonDTOContactNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ContactNumber");
    private final static QName _PersonDTOCommission_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Commission");
    private final static QName _PersonDTOPassportNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PassportNumber");
    private final static QName _PersonDTOBirthDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "BirthDate");
    private final static QName _PersonDTOEmiratesIDNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "EmiratesIDNumber");
    private final static QName _PersonDTOUIDNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "UIDNumber");
    private final static QName _MemberPoliceDTOContract_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Contract");
    private final static QName _MemberPoliceDTOMemberID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "MemberID");
    private final static QName _GenerateMemberIDResponseGenerateMemberIDResult_QNAME = new QName("http://tempuri.org/", "GenerateMemberIDResult");
    private final static QName _MemberRegisterDTOHeader_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Header");
    private final static QName _MemberRegisterDTOPerson_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Person");
    private final static QName _MemberDTOPhotoAttachment_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PhotoAttachment");
    private final static QName _MemberDTOEstablishment_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "Establishment");
    private final static QName _MemberDTORelationTo_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "RelationTo");
    private final static QName _MemberDTOID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ID");
    private final static QName _UpdateMemberResponseUpdateMemberResult_QNAME = new QName("http://tempuri.org/", "UpdateMemberResult");
    private final static QName _EstablishmentDTOEntityID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "EntityID");
    private final static QName _ContractDTOEnrollmentDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "EnrollmentDate");
    private final static QName _ContractDTOMaternityCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "MaternityCopay");
    private final static QName _ContractDTOPharmacyCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PharmacyCopay");
    private final static QName _ContractDTOTPAFee_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "TPAFee");
    private final static QName _ContractDTOOPConsultationCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "OPConsultationCopay");
    private final static QName _ContractDTOIPCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "IPCopay");
    private final static QName _ContractDTODentalCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "DentalCopay");
    private final static QName _ContractDTOPolicySequence_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PolicySequence");
    private final static QName _ContractDTOOpticalCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "OpticalCopay");
    private final static QName _ContractDTOProductCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ProductCode");
    private final static QName _ContractDTODeletionDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "DeletionDate");
    private final static QName _ContractDTOOPCopay_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "OPCopay");
    private final static QName _ContractDTOProductID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "ProductID");
    private final static QName _ContractDTOPolicyID_QNAME = new QName("http://schemas.datacontract.org/2004/07/Shared.DTOs", "PolicyID");
    private final static QName _UploadMemberRegisterFileContent_QNAME = new QName("http://tempuri.org/", "fileContent");
    private final static QName _ValidateMemberNationality_QNAME = new QName("http://tempuri.org/", "nationality");
    private final static QName _ValidateMemberDateOfBirth_QNAME = new QName("http://tempuri.org/", "dateOfBirth");
    private final static QName _ValidateMemberFileNo_QNAME = new QName("http://tempuri.org/", "fileNo");
    private final static QName _ValidateMemberGender_QNAME = new QName("http://tempuri.org/", "gender");
    private final static QName _GenerateMemberIDPolicySequeance_QNAME = new QName("http://tempuri.org/", "policySequeance");
    private final static QName _GenerateMemberIDTPALicense_QNAME = new QName("http://tempuri.org/", "TPALicense");
    private final static QName _GenerateMemberIDReferenceID_QNAME = new QName("http://tempuri.org/", "referenceID");
    private final static QName _GenerateMemberIDPayerLicense_QNAME = new QName("http://tempuri.org/", "payerLicense");
    private final static QName _GenerateMemberIDDateofbirth_QNAME = new QName("http://tempuri.org/", "dateofbirth");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ttk.business.webservice.dhponewmember
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateMemberResponse }
     * 
     */
    public UpdateMemberResponse createUpdateMemberResponse() {
        return new UpdateMemberResponse();
    }

    /**
     * Create an instance of {@link UploadResponse }
     * 
     */
    public UploadResponse createUploadResponse() {
        return new UploadResponse();
    }

    /**
     * Create an instance of {@link UploadMemberRegisterResponse }
     * 
     */
    public UploadMemberRegisterResponse createUploadMemberRegisterResponse() {
        return new UploadMemberRegisterResponse();
    }

    /**
     * Create an instance of {@link SearchTransactionResponse }
     * 
     */
    public SearchTransactionResponse createSearchTransactionResponse() {
        return new SearchTransactionResponse();
    }

    /**
     * Create an instance of {@link TransactionSearchResponse }
     * 
     */
    public TransactionSearchResponse createTransactionSearchResponse() {
        return new TransactionSearchResponse();
    }

    /**
     * Create an instance of {@link DownloadMemberTransaction }
     * 
     */
    public DownloadMemberTransaction createDownloadMemberTransaction() {
        return new DownloadMemberTransaction();
    }

    /**
     * Create an instance of {@link UpdateMember }
     * 
     */
    public UpdateMember createUpdateMember() {
        return new UpdateMember();
    }

    /**
     * Create an instance of {@link GenerateMemberID }
     * 
     */
    public GenerateMemberID createGenerateMemberID() {
        return new GenerateMemberID();
    }

    /**
     * Create an instance of {@link ValidateMember }
     * 
     */
    public ValidateMember createValidateMember() {
        return new ValidateMember();
    }

    /**
     * Create an instance of {@link GenerateMemberIDResponse }
     * 
     */
    public GenerateMemberIDResponse createGenerateMemberIDResponse() {
        return new GenerateMemberIDResponse();
    }

    /**
     * Create an instance of {@link GenerateMamberResponse }
     * 
     */
    public GenerateMamberResponse createGenerateMamberResponse() {
        return new GenerateMamberResponse();
    }

    /**
     * Create an instance of {@link SearchTransaction }
     * 
     */
    public SearchTransaction createSearchTransaction() {
        return new SearchTransaction();
    }

    /**
     * Create an instance of {@link DownloadMemberTransactionResponse }
     * 
     */
    public DownloadMemberTransactionResponse createDownloadMemberTransactionResponse() {
        return new DownloadMemberTransactionResponse();
    }

    /**
     * Create an instance of {@link TransactionResponse }
     * 
     */
    public TransactionResponse createTransactionResponse() {
        return new TransactionResponse();
    }

    /**
     * Create an instance of {@link GetMemberPolicesResponse }
     * 
     */
    public GetMemberPolicesResponse createGetMemberPolicesResponse() {
        return new GetMemberPolicesResponse();
    }

    /**
     * Create an instance of {@link MemberInsuranceResponse }
     * 
     */
    public MemberInsuranceResponse createMemberInsuranceResponse() {
        return new MemberInsuranceResponse();
    }

    /**
     * Create an instance of {@link ValidateMemberResponse }
     * 
     */
    public ValidateMemberResponse createValidateMemberResponse() {
        return new ValidateMemberResponse();
    }

    /**
     * Create an instance of {@link ValidateMemberFileNoResponse }
     * 
     */
    public ValidateMemberFileNoResponse createValidateMemberFileNoResponse() {
        return new ValidateMemberFileNoResponse();
    }

    /**
     * Create an instance of {@link GetMemberPolices }
     * 
     */
    public GetMemberPolices createGetMemberPolices() {
        return new GetMemberPolices();
    }

    /**
     * Create an instance of {@link UploadMemberRegister }
     * 
     */
    public UploadMemberRegister createUploadMemberRegister() {
        return new UploadMemberRegister();
    }

    /**
     * Create an instance of {@link ArrayOfErrorValidaions }
     * 
     */
    public ArrayOfErrorValidaions createArrayOfErrorValidaions() {
        return new ArrayOfErrorValidaions();
    }

    /**
     * Create an instance of {@link ErrorValidaions }
     * 
     */
    public ErrorValidaions createErrorValidaions() {
        return new ErrorValidaions();
    }

    /**
     * Create an instance of {@link MemberPoliceDTO }
     * 
     */
    public MemberPoliceDTO createMemberPoliceDTO() {
        return new MemberPoliceDTO();
    }

    /**
     * Create an instance of {@link PhotoAttachmentDTO }
     * 
     */
    public PhotoAttachmentDTO createPhotoAttachmentDTO() {
        return new PhotoAttachmentDTO();
    }

    /**
     * Create an instance of {@link HeaderDTO }
     * 
     */
    public HeaderDTO createHeaderDTO() {
        return new HeaderDTO();
    }

    /**
     * Create an instance of {@link ContractDTO }
     * 
     */
    public ContractDTO createContractDTO() {
        return new ContractDTO();
    }

    /**
     * Create an instance of {@link ArrayOfMemberPoliceDTO }
     * 
     */
    public ArrayOfMemberPoliceDTO createArrayOfMemberPoliceDTO() {
        return new ArrayOfMemberPoliceDTO();
    }

    /**
     * Create an instance of {@link ArrayOfMemberRegisterDTO }
     * 
     */
    public ArrayOfMemberRegisterDTO createArrayOfMemberRegisterDTO() {
        return new ArrayOfMemberRegisterDTO();
    }

    /**
     * Create an instance of {@link PersonDTO }
     * 
     */
    public PersonDTO createPersonDTO() {
        return new PersonDTO();
    }

    /**
     * Create an instance of {@link MemberDTO }
     * 
     */
    public MemberDTO createMemberDTO() {
        return new MemberDTO();
    }

    /**
     * Create an instance of {@link MemberRegisterDTO }
     * 
     */
    public MemberRegisterDTO createMemberRegisterDTO() {
        return new MemberRegisterDTO();
    }

    /**
     * Create an instance of {@link EstablishmentDTO }
     * 
     */
    public EstablishmentDTO createEstablishmentDTO() {
        return new EstablishmentDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "DhpoField", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsDhpoField(String value) {
        return new JAXBElement<String>(_ErrorValidaionsDhpoField_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "Type", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsType(String value) {
        return new JAXBElement<String>(_ErrorValidaionsType_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "ErrorText", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsErrorText(String value) {
        return new JAXBElement<String>(_ErrorValidaionsErrorText_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "AdditionalReference", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsAdditionalReference(String value) {
        return new JAXBElement<String>(_ErrorValidaionsAdditionalReference_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "Transaction", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsTransaction(String value) {
        return new JAXBElement<String>(_ErrorValidaionsTransaction_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "FieldValue", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsFieldValue(String value) {
        return new JAXBElement<String>(_ErrorValidaionsFieldValue_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "RuleId", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsRuleId(String value) {
        return new JAXBElement<String>(_ErrorValidaionsRuleId_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "ObjectName", scope = ErrorValidaions.class)
    public JAXBElement<String> createErrorValidaionsObjectName(String value) {
        return new JAXBElement<String>(_ErrorValidaionsObjectName_QNAME, String.class, ErrorValidaions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateMemberFileNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "ValidateMemberFileNoResponse")
    public JAXBElement<ValidateMemberFileNoResponse> createValidateMemberFileNoResponse(ValidateMemberFileNoResponse value) {
        return new JAXBElement<ValidateMemberFileNoResponse>(_ValidateMemberFileNoResponse_QNAME, ValidateMemberFileNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErrorValidaions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "ArrayOfErrorValidaions")
    public JAXBElement<ArrayOfErrorValidaions> createArrayOfErrorValidaions(ArrayOfErrorValidaions value) {
        return new JAXBElement<ArrayOfErrorValidaions>(_ArrayOfErrorValidaions_QNAME, ArrayOfErrorValidaions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ContractDTO")
    public JAXBElement<ContractDTO> createContractDTO(ContractDTO value) {
        return new JAXBElement<ContractDTO>(_ContractDTO_QNAME, ContractDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EstablishmentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "EstablishmentDTO")
    public JAXBElement<EstablishmentDTO> createEstablishmentDTO(EstablishmentDTO value) {
        return new JAXBElement<EstablishmentDTO>(_EstablishmentDTO_QNAME, EstablishmentDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorValidaions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "ErrorValidaions")
    public JAXBElement<ErrorValidaions> createErrorValidaions(ErrorValidaions value) {
        return new JAXBElement<ErrorValidaions>(_ErrorValidaions_QNAME, ErrorValidaions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "MemberDTO")
    public JAXBElement<MemberDTO> createMemberDTO(MemberDTO value) {
        return new JAXBElement<MemberDTO>(_MemberDTO_QNAME, MemberDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionSearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "TransactionSearchResponse")
    public JAXBElement<TransactionSearchResponse> createTransactionSearchResponse(TransactionSearchResponse value) {
        return new JAXBElement<TransactionSearchResponse>(_TransactionSearchResponse_QNAME, TransactionSearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberRegisterDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "MemberRegisterDTO")
    public JAXBElement<MemberRegisterDTO> createMemberRegisterDTO(MemberRegisterDTO value) {
        return new JAXBElement<MemberRegisterDTO>(_MemberRegisterDTO_QNAME, MemberRegisterDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "TransactionResponse")
    public JAXBElement<TransactionResponse> createTransactionResponse(TransactionResponse value) {
        return new JAXBElement<TransactionResponse>(_TransactionResponse_QNAME, TransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberInsuranceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "MemberInsuranceResponse")
    public JAXBElement<MemberInsuranceResponse> createMemberInsuranceResponse(MemberInsuranceResponse value) {
        return new JAXBElement<MemberInsuranceResponse>(_MemberInsuranceResponse_QNAME, MemberInsuranceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMemberRegisterDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ArrayOfMemberRegisterDTO")
    public JAXBElement<ArrayOfMemberRegisterDTO> createArrayOfMemberRegisterDTO(ArrayOfMemberRegisterDTO value) {
        return new JAXBElement<ArrayOfMemberRegisterDTO>(_ArrayOfMemberRegisterDTO_QNAME, ArrayOfMemberRegisterDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PersonDTO")
    public JAXBElement<PersonDTO> createPersonDTO(PersonDTO value) {
        return new JAXBElement<PersonDTO>(_PersonDTO_QNAME, PersonDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMemberPoliceDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ArrayOfMemberPoliceDTO")
    public JAXBElement<ArrayOfMemberPoliceDTO> createArrayOfMemberPoliceDTO(ArrayOfMemberPoliceDTO value) {
        return new JAXBElement<ArrayOfMemberPoliceDTO>(_ArrayOfMemberPoliceDTO_QNAME, ArrayOfMemberPoliceDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberPoliceDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "MemberPoliceDTO")
    public JAXBElement<MemberPoliceDTO> createMemberPoliceDTO(MemberPoliceDTO value) {
        return new JAXBElement<MemberPoliceDTO>(_MemberPoliceDTO_QNAME, MemberPoliceDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "UploadResponse")
    public JAXBElement<UploadResponse> createUploadResponse(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UploadResponse_QNAME, UploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateMamberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Models", name = "GenerateMamberResponse")
    public JAXBElement<GenerateMamberResponse> createGenerateMamberResponse(GenerateMamberResponse value) {
        return new JAXBElement<GenerateMamberResponse>(_GenerateMamberResponse_QNAME, GenerateMamberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhotoAttachmentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PhotoAttachmentDTO")
    public JAXBElement<PhotoAttachmentDTO> createPhotoAttachmentDTO(PhotoAttachmentDTO value) {
        return new JAXBElement<PhotoAttachmentDTO>(_PhotoAttachmentDTO_QNAME, PhotoAttachmentDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "HeaderDTO")
    public JAXBElement<HeaderDTO> createHeaderDTO(HeaderDTO value) {
        return new JAXBElement<HeaderDTO>(_HeaderDTO_QNAME, HeaderDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.Enums", name = "ResultStatus")
    public JAXBElement<String> createResultStatus(String value) {
        return new JAXBElement<String>(_ResultStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateMemberFileNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ValidateMemberResult", scope = ValidateMemberResponse.class)
    public JAXBElement<ValidateMemberFileNoResponse> createValidateMemberResponseValidateMemberResult(ValidateMemberFileNoResponse value) {
        return new JAXBElement<ValidateMemberFileNoResponse>(_ValidateMemberResponseValidateMemberResult_QNAME, ValidateMemberFileNoResponse.class, ValidateMemberResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionSearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "SearchTransactionResult", scope = SearchTransactionResponse.class)
    public JAXBElement<TransactionSearchResponse> createSearchTransactionResponseSearchTransactionResult(TransactionSearchResponse value) {
        return new JAXBElement<TransactionSearchResponse>(_SearchTransactionResponseSearchTransactionResult_QNAME, TransactionSearchResponse.class, SearchTransactionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = GetMemberPolices.class)
    public JAXBElement<String> createGetMemberPolicesPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, GetMemberPolices.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "searchValue", scope = GetMemberPolices.class)
    public JAXBElement<String> createGetMemberPolicesSearchValue(String value) {
        return new JAXBElement<String>(_GetMemberPolicesSearchValue_QNAME, String.class, GetMemberPolices.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = GetMemberPolices.class)
    public JAXBElement<String> createGetMemberPolicesLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, GetMemberPolices.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberInsuranceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetMemberPolicesResult", scope = GetMemberPolicesResponse.class)
    public JAXBElement<MemberInsuranceResponse> createGetMemberPolicesResponseGetMemberPolicesResult(MemberInsuranceResponse value) {
        return new JAXBElement<MemberInsuranceResponse>(_GetMemberPolicesResponseGetMemberPolicesResult_QNAME, MemberInsuranceResponse.class, GetMemberPolicesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "TPAID", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOTPAID(String value) {
        return new JAXBElement<String>(_HeaderDTOTPAID_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "TransactionDate", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOTransactionDate(String value) {
        return new JAXBElement<String>(_HeaderDTOTransactionDate_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ReceiverID", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOReceiverID(String value) {
        return new JAXBElement<String>(_HeaderDTOReceiverID_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "DispositionFlag", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTODispositionFlag(String value) {
        return new JAXBElement<String>(_HeaderDTODispositionFlag_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "IntermediaryID", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOIntermediaryID(String value) {
        return new JAXBElement<String>(_HeaderDTOIntermediaryID_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "SenderID", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOSenderID(String value) {
        return new JAXBElement<String>(_HeaderDTOSenderID_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PayerID", scope = HeaderDTO.class)
    public JAXBElement<String> createHeaderDTOPayerID(String value) {
        return new JAXBElement<String>(_HeaderDTOPayerID_QNAME, String.class, HeaderDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadMemberRegisterResult", scope = UploadMemberRegisterResponse.class)
    public JAXBElement<UploadResponse> createUploadMemberRegisterResponseUploadMemberRegisterResult(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UploadMemberRegisterResponseUploadMemberRegisterResult_QNAME, UploadResponse.class, UploadMemberRegisterResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "transactionId", scope = DownloadMemberTransaction.class)
    public JAXBElement<String> createDownloadMemberTransactionTransactionId(String value) {
        return new JAXBElement<String>(_DownloadMemberTransactionTransactionId_QNAME, String.class, DownloadMemberTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = DownloadMemberTransaction.class)
    public JAXBElement<String> createDownloadMemberTransactionPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, DownloadMemberTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = DownloadMemberTransaction.class)
    public JAXBElement<String> createDownloadMemberTransactionLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, DownloadMemberTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fromDate", scope = SearchTransaction.class)
    public JAXBElement<String> createSearchTransactionFromDate(String value) {
        return new JAXBElement<String>(_SearchTransactionFromDate_QNAME, String.class, SearchTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = SearchTransaction.class)
    public JAXBElement<String> createSearchTransactionPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, SearchTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "toDate", scope = SearchTransaction.class)
    public JAXBElement<String> createSearchTransactionToDate(String value) {
        return new JAXBElement<String>(_SearchTransactionToDate_QNAME, String.class, SearchTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "memberId", scope = SearchTransaction.class)
    public JAXBElement<String> createSearchTransactionMemberId(String value) {
        return new JAXBElement<String>(_SearchTransactionMemberId_QNAME, String.class, SearchTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = SearchTransaction.class)
    public JAXBElement<String> createSearchTransactionLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, SearchTransaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Photo", scope = PhotoAttachmentDTO.class)
    public JAXBElement<String> createPhotoAttachmentDTOPhoto(String value) {
        return new JAXBElement<String>(_PhotoAttachmentDTOPhoto_QNAME, String.class, PhotoAttachmentDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "DownloadMemberTransactionResult", scope = DownloadMemberTransactionResponse.class)
    public JAXBElement<TransactionResponse> createDownloadMemberTransactionResponseDownloadMemberTransactionResult(TransactionResponse value) {
        return new JAXBElement<TransactionResponse>(_DownloadMemberTransactionResponseDownloadMemberTransactionResult_QNAME, TransactionResponse.class, DownloadMemberTransactionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Email", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOEmail(String value) {
        return new JAXBElement<String>(_PersonDTOEmail_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "BirthCertificateID", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOBirthCertificateID(String value) {
        return new JAXBElement<String>(_PersonDTOBirthCertificateID_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "FullName", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOFullName(String value) {
        return new JAXBElement<String>(_PersonDTOFullName_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "GDRFAFileNumber", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOGDRFAFileNumber(String value) {
        return new JAXBElement<String>(_PersonDTOGDRFAFileNumber_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Member", scope = PersonDTO.class)
    public JAXBElement<MemberDTO> createPersonDTOMember(MemberDTO value) {
        return new JAXBElement<MemberDTO>(_PersonDTOMember_QNAME, MemberDTO.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ContactNumber", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOContactNumber(String value) {
        return new JAXBElement<String>(_PersonDTOContactNumber_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Commission", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOCommission(String value) {
        return new JAXBElement<String>(_PersonDTOCommission_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PassportNumber", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOPassportNumber(String value) {
        return new JAXBElement<String>(_PersonDTOPassportNumber_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "BirthDate", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOBirthDate(String value) {
        return new JAXBElement<String>(_PersonDTOBirthDate_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "EmiratesIDNumber", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOEmiratesIDNumber(String value) {
        return new JAXBElement<String>(_PersonDTOEmiratesIDNumber_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "UIDNumber", scope = PersonDTO.class)
    public JAXBElement<String> createPersonDTOUIDNumber(String value) {
        return new JAXBElement<String>(_PersonDTOUIDNumber_QNAME, String.class, PersonDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "TPAID", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOTPAID(String value) {
        return new JAXBElement<String>(_HeaderDTOTPAID_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Email", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOEmail(String value) {
        return new JAXBElement<String>(_PersonDTOEmail_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "FullName", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOFullName(String value) {
        return new JAXBElement<String>(_PersonDTOFullName_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Contract", scope = MemberPoliceDTO.class)
    public JAXBElement<ContractDTO> createMemberPoliceDTOContract(ContractDTO value) {
        return new JAXBElement<ContractDTO>(_MemberPoliceDTOContract_QNAME, ContractDTO.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "MemberID", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOMemberID(String value) {
        return new JAXBElement<String>(_MemberPoliceDTOMemberID_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "BirthDate", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOBirthDate(String value) {
        return new JAXBElement<String>(_PersonDTOBirthDate_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PayerID", scope = MemberPoliceDTO.class)
    public JAXBElement<String> createMemberPoliceDTOPayerID(String value) {
        return new JAXBElement<String>(_HeaderDTOPayerID_QNAME, String.class, MemberPoliceDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateMamberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GenerateMemberIDResult", scope = GenerateMemberIDResponse.class)
    public JAXBElement<GenerateMamberResponse> createGenerateMemberIDResponseGenerateMemberIDResult(GenerateMamberResponse value) {
        return new JAXBElement<GenerateMamberResponse>(_GenerateMemberIDResponseGenerateMemberIDResult_QNAME, GenerateMamberResponse.class, GenerateMemberIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Header", scope = MemberRegisterDTO.class)
    public JAXBElement<HeaderDTO> createMemberRegisterDTOHeader(HeaderDTO value) {
        return new JAXBElement<HeaderDTO>(_MemberRegisterDTOHeader_QNAME, HeaderDTO.class, MemberRegisterDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Person", scope = MemberRegisterDTO.class)
    public JAXBElement<PersonDTO> createMemberRegisterDTOPerson(PersonDTO value) {
        return new JAXBElement<PersonDTO>(_MemberRegisterDTOPerson_QNAME, PersonDTO.class, MemberRegisterDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhotoAttachmentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PhotoAttachment", scope = MemberDTO.class)
    public JAXBElement<PhotoAttachmentDTO> createMemberDTOPhotoAttachment(PhotoAttachmentDTO value) {
        return new JAXBElement<PhotoAttachmentDTO>(_MemberDTOPhotoAttachment_QNAME, PhotoAttachmentDTO.class, MemberDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Contract", scope = MemberDTO.class)
    public JAXBElement<ContractDTO> createMemberDTOContract(ContractDTO value) {
        return new JAXBElement<ContractDTO>(_MemberPoliceDTOContract_QNAME, ContractDTO.class, MemberDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EstablishmentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Establishment", scope = MemberDTO.class)
    public JAXBElement<EstablishmentDTO> createMemberDTOEstablishment(EstablishmentDTO value) {
        return new JAXBElement<EstablishmentDTO>(_MemberDTOEstablishment_QNAME, EstablishmentDTO.class, MemberDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "RelationTo", scope = MemberDTO.class)
    public JAXBElement<String> createMemberDTORelationTo(String value) {
        return new JAXBElement<String>(_MemberDTORelationTo_QNAME, String.class, MemberDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ID", scope = MemberDTO.class)
    public JAXBElement<String> createMemberDTOID(String value) {
        return new JAXBElement<String>(_MemberDTOID_QNAME, String.class, MemberDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UpdateMemberResult", scope = UpdateMemberResponse.class)
    public JAXBElement<UploadResponse> createUpdateMemberResponseUpdateMemberResult(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UpdateMemberResponseUpdateMemberResult_QNAME, UploadResponse.class, UpdateMemberResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "Email", scope = EstablishmentDTO.class)
    public JAXBElement<String> createEstablishmentDTOEmail(String value) {
        return new JAXBElement<String>(_PersonDTOEmail_QNAME, String.class, EstablishmentDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ContactNumber", scope = EstablishmentDTO.class)
    public JAXBElement<String> createEstablishmentDTOContactNumber(String value) {
        return new JAXBElement<String>(_PersonDTOContactNumber_QNAME, String.class, EstablishmentDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "EntityID", scope = EstablishmentDTO.class)
    public JAXBElement<String> createEstablishmentDTOEntityID(String value) {
        return new JAXBElement<String>(_EstablishmentDTOEntityID_QNAME, String.class, EstablishmentDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "EnrollmentDate", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOEnrollmentDate(String value) {
        return new JAXBElement<String>(_ContractDTOEnrollmentDate_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "MaternityCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOMaternityCopay(String value) {
        return new JAXBElement<String>(_ContractDTOMaternityCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PharmacyCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOPharmacyCopay(String value) {
        return new JAXBElement<String>(_ContractDTOPharmacyCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "TPAFee", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOTPAFee(String value) {
        return new JAXBElement<String>(_ContractDTOTPAFee_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "OPConsultationCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOOPConsultationCopay(String value) {
        return new JAXBElement<String>(_ContractDTOOPConsultationCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "IPCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOIPCopay(String value) {
        return new JAXBElement<String>(_ContractDTOIPCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "DentalCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTODentalCopay(String value) {
        return new JAXBElement<String>(_ContractDTODentalCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PolicySequence", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOPolicySequence(String value) {
        return new JAXBElement<String>(_ContractDTOPolicySequence_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "OpticalCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOOpticalCopay(String value) {
        return new JAXBElement<String>(_ContractDTOOpticalCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ProductCode", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOProductCode(String value) {
        return new JAXBElement<String>(_ContractDTOProductCode_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "DeletionDate", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTODeletionDate(String value) {
        return new JAXBElement<String>(_ContractDTODeletionDate_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "OPCopay", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOOPCopay(String value) {
        return new JAXBElement<String>(_ContractDTOOPCopay_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "ProductID", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOProductID(String value) {
        return new JAXBElement<String>(_ContractDTOProductID_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Shared.DTOs", name = "PolicyID", scope = ContractDTO.class)
    public JAXBElement<String> createContractDTOPolicyID(String value) {
        return new JAXBElement<String>(_ContractDTOPolicyID_QNAME, String.class, ContractDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = UploadMemberRegister.class)
    public JAXBElement<String> createUploadMemberRegisterPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, UploadMemberRegister.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fileContent", scope = UploadMemberRegister.class)
    public JAXBElement<byte[]> createUploadMemberRegisterFileContent(byte[] value) {
        return new JAXBElement<byte[]>(_UploadMemberRegisterFileContent_QNAME, byte[].class, UploadMemberRegister.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = UploadMemberRegister.class)
    public JAXBElement<String> createUploadMemberRegisterLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, UploadMemberRegister.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nationality", scope = ValidateMember.class)
    public JAXBElement<String> createValidateMemberNationality(String value) {
        return new JAXBElement<String>(_ValidateMemberNationality_QNAME, String.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = ValidateMember.class)
    public JAXBElement<String> createValidateMemberPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dateOfBirth", scope = ValidateMember.class)
    public JAXBElement<String> createValidateMemberDateOfBirth(String value) {
        return new JAXBElement<String>(_ValidateMemberDateOfBirth_QNAME, String.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fileNo", scope = ValidateMember.class)
    public JAXBElement<String> createValidateMemberFileNo(String value) {
        return new JAXBElement<String>(_ValidateMemberFileNo_QNAME, String.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = ValidateMember.class)
    public JAXBElement<String> createValidateMemberLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "gender", scope = ValidateMember.class)
    public JAXBElement<Integer> createValidateMemberGender(Integer value) {
        return new JAXBElement<Integer>(_ValidateMemberGender_QNAME, Integer.class, ValidateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "policySequeance", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDPolicySequeance(String value) {
        return new JAXBElement<String>(_GenerateMemberIDPolicySequeance_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TPALicense", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDTPALicense(String value) {
        return new JAXBElement<String>(_GenerateMemberIDTPALicense_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "referenceID", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDReferenceID(String value) {
        return new JAXBElement<String>(_GenerateMemberIDReferenceID_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "payerLicense", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDPayerLicense(String value) {
        return new JAXBElement<String>(_GenerateMemberIDPayerLicense_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dateofbirth", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDDateofbirth(String value) {
        return new JAXBElement<String>(_GenerateMemberIDDateofbirth_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = GenerateMemberID.class)
    public JAXBElement<String> createGenerateMemberIDLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, GenerateMemberID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "pwd", scope = UpdateMember.class)
    public JAXBElement<String> createUpdateMemberPwd(String value) {
        return new JAXBElement<String>(_GetMemberPolicesPwd_QNAME, String.class, UpdateMember.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fileContent", scope = UpdateMember.class)
    public JAXBElement<byte[]> createUpdateMemberFileContent(byte[] value) {
        return new JAXBElement<byte[]>(_UploadMemberRegisterFileContent_QNAME, byte[].class, UpdateMember.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "login", scope = UpdateMember.class)
    public JAXBElement<String> createUpdateMemberLogin(String value) {
        return new JAXBElement<String>(_GetMemberPolicesLogin_QNAME, String.class, UpdateMember.class, value);
    }

}
