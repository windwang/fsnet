package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.ManyToOne;

public class PrivateMessage extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String subject;
    @ManyToOne
    private SocialEntity to;

    public PrivateMessage(String body, SocialEntity from, String subject, SocialEntity to) {
        super(body, from);
        if (subject == null || to == null) {
            throw new IllegalArgumentException();
        }
        this.subject = subject;
        this.to = to;
    }

    /**
     * Get the subject
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject
     *
     * @param subject new value of subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get the recipient
     *
     * @return the value of to
     */
    public SocialEntity getTo() {
        return to;
    }

    /**
     * Set the recipient
     *
     * @param to new value of to
     */
    public void setTo(SocialEntity to) {
        this.to = to;
    }
}
