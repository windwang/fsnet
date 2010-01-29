package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Announcement.
 * 
 */
@Entity
public class Announcement extends Interaction {

    private static final long serialVersionUID = 1L;
    private String content;
    /**
     * The date of the end's ad.
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    /**
     * Constructor of the class Announcement.
     */
    public Announcement() {
    }

    /**
     * Constructor of the class Announcement.
     * @param nom
     * @param datePublication
     * @param content
     * @param endDate
     * @param visible
     * @param createur
     */
    public Announcement(SocialEntity creator, String title, String content,
            Date endDate, boolean isPrivate) {
        super(creator, title);
        if (content == null || endDate == null) {
            throw new IllegalArgumentException();
        }
        this.content = content;
        this.endDate = endDate;
    }

    /**
     *
     * @return The date of the end's ad .
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gives an end date for the ad.
     *
     * @param endDate
     *            .
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
