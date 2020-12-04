package ITCExcercies.ITCRestaurant.Dao;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private double price;

    public Course(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price=price;
    }

    public Course() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String courseName) {
        this.name = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String courseDescription) {
        this.description = courseDescription;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
