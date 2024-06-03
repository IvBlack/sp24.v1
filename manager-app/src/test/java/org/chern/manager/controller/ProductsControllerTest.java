package org.chern.manager.controller;

import org.chern.manager.client.BadRequestException;
import org.chern.manager.client.ProductsRestClient;
import org.chern.manager.controller.payload.NewProductPayload;
import org.chern.manager.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты - ProductsController")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @InjectMocks
    ProductsController controller;

    @Test
    @DisplayName("createProduct создаст новый товар и перенаправит на страницу товара")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        //given
        var payload = new NewProductPayload("Новый товар", "Описание нового товара");
        var model = new ConcurrentModel(); //допустимо использовать локальный mock-объект

        /*
            Имитация поведения зависимости (productsRestClient).
            Для предотвращения хрупкости теста не используется свойства объекта NewProductPayload().
            Только конкретные значения.
            eq - нестрогое сравнение.
        */
        doReturn(new Product(1, "Новый товар", "Описание нового товара"))
                .when(this.productsRestClient)
                .createProduct(eq("Новый товар"), eq("Описание нового товара"));
//                .createProduct(notNull(), any());

        //when
        var result = this.controller.createProduct(payload, model);

        //then
        assertEquals("redirect:/catalogue/products/1", result);

        //check mock-object is called and no any requests within test-method
        verify(this.productsRestClient).createProduct(eq("Новый товар"), eq("Описание нового товара"));
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("createProduct вернет страницу с ошибками, если запрос невалидлен.")
    void createProduct_RequestIsInvalid_ReturnsProductsFormWithErrors() {
        //given
        var payload = new NewProductPayload("   ", null);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
                .when(this.productsRestClient)
                .createProduct("   ", null);

        //when
        var result = this.controller.createProduct(payload, model);

        //then
        assertEquals("catalogue/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));

        //check mock-object is called and no any requests within test-method
        verify(this.productsRestClient).createProduct("   ", null);
        verifyNoMoreInteractions(this.productsRestClient);
    }
}