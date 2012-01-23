
package fr.univartois.ili.fsnet.webservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import fr.univartois.ili.fsnet.webservice.WsPrivateMessage;

@XmlRootElement(name = "getNewMessagesResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNewMessagesResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
public class GetNewMessagesResponse {

    @XmlElement(name = "return", namespace = "")
    private List<WsPrivateMessage> _return;

    /**
     * 
     * @return
     *     returns List<WsPrivateMessage>
     */
    public List<WsPrivateMessage> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<WsPrivateMessage> _return) {
        this._return = _return;
    }

}
