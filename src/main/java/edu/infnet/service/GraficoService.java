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

		//https://br.financas.yahoo.com/quote/MGLU3.SA/chart?p=MGLU3.SA#eyJpbnRlcnZhbCI6ImRheSIsInBlcmlvZGljaXR5IjoxLCJjYW5kbGVXaWR0aCI6NC41OTY3NzQxOTM1NDgzODcsInZvbHVtZVVuZGVybGF5Ijp0cnVlLCJhZGoiOnRydWUsImNyb3NzaGFpciI6dHJ1ZSwiY2hhcnRUeXBlIjoibGluZSIsImV4dGVuZGVkIjp0cnVlLCJtYXJrZXRTZXNzaW9ucyI6eyJwcmUiOnRydWUsInBvc3QiOnRydWV9LCJhZ2dyZWdhdGlvblR5cGUiOiJvaGxjIiwiY2hhcnRTY2FsZSI6ImxpbmVhciIsInBhbmVscyI6eyJjaGFydCI6eyJwZXJjZW50IjoxLCJkaXNwbGF5IjoiTUdMVTMuU0EiLCJjaGFydE5hbWUiOiJjaGFydCIsImluZGV4IjowLCJ5QXhpcyI6eyJuYW1lIjoiY2hhcnQiLCJwb3NpdGlvbiI6bnVsbH0sInlheGlzTEhTIjpbXSwieWF4aXNSSFMiOlsiY2hhcnQiLCJ2b2wgdW5kciJdfX0sImxpbmVXaWR0aCI6Miwic3RyaXBlZEJhY2tncm91bmQiOnRydWUsImV2ZW50cyI6dHJ1ZSwiY29sb3IiOiIjMDA4MWYyIiwic3RyaXBlZEJhY2tncm91ZCI6dHJ1ZSwiZXZlbnRNYXAiOnsiY29ycG9yYXRlIjp7ImRpdnMiOnRydWUsInNwbGl0cyI6dHJ1ZX0sInNpZ0RldiI6e319LCJzeW1ib2xzIjpbeyJzeW1ib2wiOiJNR0xVMy5TQSIsInN5bWJvbE9iamVjdCI6eyJzeW1ib2wiOiJNR0xVMy5TQSIsInF1b3RlVHlwZSI6IkVRVUlUWSIsImV4Y2hhbmdlVGltZVpvbmUiOiJBbWVyaWNhL1Nhb19QYXVsbyJ9LCJwZXJpb2RpY2l0eSI6MSwiaW50ZXJ2YWwiOiJkYXkiLCJ0aW1lVW5pdCI6bnVsbCwic2V0U3BhbiI6eyJtdWx0aXBsaWVyIjoxLCJiYXNlIjoieWVhciIsInBlcmlvZGljaXR5Ijp7InBlcmlvZCI6MSwiaW50ZXJ2YWwiOiJkYXkifX19XSwiY3VzdG9tUmFuZ2UiOm51bGwsInN0dWRpZXMiOnsidm9sIHVuZHIiOnsidHlwZSI6InZvbCB1bmRyIiwiaW5wdXRzIjp7ImlkIjoidm9sIHVuZHIiLCJkaXNwbGF5Ijoidm9sIHVuZHIifSwib3V0cHV0cyI6eyJVcCBWb2x1bWUiOiIjMDBiMDYxIiwiRG93biBWb2x1bWUiOiIjRkYzMzNBIn0sInBhbmVsIjoiY2hhcnQiLCJwYXJhbWV0ZXJzIjp7IndpZHRoRmFjdG9yIjowLjQ1LCJjaGFydE5hbWUiOiJjaGFydCIsInBhbmVsTmFtZSI6ImNoYXJ0In19LCLigIxtYeKAjCAoOSxDLGVtYSwwKSI6eyJ0eXBlIjoibWEiLCJpbnB1dHMiOnsiUGVyaW9kIjoiOSIsIkZpZWxkIjoiQ2xvc2UiLCJUeXBlIjoiZXhwb25lbnRpYWwiLCJPZmZzZXQiOjAsImlkIjoi4oCMbWHigIwgKDksQyxlbWEsMCkiLCJkaXNwbGF5Ijoi4oCMbWHigIwgKDksQyxlbWEsMCkifSwib3V0cHV0cyI6eyJNQSI6IiNmZjMzM2EifSwicGFuZWwiOiJjaGFydCIsInBhcmFtZXRlcnMiOnsiY2hhcnROYW1lIjoiY2hhcnQiLCJwYW5lbE5hbWUiOiJjaGFydCJ9fSwi4oCMbWHigIwgKDEyLEMsZW1hLDApIjp7InR5cGUiOiJtYSIsImlucHV0cyI6eyJQZXJpb2QiOiIxMiIsIkZpZWxkIjoiQ2xvc2UiLCJUeXBlIjoiZXhwb25lbnRpYWwiLCJPZmZzZXQiOjAsImlkIjoi4oCMbWHigIwgKDEyLEMsZW1hLDApIiwiZGlzcGxheSI6IuKAjG1h4oCMICgxMixDLGVtYSwwKSJ9LCJvdXRwdXRzIjp7Ik1BIjoiI2ZmZGI0OCJ9LCJwYW5lbCI6ImNoYXJ0IiwicGFyYW1ldGVycyI6eyJjaGFydE5hbWUiOiJjaGFydCJ9fSwi4oCMbWHigIwgKDI2LEMsZW1hLDApIjp7InR5cGUiOiJtYSIsImlucHV0cyI6eyJQZXJpb2QiOiIyNiIsIkZpZWxkIjoiQ2xvc2UiLCJUeXBlIjoiZXhwb25lbnRpYWwiLCJPZmZzZXQiOjAsImlkIjoi4oCMbWHigIwgKDI2LEMsZW1hLDApIiwiZGlzcGxheSI6IuKAjG1h4oCMICgyNixDLGVtYSwwKSJ9LCJvdXRwdXRzIjp7Ik1BIjoiIzY0ZjFkOSJ9LCJwYW5lbCI6ImNoYXJ0IiwicGFyYW1ldGVycyI6eyJjaGFydE5hbWUiOiJjaGFydCJ9fX0sInRpbWVVbml0IjpudWxsLCJzZXRTcGFuIjp7Im11bHRpcGxpZXIiOjEsImJhc2UiOiJ5ZWFyIiwicGVyaW9kaWNpdHkiOnsicGVyaW9kIjoxLCJpbnRlcnZhbCI6ImRheSJ9fX0%3D
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
		if ( periodo == 12) {
			multiplicador = 0.153846154;
		} else if ( periodo == 26) {
			multiplicador = 0.074074074;
		}
		
		for (int i = 0; i < grafico.getDias().size(); i++) {

			diaGraficoComum = grafico.getDias().get(i); 
			BigDecimal adjClose;

			if (isFirstDay(i)) {
				System.out.println("-----");
				System.out.println("periodo  " + periodo);
				System.out.println("close " + diaGraficoComum.getClose());
				System.out.println("emaOntem/sma " + getSMA(i, i + periodo));
				System.out.println("multi " + multiplicador);
				adjClose =  calculaEma(diaGraficoComum.getClose(), getSMA(i, i + periodo), multiplicador, i);
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
		
		BigDecimal subtract = close.subtract(emaOntemOuSma);
		//−1,053400091
		
		BigDecimal add = new BigDecimal(multiplicador).add(emaOntemOuSma);
		//21,644746245
		
		BigDecimal multiply = subtract.multiply(add);

		BigDecimal bigEma = (close.subtract(emaOntemOuSma)).multiply(new BigDecimal(multiplicador)).add(emaOntemOuSma);
		if (i ==0) {
			System.out.println("multiplicador  " + new BigDecimal(multiplicador));
			System.out.println("subtract " + subtract);
			System.out.println("add " + add);
			System.out.println("multiply " + multiply);
			System.out.println("bigEma " + bigEma.doubleValue());
			System.out.println("bigEma " + bigEma);
		}
		
		return bigEma;
	}

	private double getMultiplicador(int periodo) {
		return (2.0 / (periodo + 1.0));
	}

	private BigDecimal getSMA(int inicio, int fim) {
		List<BigDecimal> fechamentoPorDia = grafico.getDias().stream().map(Dia::getClose)
				.collect(Collectors.toList());
		
		if (fim > fechamentoPorDia.size()) {
			fim = fechamentoPorDia.size();
		}
		List<BigDecimal> subList = fechamentoPorDia.subList(inicio , fim - 1);
		//SMA ANDA CONFORME A DATA. SMA DE UM PERIODO DE 9. DIVIDE OS 9 DIAS E TEM UM SMA!
		//DIVIDE AGORA MAIS 9 DIAS, A PARTIR DO 2 DIA E TEM SE O SMA DO 2 DIA!
		//DIVIDE AGORA MAIS 9 DIAS, A PARTIR DO 3 DIA E TEM SE O SMA DO 3 DIA!
		//https://wpcalc.com/en/simple-moving-average/
		Double average = subList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		return new BigDecimal(average);
	}
}
