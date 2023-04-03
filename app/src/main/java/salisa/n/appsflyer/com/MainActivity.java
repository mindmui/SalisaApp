package salisa.n.appsflyer.com;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;




public class MainActivity extends AppCompatActivity {

    final Handler handler = new Handler();
//    private Button gotoAppleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Record Event
        Button trackEventButton = findViewById(R.id.trackEventButton);
        trackEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Track Events in real time */
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, 1);
                eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "Home Button");
                eventValue.put(AFInAppEventParameterName.CONTENT_ID, "Sample Event");
                eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Event has been recorded");
                builder.setMessage("Event name: " + AFInAppEventType.PURCHASE + "\nEvent values: " + eventValue);
                builder.create();
                builder.show();
            }
        });

         // View Seasonal Fruits Button
        Button goToAppleButton = findViewById(R.id.goToAppleButton);
        goToAppleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openAppleActivity(); }
        });
        }

        public void openAppleActivity() {
            Intent intent = new Intent(this, Apples.class);
            startActivity(intent);
        }



    }