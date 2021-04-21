/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1.connexion.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import p1.connexion.entities.Commentaire;
import p1.connexion.services.CommenterCRUD;

/**
 * FXML Controller class
 *
 * @author HP-PC
 */
public class CommenterBackController implements Initializable {

    @FXML
    private TableView<Commentaire> tablecomBack;
    @FXML
    private TableColumn<Commentaire,String> col_des_com;
    @FXML
    private Label label;
    @FXML
    private Label labe1;
    @FXML
    private Button retourbtnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
                 
                CommenterCRUD c = new CommenterCRUD();
                     try {

            ObservableList<Commentaire> ls = c.read();

         
            col_des_com.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

            tablecomBack.setItems(ls);

        } catch (Exception ex) {
            Logger.getLogger(CommenterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void displaySelectedCommBack(javafx.scene.input.MouseEvent event) {
           if (event.getClickCount() == 2) {
            Commentaire i = tablecomBack.getSelectionModel().getSelectedItem();
            CommenterCRUD si = new CommenterCRUD();
            
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Supprimer " + i.getCommentaire()+ " ?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                si.Supprimer(i);
                                JOptionPane.showMessageDialog(null, "Suppression avec sucess");
                                try {
                ObservableList<Commentaire> ls = si.read3(labe1.getText());

                
                col_des_com.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
                tablecomBack.setItems(ls);
            } catch (SQLException ex) {
                Logger.getLogger(CommenterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
                                
                            }
            

        } else if (event.getClickCount() == 1) {
            Commentaire c = (Commentaire) tablecomBack.getSelectionModel().getSelectedItem();
          
        }
    }

    @FXML
    private void retourAdmin(ActionEvent event)throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("forumBackFXML.fxml"));

        Parent root = loader.load();
        retourbtnBack.getScene().setRoot(root);
    }
    
}
