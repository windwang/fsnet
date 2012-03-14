package fr.univartois.ili.fsnet.commons.talk;

import java.util.Collection;
import java.util.HashMap;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

/**
 * 
 * @author habib
 * 
 */
public class RosterListenerImpl implements RosterListener {

	private HashMap<String, String> userStatus = new HashMap<String, String>();
	private boolean dirty = true;

	/**
	 * @param roster
	 */
	public RosterListenerImpl(/*Roster roster*/) {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.RosterListener#entriesAdded(java.util.Collection)
	 */
	@Override
	public void entriesAdded(Collection<String> addresses) {
		dirty = true;
//		for (String string : addresses) {
//			System.out.println(string);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.RosterListener#entriesUpdated(java.util.Collection
	 * )
	 */
	@Override
	public void entriesUpdated(Collection<String> addresses) {
		dirty = true;
//		for (String string : addresses) {
//			System.out.println(string);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.RosterListener#entriesDeleted(java.util.Collection
	 * )
	 */
	@Override
	public void entriesDeleted(Collection<String> addresses) {
		dirty = true;
//		for (String string : addresses) {
//			System.out.println(string);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.RosterListener#presenceChanged(org.jivesoftware
	 * .smack.packet.Presence)
	 */
	@Override
	public void presenceChanged(Presence presence) {
		dirty = true;
		userStatus.put(presence.getFrom(), presence.getStatus());
	}

	/**
	 * @param user
	 * @return
	 */
	public String getPresence(String user) {
		return userStatus.get(user);
	}

	/**
	 * @return a boolean
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * @param dirty
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public HashMap<String, String> getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(HashMap<String, String> userStatus) {
		this.userStatus = userStatus;
	}

}
