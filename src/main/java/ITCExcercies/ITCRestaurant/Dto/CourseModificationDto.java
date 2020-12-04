package ITCExcercies.ITCRestaurant.Dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CourseModificationDto {

    @NotEmpty(message = "The \"name\" field required!")
    private String name;

    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "The \"description\" field required!")
    private String description;

    @Min(value = 1, message = "The \"price\" field cannot be null or negative!")
    private double price;

    public CourseModificationDto(String name, Long id, String description, double price) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public CourseModificationDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
