package ITCExcercies.ITCRestaurant.Dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    private String id;
    private double price;
    String name;

    public Coupon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coupon(String id, double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }
}
