package org.example.assignment2demo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.assignment2demo.ADT.HashTables;
import org.example.assignment2demo.ADT.LinkedList;
import org.example.assignment2demo.ADT.LinkedNode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RecipesController {
    @FXML
    private TextField RecipeNameField;
    @FXML
    private TextField RecipeDescriptionField;
    @FXML
    private TextField RecipeIngredientsField;
    @FXML
    private TextField RecipeQuantitiesField;
    @FXML
    public ListView<Recipes> recipesListView;
    @FXML
    public ListView searchRecipesListView;

    private HashTables<String, Recipes> RecipesHashTables = new HashTables<>();

    @FXML
    public void addRecipe(){
        String recipeName = RecipeNameField.getText();
        String recipeDescription = RecipeDescriptionField.getText();
        String recipeIngredients = RecipeIngredientsField.getText();
        int recipeQuantities = Integer.valueOf(RecipeQuantitiesField.getText());

        Recipes newRecipe = new Recipes(recipeName, recipeDescription, recipeIngredients, recipeQuantities);
        DrinksApplication.recipes.addElement(newRecipe);
        RecipesHashTables.put(recipeName, newRecipe);
        System.out.println("Recipe added: " + newRecipe);
        populateListView();
    }

    @FXML
    public void deleteRecipe(ActionEvent actionEvent){
        int selectedIndex = recipesListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            System.out.println("No recipe selected for deletion.");
            return;
        }

        try {
            Recipes selectedRecipes = DrinksApplication.recipes.getElement(selectedIndex);

            DrinksApplication.recipes.deleteElement(selectedIndex);

            RecipesHashTables.remove(selectedRecipes.getRecipeName());

            populateListView();
            System.out.println("Recipe deleted: " + selectedRecipes.getRecipeName());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds. List may be empty or invalid index.");
        }
    }

    public void deleteAllRecipes(){
        DrinksApplication.recipes.deleteEntireList();
        RecipesHashTables = new HashTables<>();
        populateListView();
        System.out.println("All recipes deleted successfully.");
    }

    @FXML
    public void updateRecipes(ActionEvent actionEvent){
        Recipes selectedRecipes = recipesListView.getSelectionModel().getSelectedItem();

        if (selectedRecipes == null) {
            System.out.println("No recipes selected for editing.");
            return;
        }

        // Validate input fields
        if (RecipeNameField.getText().isEmpty() || RecipeDescriptionField.getText().isEmpty()
                || RecipeIngredientsField.getText().isEmpty() || RecipeQuantitiesField.getText().isEmpty()) {
            System.out.println("All fields must be filled to update the recipe.");
            return;
        }

        // Remove old entry from HashTable
        RecipesHashTables.remove(selectedRecipes.getRecipeName());

        // Update drink details
        selectedRecipes.setRecipeName(RecipeNameField.getText());
        selectedRecipes.setRecipeDescription(RecipeDescriptionField.getText());
        selectedRecipes.setRecipeIngredients(RecipeIngredientsField.getText());
        selectedRecipes.setRecipeQuantity(Integer.valueOf(RecipeQuantitiesField.getText()));

        // Add updated drink back to HashTable
        RecipesHashTables.put(selectedRecipes.getRecipeName(), selectedRecipes);
        populateListView();
        System.out.println("Recipes updated successfully.");
    }

    @FXML
    public void searchRecipesByName(ActionEvent actionEvent){
        LinkedNode<Recipes> IN = DrinksApplication.recipes.getHead();
        searchRecipesListView.getItems().clear();

        while(IN != null){
            if(IN.getContents().getRecipeName().contains(RecipeNameField.getText())){
                searchRecipesListView.getItems().add(IN.contents);
            }
            IN = IN.next;
        }
    }

    @FXML
    public void searchRecipesByDescription(ActionEvent actionEvent){
        LinkedNode<Recipes> IN1 = DrinksApplication.recipes.getHead();
        searchRecipesListView.getItems().clear();

        while(IN1 != null){
            if(IN1.getContents().getRecipeDescription().contains(RecipeDescriptionField.getText())){
                searchRecipesListView.getItems().add(IN1.contents);
            }
            IN1 = IN1.next;
        }
    }

    @FXML
    public void searchRecipesByIngredients(ActionEvent actionEvent){
        LinkedNode<Recipes> IN2 = DrinksApplication.recipes.getHead();
        searchRecipesListView.getItems().clear();

        while(IN2 != null){
            if(IN2.getContents().getRecipeIngredients().contains(RecipeIngredientsField.getText())){
                searchRecipesListView.getItems().add(IN2.contents);
            }
            IN2 = IN2.next;
        }
    }

    @FXML
    public void searchRecipesByQuantity(ActionEvent actionEvent){
        LinkedNode<Recipes> IN3 = DrinksApplication.recipes.getHead();
        searchRecipesListView.getItems().clear();

        while(IN3 != null){
            if(IN3.getContents().getRecipeQuantity() == Integer.valueOf(RecipeQuantitiesField.getText())){
                searchRecipesListView.getItems().add(IN3.contents);
            }
            IN3 = IN3.next;
        }
    }

    @FXML
    public void sortRecipesByName(ActionEvent actionEvent) {
        if (DrinksApplication.recipes.isEmpty()) {
            System.out.println("The recipe list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Recipes> current = DrinksApplication.recipes.head;
        while (current != null) {
            LinkedNode<Recipes> minNode = current;
            LinkedNode<Recipes> index = current.next;

            while (index != null) {
                if (index.contents.getRecipeName().compareToIgnoreCase(minNode.contents.getRecipeName()) < 0) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Recipes temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Recipes sorted by name successfully.");
    }

    @FXML
    public void sortRecipesByQuantity(ActionEvent actionEvent) {
        if (DrinksApplication.recipes.isEmpty()) {
            System.out.println("The recipe list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Recipes> current = DrinksApplication.recipes.head;

        while (current != null) {
            LinkedNode<Recipes> minNode = current;
            LinkedNode<Recipes> index = current.next;

            while (index != null) {
                if (index.contents.getRecipeQuantity() < minNode.contents.getRecipeQuantity()) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Recipes temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Recipes sorted by quantity successfully.");
    }

    @FXML
    public void searchRecipesByNameUsingHashTable(ActionEvent actionEvent) {
        String name = RecipeNameField.getText();
        if (name.isEmpty()) {
            System.out.println("Please enter an recipe name.");
            return;
        }

        Recipes result = RecipesHashTables.get(name);

        if (result != null) {
            System.out.println("Recipes found: " + result);
            searchRecipesListView.getItems().clear();
            searchRecipesListView.getItems().add(result);
        } else {
            System.out.println("No recipe found with name: " + name);
        }
    }

    @FXML
    public void populateListView(){
        recipesListView.getItems().clear();
        DrinksApplication.recipes.addContentsToListView(recipesListView);
    }

    @FXML
    public void resetRecipes(){
        recipesListView.getItems().clear();
        deleteAllRecipes();
    }

    public void load() throws Exception {
        // Define the classes allowed for deserialization
        Class<?>[] classes = new Class[]{
                Recipes.class, Drinks.class, Ingredients.class, LinkedList.class, LinkedNode.class, HashTables.class
        };

        // Setup XStream with security settings
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        // Read from the correct file (drinks.xml)
        ObjectInputStream in = xstream.createObjectInputStream(new FileInputStream("recipes.xml"));

        // Deserialize the LinkedList of Drinks
        DrinksApplication.recipes = (LinkedList<Recipes>) in.readObject();
        in.close();

        // Update the ListView with loaded data
        populateListView();
        System.out.println("Recipes loaded successfully.");
    }

    @FXML
    public void loadREC(ActionEvent actionEvent) {
        try {
            load();
            populateListView();
        } catch (Exception e) {
            System.err.println("Error loading recipes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        // Setup XStream for serialization
        XStream xstream = new XStream(new DomDriver());

        // Write the LinkedList of Drinks to an XML file
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileOutputStream("recipes.xml"));
        out.writeObject(DrinksApplication.recipes);
        out.close();

        System.out.println("Recipes saved successfully.");
    }

    @FXML
    public void saveREC(ActionEvent actionEvent) {
        try {
            save();
        } catch (Exception e) {
            System.err.println("Error saving recipe: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

