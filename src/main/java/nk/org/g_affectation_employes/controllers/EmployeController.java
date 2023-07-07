package nk.org.g_affectation_employes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.services.EmployeService;
import nk.org.g_affectation_employes.utils.Helper;
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
        this.startup();
    }

    public void startup(){
        this.toggleBtnAndText("NOUVEAU EMPLOYE", true, false);
        this.setEmployeeCode();
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
        this.toggleBtnAndText("NOUVEAU EMPLOYE", true, false);
        this.list();
        this.resetField();
    }

    private void resetField() {
        this.setEmployeeCode();
        input_nom.setText("");
        input_prenom.setText("");
        input_poste.setText("");
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        this.TxtAnnulerClicked(null);
    }

    @FXML
    void btnCloseAccueilClicked(MouseEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {
        Employe employee = table_employe.getSelectionModel().getSelectedItem();
        notification = new Notification("Suppression Employé");

        if(employee == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        }
        else if(
                notification.showConfirmation("Voulez-vous supprimer cette ligne?",
                        Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                        && EmployeService.delete(employee)
        ){
            notification.showAlert("La suppression a été effectué avec succès", Alert.AlertType.INFORMATION);
            this.list();
            this.resetField();
        }
    }

    @FXML
    void exportToExcelClicked(MouseEvent event) {
        Helper.exportToExcel(table_employe, Helper.getFilePath("EXCEL Files", "*.xlsx"));
    }

    @FXML
    void exportToPdfClicked(MouseEvent event) {
        Helper.exportToPDF(table_employe, Helper.getFilePath("PDF Files", "*.pdf"));
    }

    @FXML
    void getSelectedRowFromTable(MouseEvent event) {
        Employe selectedItem = table_employe.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            this.toggleBtnAndText("MODIFICATION", false, true);
            input_code_employe.setText(selectedItem.getCodeemp());
            input_nom.setText(selectedItem.getNom());
            input_prenom.setText(selectedItem.getPrenom());
            input_poste.setText(selectedItem.getPoste());
        }
    }

    @FXML
    void handleDisplayBy(ContextMenuEvent event) {

    }

    @FXML
    void store(ActionEvent event) {
        String code = input_code_employe.getText();
        String nom = input_nom.getText();
        String prenom = input_prenom.getText();
        String poste = input_poste.getText();
        notification = new Notification("Nouveau Employé");

        if(code.isEmpty() || nom.isEmpty() || poste.isEmpty()){
            notification.showAlert("Veuillez remplir les champs obligatoires!", Alert.AlertType.WARNING);
        } else {

            Employe newEmploye = new Employe(code, nom.toUpperCase(), Helper.toCapitalize(prenom), poste.toUpperCase());

            if(EmployeService.store(newEmploye)){
                notification.showAlert("L'ajout d'un nouveau employé a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.resetField();
                this.list();
            }
            else notification.showAlert("Erreur Ajout!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void txtSearchProf(KeyEvent event) {
        String recherche = txt_search.getText();
        if(recherche.isEmpty()) this.list();
        else{
            data.clear();
            List<Employe> employeList = EmployeService.getAll();

            for(Employe employe: employeList){
                if(
                        employe.getCodeemp().toLowerCase().contains(recherche.toLowerCase()) ||
                                employe.getNom().toLowerCase().contains(recherche.toLowerCase()) ||
                                employe.getPrenom().toLowerCase().contains(recherche.toLowerCase()) ||
                                employe.getPoste().toLowerCase().contains(recherche.toLowerCase())
                ){
                    data.add(employe);
                }
            }

            table_employe.setItems(data);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Employe selectedItem = table_employe.getSelectionModel().getSelectedItem();
        notification = new Notification("Modification Employé");

        String code =  input_code_employe.getText();
        String nom = input_nom.getText();
        String prenom = input_prenom.getText();
        String grade = input_poste.getText();

        if(selectedItem == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        }
        else {
            selectedItem.setCodeemp(code);
            selectedItem.setNom(nom);
            selectedItem.setPrenom(prenom);
            selectedItem.setPoste(grade);

            if(
                    notification.showConfirmation("Voulez-vous modifier cette ligne?",
                            Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                            && EmployeService.update(selectedItem)
            ) {
                notification.showAlert("La modification a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.list();
                this.toggleBtnAndText("NOUVEAU EMPLOYE", true, false);
                this.resetField();
            }
        }
    }
}
