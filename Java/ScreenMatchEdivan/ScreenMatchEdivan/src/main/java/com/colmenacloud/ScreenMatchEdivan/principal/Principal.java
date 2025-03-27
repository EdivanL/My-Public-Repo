package com.colmenacloud.ScreenMatchEdivan.principal;

import com.colmenacloud.ScreenMatchEdivan.model.*;
import com.colmenacloud.ScreenMatchEdivan.service.ConsumoAPI;
import com.colmenacloud.ScreenMatchEdivan.service.ConverteDados;

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

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Series
                    2 - Buscar Episódios
                    3 - Listar Series Buscadas
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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarSeriesBuscadas(){
        List<Serie> series = new ArrayList<>();
        series = dadosSeries.stream()
                        .map(d -> new Serie(d))
                                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        dadosSeries.add(dados);
        System.out.println(dados);

    }

    private DadosSerie getDadosSerie(){
        System.out.println("Digito o nome da série: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
//        System.out.println(serie);

//        System.out.println("\nListando as Temporadas e Episódios");
        List<DadosTemporada> listTemporadas = new ArrayList<>();
        for (
                int i = 1;
                i <= serie.totalTemporada(); i++) {
            json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//            System.out.println(URL+ nomeSerie.replace(" ", "+") +"&season"+ i + API_KEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            listTemporadas.add(temporada);
//            System.out.println(temporada);
        }

        //        json = consumoAPI.obterDados(URL+ nomeSerie.replace(" ", "+") +"&season="+ 1 + "&episode="+ 2 +API_KEY);
//        DadosEpisodio episodio = conversor.obterDados(json, DadosEpisodio.class);
//		System.out.println(episodio);

//        json = consumoAPI.obterDados(URL+ nomeSerie.replace(" ", "+") +"&season="+ 1 + "&episode="+ 1 +API_KEY);
//        DadosTemporada temp = conversor.obterDados(json, DadosTemporada.class);
//        System.out.println(episodio);
        return serie;
    }


    private void buscarEpisodioPorSerie() {
        System.out.println("Digito o nome da série: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serie);

//        System.out.println("\nListando as Temporadas e Episódios");
        List<DadosTemporada> listTemporadas = new ArrayList<>();
        for (
                int i = 1;
                i <= serie.totalTemporada(); i++) {
            json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//            System.out.println(URL+ nomeSerie.replace(" ", "+") +"&season"+ i + API_KEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            listTemporadas.add(temporada);
//            System.out.println(temporada);
        }



//        System.out.println("\nTemporadas e Episódios");
//        List<DadosTemporada> listTemporadas = new ArrayList<>();
        for (
                int i = 1;
                i <= serie.totalTemporada(); i++) {
            json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//            System.out.println(URL+ nomeSerie.replace(" ", "+") +"&season"+ i + API_KEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            listTemporadas.add(temporada);
//            System.out.println(temporada);
        }


//        for (int i = 0; i < serie.totalTemporada(); i++) {
//            List<DadosEpisodio> listEpisodio = listTemporadas.get(i).episodios();
//            for (int j = 0; j < listEpisodio.size(); j++) {
//                System.out.println(listEpisodio.get(j).titulo());
//            }
//        }

// MESMA COISA
//		listTemporadas.forEach(System.out::println);
//      listTemporadas.forEach(t ->System.out.println(t));
// "http://www.omdbapi.com/?t=supernatural&apikey=d41de10d"

//        listTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodio> listaDadosEp = listTemporadas.stream()
                .flatMap(t -> t.episodios().stream())
//                .add
                .collect(Collectors.toList());

//        System.out.println("\nTop 7 episódios");
//        listaDadosEp.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(7)
//                .forEach(System.out::println);

        System.out.println("\nLista de Episódios");
        List<Episodio> episodios = listTemporadas.stream()
                .flatMap(dt -> dt.episodios().stream()
                        .map(de -> new Episodio(dt.numero(), de))
                ).collect(Collectors.toUnmodifiableList());

//        episodios.forEach(System.out::println);


        System.out.println("Digite o nome do espisódio que está buscando: ");
        var trechoTitulo = leitura.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio Encontrado: " + episodioBuscado.get().getTitulo() + "  Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado");
        }


//        Map<Integer, Double> avaliacoesTemporada = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//
//        System.out.println("Avaliação por temporada:" + avaliacoesTemporada);
//
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println("Média: " + est.getAverage() + "Minimo: " + est.getMin() + "Máximo: " + est.getMax() + "Quantidade: " + est.getCount());


//        System.out.println("A partir de que ano quer ver os episódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data Lançamento: " + e.getDataLancamento().format(formatadorData)
//
//                ));


//        System.out.println("Stream:");
//        List<String> listaNomes = Arrays.asList("Paulo", "Edivan", "Gabriel", "Marcos", "Lucas");
//        listaNomes.stream()
//                .sorted()
//                .peek(ln -> System.out.println("Ordenando" + ln ))
//                .limit(3)
//                .peek(ln -> System.out.println("Filtrando  3" + ln ))
//                .filter(n -> n.startsWith("E"))
//                .peek(ln -> System.out.println("Filtrando  começa com E" + ln ))
//                .map(n -> n.toUpperCase())

        /// /                .forEach(System.out::println)
//                .peek(ln -> System.out.println("Maiúsculo" + ln ))
//                .forEach(n -> System.out.println("Olá, " + n + "!"));
//
//
    }


}