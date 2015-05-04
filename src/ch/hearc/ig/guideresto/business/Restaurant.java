package ch.hearc.ig.guideresto.business;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author julien.plumez
 */
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REST")
  @SequenceGenerator(name = "SEQ_REST", sequenceName = "SEQ_RESTAURANTS",
          initialValue = 1, allocationSize = 1)
  @Column(name = "NUMERO")
  private Integer id;
  @Column(name = "NOM")
  private String name;
  @Column(name = "DESCRIPTION")
  private String description;
  @Column(name = "SITE_WEB")
  private String website;
//  @OneToMany
  //a voir comment on fait avec les bi-directionnelles...s
  @Transient
  private Set<Evaluation> evaluations;
  @Embedded
  private Localisation address;
  //l'adresse n'a pas d'existence propre sans le restaurant
  @ManyToOne
  @JoinColumn(name = "FK_TYPE")
  private RestaurantType type;

  public Restaurant() {
    this(null, null, null, null, null, null);
  }

  public Restaurant(Integer id, String name, String description, String website, String street, City city, RestaurantType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.website = website;
    this.evaluations = new HashSet();
    this.address = new Localisation(street, city);
    this.type = type;
  }

  public Restaurant(Integer id, String name, String description, String website, Localisation address, RestaurantType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.website = website;
    this.evaluations = new HashSet();
    this.address = address;
    this.type = type;
  }

  /**
   * Adds the evaluation to the list, and sets the evaluation's restaurant as
   * this.
   *
   * @param eval The evaluation to add to the list
   */
  public void addEvaluation(Evaluation eval) {
    eval.setRestaurant(this);
    this.evaluations.add(eval);
  }

  /**
   * Removes the evaluation of the list, and sets the evaluation's restaurant as
   * null.
   *
   * @param eval The evaluation to remove of the list
   */
  public void removeEvaluation(Evaluation eval) {
    if (this.evaluations.contains(eval)) {
      this.evaluations.remove(eval);
    }
    eval.setRestaurant(null);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public Set<Evaluation> getEvaluations() {
    return evaluations;
  }

  public void setEvaluations(Set<Evaluation> evaluations) {
    this.evaluations = evaluations;
  }

  public Localisation getAddress() {
    return address;
  }

  public void setAddress(Localisation address) {
    this.address = address;
  }

  public RestaurantType getType() {
    return type;
  }

  public void setType(RestaurantType type) {
    this.type = type;
  }
}