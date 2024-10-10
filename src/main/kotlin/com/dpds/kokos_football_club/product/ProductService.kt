package com.dpds.kokos_football_club.product;

import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.image.ImageService
import com.dpds.kokos_football_club.image.UploadImageRequest
import com.dpds.kokos_football_club.product_cart.ProductCart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(
    private val productRepository: ProductRepository,
    private val productCart: ProductCart,
    private val imageService: ImageService
){

    fun getProductList(
        page: Int,
        pageSize: Int,
        ordering: ProductOrdering,
        search: String
    ): PageImpl<Product> {
        val sort = when(ordering) {
            ProductOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            ProductOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            ProductOrdering.TITLE_ASC -> Sort.by(Sort.Direction.ASC, "title")
            ProductOrdering.TITLE_DESC -> Sort.by(Sort.Direction.DESC, "title")
            ProductOrdering.PRICE_ASC -> Sort.by(Sort.Direction.ASC, "price")
            ProductOrdering.PRICE_DESC -> Sort.by(Sort.Direction.DESC, "price")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        val products = productRepository.findAll(pageRequest).filter {
            it.title.lowercase().contains(search.lowercase())
                    || it.price.toString().lowercase().contains(search.lowercase())
        }.toMutableList()
        return PageImpl(products.drop(pageSize * page).take(pageSize), pageRequest, products.size.toLong())
    }

    fun getProduct(id: Long): Product {
        return productRepository.findByIdOrNull(id) ?: throw NotFoundException("Товар не найден")
    }

    fun createProduct(productRequest: ProductRequest, login: String): Product {
        return productRepository.save(
            Product(
                title = productRequest.title,
                img = productRequest.img?.let { imageService.saveFile(login, it) },
                price = productRequest.price,
                description = productRequest.description,
                purchase = null
            )
        )
    }

    fun updateProduct(id: Long, productRequest: ProductRequest): Product {
        val product = getProduct(id)
        product.title = productRequest.title
        product.description = productRequest.description
        product.price = productRequest.price
        return productRepository.save(product)
    }

    fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }

    fun addProductToCart(id: Long) {
        val product = getProduct(id)
        productCart.products.add(product)
    }

    fun removeProductFromCart(id: Long) {
        val product = getProduct(id)
        productCart.products.remove(product)
    }

    fun setImage(login: String, id: Long, avatarRequest: UploadImageRequest) {
        val product = getProduct(id)
        product.img = imageService.saveFile(login, avatarRequest)
        productRepository.save(product)
    }
}