package edu.infnet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

	private List<Grafico> graficos;

	@PostConstruct
	private void leArquivo() {

		graficos = new ArrayList<>();

		grafico = leitor.leArquivo();

//		List<Dia> diaTeste = new ArrayList<>();
//		for (int i = 2; i <= 20 ; i+=2) {
//			diaTeste.add(new Dia(LocalDate.now(), new BigDecimal(i)));
//		}
//		
//		grafico.setDias(diaTeste);
		
		graficos.add(grafico);

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

		List<Dia> dias = new ArrayList<>();

		Dia diaGraficoComum;
		
		double multiplicador = getMultiplicador(periodo);
		
		for (int i = 0; i < grafico.getDias().size(); i++) {

			diaGraficoComum = grafico.getDias().get(i);
			BigDecimal adjClose;

			if (isFirstDay(i)) {
				adjClose =  calculaEma(diaGraficoComum.getAdjClose(), getSMA(i, i + periodo), multiplicador);
			} else {
				adjClose = calculaEma(diaGraficoComum.getAdjClose(), dias.get(i - 1).getAdjClose(), multiplicador);
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

	private BigDecimal calculaEma(BigDecimal close, BigDecimal emaOntemOuSma, double multiplicador) {
		
		System.out.println("----------");
		System.out.println("tod" + close);
		System.out.println("yest" + emaOntemOuSma);
		
		
		BigDecimal bigEma = (close.subtract(emaOntemOuSma)).multiply(new BigDecimal(multiplicador)).add(emaOntemOuSma);
		//System.out.println(bigEma);
		return bigEma;
	}

	private double getMultiplicador(int periodo) {
		return (2.0 / (periodo + 1.0));
	}

	private BigDecimal getSMA(int inicio, int fim) {
		List<BigDecimal> fechamentoPorDia = grafico.getDias().stream().map(Dia::getAdjClose)
				.collect(Collectors.toList());
		
		if (fim > fechamentoPorDia.size()) {
			fim = fechamentoPorDia.size();
		}
		
		List<BigDecimal> subList = fechamentoPorDia.subList(inicio , fim);
		//SMA ANDA CONFORME A DATA. SMA DE UM PERIODO DE 9. DIVIDE OS 9 DIAS E TEM UM SMA!
		//DIVIDE AGORA MAIS 9 DIAS, A PARTIR DO 2 DIA E TEM SE O SMA DO 2 DIA!
		//DIVIDE AGORA MAIS 9 DIAS, A PARTIR DO 3 DIA E TEM SE O SMA DO 3 DIA!
		//https://wpcalc.com/en/simple-moving-average/
		Double average = subList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		System.out.println("inicio :" + inicio + " fim " + fim + " avg " + average);
		return new BigDecimal(average);
	}
}
