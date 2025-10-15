package com.pablomelzi.prueba.infrastructure.in;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetPriceQuery getPriceQuery;

    public PriceController(GetPriceQuery getPriceQuery) {
        this.getPriceQuery = getPriceQuery;
    }

    @GetMapping
    public Price getPrice(@RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate, @RequestParam("productId") long productId, @RequestParam("brandId") long brandId) {

        return getPriceQuery.getPrice(applicationDate, productId, brandId);
    }
}


