package edu.infnet.leitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.infnet.model.Grafico;
import edu.infnet.util.Conversor;

@Component
public class LeitorDeCSV {

	@Autowired
	private Conversor conversor;

	public List<Grafico> leArquivo() {

		String csvFile = "MGLU3.SA.csv";
		String line = "";
		String cvsSplitBy = ",";

		Grafico grafico;
		List<Grafico> graficos = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();

			while ((line = br.readLine()) != null) {

				String[] linha = line.split(cvsSplitBy);

				for (int i = 1; i < 7; i++) {

					if ("null".equals(linha[i].trim())) {
						line = br.readLine();
						linha = line.split(cvsSplitBy);
						break;
					}
				}

				grafico = new Grafico();
				grafico.setDate(conversor.stringToLocalDate(linha[0]));
				grafico.setOpen(new BigDecimal(linha[1]));
				grafico.setHigh(new BigDecimal(linha[2]));
				grafico.setLow(new BigDecimal(linha[3]));
				grafico.setClose(new BigDecimal(linha[4]));
				grafico.setAdjClose(new BigDecimal(linha[5]));
				grafico.setVolume(new BigDecimal(linha[6]));

				graficos.add(grafico);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return graficos;
	}
}
