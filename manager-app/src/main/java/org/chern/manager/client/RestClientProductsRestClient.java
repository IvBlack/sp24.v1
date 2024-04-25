package org.chern.manager.client;

import lombok.RequiredArgsConstructor;
import org.chern.manager.controller.payload.NewProductPayload;
import org.chern.manager.entity.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientProductsRestClient  implements ProductsRestClient {

    /*
        Полученный экземпляр PRODUCTS_TYPE_REFERENCE затем может быть использован для получения экземпляра Product,
        который содержит информацию о параметризованном типе, полученную во время выполнения.
        используя ParameterizedTypeReference, вы можете получить в результате объект с проверенным типом.
        Используя тип БЕЗ параметров, вам нужно будет в какой-то момент
        привести этот объект к безопасному по типу представлению.
        Это противоречит цели использования языков типов и снижает производительность.
    */
    private static final ParameterizedTypeReference<List<Product>>  PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<Product> findAllProducts() {
        return this.restClient
                .get()
                .uri("/catalogue-api/products")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    /*
        На стыке двух модулей приложения не рекомендуется использовать одну сущность - NewProductPayload в данном случае.
        здесь сделана одна сущность - поскольку по составу она одна и таже.
        В иных случаях нужно использовать свои сущности-классы для каждого модуля.

        Каждый из методов данного клиента предоставляет возможность менеджеру ритейла 
        взаимодействлвать с API каталога товаров или услуг посредством обращения на API's end-points.
    */
    @SuppressWarnings("unchecked")
    @Override
    public Product createProduct(String title, String details) {
        try {
            return this.restClient
                    .post()
                    .uri("/catalogue-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(title, details))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest ex) {
            ProblemDetail problemDetail = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateProduct(int productId, String title, String details) {
        try {
            this.restClient
            .patch()
            .uri("/catalogue-api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new NewProductPayload(title, details))
            .retrieve()
            .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest ex) {
            ProblemDetail problemDetail = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            this.restClient
            .delete()
            .uri("/catalogue-api/products/{productId}", productId)
            .retrieve()
            .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new NoSuchElementException(ex);
        }
    }
}
