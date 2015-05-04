package ch.hearc.ig.guideresto.business;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author julien.plumez
 */
@Entity
@Table(name = "TYPES_GASTRONOMIQUES")
public class RestaurantType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TYPES_GA")
  @SequenceGenerator(name = "SEQ_TYPES_GA", sequenceName = "SEQ_TYPES_GASTRONOMIQUES",
          initialValue = 1, allocationSize = 1)
  @Column(name = "NUMERO")
  private Integer id;
  @Column(name = "LIBELLE")
  private String label;
  @Column(name = "DESCRIPTION")
  private String description;
  @OneToMany
  //voir pour les bidirectionnelles...
  private Set<Restaurant> restaurants;

  public RestaurantType() {
    this(null, null);
  }

  public RestaurantType(String label, String description) {
    this(null, label, description);
  }

  public RestaurantType(Integer id, String label, String description) {
    this.restaurants = new HashSet();
    this.id = id;
    this.label = label;
    this.description = description;
  }

  /**
   * Adds the restaurant to the list Set the restaurant's type as this
   *
   * @param rest The restaurant to add to the list
   */
  public void addRestaurant(Restaurant newRestaurant) {
    newRestaurant.setType(this);
    this.restaurants.add(newRestaurant);
  }

  /**
   * Removes the restaurant from the list Sets the restaurant's type to null
   *
   * @param rest The Restaurant to remove
   */
  public void removeRestaurant(Restaurant rest) {
    if (this.restaurants.contains(rest)) {
      this.restaurants.remove(rest);
    }

    rest.setType(null);
  }

  @Override
  public String toString() {
    return label;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Restaurant> getRestaurants() {
    return restaurants;
  }

  public void setRestaurants(Set<Restaurant> restaurants) {
    this.restaurants = restaurants;
  }
}