package org.example.assignment2demo;

public class Recipes {

    private String recipeName;
    private String recipeDescription;
    private String recipeIngredients;
    private int recipeQuantity;

    public Recipes(String recipeName, String recipeDescription, String recipeIngredients, int recipeQuantity) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeIngredients = recipeIngredients;
        this.recipeQuantity = recipeQuantity;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public int getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(int recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", recipeIngredients='" + recipeIngredients + '\'' +
                ", recipeQuantity=" + recipeQuantity +
                '}';
    }
}
