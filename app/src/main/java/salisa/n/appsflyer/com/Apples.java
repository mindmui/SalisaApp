package salisa.n.appsflyer.com;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

public class Apples extends AppCompatActivity {

    private EditText fruit_Quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples);
        // Record View Event
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.CONTENT, getClass());
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.CONTENT_VIEW, eventValue);


        fruit_Quantity = findViewById(R.id.quantityInput);

        // Record Order Event
        Button trackOrderButton = findViewById(R.id.trackOrderButton);
        trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Track Events in real time */
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, fruit_Quantity.getText());
                eventValue.put(AFInAppEventParameterName.QUANTITY, fruit_Quantity.getText());
                eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "Seasonal Fruits");
                eventValue.put(AFInAppEventParameterName.CONTENT_ID, "Apples");
                eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
                AlertDialog.Builder builder = new AlertDialog.Builder(Apples.this);
                builder.setTitle("Event has been recorded");
                builder.setMessage("Event name: "+AFInAppEventType.PURCHASE+"\nEvent values: "+eventValue);
                builder.create();
                builder.show();


            }
        });



    }
}