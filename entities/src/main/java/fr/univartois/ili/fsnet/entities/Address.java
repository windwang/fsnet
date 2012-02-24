package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class Address implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address;

    public Address() {
    }

    public Address(String address, String city) {
        if (address == null || city == null) {
            throw new IllegalArgumentException();
        }
        this.address = address;
        this.city = city;
    }

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Address other = (Address) obj;
		if (address == null) {
			if (other.address != null){
				return false;
			}
		} else if (!address.equals(other.address)){
			return false;
		}
		if (city == null) {
			if (other.city != null){
				return false;
			}
		} else if (!city.equals(other.city)){
			return false;
		}
		return true;
	}

	

	
    
}
