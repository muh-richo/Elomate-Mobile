package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EducationResponse(

	@field:SerializedName("jenjang_studi")
	val jenjangStudi: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("tahun_lulus")
	val tahunLulus: Int? = null,

	@field:SerializedName("universitas")
	val universitas: String? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("id_education")
	val idEducation: Int? = null
) : Parcelable
