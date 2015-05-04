package ch.hearc.ig.guideresto.business;

import java.io.Serializable;
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
@Table(name = "VILLES")
public class City implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VI")
  @SequenceGenerator(name = "SEQ_VI", sequenceName = "SEQ_VILLES",
          initialValue = 1, allocationSize = 1)
  @Column(name = "NUMERO")
  private Integer id;
  @Column(name = "CODE_POSTAL")
  private String zipCode;
  @Column(name = "NOM_VILLE")
  private String cityName;
  @OneToMany
  private Set<Restaurant> restaurants;
  //une ville pour plusieurs restaurants => OneToMany 1-N

  public City() {
    this(null, null);
  }

  public City(String zipCode, String cityName) {
    this(null, zipCode, cityName);
  }

  public City(Integer id, String zipCode, String cityName) {
    this.id = id;
    this.zipCode = zipCode;
    this.cityName = cityName;
    this.restaurants = new HashSet();
  }

  /**
   * Adds the restaurant passed in the parameter to the list Sets the
   * restaurant's city as this
   *
   * @param newRestaurant The restaurant to add to the city.
   */
  public void addRestaurant(Restaurant newRestaurant) {
    newRestaurant.getAddress().setCity(this);
    this.restaurants.add(newRestaurant);
  }

  public void removeRestaurant(Restaurant restaurant) {
    if (this.restaurants.contains(restaurant)) {
      this.restaurants.remove(restaurant);
    }

    restaurant.getAddress().setCity(null);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String city) {
    this.cityName = city;
  }

  public Set<Restaurant> getRestaurants() {
    return restaurants;
  }

  public void setRestaurants(Set<Restaurant> restaurants) {
    this.restaurants = restaurants;
  }
}