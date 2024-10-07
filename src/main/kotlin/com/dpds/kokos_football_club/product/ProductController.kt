package com.dpds.kokos_football_club.product

import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(
    private val productService: ProductService
){

    @Operation(
        summary = "Получить список товаров",
        tags = ["Товары"]
    )
    @GetMapping("/")
    fun getProductList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: ProductOrdering
    ): PageImpl<ProductResponse> {
        val products = productService.getProductList(page, pageSize, ordering, search)
        val productResponse: MutableList<ProductResponse> = mutableListOf()
        products.forEach {
            productResponse.add(ProductResponse(it))
        }
        return PageImpl(productResponse)
    }

    @Operation(
        summary = "Получить товар",
        tags = ["Товары"]
    )
    @GetMapping("/{id}/")
    fun getProduct(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long
    ): ProductResponse {
        val product = productService.getProduct(id)
        return ProductResponse(product)
    }

    @Operation(
        summary = "Создать товар",
        tags = ["Товары"]
    )
    @PostMapping("/")
    fun createProduct(@RequestBody productRequest: ProductRequest): ProductResponse {
        val product = productService.createProduct(productRequest)
        return ProductResponse(product)
    }

    @Operation(
        summary = "Обновить товар",
        tags = ["Товары"]
    )
    @PutMapping("/{id}/")
    fun updateProduct(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody productRequest: ProductRequest
    ): ProductResponse {
        val product = productService.updateProduct(id, productRequest)
        return ProductResponse(product)
    }

    @Operation(
        summary = "Удалить товар",
        tags = ["Товары"]
    )
    @DeleteMapping("/{id}/")
    fun deleteProduct(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long
    ): DetailsResponse {
        productService.deleteProduct(id)
        return DetailsResponse("Товар успешно удален")
    }
}