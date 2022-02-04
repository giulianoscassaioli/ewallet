package it.ewallet.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimento {
	
	DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			
	private String dataMov=LocalDateTime.now().format(formatter);
	private Float importo;
	private String tipoMov;
	private String iban;
	
	public Movimento() {}

	public Movimento(Float importo,String iban,String mov) {
		this.tipoMov=mov;
		this.iban=iban;
		this.importo = importo;
		
	}


	public Float getImporto() {
		return importo;
	}

	public void setImporto(Float importo) {
		this.importo = importo;
	}

	public String getDataMov() {
		return dataMov;
	}

	public void setDataMov(String dataMov) {
		this.dataMov = dataMov;
	}


	public String getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	

	
	
	
	
}
