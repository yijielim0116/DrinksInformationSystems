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

public class IngredientsController {

    @FXML
    public TextField ingredientsNameField;
    @FXML
    public TextField ingredientsDescriptionField;
    @FXML
    public TextField ABVField;
    @FXML
    public ListView<Ingredients> ingredientsListView;
    @FXML
    public ListView searchingredientsListView;

    private HashTables<String, Ingredients> ingredientsHashTables = new HashTables<>();


    @FXML
    public void addIngredients(){
        String ingredientsName = ingredientsNameField.getText();
        String ingredientsDescription = ingredientsDescriptionField.getText();

        double ABV;
        try {
            ABV = Double.parseDouble(ABVField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Invalid ABV input. Please enter a numeric value.");
            return;
        }

        Ingredients newIngredient = new Ingredients(ingredientsName, ingredientsDescription, ABV);
        DrinksApplication.ingredients.addElement(newIngredient);
        ingredientsHashTables.put(ingredientsName, newIngredient);
        System.out.println("Ingredient added: " + newIngredient);
        populateListView();
    }

    @FXML
    public void populateListView(){
        ingredientsListView.getItems().clear();
        DrinksApplication.ingredients.addContentsToListView(ingredientsListView);

    }

    @FXML
    public void deleteIngredients(ActionEvent actionEvent){
        int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            System.out.println("No ingredient selected for deletion.");
            return;
        }

        try {
            Ingredients selectedIngredient = DrinksApplication.ingredients.getElement(selectedIndex);

            DrinksApplication.ingredients.deleteElement(selectedIndex);

            ingredientsHashTables.remove(selectedIngredient.getIngname());

            populateListView();
            System.out.println("Ingredient deleted: " + selectedIngredient.getIngname());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds. List may be empty or invalid index.");
        }
    }

    public void deleteAllIngredients(){
        DrinksApplication.ingredients.deleteEntireList();
        ingredientsHashTables = new HashTables<>();
        populateListView();
        System.out.println("All ingredients deleted successfully.");
    }

    public void updateIngredients(ActionEvent actionEvent){
        Ingredients selectedIngredients = ingredientsListView.getSelectionModel().getSelectedItem();

        if (selectedIngredients == null) {
            System.out.println("No ingredients selected for editing.");
            return;
        }

        // Validate input fields
        if (ingredientsNameField.getText().isEmpty() || ingredientsDescriptionField.getText().isEmpty()
                || ABVField.getText().isEmpty()) {
            System.out.println("All fields must be filled to update the ingredients.");
            return;
        }

        // Remove old entry from HashTable
        ingredientsHashTables.remove(selectedIngredients.getIngname());

        // Update drink details
        selectedIngredients.setIngname(ingredientsNameField.getText());
        selectedIngredients.setIngdecription(ingredientsDescriptionField.getText());
        selectedIngredients.setABV(Double.parseDouble(ABVField.getText()));

        // Add updated drink back to HashTable
        ingredientsHashTables.put(selectedIngredients.getIngname(), selectedIngredients);
        populateListView();
        System.out.println("Ingredients updated successfully.");
    }

    @FXML
    public void searchIngredientsByName(ActionEvent actionEvent){
        LinkedNode<Ingredients> IN = DrinksApplication.ingredients.getHead();
        searchingredientsListView.getItems().clear();

        while(IN != null){
            if(IN.getContents().getIngname().contains(ingredientsNameField.getText())){
                searchingredientsListView.getItems().add(IN.contents);
            }
            IN = IN.next;
        }
    }

    @FXML
    public void searchIngredientsByDescription(ActionEvent actionEvent){
        LinkedNode<Ingredients> IN1 = DrinksApplication.ingredients.getHead();
        searchingredientsListView.getItems().clear();

        while(IN1 != null){
            if(IN1.getContents().getIngdecription().contains(ingredientsDescriptionField.getText())){
                searchingredientsListView.getItems().add(IN1.contents);
            }
            IN1 = IN1.next;
        }
    }

    @FXML
    public void searchIngredientsByABV(ActionEvent actionEvent){
        LinkedNode<Ingredients> IN2 = DrinksApplication.ingredients.getHead();
        double ABV = Double.parseDouble(ABVField.getText());
        searchingredientsListView.getItems().clear();

        while(IN2 != null){
            if(IN2.getContents().getABV() <= ABV){
                searchingredientsListView.getItems().add(IN2.contents);
            }
            IN2 = IN2.next;
        }
    }

    @FXML
    public void sortIngredientsByName(ActionEvent actionEvent) {
        if (DrinksApplication.ingredients.isEmpty()) {
            System.out.println("The ingredients list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Ingredients> current = DrinksApplication.ingredients.head;
        while (current != null) {
            LinkedNode<Ingredients> minNode = current;
            LinkedNode<Ingredients> index = current.next;

            while (index != null) {
                if (index.contents.getIngname().compareToIgnoreCase(minNode.contents.getIngname()) < 0) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Ingredients temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Ingredients sorted by name successfully.");
    }

    @FXML
    public void sortIngredientsByABV(ActionEvent actionEvent) {
        if (DrinksApplication.ingredients.isEmpty()) {
            System.out.println("The ingredients list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Ingredients> current = DrinksApplication.ingredients.head;
        while (current != null) {
            LinkedNode<Ingredients> minNode = current;
            LinkedNode<Ingredients> index = current.next;

            while (index != null) {
                if (index.contents.getABV() < minNode.contents.getABV()) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Ingredients temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Ingredients sorted by ABV successfully.");
    }

    @FXML
    public void searchIngredientByNameUsingHashTable(ActionEvent actionEvent) {
        String name = ingredientsNameField.getText();
        if (name.isEmpty()) {
            System.out.println("Please enter an ingredient name.");
            return;
        }

        Ingredients result = ingredientsHashTables.get(name);

        if (result != null) {
            System.out.println("Ingredient found: " + result);
            searchingredientsListView.getItems().clear();
            searchingredientsListView.getItems().add(result);
        } else {
            System.out.println("No ingredient found with name: " + name);
        }
    }

    @FXML
    public void resetIngredients(){
        ingredientsListView.getItems().clear();
        deleteAllIngredients();
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
        ObjectInputStream in = xstream.createObjectInputStream(new FileInputStream("ingredients.xml"));

        // Deserialize the LinkedList of Drinks
        DrinksApplication.ingredients = (LinkedList<Ingredients>) in.readObject();
        in.close();

        // Update the ListView with loaded data
        populateListView();
        System.out.println("Ingredients loaded successfully.");
    }

    @FXML
    public void loadING(ActionEvent actionEvent) {
        try {
            load();
            populateListView();
        } catch (Exception e) {
            System.err.println("Error loading ingredients: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        // Setup XStream for serialization
        XStream xstream = new XStream(new DomDriver());

        // Write the LinkedList of Drinks to an XML file
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileOutputStream("ingredients.xml"));
        out.writeObject(DrinksApplication.ingredients);
        out.close();

        System.out.println("Ingredients saved successfully.");
    }

    @FXML
    public void saveING(ActionEvent actionEvent) {
        try {
            save();
        } catch (Exception e) {
            System.err.println("Error saving ingredients: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
