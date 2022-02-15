package com.reader.txtreader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;

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
                try {
                    String tmp = getName();
                    if(!tmp.isEmpty()) {
                        String filePath = getFolder() + "/" + tmp;
                        if(!filePath.equals("/" + tmp)) {
                            fileName.setText(filePath);
                            saveText(fileName.getText(), textArea.getText());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("File Save");
                            alert.setHeaderText("Your file was save");
                            alert.showAndWait();
                        }
                    }
                }
                catch(Exception e){
                    System.out.println("Processo interrompido.");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("This file already exists.");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("Press ok to subscribe!");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    saveText(fileName.getText(), textArea.getText());
                }
            }
        }
    }

   private String read(String absolutePath) throws IOException {
       try {
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
           } finally {
               br.close();
               return fullText;
           }
       } catch (FileNotFoundException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("File doesn't exist.");
           alert.setContentText("The file doesn't exist.");
           alert.showAndWait();
       }
       return "";
   }

    private String getAbsolutePath() {
        String path = "";
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*"));
        fileChooser.setTitle("Open Resource File");
        try {
            path = fileChooser.showOpenDialog(stage).getAbsoluteFile().toString();
        }
        catch (NullPointerException e) {
            System.out.println("Processo interrompido.");
        }
        return path;
    }

    private String getName() {
        String name = "";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("File name.");
        dialog.setContentText("Please enter file name:");
        try {
            name = dialog.showAndWait().get();
        }
        catch (Exception e) {
            System.out.println("Processo interrompido.");
        }
        return name;
    }

    private String getFolder() throws NullPointerException{
        String path = "";
        DirectoryChooser chooser;
        chooser = new DirectoryChooser();
        chooser.setTitle("Choose Folder");
        try {
            path = chooser.showDialog(null).toString();
        }
        catch (NullPointerException e) {
            System.out.println("Processo interrompido.");
        }
        return path;
    }

   private void saveText (String absolutePath, String contents) {
       try {
           Files.writeString(Path.of(absolutePath), contents, StandardCharsets.UTF_8);
       } catch (IOException ex) {
           System.out.println("Processo interrompido.");
       }
   }
}