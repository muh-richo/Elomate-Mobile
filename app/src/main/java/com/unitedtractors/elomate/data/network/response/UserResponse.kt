package com.unitedtractors.elomate.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("jenjang_studi")
	val jenjangStudi: String? = null,

	@field:SerializedName("tahun_lulus")
	val tahunLulus: Int? = null,

	@field:SerializedName("nrp")
	val nrp: String? = null,

	@field:SerializedName("role_name")
	val roleName: String? = null,

	@field:SerializedName("batch_name")
	val batchName: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

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
) : Parcelable
