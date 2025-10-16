// src/main/java/com/pablomelzi/prueba/application/service/GetPriceServiceImpl.java
package com.pablomelzi.prueba.application.service;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import com.pablomelzi.prueba.domain.usecase.GetPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GetPriceServiceImpl implements GetPriceQuery {

    private static final Logger logger = LoggerFactory.getLogger(GetPriceServiceImpl.class);

    private final GetPriceService getPriceService;

    public GetPriceServiceImpl(PriceRepositoryPort priceRepositoryPort) {
        this.getPriceService = new GetPriceService(priceRepositoryPort);
    }

    @Override
    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        logger.info("Consulta de precio: productId={}, brandId={}, date={}", productId, brandId, applicationDate);
        try {
            Price price = getPriceService.getPrice(applicationDate, productId, brandId);
            logger.debug("Precio encontrado: priceList={}, priority={}, currency={}", price.getPriceList(), price.getPriority(), price.getCurrency());
            return price;
        } catch (Exception e) {
            logger.error("Error al obtener precio para productId={}, brandId={}, date={}: {}", productId, brandId, applicationDate, e.getMessage());
            throw e;
        }
    }
}


