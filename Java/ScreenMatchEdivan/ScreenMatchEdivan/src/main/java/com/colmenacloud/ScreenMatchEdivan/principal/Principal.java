package com.colmenacloud.ScreenMatchEdivan.principal;

import com.colmenacloud.ScreenMatchEdivan.model.DadosEpisodio;
import com.colmenacloud.ScreenMatchEdivan.model.DadosSerie;
import com.colmenacloud.ScreenMatchEdivan.model.DadosTemporada;
import com.colmenacloud.ScreenMatchEdivan.service.ConsumoAPI;
import com.colmenacloud.ScreenMatchEdivan.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d41de10d";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    public void exibeMenu() {
        System.out.println("Digito o nome da série: ");
        var nomeSerie = leitura.nextLine();


        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serie);

//        json = consumoAPI.obterDados(URL+ nomeSerie.replace(" ", "+") +"&season="+ 1 + "&episode="+ 2 +API_KEY);
//        DadosEpisodio episodio = conversor.obterDados(json, DadosEpisodio.class);
//		System.out.println(episodio);

//        json = consumoAPI.obterDados(URL+ nomeSerie.replace(" ", "+") +"&season="+ 1 + "&episode="+ 1 +API_KEY);
//        DadosTemporada temp = conversor.obterDados(json, DadosTemporada.class);
//        System.out.println(episodio);


        List<DadosTemporada> listTemporadas = new ArrayList<>();
        for (int i = 1; i <= serie.totalTemporada(); i++) {
            json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//            System.out.println(URL+ nomeSerie.replace(" ", "+") +"&season"+ i + API_KEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            listTemporadas.add(temporada);
            System.out.println(temporada);


        }
        for (int i = 0; i < serie.totalTemporada(); i++) {
            List<DadosEpisodio> listEpisodio = listTemporadas.get(i).episodios();
            for (int j = 0; j < listEpisodio.size(); j++) {
                System.out.println(listEpisodio.get(j).titulo());
            }
        }


//		listTemporadas.forEach(System.out::println);


//"http://www.omdbapi.com/?t=supernatural&apikey=d41de10d"
    }
}
