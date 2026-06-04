package be.vdab.luigi.services;

import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.restclients.KoersClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultEuroService implements EuroService {
    private final KoersClient[] koersClients;

    DefaultEuroService(KoersClient[] koersClients) {
        this.koersClients = koersClients;
    }

    @Override
    public BigDecimal toDollar(BigDecimal euro) {
        Exception lastException = null;
        for (var client : koersClients) {
            try {
                return euro.multiply(client.getDollarKoers())
                        .setScale(2, RoundingMode.HALF_UP);
            } catch (KoersClientException ex) {

            }
        }
        throw new KoersClientException(" Kan dollar koers nergens lezen.", lastException);
    }
}
