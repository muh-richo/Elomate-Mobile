package com.unitedtractors.elomate.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("role_name")
	val roleName: String? = null,

	@field:SerializedName("batch_name")
	val batchName: String? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("posisi")
	val posisi: String? = null,

	@field:SerializedName("divisi")
	val divisi: String? = null,

	@field:SerializedName("domisili")
	val domisili: String? = null,

	@field:SerializedName("nrp")
	val nrp: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null
) : Parcelable
