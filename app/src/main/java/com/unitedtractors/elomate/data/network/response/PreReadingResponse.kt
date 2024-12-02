package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PreReadingResponse(

	@field:SerializedName("course_id")
	val courseId: Int? = null,

	@field:SerializedName("batch_name")
	val batchName: String? = null,

	@field:SerializedName("nama_course")
	val namaCourse: String? = null,

	@field:SerializedName("title_materi")
	val titleMateri: String? = null,

	@field:SerializedName("materi_id")
	val materiId: Int? = null,

	@field:SerializedName("description_materi")
	val descriptionMateri: String? = null,

	@field:SerializedName("files")
	val files: List<FilesItem?>? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable

@Parcelize
data class FilesItem(

	@field:SerializedName("signed_url")
	val signedUrl: String? = null,

	@field:SerializedName("content_type")
	val contentType: String? = null,

	@field:SerializedName("bucket_name")
	val bucketName: String? = null,

	@field:SerializedName("file_name_id")
	val fileNameId: String? = null
) : Parcelable
