package fr.univartois.ili.fsnet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * The class Decideur.
 * 
 */
@Entity
public class Decideur {

    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * A decision-maker is a social entity.
     */
    @ManyToOne
    @Column(nullable = false)
    private EntiteSociale entSociale;
    /**
     * A decision-maker governs interaction.
     */
    @OneToOne
    @Column(nullable = false)
    private Interaction interaction;

    /**
     * Constructor of the class Decideur.
     */
    public Decideur() {
    }

    /**
     * Constructor of the class Decideur.
     *
     * @param entSociale
     *            .
     * @param interaction
     *            .
     */
    public Decideur(EntiteSociale entSociale, Interaction interaction) {
        this.entSociale = entSociale;
        this.interaction = interaction;
    }

    /**
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives an identifier to the decision-maker.
     *
     * @param id
     *            .
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the social entity.
     */
    public EntiteSociale getEntSociale() {
        return entSociale;
    }

    /**
     * Links a decision-maker to a social entity.
     *
     * @param entSociale
     *            .
     */
    public void setEntSociale(EntiteSociale entSociale) {
        this.entSociale = entSociale;
    }

    /**
     *
     * @return the interaction that governs the decision-maker.
     */
    public Interaction getInteraction() {
        return interaction;
    }

    /**
     * Gives an interaction to the decision-maker.
     *
     * @param interaction
     *            .
     */
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }
}
