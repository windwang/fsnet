package fr.univartois.ili.fsnet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import fr.univartois.ili.fsnet.entities.Address;

public class GeolocalisationTag extends SimpleTagSupport {

	private Address address;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		String addr = address.getAddress();
		String city = address.getCity();

		out.println("<div id='mapCanvas' class='mapCanvas'></div>");
		String value = "";
		if (addr.isEmpty() == false || city.isEmpty() == false) {
			value += addr + " " + city;
		}
		out.println("<input id='address' type='hidden' value=' " + value
				+ "'></div>");

		out.println("<script type='text/javascript'> document.onload = initializeGeolocalisation(); </script>");

	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
