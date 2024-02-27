package id.co.tonim.bniqris

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.tonim.bniqris.databinding.ActivityDoneBinding

class DoneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDoneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.ok.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
