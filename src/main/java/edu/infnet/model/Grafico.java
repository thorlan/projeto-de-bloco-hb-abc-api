package edu.infnet.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grafico {

	@JsonProperty(value="dia")
	private List<Dia> dias;

	public List<Dia> getDias() {
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}

}
