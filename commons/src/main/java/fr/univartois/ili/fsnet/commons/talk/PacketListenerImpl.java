package fr.univartois.ili.fsnet.commons.talk;

import java.util.LinkedList;
import java.util.Queue;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * listener for Packet
 * 
 * @author habib
 * 
 */
public class PacketListenerImpl implements PacketListener {

	Queue<String> messageQueue = new LinkedList<String>();

	/**
	 * @return a String
	 */
	public String getLastMessage() {

		return messageQueue.poll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.
	 * smack.packet.Packet)
	 */
	@Override
	public void processPacket(Packet packet) {
		if (packet.getError() != null) {
			System.out.println("Error:" + packet.getError());
		}

		if (packet instanceof Presence) {
			Presence presencePacket = (Presence) packet;
			if (presencePacket.getType().equals(Presence.Type.subscribe)) {
				System.out.println("Subscription request");

			}
		}
		
		if (packet instanceof Message) {
			Message message = (Message) packet;

			String body = message.getBody();
			String from = message.getFrom();
			System.out.println(from + body);
			messageQueue.add(from + ": " + body);

		}
		System.out.println(packet.getFrom());
	}

}