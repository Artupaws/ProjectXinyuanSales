package project.xinyuan.sales.model

data class ResponseGetListProduct(
    val data: List<DataProduct>,
    val message: String,
    val value: Int
)
