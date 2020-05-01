package edu.infnet.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.infnet.leitor.LeitorDeCSV;
import edu.infnet.model.Dia;
import edu.infnet.model.Grafico;

@Service
public class GraficoService {

	@Autowired
	private LeitorDeCSV leitor;

	@JsonProperty(value="grafico")
	private Grafico grafico;
	
	@JsonProperty(value="ema9")
	private Grafico ema9;
	
	@JsonProperty(value="ema12")
	private Grafico ema12;
	
	@JsonProperty(value="ema26")
	private Grafico ema26;

	private List<Grafico> graficos;

//	private double sma;

//	private BigDecimal multiplicador;

	@PostConstruct
	private void leArquivo() {
		
		graficos = new ArrayList<>();
		
		grafico = leitor.leArquivo();
		graficos.add(grafico);

	//	this.sma = getSMA();
	//	this.multiplicador = getMultiplicador();
		
		ema9 = calculaEma(9);
		graficos.add(ema9);
		
		ema12 = calculaEma(12);
		graficos.add(ema12);
		
		ema26 = calculaEma(26);
		graficos.add(ema26);
	}

	public List<Grafico> getGraficos() {
		// TODO: REALIZAR O CALCULO MACD
		// MOSTRAR HISTOGRAMA DO MACD
		// calculaMACD
		
		return this.graficos;
	}

	private Grafico calculaEma(int periodo) {

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
		 * 
		 *  FALTA ABAIXO!
		 * 3. Calcule a EMA
		 * 
		 * Para a primeira EMA, usamos o SMA (dia anterior) em vez de EMA (dia
		 * anterior).
		 * 
		 * EMA = {Fechamento - EMA (dia anterior)} x multiplicador + EMA (dia anterior)
		 */
		
		List<Dia> dias = new ArrayList<>();

		Dia diaGraficoComum;
		
		for (int i = 0; i < grafico.getDias().size() ; i++) {
		
			diaGraficoComum = grafico.getDias().get(i);
			BigDecimal close;
			
			if(isFirstDay(i)){
				close = calculaEma(diaGraficoComum.getClose(), getSMA(), getMultiplicador());
			} else {
				close = calculaEma(diaGraficoComum.getClose(), dias.get(i-1).getClose(), getMultiplicador());
			}
			
			dias.add(new Dia(diaGraficoComum.getDate(),close));
			
		}

		Grafico graficoEMA = new Grafico();
		graficoEMA.setDias(dias);
		
		return graficoEMA;

	}

	private boolean isFirstDay(int i) {
		return i == 0;
	}

	private BigDecimal calculaEma(BigDecimal close, BigDecimal emaOuSma, BigDecimal multiplicador) {
		//EMA = {Fechamento - EMA (dia anterior)} x multiplicador + EMA (dia anterior)
		BigDecimal ema = close.subtract(emaOuSma);
		ema = ema.multiply(multiplicador).add(emaOuSma);
		return ema;
	}

	private BigDecimal getMultiplicador() {
		
		int periodos = grafico.getDias().size();
		
		BigDecimal divisor = new BigDecimal((periodos + 1));
		BigDecimal denominador = new BigDecimal(2);
		BigDecimal multiplicador = denominador.divide(divisor);
		
		return multiplicador;
	}

	private BigDecimal getSMA() {
		List<BigDecimal> fechamentoPorDia = grafico.getDias().stream().map(Dia::getAdjClose).collect(Collectors.toList());
		Double average = fechamentoPorDia.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		return new BigDecimal(average);
	}
}
