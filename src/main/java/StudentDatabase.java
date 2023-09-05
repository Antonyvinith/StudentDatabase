//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StudentDatabase extends Application {
    public StudentDatabase() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        TableView<Student> table = new TableView<>();
        ObservableList<Student> data = this.retrieveDataFromDatabase();
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Student, String> dobCol = new TableColumn("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory("dob"));
        TableColumn<Student, String> genderCol = new TableColumn("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
        table.getColumns().addAll(new TableColumn[]{nameCol, dobCol, genderCol});
        table.setItems(data);
        StackPane root = new StackPane();
        root.getChildren().add(table);
        Scene scene = new Scene(root, 600.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Student> retrieveDataFromDatabase() {
        ObservableList<Student> data = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student_table");

            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String gender = resultSet.getString("gender");
                data.add(new Student(name, dob, gender));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return data;
    }

    public static class Student {
        private String name;
        private String dob;
        private String gender;

        public Student(String name, String dob, String gender) {
            this.name = name;
            this.dob = dob;
            this.gender = gender;
        }

        public String getName() {
            return this.name;
        }

        public String getDob() {
            return this.dob;
        }

        public String getGender() {
            return this.gender;
        }
    }
}
