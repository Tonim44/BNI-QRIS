package id.co.tonim.bniqris.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RiwayatTransfer(
    val bankSumber: String,
    val idTransaksi: String,
    val namaMerchant: String,
    val nominalTransaksi: String,
    val waktuTransaksi: Long
) : Parcelable