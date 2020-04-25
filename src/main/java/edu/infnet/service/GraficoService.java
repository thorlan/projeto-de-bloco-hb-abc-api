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
		return this.graficos;
	}
}
