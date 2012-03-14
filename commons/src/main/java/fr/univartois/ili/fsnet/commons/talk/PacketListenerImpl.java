package fr.univartois.ili.fsnet.commons.talk;

import java.util.LinkedList;
import java.util.Queue;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * listener for Packet
 * 
 * @author habib
 * 
 */
public class PacketListenerImpl implements PacketListener {

	private Queue<String> messageQueue = new LinkedList<String>();

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
		if (packet instanceof Message) {
			Message message = (Message) packet;

			String body = message.getBody();
			String from = message.getFrom();
			messageQueue.add(from + ": " + body);

		}
	}

	public Queue<String> getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(Queue<String> messageQueue) {
		this.messageQueue = messageQueue;
	}

}