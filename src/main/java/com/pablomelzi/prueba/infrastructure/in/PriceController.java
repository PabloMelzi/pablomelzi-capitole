package com.pablomelzi.prueba.infrastructure.in;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);
    private final GetPriceQuery getPriceQuery;

    public PriceController(GetPriceQuery getPriceQuery) {
        this.getPriceQuery = getPriceQuery;
    }

    @GetMapping
    public Price getPrice(@RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate, @RequestParam("productId") long productId, @RequestParam("brandId") long brandId) {
        logger.info("Petición recibida: productId={}, brandId={}, date={}", productId, brandId, applicationDate);
        try {
            Price price = getPriceQuery.getPrice(applicationDate, productId, brandId);
            logger.debug("Precio devuelto: priceList={}, priority={}, currency={}", price.getPriceList(), price.getPriority(), price.getCurrency());
            return price;
        } catch (Exception e) {
            logger.error("Error al obtener precio: productId={}, brandId={}, date={}: {}", productId, brandId, applicationDate, e.getMessage());
            throw e;
        }
    }
}



