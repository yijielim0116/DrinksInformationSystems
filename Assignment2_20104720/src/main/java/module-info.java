module org.example.assignment2demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;


    opens org.example.assignment2demo to javafx.fxml, xstream;
    exports org.example.assignment2demo;
    exports org.example.assignment2demo.ADT;
    opens org.example.assignment2demo.ADT to javafx.fxml;
}