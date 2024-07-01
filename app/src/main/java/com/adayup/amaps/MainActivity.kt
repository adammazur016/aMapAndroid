package com.adayup.aMaps

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.xiaomi.xms.wearable.Status
import com.xiaomi.xms.wearable.node.Node
import test.invoke.sdk.XiaomiWatchHelper

class MainActivity : AppCompatActivity() {
    lateinit var bannerText: TextView
    lateinit var textInput: TextInputEditText
    lateinit var sendButton: Button
    var instance = XiaomiWatchHelper.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bannerText = findViewById(R.id.bannerText)
        textInput = findViewById(R.id.textInput)
        sendButton = findViewById(R.id.sendButton)

        instance.setInitMessageListener { device: Node? ->
            instance.sendMessageToWear(
                "connected"
            ) { obj: Status ->
                if (obj.isSuccess) {
                    Log.e("WATCH", "Init message send")
                }
            }
        }

        instance.registerMessageReceiver()
        instance.sendUpdateMessageToWear()
        instance.sendNotify(
            "aMaps", "Connected to aMaps :)"
        ) { obj: Status ->
            Log.e(
                "WATCH",
                "send notify ->" + obj.isSuccess
            )
        }

        sendButton.setOnClickListener {
            instance.sendMessageToWear(
                textInput.text.toString()
            ) { obj: Status ->
                if (obj.isSuccess) {
                    Log.e("WATCH", "Message send")
                }
            }
        }
    }
}