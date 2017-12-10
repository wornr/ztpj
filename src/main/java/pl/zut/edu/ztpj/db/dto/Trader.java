package pl.zut.edu.ztpj.db.dto;

import java.io.Serializable;

public class Trader extends Employee implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6574056316087799259L;
	private Integer provision;
    private Integer provisionLimit;

    public Trader(){
        super();
    }

    public Trader(String firstName, String lastName, Integer salary, String phoneNumber, Integer provision, Integer provisionLimit){
        super(firstName, lastName, salary, phoneNumber);
        this.provision = provision;
        this.provisionLimit = provisionLimit;
    }

    public Integer getProvision() {
        return provision;
    }

    public void setProvision(Integer provision) {
        this.provision = provision;
    }

    public Integer getProvisionLimit() {
        return provisionLimit;
    }

    public void setProvisionLimit(Integer provisionLimit) {
        this.provisionLimit = provisionLimit;
    }
}
