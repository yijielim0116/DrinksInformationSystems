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

import java.io.*;


public class DrinksController {
    @FXML
    public TextField drinksNameField;
    @FXML
    public TextField countryofOriginField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField imageURLField;
    @FXML
    public ListView<Drinks> drinksListView;
    @FXML
    public ListView searchdrinksListView;
    public LinkedList<Drinks> drinksList = DrinksApplication.drinks;
    private HashTables<String, Drinks> drinksHashTables = new HashTables<>();

    @FXML
    public void addDrink(){
        String drinkName = drinksNameField.getText();
        String countryOfOrigin = countryofOriginField.getText();
        String drinkDescription = descriptionField.getText();
        String imgURL = imageURLField.getText();

        Drinks d1 = new Drinks(drinkName, countryOfOrigin, drinkDescription,imgURL);
        DrinksApplication.drinks.addElement(d1);
        drinksHashTables.put(drinkName, d1);
        System.out.println(DrinksApplication.drinks.head.contents);
        populateListView();
    }

    @FXML
    public void populateListView(){
        drinksListView.getItems().clear();
        DrinksApplication.drinks.addContentsToListView(drinksListView);
    }

    @FXML
    public void deleteDrinks(ActionEvent actionEvent){
        int selectedIndex = drinksListView.getSelectionModel().getSelectedIndex();

        // Check if an item is selected
        if (selectedIndex == -1) {
            System.out.println("No drink selected for deletion.");
            return;
        }

        try {
            // Get the selected drink
            Drinks deletedDrink = drinksList.getElement(selectedIndex);

            // Remove from LinkedList
            drinksList.deleteElement(selectedIndex);

            // Optional: Remove from HashTable if needed
            drinksHashTables.remove(deletedDrink.getDrinkName());

            // Refresh the ListView
            populateListView();
            System.out.println("Drink deleted successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds. " +
                    "Selected index: " + selectedIndex +
                    ", List size: " + drinksList.listRange());
        }
    }

    @FXML
    public void deleteAllDrinks(){
        DrinksApplication.drinks.deleteEntireList();
        drinksHashTables = new HashTables<>();
        populateListView();
        System.out.println("All drinks deleted successfully.");
    }

    @FXML
    public void updateDrinks(ActionEvent actionEvent){
        Drinks selectedDrink = drinksListView.getSelectionModel().getSelectedItem();

        if (selectedDrink == null) {
            System.out.println("No drink selected for editing.");
            return;
        }

        // Validate input fields
        if (drinksNameField.getText().isEmpty() || countryofOriginField.getText().isEmpty()
                || descriptionField.getText().isEmpty() || imageURLField.getText().isEmpty()) {
            System.out.println("All fields must be filled to update the drink.");
            return;
        }

        // Remove old entry from HashTable
        drinksHashTables.remove(selectedDrink.getDrinkName());

        // Update drink details
        selectedDrink.setDrinkName(drinksNameField.getText());
        selectedDrink.setCountryofOrigin(countryofOriginField.getText());
        selectedDrink.setDescription(descriptionField.getText());
        selectedDrink.setImageURL(imageURLField.getText());

        // Add updated drink back to HashTable
        drinksHashTables.put(selectedDrink.getDrinkName(), selectedDrink);

        populateListView();
        System.out.println("Drink updated successfully.");
    }

    @FXML
    public void resetDrinks(){
        deleteAllDrinks();
        drinksListView.getItems().clear();
        System.out.println("System reset: All drinks removed.");
    }

    @FXML
    public void searchDrinksByName(ActionEvent actionEvent){
        LinkedNode<Drinks> DN = DrinksApplication.drinks.getHead();
        searchdrinksListView.getItems().clear();

        while(DN != null){
            if(DN.getContents().getDrinkName().contains(drinksNameField.getText())){
                searchdrinksListView.getItems().add(DN.contents);
            }
            DN = DN.next;
        }
    }

