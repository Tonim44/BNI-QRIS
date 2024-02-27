package id.co.tonim.bniqris

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.co.tonim.bniqris.data.RiwayatTransfer
import id.co.tonim.bniqris.databinding.ActivityDetaitransaksilBinding
import id.co.tonim.bniqris.tools.DateUtils
class DetailTransaksiActivity : AppCompatActivity() {

    private lateinit var riwayatTransfer : RiwayatTransfer
    private lateinit var binding: ActivityDetaitransaksilBinding

    companion object {
        const val EXTRA_TRANSFER = "extra_transfer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDetaitransaksilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        riwayatTransfer = intent.getParcelableExtra<RiwayatTransfer>(EXTRA_TRANSFER) as RiwayatTransfer

        val waktuTanggal = DateUtils.formatTime(riwayatTransfer.waktuTransaksi)
        val idTransaksi = riwayatTransfer.idTransaksi
        val nominal = riwayatTransfer.nominalTransaksi
        val namaBank = riwayatTransfer.bankSumber
        val namaMechart = riwayatTransfer.namaMerchant

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })

        binding.waktuTanggal.text = waktuTanggal
        binding.namaBank.text = namaBank
        binding.nominal.text = "Rp.${nominal},-"
        binding.idTransaksi.text = idTransaksi
        binding.namaMechart.text = namaMechart

    }
}