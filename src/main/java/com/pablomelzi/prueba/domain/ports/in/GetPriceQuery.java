package com.pablomelzi.prueba.domain.ports.in;

import com.pablomelzi.prueba.domain.model.Price;

import java.time.LocalDateTime;

public interface GetPriceQuery {
    Price getPrice(LocalDateTime applicationDate, long productId, long brandId);
}

