package nk.org.g_affectation_employes.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lieu")
public class Lieu {
    @Id
    private String codelieu;
    private String designation;
    private String province;

    public Lieu() {
    }

    public Lieu(String codelieu, String designation, String province) {
        this.codelieu = codelieu;
        this.designation = designation;
        this.province = province;
    }

    public String getCodelieu() {
        return codelieu;
    }

    public void setCodelieu(String codelieu) {
        this.codelieu = codelieu;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
