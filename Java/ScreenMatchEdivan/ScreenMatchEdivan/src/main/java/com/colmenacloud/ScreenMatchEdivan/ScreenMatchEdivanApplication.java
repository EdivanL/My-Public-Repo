package com.colmenacloud.ScreenMatchEdivan;

import com.colmenacloud.ScreenMatchEdivan.model.DadosSerie;
import com.colmenacloud.ScreenMatchEdivan.service.ConsumoAPI;
import com.colmenacloud.ScreenMatchEdivan.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchEdivanApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchEdivanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoapi =new ConsumoAPI();
		var json = consumoapi.obterDados("http://www.omdbapi.com/?t=supernatural&apikey=d41de10d");

		System.out.println(json);
//		json = consumoapi.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}
