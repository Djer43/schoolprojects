package ch.hearc.ig.guideresto.business;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author julien.plumez
 */
@Embeddable
public class Localisation implements Serializable {
    
    @Column(name = "ADRESSE")
    private String street;
    @ManyToOne
    //il peut y avoir plusieurs localisations pour une ville
    @JoinColumn(name = "FK_VILL")
    private City city;

    public Localisation() {
        this(null, null);
    }
    
    public Localisation(String street, City city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}