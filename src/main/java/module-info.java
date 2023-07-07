module nk.org.g_affectation_employes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires javafx.base;
    requires java.desktop;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens nk.org.g_affectation_employes to javafx.fxml, javafx.graphics;
    opens nk.org.g_affectation_employes.models to org.hibernate.orm.core, javafx.base;
    opens nk.org.g_affectation_employes.controllers to javafx.fxml;

    exports nk.org.g_affectation_employes;
}