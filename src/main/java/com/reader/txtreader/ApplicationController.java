package com.reader.txtreader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ApplicationController {
    @FXML
    private TextField fileName;
    @FXML
    private Button btChoose;
    @FXML
    private Button btRead;
    @FXML
    private Button btSave;
    @FXML
    private TextArea textArea;
    @FXML
    private Stage stage;

    @FXML
    private void chooseAction(ActionEvent e) {
        String absolutePath;
        absolutePath = getAbsolutePath();
        fileName.setText(absolutePath);
    }

    @FXML
    private void readAction(ActionEvent e) throws IOException {
        if (!fileName.getText().equals("")) {
            textArea.setText(read(fileName.getText().toString()));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is no file selected.");
            alert.setContentText("You have to select a file to be read!");
            alert.showAndWait();
        }
    }

    @FXML
    private void saveFile() {
        if(textArea.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Write something!");
            alert.setContentText("You have to write something on text area!!!");
            alert.showAndWait();
        }
        else {
            if (fileName.getText().equals("")){
                String filePath = getFolder() + "/" + getName();
                fileName.setText(filePath);
            } else {


            }
        }
    }

   private String read(String absolutePath) throws IOException {
       BufferedReader br = new BufferedReader(new FileReader(absolutePath));
       String fullText = "";
       try {
           StringBuilder text = new StringBuilder();
           String line = br.readLine();

           while (line != null) {
               text.append(line);
               text.append(System.lineSeparator());
               line = br.readLine();
           }
           fullText = text.toString();
       }finally {
           br.close();
           return fullText;
       }
   }

    private String getAbsolutePath() {
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*"));
        fileChooser.setTitle("Open Resource File");

        return fileChooser.showOpenDialog(stage).getAbsoluteFile().toString();
    }

    private String getName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("File name.");
        dialog.setContentText("Please enter file name:");
        return dialog.showAndWait().get();
    }

    private String getFolder(){
        DirectoryChooser chooser;
        chooser = new DirectoryChooser();
        chooser.setTitle("Choose Folder");
        return chooser.showDialog(null).toString();
    }

   private String saveNewOrSubscribe (String absolutePath) {


        return "";
   }
}