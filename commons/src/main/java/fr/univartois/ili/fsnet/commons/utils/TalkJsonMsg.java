package fr.univartois.ili.fsnet.commons.utils;

/**
 * POJO represent JSON messsage
 * 
 * @author habib
 * 
 */
public class TalkJsonMsg {

	private String msg;
	private String name;
	private String mailXmpp;

	public TalkJsonMsg(String msg, String name, String mailXmpp) {
		super();
		this.msg = msg;
		this.name = name;
		this.mailXmpp = mailXmpp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailXmpp() {
		return mailXmpp;
	}

	public void setMailXmpp(String mailXmpp) {
		this.mailXmpp = mailXmpp;
	}

}