    @FXML
    public void searchDrinksByDescription(ActionEvent actionEvent){
        LinkedNode<Drinks> DN1 = DrinksApplication.drinks.getHead();
        searchdrinksListView.getItems().clear();

        while(DN1 != null){
            if(DN1.getContents().getDescription().contains(descriptionField.getText())){
                searchdrinksListView.getItems().add(DN1.contents);
            }
            DN1 = DN1.next;
        }
    }

    @FXML
    public void searchDrinksByCountry(ActionEvent actionEvent){
        LinkedNode<Drinks> DN2 = DrinksApplication.drinks.getHead();
        searchdrinksListView.getItems().clear();

        while(DN2 != null){
            if(DN2.getContents().getCountryofOrigin().contains(countryofOriginField.getText())){
                searchdrinksListView.getItems().add(DN2.contents);
            }
            DN2 = DN2.next;
        }
    }

    @FXML
    public void sortDrinksByName(ActionEvent actionEvent) {
        if (DrinksApplication.drinks.isEmpty()) {
            System.out.println("The drinks list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Drinks> current = DrinksApplication.drinks.head;
        while (current != null) {
            LinkedNode<Drinks> minNode = current;
            LinkedNode<Drinks> index = current.next;

            while (index != null) {
                if (index.contents.getDrinkName().compareToIgnoreCase(minNode.contents.getDrinkName()) < 0) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Drinks temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Drinks sorted by name successfully.");
    }

    @FXML
    public void sortDrinksByCountry(ActionEvent actionEvent) {
        if (DrinksApplication.drinks.isEmpty()) {
            System.out.println("The drinks list is empty. Nothing to sort.");
            return;
        }

        LinkedNode<Drinks> current = DrinksApplication.drinks.head;
        while (current != null) {
            LinkedNode<Drinks> minNode = current;
            LinkedNode<Drinks> index = current.next;

            while (index != null) {
                if (index.contents.getCountryofOrigin().compareToIgnoreCase(minNode.contents.getCountryofOrigin()) < 0) {
                    minNode = index;
                }
                index = index.next;
            }

            if (minNode != current) {
                Drinks temp = current.contents;
                current.contents = minNode.contents;
                minNode.contents = temp;
            }

            current = current.next;
        }

        populateListView();
        System.out.println("Drinks sorted by country successfully.");
    }

    @FXML
    public void searchDrinksByNameUsingHashTable(ActionEvent actionEvent) {
        String name = drinksNameField.getText();
        if (name.isEmpty()) {
            System.out.println("Please enter an drinks name.");
            return;
        }

        Drinks result = drinksHashTables.get(name);

        if (result != null) {
            System.out.println("Drinks found: " + result);
            searchdrinksListView.getItems().clear();
            searchdrinksListView.getItems().add(result);
        } else {
            System.out.println("No drink found with name: " + name);
        }
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
        ObjectInputStream in = xstream.createObjectInputStream(new FileInputStream("drinks.xml"));

        // Deserialize the LinkedList of Drinks
        DrinksApplication.drinks = (LinkedList<Drinks>) in.readObject();
        in.close();

        // Update the ListView with loaded data
        populateListView();
        System.out.println("Drinks loaded successfully.");
    }

    @FXML
    public void loadDN(ActionEvent actionEvent) {
        try {
            load();
            populateListView();
        } catch (Exception e) {
            System.err.println("Error loading drinks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        // Setup XStream for serialization
        XStream xstream = new XStream(new DomDriver());

        // Write the LinkedList of Drinks to an XML file
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileOutputStream("drinks.xml"));
        out.writeObject(DrinksApplication.drinks);
        out.close();

        System.out.println("Drinks saved successfully.");
    }

    @FXML
    public void saveDN(ActionEvent actionEvent) {
        try {
            save();
        } catch (Exception e) {
            System.err.println("Error saving drinks: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
