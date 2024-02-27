package id.co.tonim.bniqris

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.tonim.bniqris.adapter.RiwayatTransferMenuAdapter
import id.co.tonim.bniqris.data.MyDatabaseHelper
import id.co.tonim.bniqris.data.RiwayatTransfer
import id.co.tonim.bniqris.data.SharedPreferencesManager
import id.co.tonim.bniqris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var saldo: Int? = null
    private val riwayatTransfers = mutableListOf<RiwayatTransfer>()

    companion object {
        lateinit var sharedPreferencesManager: SharedPreferencesManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferencesManager = SharedPreferencesManager

        saldo = sharedPreferencesManager.getSaldo(this)
        binding.user.text = "Toni Muhayat"
        binding.saldo.text = "Saldo Anda : Rp.${saldo.toString()},-"

        setupListeners()
        loadRiwayatTransfers()

    }

    private fun setupListeners() {

        binding.scan.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanCodeActivity::class.java)
            startActivity(intent)
        }

        binding.riwayat.setOnClickListener {
            val intent = Intent(this@MainActivity, RiwayatTransferActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadRiwayatTransfers() {
        riwayatTransfers.addAll(getRiwayatTransfersFromDatabase())
        val adapter = RiwayatTransferMenuAdapter(riwayatTransfers)
        binding.recyclerViewRiwayat.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRiwayat.setHasFixedSize(true)
        binding.recyclerViewRiwayat.adapter = adapter
    }

    @SuppressLint("Range")
    private fun getRiwayatTransfersFromDatabase(): List<RiwayatTransfer> {
        val databaseHelper = MyDatabaseHelper(this)
        return databaseHelper.getRiwayatTransfers()
    }

    override fun onPause() {
        super.onPause()
        sharedPreferencesManager.saveSaldo(this, saldo)
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Keluar")
        alertDialogBuilder
            .setMessage("Apakah Anda Yakin Untuk Menutup Aplikasi ?")
            .setCancelable(false)
            .setPositiveButton(
                "Iya"
            ) { dialog, id ->
                moveTaskToBack(true)
                Process.killProcess(Process.myPid())
                System.exit(1)
            }
            .setNegativeButton(
                "Tidak"
            ) { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
