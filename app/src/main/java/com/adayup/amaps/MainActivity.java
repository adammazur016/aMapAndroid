package com.adayup.aMaps;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import test.invoke.sdk.XiaomiWatchHelper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 1;

    private View.OnClickListener sendMessageFunction() {
        return view -> {
            Toast.makeText(view.getContext(), "Button clicked", Toast.LENGTH_SHORT).show();
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.sendButton);

        //for bluetooth connection
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN},
                    PERMISSION_REQUEST_CODE);
        }


        XiaomiWatchHelper instance = XiaomiWatchHelper.getInstance(this);
//        instance.setReceiver((id, message) -> {
//            runOnUiThread(() -> msg.setText(new String(message, StandardCharsets.UTF_8)));
//            lastMessage = new String(message, StandardCharsets.UTF_8);
//        });
        instance.setInitMessageListener((device) -> instance.sendMessageToWear("connected", obj -> {
            if (obj.isSuccess()) {
                Log.e(TAG, "Init message send");
            }
        }));

        instance.registerMessageReceiver();
        instance.sendUpdateMessageToWear();
        instance.sendNotify("Title", "This is test message",
                obj -> Log.e(TAG, "send notify ->" + obj.isSuccess()));

        //handle the file when clicked
        button.setOnClickListener(sendMessageFunction());
    }
}