package edu.infnet;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.infnet.leitor.LeitorDeCSV;
import edu.infnet.model.Dia;
import edu.infnet.model.Grafico;
import edu.infnet.service.GraficoService;
import edu.infnet.util.Conversor;

@SpringBootTest
class InfnetPbDApplicationTests {

	@Autowired
	private LeitorDeCSV leitor;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private GraficoService graficoService;

	private List<Grafico> graficosLidos;
	
	@Test
	void deveLerOArquivoCSV() {
		Assertions.assertThatCode(() -> leitor.leArquivo()).doesNotThrowAnyException();
	}
	
	@Test
	void deveRetornarUmaLocalDateAPartirDeUmaString() {
		String strLocalDate = "2020-06-28";
		
		LocalDate localDate = LocalDate.of(2020, 06, 28);
		LocalDate resultadoDoConversorParaData = conversor.stringToLocalDate(strLocalDate);
		
		assertThat(localDate).isEqualTo(resultadoDoConversorParaData);
	}
	
	@Test
	void deveRetornarUmaListaDeGraficos() {
		graficosLidos = graficoService.getGraficos();
		assertThat(graficosLidos.size()).isGreaterThan(0);
	}

}
