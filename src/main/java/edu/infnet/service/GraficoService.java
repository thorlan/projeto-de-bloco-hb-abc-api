package edu.infnet.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.infnet.leitor.LeitorDeCSV;
import edu.infnet.model.Dia;
import edu.infnet.model.Grafico;

@Service
public class GraficoService {

	@Autowired
	private LeitorDeCSV leitor;

	private Grafico grafico;

	private List<Grafico> graficos;

	@PostConstruct
	private void leArquivo() {
		graficos = new ArrayList<>();
		this.grafico = leitor.leArquivo();
		graficos.add(grafico);
	}

	public List<Grafico> getGraficos() {
		// TODO: REALIZAR O CALCULO MACD
		// MOSTRAR HISTOGRAMA DO MACD
		// calculaMACD

		// REALIZAR O CALCULO EMA 9,12,26
		calculaEma(9);
		calculaEma(12);
		calculaEma(26);
		
		return this.graficos;
	}

	private void calculaEma(int periodo) {

		/**
		 * CÁLCULO
		 * 
		 * Existem três etapas para calcular a EMA. Aqui está a fórmula para um EMA de 5
		 * períodos
		 * 
		 * 1. Calcule o SMA
		 * 
		 * (Valores do período / número de períodos)
		 * 
		 * 2. Calcular o multiplicador
		 * 
		 * (2 / (Número de períodos + 1), portanto (2 / (5 + 1) = 33,333%
		 * 
		 * 3. Calcule a EMA
		 * 
		 * Para a primeira EMA, usamos o SMA (dia anterior) em vez de EMA (dia
		 * anterior).
		 * 
		 * EMA = {Fechamento - EMA (dia anterior)} x multiplicador + EMA (dia anterior)
		 */

		double sma = getSMA();
		double multiplicador = getMultiplicador();
		Grafico graficoEMA = getEMA();

		graficos.add(graficoEMA);

	}

	private Grafico getEMA() {
		// TODO Auto-generated method stub
		return null;
	}

	private double getMultiplicador() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double getSMA() {
		// TODO Auto-generated method stub

		return 0;
	}
}
