package org.example.assignment2demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assignment2demo.ADT.LinkedList;

import java.io.IOException;

public class DrinksApplication extends Application {
    public static LinkedList<Drinks> drinks = new LinkedList<>();
    public static LinkedList<Ingredients> ingredients = new LinkedList<>();
    public static LinkedList<Recipes> recipes = new LinkedList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Mainpage.fxml"));
            primaryStage.setTitle("WAKANDA DRINKS RECIPES");
            Scene scene1 = new Scene(root);
            primaryStage.setScene(scene1);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
