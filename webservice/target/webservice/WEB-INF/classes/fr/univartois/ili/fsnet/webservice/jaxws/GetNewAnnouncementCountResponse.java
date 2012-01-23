
package fr.univartois.ili.fsnet.webservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getNewAnnouncementCountResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNewAnnouncementCountResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
public class GetNewAnnouncementCountResponse {

    @XmlElement(name = "return", namespace = "")
    private Integer _return;

    /**
     * 
     * @return
     *     returns Integer
     */
    public Integer getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Integer _return) {
        this._return = _return;
    }

}
