package fr.univartois.ili.fsnet.admin.tags;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import fr.univartois.ili.fsnet.admin.tags.utils.AbstractSingleLoopTag;

public class DateJourTag extends AbstractSingleLoopTag {

	private static final long serialVersionUID = 1L;

	private String var;

	@Override
	public void retrieveInfos(Map<String, Object> infos) {
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int jour = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int mois = calendar.get(GregorianCalendar.MONTH) + 1;
		int année = calendar.get(GregorianCalendar.YEAR);
		String dateJour = jour + "/" + mois + "/" + année;
		infos.put(var, dateJour);
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
}
