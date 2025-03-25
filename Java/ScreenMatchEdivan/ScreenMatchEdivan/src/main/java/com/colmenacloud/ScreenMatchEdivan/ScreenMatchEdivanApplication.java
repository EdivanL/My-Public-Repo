package com.colmenacloud.ScreenMatchEdivan;

import com.colmenacloud.ScreenMatchEdivan.model.DadosEpisodio;
import com.colmenacloud.ScreenMatchEdivan.model.DadosSerie;
import com.colmenacloud.ScreenMatchEdivan.model.DadosTemporada;
import com.colmenacloud.ScreenMatchEdivan.principal.Principal;
import com.colmenacloud.ScreenMatchEdivan.service.ConsumoAPI;
import com.colmenacloud.ScreenMatchEdivan.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatchEdivanApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchEdivanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

//		ConsumoAPI consumoapi =new ConsumoAPI();
//		var json = consumoapi.obterDados("http://www.omdbapi.com/?t=supernatural&apikey=d41de10d");
//
//		System.out.println(json);
//
//
//
//		json = consumoapi.obterDados("https://www.omdbapi.com/?t=supernatural&season=1&episode=2&apikey=d41de10d");
//		DadosEpisodio episodio = conversor.obterDados(json, DadosEpisodio.class);
//		System.out.println(episodio);
//
//		List<DadosTemporada> listTemporadas = new ArrayList<>();
//
//		for (int i = 0; i <= serie.totalTemporada() ; i++) {
//			json = consumoapi.obterDados("https://www.omdbapi.com/?t=supernatural&season="+ i +"&apikey=d41de10d");
//			DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
//			listTemporadas.add(temporada);
//		}
//		listTemporadas.forEach(System.out::println);


//		json = consumoapi.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(json);


	}
}
