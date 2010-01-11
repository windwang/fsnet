
package fr.univartois.ili.fsnet.webservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getNumberOfNewEventsResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNumberOfNewEventsResponse", namespace = "http://webservice.fsnet.ili.univartois.fr/")
public class GetNumberOfNewEventsResponse {

    @XmlElement(name = "nbevenement", namespace = "")
    private int nbevenement;

    /**
     * 
     * @return
     *     returns int
     */
    public int getNbevenement() {
        return this.nbevenement;
    }

    /**
     * 
     * @param nbevenement
     *     the value for the nbevenement property
     */
    public void setNbevenement(int nbevenement) {
        this.nbevenement = nbevenement;
    }

}
