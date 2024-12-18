package org.example.assignment2demo;

public class Drinks {
    private String drinkName;
    private String countryofOrigin;
    private String description;
    private String imageURL;

    public Drinks(String drinkName, String countryofOrigin, String description, String imageURL) {
        this.drinkName = drinkName;
        this.countryofOrigin = countryofOrigin;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getCountryofOrigin() {
        return countryofOrigin;
    }

    public void setCountryofOrigin(String countryofOrigin) {
        this.countryofOrigin = countryofOrigin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Drinks{" +
                "drinkName='" + drinkName + '\'' +
                ", countryofOrigin='" + countryofOrigin + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
