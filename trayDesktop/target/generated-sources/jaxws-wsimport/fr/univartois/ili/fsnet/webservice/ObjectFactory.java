
package fr.univartois.ili.fsnet.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.univartois.ili.fsnet.webservice package. 
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

    private final static QName _GetNewEventsCountResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewEventsCountResponse");
    private final static QName _GetNewDemandeCountResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewDemandeCountResponse");
    private final static QName _IsMember_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "isMember");
    private final static QName _GetNewConsultationCountResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewConsultationCountResponse");
    private final static QName _GetNewMessages_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewMessages");
    private final static QName _IsMemberResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "isMemberResponse");
    private final static QName _GetNewAnnouncementCount_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewAnnouncementCount");
    private final static QName _GetNewConsultationCount_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewConsultationCount");
    private final static QName _GetNewEventsCount_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewEventsCount");
    private final static QName _GetNewMessagesResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewMessagesResponse");
    private final static QName _GetNewAnnouncementCountResponse_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewAnnouncementCountResponse");
    private final static QName _GetNewDemandeCount_QNAME = new QName("http://webservice.fsnet.ili.univartois.fr/", "getNewDemandeCount");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.univartois.ili.fsnet.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetNewConsultationCount }
     * 
     */
    public GetNewConsultationCount createGetNewConsultationCount() {
        return new GetNewConsultationCount();
    }

    /**
     * Create an instance of {@link GetNewAnnouncementCountResponse }
     * 
     */
    public GetNewAnnouncementCountResponse createGetNewAnnouncementCountResponse() {
        return new GetNewAnnouncementCountResponse();
    }

    /**
     * Create an instance of {@link GetNewAnnouncementCount }
     * 
     */
    public GetNewAnnouncementCount createGetNewAnnouncementCount() {
        return new GetNewAnnouncementCount();
    }

    /**
     * Create an instance of {@link GetNewEventsCountResponse }
     * 
     */
    public GetNewEventsCountResponse createGetNewEventsCountResponse() {
        return new GetNewEventsCountResponse();
    }

    /**
     * Create an instance of {@link GetNewMessagesResponse }
     * 
     */
    public GetNewMessagesResponse createGetNewMessagesResponse() {
        return new GetNewMessagesResponse();
    }

    /**
     * Create an instance of {@link IsMember }
     * 
     */
    public IsMember createIsMember() {
        return new IsMember();
    }

    /**
     * Create an instance of {@link GetNewEventsCount }
     * 
     */
    public GetNewEventsCount createGetNewEventsCount() {
        return new GetNewEventsCount();
    }

    /**
     * Create an instance of {@link GetNewConsultationCountResponse }
     * 
     */
    public GetNewConsultationCountResponse createGetNewConsultationCountResponse() {
        return new GetNewConsultationCountResponse();
    }

    /**
     * Create an instance of {@link GetNewMessages }
     * 
     */
    public GetNewMessages createGetNewMessages() {
        return new GetNewMessages();
    }

    /**
     * Create an instance of {@link GetNewDemandeCountResponse }
     * 
     */
    public GetNewDemandeCountResponse createGetNewDemandeCountResponse() {
        return new GetNewDemandeCountResponse();
    }

    /**
     * Create an instance of {@link IsMemberResponse }
     * 
     */
    public IsMemberResponse createIsMemberResponse() {
        return new IsMemberResponse();
    }

    /**
     * Create an instance of {@link WsPrivateMessage }
     * 
     */
    public WsPrivateMessage createWsPrivateMessage() {
        return new WsPrivateMessage();
    }

    /**
     * Create an instance of {@link GetNewDemandeCount }
     * 
     */
    public GetNewDemandeCount createGetNewDemandeCount() {
        return new GetNewDemandeCount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewEventsCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewEventsCountResponse")
    public JAXBElement<GetNewEventsCountResponse> createGetNewEventsCountResponse(GetNewEventsCountResponse value) {
        return new JAXBElement<GetNewEventsCountResponse>(_GetNewEventsCountResponse_QNAME, GetNewEventsCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewDemandeCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewDemandeCountResponse")
    public JAXBElement<GetNewDemandeCountResponse> createGetNewDemandeCountResponse(GetNewDemandeCountResponse value) {
        return new JAXBElement<GetNewDemandeCountResponse>(_GetNewDemandeCountResponse_QNAME, GetNewDemandeCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsMember }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "isMember")
    public JAXBElement<IsMember> createIsMember(IsMember value) {
        return new JAXBElement<IsMember>(_IsMember_QNAME, IsMember.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewConsultationCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewConsultationCountResponse")
    public JAXBElement<GetNewConsultationCountResponse> createGetNewConsultationCountResponse(GetNewConsultationCountResponse value) {
        return new JAXBElement<GetNewConsultationCountResponse>(_GetNewConsultationCountResponse_QNAME, GetNewConsultationCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewMessages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewMessages")
    public JAXBElement<GetNewMessages> createGetNewMessages(GetNewMessages value) {
        return new JAXBElement<GetNewMessages>(_GetNewMessages_QNAME, GetNewMessages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsMemberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "isMemberResponse")
    public JAXBElement<IsMemberResponse> createIsMemberResponse(IsMemberResponse value) {
        return new JAXBElement<IsMemberResponse>(_IsMemberResponse_QNAME, IsMemberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewAnnouncementCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewAnnouncementCount")
    public JAXBElement<GetNewAnnouncementCount> createGetNewAnnouncementCount(GetNewAnnouncementCount value) {
        return new JAXBElement<GetNewAnnouncementCount>(_GetNewAnnouncementCount_QNAME, GetNewAnnouncementCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewConsultationCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewConsultationCount")
    public JAXBElement<GetNewConsultationCount> createGetNewConsultationCount(GetNewConsultationCount value) {
        return new JAXBElement<GetNewConsultationCount>(_GetNewConsultationCount_QNAME, GetNewConsultationCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewEventsCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewEventsCount")
    public JAXBElement<GetNewEventsCount> createGetNewEventsCount(GetNewEventsCount value) {
        return new JAXBElement<GetNewEventsCount>(_GetNewEventsCount_QNAME, GetNewEventsCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewMessagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewMessagesResponse")
    public JAXBElement<GetNewMessagesResponse> createGetNewMessagesResponse(GetNewMessagesResponse value) {
        return new JAXBElement<GetNewMessagesResponse>(_GetNewMessagesResponse_QNAME, GetNewMessagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewAnnouncementCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewAnnouncementCountResponse")
    public JAXBElement<GetNewAnnouncementCountResponse> createGetNewAnnouncementCountResponse(GetNewAnnouncementCountResponse value) {
        return new JAXBElement<GetNewAnnouncementCountResponse>(_GetNewAnnouncementCountResponse_QNAME, GetNewAnnouncementCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewDemandeCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fsnet.ili.univartois.fr/", name = "getNewDemandeCount")
    public JAXBElement<GetNewDemandeCount> createGetNewDemandeCount(GetNewDemandeCount value) {
        return new JAXBElement<GetNewDemandeCount>(_GetNewDemandeCount_QNAME, GetNewDemandeCount.class, null, value);
    }

}
