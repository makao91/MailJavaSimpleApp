package MainPackage.controller.persistence;

import java.io.Serializable;

public class ValidAccount implements Serializable {

    private String adress;
    private String password;

    public ValidAccount(String adress, String password) {
        this.adress = adress;
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
