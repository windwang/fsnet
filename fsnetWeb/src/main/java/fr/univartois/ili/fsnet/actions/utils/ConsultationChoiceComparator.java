package fr.univartois.ili.fsnet.actions.utils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.validator.routines.DateValidator;

import fr.univartois.ili.fsnet.entities.ConsultationChoice;

public class ConsultationChoiceComparator implements Comparator<ConsultationChoice> , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(ConsultationChoice c1, ConsultationChoice c2) {
		Date date1 = DateValidator.getInstance().validate(c1.getIntituled(), "dd/MM/yyyy");
		Date date2 = DateValidator.getInstance().validate(c2.getIntituled(), "dd/MM/yyyy");
		if (date1 != null && date2 != null){
			return date1.compareTo(date2);
		}else{
			return c1.getIntituled().compareTo(c2.getIntituled());
		}
	}

}
