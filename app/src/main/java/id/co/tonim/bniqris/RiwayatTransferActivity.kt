package id.co.tonim.bniqris

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.tonim.bniqris.adapter.RiwayatTransferAdapter
import id.co.tonim.bniqris.data.MyDatabaseHelper
import id.co.tonim.bniqris.data.RiwayatTransfer
import id.co.tonim.bniqris.databinding.ActivityRiwayatBinding
import java.util.*

class RiwayatTransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private val riwayatTransfers = mutableListOf<RiwayatTransfer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(binding.root)

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })

        riwayatTransfers.addAll(getRiwayatTransfersFromDatabase())
        val adapter = RiwayatTransferAdapter(riwayatTransfers)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRiwayat.layoutManager = layoutManager
        binding.recyclerViewRiwayat.adapter = adapter

    }

    @SuppressLint("Range")
    private fun getRiwayatTransfersFromDatabase(): List<RiwayatTransfer> {
        val databaseHelper = MyDatabaseHelper(this)
        return databaseHelper.getRiwayatTransfers()
    }

}
