package countryservice.repository;

import countryservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findByCode(String code);
    Optional<Country> findByName(String name);

    @Query("SELECT c FROM Country c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Country> searchByName(@Param("query") String query);

    List<Country> findAllByOrderByNameAsc();

}