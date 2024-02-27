package id.co.tonim.bniqris

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.co.tonim.bniqris.data.MyDatabaseHelper
import id.co.tonim.bniqris.data.RiwayatTransfer
import id.co.tonim.bniqris.data.SharedPreferencesManager
import id.co.tonim.bniqris.databinding.ActivityDetailtransferBinding
import id.co.tonim.bniqris.tools.LoadingDialog

class DetailTransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailtransferBinding
    private lateinit var loadingDialog: LoadingDialog
    private var saldo: Int? = null
    private var bankSumber: String? = null
    private var idTransaksi: String? = null
    private var namaMerchant: String? = null
    private var nominalTransaksi: String? = null

    companion object {
        lateinit var sharedPreferencesManager: SharedPreferencesManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailtransferBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager

        saldo = sharedPreferencesManager.getSaldo(this)

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })

        bankSumber = intent.getStringExtra("bankSumber")
        idTransaksi = intent.getStringExtra("idTransaksi")
        namaMerchant = intent.getStringExtra("namaMerchant")
        nominalTransaksi = intent.getStringExtra("nominalTransaksi")

        binding.tvBankSumber.text = bankSumber
        binding.tvIdTransaksi.text = idTransaksi
        binding.tvNamaMerchant.text = namaMerchant
        binding.tvNominalTransaksi.text = "Rp.${nominalTransaksi},-"

        binding.transfer.setOnClickListener {
            transfer()
        }
    }

    private fun transfer() {
        val nominalTransaksi = intent.getStringExtra("nominalTransaksi") ?: "0"
        val saldoSekarang = saldo!! - nominalTransaksi.toInt()

        if (saldoSekarang >= 0) {
            val waktuTransaksi = System.currentTimeMillis()

            val riwayatTransfer = RiwayatTransfer(
                bankSumber = bankSumber!!,
                idTransaksi = idTransaksi!!,
                namaMerchant = namaMerchant!!,
                nominalTransaksi = nominalTransaksi!!,
                waktuTransaksi
            )

            val myDatabaseHelper = MyDatabaseHelper(this)
            myDatabaseHelper.saveRiwayatTransfer(riwayatTransfer)

            DetailTransferActivity.sharedPreferencesManager.saveSaldo(this, saldoSekarang)

            loadingDialog = LoadingDialog(this)
            loadingDialog.startLoading()

            Handler().postDelayed({
                loadingDialog.isDismiss()
                val intent = Intent(this@DetailTransferActivity, DoneActivity::class.java)
                startActivity(intent)
                finish()
            }, 5000)
        } else {
            Toast.makeText(
                this,
                "Saldo tidak cukup untuk melakukan transfer",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}