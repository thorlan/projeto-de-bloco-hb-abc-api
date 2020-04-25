package edu.infnet.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.infnet.model.Grafico;
import edu.infnet.service.GraficoService;

@RestController()
@RequestMapping("/abc-hb/api")
public class GraficoResource {

	
	//endereço https://hb-abc.herokuapp.com/abc-hb/api/grafico
	@Autowired
	private GraficoService graficoService;
	
	@CrossOrigin("*")
	@GetMapping("/grafico")
	public ResponseEntity<List<Grafico>> getGrafico(){
		
		List<Grafico> graficos = graficoService.getGraficos();
		
		return new ResponseEntity<List<Grafico>>(graficos, HttpStatus.ACCEPTED);
	}
}
