package org.chern.manager.controller;

import jakarta.inject.Inject;
import org.chern.manager.client.ProductsRestClient;
import org.chern.manager.controller.payload.NewProductPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты ProductsController")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @InjectMocks
    ProductsController controller;

    @Test
    @DisplayName("createProduct создаст товар и перенаправит на страницу товара")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        //given


        //when


        //then
    }
}