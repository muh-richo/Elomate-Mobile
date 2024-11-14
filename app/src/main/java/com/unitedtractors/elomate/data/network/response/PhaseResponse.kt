package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PhaseResponse(

	@field:SerializedName("nama_phase")
	val namaPhase: String? = null,

	@field:SerializedName("phase_id")
	val phaseId: Int? = null
) : Parcelable
