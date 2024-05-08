package yp.recorder

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.media.MediaRecorder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private var recorder: MediaRecorder? = null

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startRecording()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnAction = findViewById<Button>(R.id.btnAction)
        btnAction.setOnClickListener {
            if (recorder!= null) {
                stopRecording()
                btnAction.setText("Start")
            }else{
                checkPermissionAndStartRecord()
                btnAction.setText("Stop")
            }
        }

        val btnHistory = findViewById<Button>(R.id.btnHistory);

        btnHistory.setOnClickListener{
            val intent = Intent(this, RecordHistoryActivity::class.java);
            startActivity(intent);
        }
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis())
            val recordFilePath = File(filesDir.absolutePath + "/records/")
            recordFilePath.mkdirs()
            val newFile = File(recordFilePath, "$dateTime.3gp")
            setOutputFile(newFile.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("MainActivity", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    private fun checkPermissionAndStartRecord() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                startRecording()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.RECORD_AUDIO
            ) -> {
                Toast.makeText(this, "Permission needed", Toast.LENGTH_SHORT).show()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (recorder!= null) {
            onStop()
        }
    }
}