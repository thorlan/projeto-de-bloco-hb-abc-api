package edu.infnet.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infnet.leitor.LeitorDeCSV;
import edu.infnet.model.Grafico;

@Service
public class GraficoService {

	@Autowired
	private LeitorDeCSV leitor;
	
	private List<Grafico> graficos;
	
	@PostConstruct
	private void preencheLista() {
		this.graficos = leitor.leArquivo();
	}
	
	public List<Grafico> getGrafico(){
		//TODO: REALIZAR O CALCULO MACD
		//REALIZAR O CALCULO EMA 9,12,26
		//MOSTRAR HISTOGRAMA DO MACD
		
		return this.graficos;
	}
}
