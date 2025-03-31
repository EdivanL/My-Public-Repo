package com.colmenacloud.ScreenMatchEdivan.principal;

import com.colmenacloud.ScreenMatchEdivan.model.*;
import com.colmenacloud.ScreenMatchEdivan.repository.SerieRespository;
import com.colmenacloud.ScreenMatchEdivan.service.ConsumoAPI;
import com.colmenacloud.ScreenMatchEdivan.service.ConverteDados;
import org.aspectj.apache.bcel.Repository;

import javax.xml.transform.Source;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d41de10d";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();
//    private DadosSerie serie = new DadosSerie();
    private SerieRespository repositorio;
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBusca;

    public Principal(SerieRespository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Series
                    2 - Buscar Episódios
                    3 - Listar Series Buscadas
                    4 - Buscar Serie por título
                    5 - Buscar Series por ator
                    6 - Top 5 Séries
                    7 - Series por Categoria
                    8 - Series por Temporada e Avaliacao
                    9 - Buscar Episodio por Trecho
                    10 - Top 5 Episodios por Série
                    0 - Sair""";
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriePorCategoria();
                    break;
                case 8:
                    buscarSerieTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    Top5EpisodioPorSerie();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }




    private void listarSeriesBuscadas(){
          series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
//        dadosSeries.add(dados);
        repositorio.save(serie);

        System.out.println(dados);

    }

    private DadosSerie getDadosSerie(){
        listarSeriesBuscadas();
        System.out.println("Digito o nome da série: ");
        var nomeSerie = leitura.nextLine();



        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosS = conversor.obterDados(json, DadosSerie.class);
        return dadosS;
    }


    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
//                series.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporada(); i++) {
                var json = consumoAPI.obterDados(URL + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());

        } else {
            System.out.println("Série não encontrada!");
        }

    }

    private void buscarSeriePorAtor() {
        System.out.println("Qua o ator que está buscando?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Qual a avaliacao mínima?");
        var Avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, Avaliacao);
        System.out.println("Series em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println("Serie: " + s.getTitulo()+ " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> seriesTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
        seriesTop.forEach(s ->
                System.out.println("Serie: " + s.getTitulo()+ " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Deseja buscar séries de que categoria? ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriePorCategiria  = repositorio.findByGenero(categoria);
        System.out.println("Series da Categoria " + nomeGenero);
        seriePorCategiria.forEach(System.out::println);
    }

    private void buscarSerieTemporadaEAvaliacao() {
        System.out.println("Até quantas temporadas pode ter? ");
        var totalTemporada = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Qual avaliacao mínima desejada? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAvalicao(totalTemporada, avaliacao);
        filtroSeries.forEach(s ->
                System.out.println("Serie: " + s.getTitulo()+ " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Qua o nome do episodio que está buscando?");
        var trechoEP = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEP);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Serie: %s Temporada %s - Episodio %s - %s \n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void Top5EpisodioPorSerie() {
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEPSerie(serie);
            System.out.println("SerieBusca: " + serieBusca);
            topEpisodios.forEach(e -> System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo() ));
            System.out.println("SerieBusca: " + serieBusca);
        }
    }


}