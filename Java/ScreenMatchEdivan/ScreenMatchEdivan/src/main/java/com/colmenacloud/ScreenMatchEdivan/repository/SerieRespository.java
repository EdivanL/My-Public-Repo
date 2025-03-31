package com.colmenacloud.ScreenMatchEdivan.repository;

import com.colmenacloud.ScreenMatchEdivan.model.Categoria;
import com.colmenacloud.ScreenMatchEdivan.model.Episodio;
import com.colmenacloud.ScreenMatchEdivan.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRespository extends JpaRepository<Serie, Long> {


    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadaLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporada, double avaliacao);
    //select * from series WHERE series.total_temporadas <= 5 AND series.avaliacao >= 7.5

//    @Query(value = "select * from series WHERE series.total_temporadas <= 5 AND series.avaliacao >= 7.5", nativeQuery = true)
//    List<Serie> seriesPorTemporadaEAvalicao();

    @Query("SELECT s FROM Serie s WHERE s.totalTemporada <= :totalTemporada AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvalicao(int totalTemporada, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEP%")
    List<Episodio> episodiosPorTrecho(String trechoEP);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEPSerie(Serie serie);
}
