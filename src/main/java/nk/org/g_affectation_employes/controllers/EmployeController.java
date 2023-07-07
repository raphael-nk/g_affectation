package nk.org.g_affectation_employes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.services.EmployeService;
import nk.org.g_affectation_employes.utils.Notification;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeController implements Initializable {

    @FXML
    private ImageView close_accueil_win;

    @FXML
    private TextField input_code_employe;

    @FXML
    private TextField input_nom;

    @FXML
    private TextField input_prenom;

    @FXML
    private TextField input_poste;

    @FXML
    private Label txt_new_prof;

    @FXML
    private Button btn_supprimer;

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Text txt_annuler;

    @FXML
    private Button btn_annuler;

    @FXML
    private TableView<Employe> table_employe;

    @FXML
    private TableColumn<Employe, String> col_code;

    @FXML
    private TableColumn<Employe, String> col_nom;

    @FXML
    private TableColumn<Employe, String> col_prenom;

    @FXML
    private TableColumn<Employe, String> col_grade;

    @FXML
    private TextField txt_search;

    @FXML
    private Label txt_new_prof1;

    @FXML
    private ImageView export_to_pdf;

    @FXML
    private ImageView export_to_excel;

    @FXML
    private ChoiceBox<Integer> display_by;

    private Integer[] displayBy = {25, 50, 100, 250, 500};
    private Notification notification;
    public ObservableList<Employe> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.displayBy();
        this.toggleBtnAndText("NOUVEAU EMPLOYE", true, false);
        this.list();
    }

    private void list() {
        data.clear();
        List<Employe> employees = EmployeService.getAll();
        data.addAll(employees);

        col_code.setCellValueFactory(new PropertyValueFactory<Employe, String>("codeemp"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Employe, String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<Employe, String>("prenom"));
        col_grade.setCellValueFactory(new PropertyValueFactory<Employe, String>("poste"));

        table_employe.setItems(data);
    }

    public void setEmployeeCode(){
        int nbEmployee = EmployeService.totalEmploye() + 1;
        input_code_employe.setText("EMP-" + nbEmployee);
    }

    public void toggleBtnAndText(String title, Boolean show, Boolean hide){
        txt_annuler.setVisible(hide);
        txt_new_prof.setText(title);
        btn_ajouter.setVisible(show);
        btn_annuler.setVisible(show);
        btn_modifier.setVisible(hide);
        btn_supprimer.setVisible(hide);
    }
    public void displayBy(){
        this.display_by.getItems().addAll(this.displayBy);
        this.display_by.setValue(25);
    }
    @FXML
    void TxtAnnulerClicked(MouseEvent event) {
        this.list();
        this.resetField();
    }

    private void resetField() {
        input_nom.setText("");
        input_prenom.setText("");
        input_poste.setText("");
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {

    }

    @FXML
    void btnCloseAccueilClicked(MouseEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void exportToExcelClicked(MouseEvent event) {

    }

    @FXML
    void exportToPdfClicked(MouseEvent event) {

    }

    @FXML
    void getSelectedRowFromTable(MouseEvent event) {

    }

    @FXML
    void handleDisplayBy(ContextMenuEvent event) {

    }

    @FXML
    void store(ActionEvent event) {

    }

    @FXML
    void txtSearchProf(KeyEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }
}
