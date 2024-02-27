package id.co.tonim.bniqris.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "riwayat_transfer_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_RIWAYAT_TRANSFER = "riwayat_transfer"
        private const val COLUMN_BANK_SUMBER = "bank_sumber"
        private const val COLUMN_ID_TRANSAKSI = "id_transaksi"
        private const val COLUMN_NAMA_MERCHANT = "nama_merchant"
        private const val COLUMN_NOMINAL_TRANSAKSI = "nominal_transaksi"
        private const val COLUMN_WAKTU_TRANSAKSI = "waktu_transaksi"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_RIWAYAT_TRANSFER (" +
                "$COLUMN_BANK_SUMBER TEXT, " +
                "$COLUMN_ID_TRANSAKSI TEXT, " +
                "$COLUMN_NAMA_MERCHANT TEXT, " +
                "$COLUMN_NOMINAL_TRANSAKSI TEXT, " +
                "$COLUMN_WAKTU_TRANSAKSI INTEGER)"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun saveRiwayatTransfer(riwayatTransfer: RiwayatTransfer) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BANK_SUMBER, riwayatTransfer.bankSumber)
            put(COLUMN_ID_TRANSAKSI, riwayatTransfer.idTransaksi)
            put(COLUMN_NAMA_MERCHANT, riwayatTransfer.namaMerchant)
            put(COLUMN_NOMINAL_TRANSAKSI, riwayatTransfer.nominalTransaksi)
            put(COLUMN_WAKTU_TRANSAKSI, riwayatTransfer.waktuTransaksi)
        }
        db.insert(TABLE_RIWAYAT_TRANSFER, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getRiwayatTransfers(): List<RiwayatTransfer> {
        val riwayatTransfers = mutableListOf<RiwayatTransfer>()
        val db = readableDatabase

        val cursor = db.query(TABLE_RIWAYAT_TRANSFER, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val bankSumber = cursor.getString(cursor.getColumnIndex(COLUMN_BANK_SUMBER))
            val idTransaksi = cursor.getString(cursor.getColumnIndex(COLUMN_ID_TRANSAKSI))
            val namaMerchant = cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_MERCHANT))
            val nominalTransaksi = cursor.getString(cursor.getColumnIndex(COLUMN_NOMINAL_TRANSAKSI))
            val waktuTransaksi = cursor.getLong(cursor.getColumnIndex(COLUMN_WAKTU_TRANSAKSI))

            val riwayatTransfer = RiwayatTransfer(bankSumber, idTransaksi, namaMerchant, nominalTransaksi, waktuTransaksi)
            riwayatTransfers.add(riwayatTransfer)
        }

        cursor.close()
        db.close()

        return riwayatTransfers
    }
}
