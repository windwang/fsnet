package fr.univartois.ili.fsnet.commons.talk;

import java.util.Collection;
import java.util.HashMap;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

/**
 * 
 * @author habib
 * 
 */
public class RosterListenerImpl implements RosterListener {

	HashMap<String, String> userStatus = new HashMap<String, String>();
	boolean dirty = true;

	public RosterListenerImpl(Roster roster) {
		super();
	}

	@Override
	public void entriesAdded(Collection<String> addresses) {
		dirty = true;
		System.out.println("Entry added");
		for (String string : addresses) {
			System.out.println(string);

		}
	}

	@Override
	public void entriesUpdated(Collection<String> addresses) {
		dirty = true;
		System.out.println("Entry update");
		for (String string : addresses) {
			System.out.println(string);
		}
	}

	@Override
	public void entriesDeleted(Collection<String> addresses) {
		dirty = true;
		System.out.println("Entry deleted");
		for (String string : addresses) {
			System.out.println(string);
		}
	}

	@Override
	public void presenceChanged(Presence presence) {
		dirty = true;
		System.out.println("Presenceof " + presence.getFrom() + " changed to"
				+ presence.getStatus());
		userStatus.put(presence.getFrom(), presence.getStatus());
	}

	public String getPresence(String user) {
		return userStatus.get(user);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
