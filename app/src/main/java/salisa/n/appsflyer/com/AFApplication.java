

package salisa.n.appsflyer.com;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.deeplink.DeepLinkListener;
import com.appsflyer.deeplink.DeepLinkResult;
import com.google.gson.Gson;
import java.util.Map;


public class AFApplication extends Application {
    private static final String AF_DEV_KEY = "Y8futxa4cnQu8yLuUgu2Dd";
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    public static final String DL_ATTRS = "dl_attrs";
    Map<String, Object> conversionData = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();

        // Make sure you remove the following line when building to production
        appsflyer.setDebugLog(true);

        appsflyer.subscribeForDeepLink(new DeepLinkListener() {
            @Override
            public void onDeepLinking(@NonNull DeepLinkResult deepLinkResult) {
                // An example for getting deep_link_value
                String deeplinkPage = "";
                DeepLink deepLinkObj = deepLinkResult.getDeepLink();
                try {
                    deeplinkPage = deepLinkObj.getDeepLinkValue();
                    if (deeplinkPage == null) {
                        Log.d(LOG_TAG, "Deeplink value returned null");
                        return;
                    }
                    Log.d(LOG_TAG, "The DeepLink will route to: " + deeplinkPage);
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Custom param fruit_name was not found in DeepLink data");
                    return;
                }
                goToFruit(deeplinkPage, deepLinkObj);
            }
        });


        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                for (String attrName : attributionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + attributionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().setOneLinkCustomDomain("go.mind.in.th"); //branded link
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this);
        AppsFlyerLib.getInstance().start(this);
    }


        private void goToFruit(String fruitName, DeepLink dlData) {
            String fruitClassName = (fruitName);
            try {
                Class fruitClass = Class.forName(this.getPackageName().concat(".").concat(fruitClassName));
                Log.d(LOG_TAG, "Looking for class " + fruitClass);
                Intent intent = new Intent(getApplicationContext(), fruitClass);
                if (dlData != null) {
                    // TODO - make DeepLink Parcelable
                    String objToStr = new Gson().toJson(dlData);
                    intent.putExtra(DL_ATTRS, objToStr);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                Log.d(LOG_TAG, "Deep linking failed looking for " + fruitName);
                e.printStackTrace();
            }
        }


    }

