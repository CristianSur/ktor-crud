package com.example.services

import com.example.models.ProductDTO
import com.example.models.Products
import com.example.models.toProductDTO
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update


fun getAllProducts() = transaction {
    Products.selectAll().map { it.toProductDTO() }
}

fun getProductById(id: Int) = transaction {
    Products.selectAll()
        .where { Products.id eq id }
        .singleOrNull()
        ?.toProductDTO()
}

fun saveProduct(product: ProductDTO) = transaction {
    Products.insertAndGetId {
        it[name] = product.name
        it[price] = product.price.toBigDecimal()
    }.value
}

fun updateProduct(product: ProductDTO) = transaction {
    Products.update({ Products.id eq product.id }) {
        it[name] = product.name
        it[price] = product.price.toBigDecimal()
    }
}

fun deleteProduct(id: Int) = transaction {
    Products.deleteWhere { Products.id eq id }
}
