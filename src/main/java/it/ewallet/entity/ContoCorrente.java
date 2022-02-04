package it.ewallet.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ContoCorrente {
	
	
	DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	private String iban;
	private Float saldo;
	private String intestatario;
	
	private String dataCreazione= LocalDateTime.now().format(formatter);
    private ArrayList<Movimento> movimenti= new ArrayList<>();


    public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public Float getSaldo() {
		return saldo;
	}
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	public String getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	
	public ArrayList<Movimento> getMovimenti() {
		return movimenti;
	}
	public void setMovimenti(ArrayList<Movimento> movimenti) {
		this.movimenti = movimenti;
	}
	
	
	
	
	

}
