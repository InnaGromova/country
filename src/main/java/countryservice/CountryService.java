package countryservice;


import countryservice.model.Country;
import countryservice.repository.CountryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findByCode(code);
    }

    public Optional<Country> getCountryByName(String name) {
        return countryRepository.findByName(name);
    }

    public List<Country> searchCountries(String query) {
        return countryRepository.searchByName(query);
    }

    public Country createCountry(String code, String name) {
        Country country = new Country(code, name);
        return countryRepository.save(country);
    }

    public Optional<Country> updateCountry(String code, String newName) {
        Optional<Country> existing = countryRepository.findByCode(code);
        if (existing.isPresent()) {
            Country country = existing.get();
            country.setName(newName);
            return Optional.of(countryRepository.save(country));
        }
        return Optional.empty();
    }

    public boolean deleteCountry(String code) {
        Optional<Country> country = countryRepository.findByCode(code);
        if (country.isPresent()) {
            countryRepository.delete(country.get());
            return true;
        }
        return false;
    }
}