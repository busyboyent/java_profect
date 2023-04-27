package com.dev.javadevproject;

import com.dev.javadevproject.dto.goods.ProductResponse;
import com.dev.javadevproject.entities.ProductEntity;
import com.dev.javadevproject.repositories.GoodsRepo;
import com.dev.javadevproject.resources.GoodsResource;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GoodsResourceTest {

    @MockBean
    private GoodsRepo goodsRepo;

    public static final List<ProductEntity> testProductsList = List.of(
            new ProductEntity(1, "Яблоко", "Просто яблоко", "Фрукты", 200, 50),
            new ProductEntity(2, "Огурец", "Просто огурец", "Фрукты", 200, 50)
    );

    public static final List<ProductEntity> searchTestProductsList = List.of(
            new ProductEntity(3, "Яблоко", "Просто яблоко", "Фрукты", 200, 50)
    );

    @AfterEach
    public void tearDown() {
        Mockito.reset(goodsRepo);
    }

    @Test
    @DisplayName("Возвращает продукты в нужном формате")
    public void allProductsAreReturned() throws Exception {
        when(goodsRepo.findAll()).thenReturn(testProductsList);

        final HttpResponse<String> response = get("/goods");

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"items\":[{\"Id\":1,\"name\":\"Яблоко\",\"description\":\"Просто яблоко\",\"category\":\"Фрукты\",\"price\":200,\"discount\":50,\"сategory\":\"Фрукты\"},{\"Id\":2,\"name\":\"Огурец\",\"description\":\"Просто огурец\",\"category\":\"Фрукты\",\"price\":200,\"discount\":50,\"сategory\":\"Фрукты\"}],\"count\":2}");

        verify(goodsRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Возвращает продукты через поиск")
    public void filteredProductsAreReturnedWhenSearchIsUsed() throws Exception {
        when(goodsRepo.findByNameContainingIgnoreCase(eq("Ябло"))).thenReturn(searchTestProductsList);

        final HttpResponse<String> response = get("/goods?search=Ябло");

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"items\":[{\"Id\":3,\"name\":\"Яблоко\",\"description\":\"Просто яблоко\",\"category\":\"Фрукты\",\"price\":200,\"discount\":50,\"сategory\":\"Фрукты\"}],\"count\":1}");

        verify(goodsRepo, times(1)).findByNameContainingIgnoreCase(eq("Ябло"));
    }

    private HttpResponse<String> get(final String url) throws java.io.IOException, InterruptedException {
        return HttpClient.newHttpClient().send(
                HttpRequest.newBuilder()
                        .uri(URI.create(TestConfig.CLIENT_BASEURL + url))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());
    }
}
