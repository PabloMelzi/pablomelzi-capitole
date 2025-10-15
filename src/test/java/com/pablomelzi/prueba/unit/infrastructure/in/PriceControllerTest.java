package com.pablomelzi.prueba.unit.infrastructure.in;


import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.infrastructure.in.PriceController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceControllerTest {

    @Test
    void devuelvePrecioCorrecto() {
        GetPriceQuery mockQuery = Mockito.mock(GetPriceQuery.class);
        Price expected = Price.builder()
                .productId(35455L)
                .brandId(1L)
                .priceList(2)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .build();

        Mockito.when(mockQuery.getPrice(
                        LocalDateTime.parse("2020-06-14T16:00:00"), 35455L, 1L))
                .thenReturn(expected);

        PriceController controller = new PriceController(mockQuery);

        Price result = controller.getPrice(
                LocalDateTime.parse("2020-06-14T16:00:00"), 35455L, 1L);

        assertThat(result).isEqualTo(expected);
    }
}

