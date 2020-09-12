package edu.infnet.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	private Grafico ema9;

	private Grafico ema12;

	private Grafico ema26;
	
	private Grafico macdLinha;
	
	private Grafico macdHistograma;
	
	private Grafico macdSinal;

	private List<Grafico> graficos;

	@PostConstruct
	private void leArquivo() {

		graficos = new ArrayList<>();

		grafico = leitor.leArquivo();

		graficos.add(grafico);

		ema9 = calculaEma(9, grafico.getDias());
		graficos.add(ema9);

		ema12 = calculaEma(12, grafico.getDias());
		graficos.add(ema12);

		ema26 = calculaEma(26, grafico.getDias());
		graficos.add(ema26);
		
		macdLinha = calculaMacdLinha();
		graficos.add(macdLinha);
		
		macdHistograma = calculaMacdHistograma();
		graficos.add(macdHistograma);
		
		macdSinal = calculaEma(9, macdLinha.getDias());
		graficos.add(macdSinal);
		
	}

	private Grafico calculaMacdHistograma() {
		
		Grafico macdHistograma = new Grafico();
		List<Dia> dias = new ArrayList<>();
		Dia dia;
		BigDecimal close;
		
		Grafico signalLine = calculaEma(9, macdLinha.getDias());
		
		for ( int i = 0; i < signalLine.getDias().size(); i++){
			close = macdLinha.getDias().get(i).getClose().subtract(signalLine.getDias().get(i).getClose());
			dia = new Dia(signalLine.getDias().get(i).getDate(), close);
			dias.add(dia);
		}
		
		macdHistograma.setDias(dias);
		return macdHistograma;
	}

	private Grafico calculaMacdLinha() {
		
		Grafico macdLinha = new Grafico();
		List<Dia> dias = new ArrayList<>();
		Dia dia;
		BigDecimal close;
		
		for(int i = 0; i < grafico.getDias().size() ; i++) {
			close = ema12.getDias().get(i).getClose().subtract(ema26.getDias().get(i).getClose());
			dia = new Dia(grafico.getDias().get(i).getDate(), close);
			dias.add(dia);
		}
		
		macdLinha.setDias(dias);
		return macdLinha;
	}

	public List<Grafico> getGraficos() {
		return this.graficos;
	}

	private Grafico calculaEma(int periodo, List<Dia> diasParaOCalculo) {

		List<Dia> dias = new ArrayList<>();

		Dia diaGraficoComum;
		double multiplicador = getMultiplicador(periodo);
		
		for (int i = 0; i < diasParaOCalculo.size(); i++) {

			diaGraficoComum = diasParaOCalculo.get(i); 
			BigDecimal adjClose;

			if (isFirstDay(i)) {
				adjClose =  calculaEma(diaGraficoComum.getClose(), getSMA(i, i + periodo, diasParaOCalculo), multiplicador, i);
			} else {
				adjClose = calculaEma(diaGraficoComum.getClose(), dias.get(i - 1).getClose(), multiplicador, i);
			}
			
			dias.add(new Dia(diaGraficoComum.getDate(), adjClose));

		}
		Grafico graficoEMA = new Grafico();
		graficoEMA.setDias(dias);
		
		return graficoEMA;
	}

	private boolean isFirstDay(int i) {
		return i == 0;
	}

	private BigDecimal calculaEma(BigDecimal close, BigDecimal emaOntemOuSma, double multiplicador, int i) {
		BigDecimal bigEma = (close.subtract(emaOntemOuSma)).multiply(new BigDecimal(multiplicador)).add(emaOntemOuSma);
		
		return bigEma;
	}

	private double getMultiplicador(int periodo) {
		return (2.0 / (periodo + 1.0));
	}

	private BigDecimal getSMA(int inicio, int fim, List<Dia> diasParaOCalculo) {
		
		List<BigDecimal> fechamentoPorDia = diasParaOCalculo.stream().map(Dia::getClose)
				.collect(Collectors.toList());
		
		if (fim > fechamentoPorDia.size()) {
			fim = fechamentoPorDia.size();
		}
		List<BigDecimal> subList = fechamentoPorDia.subList(inicio , fim - 1);
		Double average = subList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		return new BigDecimal(average);
	}
}
