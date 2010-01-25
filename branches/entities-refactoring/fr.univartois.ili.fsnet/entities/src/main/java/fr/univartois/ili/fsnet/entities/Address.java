package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
@Embeddable
public class Address implements Serializable {

    private String address;

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the value of address
     *
     * @param address new value of address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    private String city;

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }
}
