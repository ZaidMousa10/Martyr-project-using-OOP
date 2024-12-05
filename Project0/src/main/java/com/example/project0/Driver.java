package com.example.project0;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Driver extends Application {
    private Martyr<MartyrRecord> martyrList = new Martyr<>(16);
    private TextArea outputTextArea = new TextArea();
    private TextField textName = new TextField();
    private TextField textAge = new TextField();
    private TextField textLocation = new TextField();
    private TextField textDate = new TextField();
    private TextField textGender = new TextField();
    private TextField deleteText = new TextField();
    private TextField searchText = new TextField();


    @Override
    public void start(Stage primaryStage) {
        Button insertButton = new Button("Insert Record");
        Button deleteButton = new Button("Delete Record");
        Button searchButton = new Button("Search Record");
        Button loadButton = new Button("Load Records");
        RadioButton ageRadioButton = new RadioButton("Age");
        RadioButton genderRadioButton = new RadioButton("Gender");
        RadioButton dateRadioButton = new RadioButton("Date");

        Label labelName = new Label("Name:");
        Label labelAge = new Label("Age:");
        Label labelLocation = new Label("Location:");
        Label labelDate = new Label("Date:");
        Label labelGender = new Label("Gender:");
        Label resultLabel = new Label();

        textName.setPromptText("Name");
        textAge.setPromptText("Age");
        textDate.setPromptText("Date");
        textLocation.setPromptText("Location");
        textGender.setPromptText("Gender");
        deleteText.setPromptText("Enter name to delete");
        searchText.setPromptText("Enter name to search");


        ToggleGroup toggleGroup = new ToggleGroup();
        ageRadioButton.setToggleGroup(toggleGroup);
        genderRadioButton.setToggleGroup(toggleGroup);
        dateRadioButton.setToggleGroup(toggleGroup);

        toggleGroup.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            radioButton.setOnAction(event -> {
                String statisticType = radioButton.getText().toLowerCase();
                String statistics = calculateStatistics(statisticType);
                outputTextArea.setText(statistics);
            });
        });

        ageRadioButton.setOnAction(event -> handleRadioButtonAction("age"));
        genderRadioButton.setOnAction(event -> handleRadioButtonAction("gender"));
        dateRadioButton.setOnAction(event -> handleRadioButtonAction("date"));


        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                loadRecordsFromFile(selectedFile);
            }
        });

        insertButton.setOnAction(e -> {
            try {
                String name = textName.getText().isEmpty() ? "Unknown" : textName.getText();
                int age = textAge.getText().isEmpty() ? 200 : Integer.parseInt(textAge.getText());
                String eventLocation = textLocation.getText().isEmpty() ? "Unknown" : textLocation.getText();
                String dateOfDeath = textDate.getText().isEmpty() ? "Unknown" : textDate.getText();
                String gender = textGender.getText().isEmpty() ? "NA" : textGender.getText();
                if (age < 0 || age>201) {
                    throw new NumberFormatException();
                }
                if (name.equals("Unknown") || eventLocation.equals("Unknown") || dateOfDeath.equals("Unknown") || gender.equals("NA")) {
                    resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    resultLabel.setText("Error adding record: Required fields cannot be empty.\n");
                    return;
                }
                MartyrRecord record = new MartyrRecord(name, age, eventLocation, dateOfDeath, gender);
                martyrList.add(record);
                resultLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                resultLabel.setText("Record added successfully.\n");
                textName.clear();
                textAge.clear();
                textLocation.clear();
                textDate.clear();
                textGender.clear();
            } catch (NumberFormatException ex) {
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabel.setText("Error adding record: Invalid age format.\n");
            } catch (Exception ex) {
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabel.setText("Error adding record.\n");
                ex.printStackTrace();
            }
        });


        deleteButton.setOnAction(e -> {
            try {
                String nameToDelete = deleteText.getText();
                int index = martyrList.find(new MartyrRecord(nameToDelete, 0, "", "", ""));
                if (index != -1) {
                    martyrList.delete(index);
                    deleteText.clear();
                    resultLabel.setText("Record deleted successfully.");
                    resultLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    resultLabel.setText("Record u want to delete did not found.");
                    resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            } catch (Exception e1) {
                resultLabel.setText("Error deleting record.");
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                e1.printStackTrace();
            }

        });

        searchButton.setOnAction(e -> {
            try {
                String nameToSearch = searchText.getText();
                int index = martyrList.find(new MartyrRecord(nameToSearch, 0, "", "", ""));
                if (index != -1) {
                    MartyrRecord foundRecord = martyrList.get(index);
                    resultLabel.setText(foundRecord.toString());
                    resultLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    searchText.clear();
                } else {
                    resultLabel.setText("Record u want to search did not found.");
                    resultLabel.setStyle("-fx-text-fill: red;  -fx-font-weight: bold;");
                }
            } catch (Exception e1) {
                resultLabel.setText("Error searching for record.");
                resultLabel.setStyle("-fx-text-fill: red;  -fx-font-weight: bold;");
                e1.printStackTrace();
            }
        });

        HBox radioBox = new HBox(ageRadioButton, genderRadioButton, dateRadioButton);
        radioBox.setAlignment(Pos.CENTER);
        radioBox.setSpacing(10);

        HBox buttonBox = new HBox(labelName,textName,labelAge,textAge,labelDate,textDate,labelLocation,textLocation,labelGender,textGender);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();

        gridPane.add(loadButton, 1, 0);
        GridPane.setHalignment(loadButton, HPos.CENTER);
        GridPane.setValignment(loadButton, VPos.CENTER);

        gridPane.add(insertButton, 0, 1);
        gridPane.add(buttonBox,1,1);
        gridPane.add(deleteButton, 0, 2);
        gridPane.add(deleteText, 1, 2);
        gridPane.add(searchButton, 0, 3);
        gridPane.add(searchText, 1, 3);
        gridPane.add(radioBox, 1, 4);

        gridPane.add(outputTextArea, 1, 5);
        GridPane.setHalignment(resultLabel, HPos.CENTER);
        GridPane.setValignment(resultLabel, VPos.CENTER);

        gridPane.add(resultLabel, 1, 6);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 1200, 500);
        primaryStage.setTitle("The Records Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleRadioButtonAction(String statisticType) {
        String statistics = calculateStatistics(statisticType);
        outputTextArea.setText(statistics);
    }
    private void loadRecordsFromFile(File file) {
        martyrList.clear();
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    String name = parts[0].isEmpty() ? "Unknown" : parts[0];
                    int age = 0;
                    try {
                        age = parts[1].isEmpty() ? 200 : Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ex) {
                        outputTextArea.appendText("Invalid age format for record: " + name + ". Setting age to 0.\n");
                    }
                    String eventLocation = parts[2].isEmpty() ? "Unknown" : parts[2];
                    String dateOfDeath = parts[3].isEmpty() ? "Unknown" : parts[3];
                    String gender = parts[4].equals("NA") ? "NA" : parts[4];
                    MartyrRecord record = new MartyrRecord(name, age, eventLocation, dateOfDeath, gender);
                    martyrList.add(record);
                    System.out.println(martyrList.getCount());
                }
            }
            outputTextArea.appendText("Records loaded successfully.\n");

        } catch (IOException e) {
            outputTextArea.appendText("Failed to load records.\n");
        }
    }


    private String calculateStatistics(String type) {
        String stats = "";
        type = type.toLowerCase();
        if ("age".equals(type)) {
            stats += "Age statistics:\n";
            int[] ageCounts = new int[201];
            for (int i = 0; i < martyrList.getCount(); i++) {
    MartyrRecord record = martyrList.get(i);
    int age = record.getAge();
    if (age >= 0 && age < ageCounts.length) {
        ageCounts[age]++;
    }
}
            for (int i = 0; i < ageCounts.length; i++) {
                if (ageCounts[i] > 0) {
                    stats += "Age " + i + ": " + ageCounts[i] + " martyr(s)\n";
                }
            }
        } else if ("gender".equals(type)) {
            stats += "Gender statistics:\n";
            int maleCount = 0;
            int femaleCount = 0;
            int unknownCount = 0;
            for (int i = 0; i < martyrList.getCount(); i++) {
                MartyrRecord record = martyrList.get(i);
                if ("M".equalsIgnoreCase(record.getGender())) {
                    maleCount++;
                } else if ("F".equalsIgnoreCase(record.getGender())) {
                    femaleCount++;
                }
                else if ("NA".equalsIgnoreCase(record.getGender())){
                    unknownCount++;
                }
            }
            stats += "Male: " + maleCount + "\n";
            stats += "Female: " + femaleCount + "\n";
            stats += "Unknown: " + unknownCount + "\n";
        } else if ("date".equals(type)) {
            stats += "Date of death statistics:\n";
            String[] uniqueDates = new String[martyrList.getCount()];
            int[] dateCounts = new int[martyrList.getCount()];
            int dateIndex = 0;
            for (int j = 0; j < martyrList.getCount(); j++) {
                MartyrRecord record = martyrList.get(j);
                String date = record.getDateOfDeath();
                boolean dateFound = false;
                for (int i = 0; i < dateIndex; i++) {
                    if (date.equals(uniqueDates[i])) {
                        dateCounts[i]++;
                        dateFound = true;
                        break;
                    }
                }
                if (!dateFound) {
                    uniqueDates[dateIndex] = date;
                    dateCounts[dateIndex]++;
                    dateIndex++;
                }
            }
            for (int i = 0; i < dateIndex; i++) {
                stats += uniqueDates[i] + ": " + dateCounts[i] + " martyr(s)\n";
            }
        } else {
            return "Invalid statistic type. Please enter age, gender, or date.";
        }
        return stats;
    }
    public void clear(){
        martyrList.clear();
        outputTextArea.setText("");
    }
}
