package com.example.rovapay

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.rovapay.databinding.ActivityMainBinding
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import java.util.ArrayList

class MainActivity : AppCompatActivity(), Hover.DownloadListener {
    lateinit var binding: ActivityMainBinding

        private lateinit var hoverLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Hover.initialize(applicationContext, this)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(pendingTransactionReceiver, IntentFilter("PENDING_TRANSACTION_CREATED"))

        hoverLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            onHoverResult(result.resultCode, result.data)
        }
        binding.btnClick.setOnClickListener {

            val hoverIntent = HoverParameters.Builder(this)
                .request("7661aa13")
//                .extra("step_variable_name", variable_value_as_string) // Only if your action has variables
                .buildIntent()

            hoverLauncher.launch(hoverIntent)

        }


    }
    private fun onHoverResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val sessionTextArr = data?.getStringArrayExtra("session_messages")
            val uuid = data?.getStringExtra("uuid")
            Log.e( "onHoverResult: ", "$sessionTextArr $uuid")
            // Handle the session messages and UUID here
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val error = data?.getStringExtra("error")
            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onError(p0: String?) {
        Log.e("onError: ", p0.toString())
    }

    override fun onSuccess(p0: ArrayList<HoverAction>?) {
        Log.e("onError: ", p0.toString())
    }

    private val pendingTransactionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }
}