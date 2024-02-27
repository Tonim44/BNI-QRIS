package id.co.tonim.bniqris.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.co.tonim.bniqris.DetailTransaksiActivity
import id.co.tonim.bniqris.R
import id.co.tonim.bniqris.data.RiwayatTransfer
import id.co.tonim.bniqris.tools.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RiwayatTransferAdapter(private val riwayatTransfers: List<RiwayatTransfer>) : RecyclerView.Adapter<RiwayatTransferAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_transfer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayatTransfer = riwayatTransfers[position]

        holder.tvNamaMerchant.text = riwayatTransfer.namaMerchant
        holder.tvNominalTransaksi.text = "Rp.${riwayatTransfer.nominalTransaksi},-"
        holder.tvWaktuTransaksi.text = DateUtils.formatTime(riwayatTransfer.waktuTransaksi)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailTransaksiActivity::class.java)
            intent.putExtra(DetailTransaksiActivity.EXTRA_TRANSFER, riwayatTransfer)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return riwayatTransfers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaMerchant: TextView = itemView.findViewById(R.id.tvNamaMerchant)
        val tvNominalTransaksi: TextView = itemView.findViewById(R.id.tvNominalTransaksi)
        val tvWaktuTransaksi: TextView = itemView.findViewById(R.id.tvWaktuTransaksi)
    }
}
