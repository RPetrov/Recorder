package yp.recorder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import java.io.File

class RecordHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_history)

        loadRecordsList()
    }

    private fun loadRecordsList(){
        val recordFilePath = File(filesDir.absolutePath + "/records/")

        recordFilePath.listFiles()?.let {

            val adapter = RecordsAdapter(it)

            val historyList = findViewById<RecyclerView>(R.id.historyList);

            historyList.layoutManager = LinearLayoutManager(this);

            historyList.adapter = adapter;

        } ?:{
            Log.d("RECORDS_LOG", "Нет файлов с записями в директории")
        }
    }
}