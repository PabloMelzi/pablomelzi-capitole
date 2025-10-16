package com.pablomelzi.prueba.domain.usecase;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import com.pablomelzi.prueba.domain.exception.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GetPriceService implements GetPriceQuery {

    private static final Logger logger = LoggerFactory.getLogger(GetPriceService.class);
    private final PriceRepositoryPort priceRepository;

    public GetPriceService(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        logger.info("Buscando precio: productId={}, brandId={}, date={}", productId, brandId, applicationDate);
        List<Price> prices = priceRepository.findPrices(applicationDate, productId, brandId);

        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> {
                    logger.error("No se encontró precio para productId={}, brandId={}, date={}", productId, brandId, applicationDate);
                    return new PriceNotFoundException(
                            String.format("No price found for productId %d, brandId %d, date %s",
                                    productId, brandId, applicationDate));
                });
    }
}



