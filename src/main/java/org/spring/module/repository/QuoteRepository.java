package org.spring.module.repository;

import org.spring.module.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT * FROM quote ORDER BY date DESC, latest_price DESC limit 5", nativeQuery = true)
    List<Quote> findFiveMostValuableCompanies();

    @Query(value = "SELECT * FROM quote ORDER BY date DESC, iex_realtime_price - latest_price DESC LIMIT 5", nativeQuery = true)
    List<Quote> findFiveCompaniesBiggestChange();

}
