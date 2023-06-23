package salisa.n.appsflyer.com;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.share.ShareInviteHelper;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;




public class Apples extends AppCompatActivity {

    private EditText fruit_Quantity;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples);
        // Record View Event (AF)
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.CONTENT, getClass());
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.CONTENT_VIEW, eventValue);

        // Share button
        Button sharedInvitesBtn = (Button)findViewById(R.id.shareinvitesbtn);
        sharedInvitesBtn.setOnClickListener(v -> {
            copyShareInviteLink();
        });

        //
        fruit_Quantity = findViewById(R.id.quantityInput);

        // Record Order Event (AF)
        Button trackOrderButton = findViewById(R.id.trackOrderButton);
        trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Track Events in real time */
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, Integer.valueOf(fruit_Quantity.getText().toString()) * 5);
                eventValue.put(AFInAppEventParameterName.QUANTITY, fruit_Quantity.getText());
                eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "Seasonal Fruits");
                eventValue.put(AFInAppEventParameterName.CONTENT_ID, "Apples");
                eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);

                AlertDialog.Builder builder = new AlertDialog.Builder(Apples.this);
                builder.setTitle("Event has been recorded");
                builder.setMessage("Event name: " + AFInAppEventType.PURCHASE + "\nEvent values: " + eventValue);
                builder.create();
                builder.show();
            }

        });
    }

    protected void copyShareInviteLink() {
        LinkGenerator linkGenerator = ShareInviteHelper.generateInviteUrl(getApplicationContext());
        linkGenerator.addParameter("deep_link_value", "Apples");
        linkGenerator.addParameter("deep_link_sub1", String.valueOf(fruit_Quantity.getText()));
        linkGenerator.setCampaign("Seasonal Fruits Sharing");
        linkGenerator.setChannel("mobile_share");
        linkGenerator.setBrandDomain("go.mind.in.th"); // optional


        //Log.d(LOG_TAG, "Link params:" + linkGenerator.getUserParams().toString());

        CreateOneLinkHttpTask.ResponseListener listener = new CreateOneLinkHttpTask.ResponseListener() {
            @Override
            public void onResponse(String s) {
              //  Log.d(LOG_TAG, "Share invite link: " + s);

            //Copy the share invite link to clipboard and indicate it with a toast
            runOnUiThread(() -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Share invite link", s);
                clipboard.setPrimaryClip(clip);

            // Alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Apples.this);
                builder.setTitle("Link has been copied");
                builder.setMessage(s + " has been copied to your clipboard");
                builder.create();
                builder.show();

            });
            // Log share invite event
            HashMap<String,String> logInviteMap = new HashMap<String,String>();
            logInviteMap.put("referrerId", "Default");
            logInviteMap.put("campaign", "Seasonal Fruits Invites");
            ShareInviteHelper.logInvite(getApplicationContext(), "mobile_share", logInviteMap);
            }

            @Override
            public void onResponseError(String s) {
              //  Log.d(LOG_TAG, "onResponseError called");
            }

        };

          linkGenerator.generateLink(getApplicationContext(), listener);

    }


}







    /*
    // Track Firebase Order Event
    // android:onClick="sendPurchaseEvent" in Button
    public void sendPurchaseEvent(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        bundle.putString(FirebaseAnalytics.Param.VALUE, String.valueOf(fruit_Quantity.getText()) );
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Apples");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, bundle);

        AlertDialog.Builder builder = new AlertDialog.Builder(Apples.this);
        builder.setTitle("Firebase Event has been recorded");
        builder.setMessage("Event name: "+FirebaseAnalytics.Event.PURCHASE+"\nEvent values: "+bundle);
        builder.create();
        builder.show();

    }
     */



