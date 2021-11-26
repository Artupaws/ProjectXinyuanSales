package project.xinyuan.sales.model.product

import project.xinyuan.sales.model.product.master.DataProduct

data class ResponseGetListProduct(
    val data: List<DataProduct>,
    val message: String,
    val value: Int
)
