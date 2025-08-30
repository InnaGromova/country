package countryservice;

import com.yourpackage.grpc.CountryRequest;
import com.yourpackage.grpc.CountryResponse;
import com.yourpackage.grpc.CountryServiceGrpc;
import com.yourpackage.grpc.Empty;
import countryservice.model.Country;
import countryservice.repository.CountryRepository;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CountryGrpcService extends CountryServiceGrpc.CountryServiceImplBase {
    private final CountryRepository countryRepository;

    public CountryGrpcService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<CountryResponse> responseObserver) {
        return new StreamObserver<CountryRequest>() {
            private final List<Country> savedCountries = new ArrayList<>();
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public void onNext(CountryRequest request) {
                try {
                    Country countryEntity = new Country();
                    countryEntity.setCode(request.getCode());
                    countryEntity.setName(request.getName());

                    countryRepository.save(countryEntity);
                    count.incrementAndGet();
                    savedCountries.add(countryEntity);

                } catch (Exception e) {
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                List<com.yourpackage.grpc.Country> grpcCountries = new ArrayList<>();
                for (Country entity : savedCountries) {
                    com.yourpackage.grpc.Country grpcCountry = com.yourpackage.grpc.Country.newBuilder()
                            .setName(entity.getName())
                            .setCode(entity.getCode())
                            .build();
                    grpcCountries.add(grpcCountry);
                }

                CountryResponse response = CountryResponse.newBuilder()
                        .setCount(count.get())
                        .addAllCountries(grpcCountries)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
    @Override
    public void getAllCountries(Empty request, StreamObserver<com.yourpackage.grpc.Country> responseObserver) {
        List<Country> allCountries = countryRepository.findAll();

        for (Country entity : allCountries) {
            com.yourpackage.grpc.Country grpcCountry = com.yourpackage.grpc.Country.newBuilder()
                    .setName(entity.getName() != null ? entity.getName() : "")
                    .setCode(entity.getCode() != null ? entity.getCode() : "")
                    .build();

            responseObserver.onNext(grpcCountry);
        }
        responseObserver.onCompleted();
    }
}
