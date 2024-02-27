package id.co.tonim.bniqris.tools

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy\n"+
                "HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}