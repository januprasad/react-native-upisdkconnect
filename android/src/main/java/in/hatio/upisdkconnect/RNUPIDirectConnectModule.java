package in.hatio.upisdkconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import in.hatio.upi.shared.SDK;

public class RNUPIDirectConnectModule extends ReactContextBaseJavaModule {

    private static final String TAG = RNUPIDirectConnectModule.class.getSimpleName();
    private static final String POSITIVE_BUTTON_KEY = "POSITIVE_BUTTON";
    private static final String NEGATIVE_BUTTON_KEY = "NEGATIVE_BUTTON";
    private static final String NEUTRAL_BUTTON_KEY = "NEUTRAL_BUTTON";

    public RNUPIDirectConnectModule(ReactApplicationContext reactContext) {
      super(reactContext);
    }

    @Override
    public String getName() {
      return "UPIDirectConnect";
    }

    @Override
    public Map<String, Object> getConstants() {
      final Map<String, Object> constants = new HashMap<>();
      constants.put(POSITIVE_BUTTON_KEY, POSITIVE_BUTTON_KEY);
      constants.put(NEGATIVE_BUTTON_KEY, NEGATIVE_BUTTON_KEY);
      constants.put(NEUTRAL_BUTTON_KEY, NEUTRAL_BUTTON_KEY);
      return constants;
    }


    @ReactMethod void initPayment(final String params, final Callback callbackContext){
        String paramsJson = "{\n" +
                "\t\"merchant_name\": \"ShoppingCart\",\n" +
                "\t\"order_id\": \"" + getRandomHexString(12) + "\",\n" +
                "\t\"amount\": \"1000\"\n" +
                "}";

        SDK sdk = new SDK.Builder(getReactApplicationContext()).build();
        sdk.initPayment(paramsJson, new SDK.SDKCallBack(){

            @Override
            public void onOtpReceive(String otp) {

            }

            @Override
            public void onPaymentFailed(String error) {

            }

            @Override
            public void onPaymentSuccess(String message) {
                callbackContext.invoke(message);
            }
        });

    }
    @ReactMethod
    public void alert(final String title, final String message, final ReadableArray buttonConfig, final Callback buttonsCallback) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());

      if(title != null) builder.setTitle(title);
      if(message != null) builder.setMessage(message);

      if(buttonConfig != null && buttonConfig.size() > 0) {
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialog) {
            if(buttonsCallback != null) buttonsCallback.invoke(NEGATIVE_BUTTON_KEY);
          }
        });

        for (int i = 0; i<buttonConfig.size(); i++) {
          ReadableMap button = buttonConfig.getMap(i);
          if(button != null && button.hasKey("type") && button.hasKey("text")) {
            switch(button.getString("type")) {
              case NEGATIVE_BUTTON_KEY:
                builder.setNegativeButton(button.getString("text"), new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                     if(buttonsCallback != null) buttonsCallback.invoke(NEGATIVE_BUTTON_KEY);
                  }
                });
                break;
              case NEUTRAL_BUTTON_KEY:
                builder.setNeutralButton(button.getString("text"), new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                     if(buttonsCallback != null) buttonsCallback.invoke(NEUTRAL_BUTTON_KEY);
                  }
                });
                break;
              default:
                builder.setPositiveButton(button.getString("text"), new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                     if(buttonsCallback != null) buttonsCallback.invoke(POSITIVE_BUTTON_KEY);
                  }
                });
                break;
            }
          }
        }
      }

      AlertDialog ad = builder.create();
      ad.show();
    }
    private String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        sb.append("TXN");
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars).toUpperCase();
    }

}
