package com.ceyeclon.monitoringapp.jsfbeans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("dummy")
@SessionScoped
public class DummyBean implements Serializable {

    String contract = "Pavel Radkevich";

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }
}
