package fr.univartois.ili.fsnet.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class ActivityReport
 * 
 */
@Entity
public class ActivityReport {

    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * The list of interactions that the activities report contains.
     */
    @OneToMany(mappedBy = "report")
    private List<Interaction> lesInteractions;
    /**
     * The date of the activities report.
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateRapport;

    /**
     * Constructor of the class ActivityReport.
     */
    public ActivityReport() {
    }

    /**
     * Constructor of the class ActivityReport.
     *
     * @param lesInteractions
     * @param dateRapport
     */
    public ActivityReport(List<Interaction> lesInteractions, Date dateRapport) {
        this.lesInteractions = lesInteractions;
        this.dateRapport = dateRapport;
    }

    /**
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives an identifier to the activities report.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the list of interactions that the activities report contains.
     */
    public List<Interaction> getLesInteractions() {
        return lesInteractions;
    }

    /**
     * Give a list of interactions to the activities report.
     *
     * @param lesInteractions
     */
    public void setLesInteractions(List<Interaction> lesInteractions) {
        this.lesInteractions = lesInteractions;
    }

    /**
     *
     * @return the date of the activities report.
     */
    public Date getDateRapport() {
        return dateRapport;
    }

    /**
     * Gives a date to the activities report.
     *
     * @param dateRapport
     */
    public void setDateRapport(Date dateRapport) {
        this.dateRapport = dateRapport;
    }
}
