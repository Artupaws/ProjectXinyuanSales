package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class CustomerLevel(

	@field:SerializedName("tempo_limit")
	val tempoLimit: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("loan_limit")
	val loanLimit: Long? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nice")
	val nice: Int? = null
)