package com.unitedtractors.elomate.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("data")
	val data: UserItem? = null,  // Ubah dari List ke UserItem untuk objek tunggal

	@field:SerializedName("message")
	val message: String? = null
)

data class UserItem(

	@field:SerializedName("batch_data_batch_id")
	val batchDataBatchId: Int? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("nrp")
	val nrp: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("posisi")
	val posisi: String? = null,

	@field:SerializedName("domisili")
	val domisili: String? = null,

	@field:SerializedName("asal_universitas")
	val asalUniversitas: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null
)
