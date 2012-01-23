
package fr.univartois.ili.fsnet.webservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getNewMessages", namespace = "http://webservice.fsnet.ili.univartois.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNewMessages", namespace = "http://webservice.fsnet.ili.univartois.fr/", propOrder = {
    "login",
    "password"
})
public class GetNewMessages {

    @XmlElement(name = "login", namespace = "")
    private String login;
    @XmlElement(name = "password", namespace = "")
    private String password;

    /**
     * 
     * @return
     *     returns String
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * 
     * @param login
     *     the value for the login property
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 
     * @param password
     *     the value for the password property
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
