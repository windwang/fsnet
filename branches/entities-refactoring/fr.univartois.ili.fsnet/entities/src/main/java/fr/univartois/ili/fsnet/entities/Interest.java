package fr.univartois.ili.fsnet.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * 
 * The class Interest.
 * 
 */
@Entity
public class Interest {

    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * The list of social entities that are affected by this interest.
     */
    @ManyToMany(mappedBy = "interests")
    @JoinColumn(nullable = false)
    private Set<SocialEntity> entities = new HashSet<SocialEntity>();
    /**
     * The interest name.
     */
    @Column(nullable = false, unique = true)
    private String nomInteret;

    /**
     * Constructor of the class Interest.
     */
    public Interest() {
    }

    /**
     * Constructor of the class Interest.
     *
     * @param entities
     * @param name
     */
    public Interest(String name) {
        this.nomInteret = name;
    }

    /**
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives an identifier to the interest.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the list of social entities that are affected by this interest.
     */
    public Set<SocialEntity> getEntities() {
        return entities;
    }

    /**
     * Gives the list of social entities that are affected by this interest.
     *
     * @param entities
     */
    public void setEntities(Set<SocialEntity> entities) {
        this.entities = entities;
    }

    /**
     *
     * @return the interest name.
     */
    public String getName() {
        return nomInteret;
    }

    /**
     * Gives a name to the interest.
     *
     * @param name
     */
    public void setName(String name) {
        this.nomInteret = name;
    }
}
