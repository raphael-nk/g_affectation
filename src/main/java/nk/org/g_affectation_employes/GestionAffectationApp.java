package nk.org.g_affectation_employes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class GestionAffectationApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionAffectationApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        SessionFactory factory = HibernateUtil.getSessionFactory();

        Employe prof = new Employe("002", "test2", "test2", "prof2");
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(prof);
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}