package com.pablomelzi.prueba.unit.domain.usecase;

import com.pablomelzi.prueba.domain.exception.PriceNotFoundException;
import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import com.pablomelzi.prueba.domain.usecase.GetPriceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GetPriceServiceTest {

    private PriceRepositoryPort repoWithData = (date, productId, brandId) -> List.of(
                    Price.builder()
                            .productId(35455L)
                            .brandId(1L)
                            .priceList(1)
                            .priority(0)
                            .price(new BigDecimal("35.50"))
                            .currency("EUR")
                            .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                            .build(),
                    Price.builder()
                            .productId(35455L)
                            .brandId(1L)
                            .priceList(2)
                            .priority(1)
                            .price(new BigDecimal("25.45"))
                            .currency("EUR")
                            .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                            .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                            .build()
            ).stream()
            .filter(p -> !date.isBefore(p.getStartDate()) && !date.isAfter(p.getEndDate()))
            .toList();


    @Test
    void devuelvePrecioConMayorPrioridadSiHaySolapamiento() {
        GetPriceService service = new GetPriceService(repoWithData);
        Price price = service.getPrice(LocalDateTime.parse("2020-06-14T16:00:00"), 35455L, 1L);
        assertThat(price.getPriceList()).isEqualTo(2);
        assertThat(price.getPriority()).isEqualTo(1);
    }

    @Test
    void lanzaExcepcionSiNoHayPrecio() {
        PriceRepositoryPort emptyRepo = (date, productId, brandId) -> List.of();
        GetPriceService service = new GetPriceService(emptyRepo);
        assertThatThrownBy(() -> service.getPrice(LocalDateTime.now(), 99999L, 1L))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No price found");
    }

    @Test
    void seleccionaPrecioEnElBordeDeFechaInicio() {
        GetPriceService service = new GetPriceService(repoWithData);
        Price price = service.getPrice(LocalDateTime.parse("2020-06-14T00:00:00"), 35455L, 1L);
        assertThat(price.getPriceList()).isEqualTo(1);
    }

    @Test
    void seleccionaPrecioEnElBordeDeFechaFin() {
        GetPriceService service = new GetPriceService(repoWithData);
        Price price = service.getPrice(LocalDateTime.parse("2020-12-31T23:59:59"), 35455L, 1L);
        assertThat(price.getPriceList()).isEqualTo(1);
    }

    @Test
    void devuelveMonedaCorrecta() {
        GetPriceService service = new GetPriceService(repoWithData);
        Price price = service.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1L);
        assertThat(price.getCurrency()).isEqualTo("EUR");
    }
}

