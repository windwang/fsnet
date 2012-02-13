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

	/**
	 * @param msg
	 * @param name
	 * @param mailXmpp
	 */
	public TalkJsonMsg(String msg, String name, String mailXmpp) {
		super();
		this.msg = msg;
		this.name = name;
		this.mailXmpp = mailXmpp;
	}

	/**
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getMailXmpp() {
		return mailXmpp;
	}

	/**
	 * @param mailXmpp
	 */
	public void setMailXmpp(String mailXmpp) {
		this.mailXmpp = mailXmpp;
	}

}
