package in.httpcsehithaldia.departmentofcsehit;
/*
* Created by,
 *          Nishant Raj
 *          On 01-10-2017
* */
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class SplashMain extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 59;
    private static Animation hyperspaceJump;
    private TextView tvCSEDEPT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        intiallize();

        hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.animation_zoom_out);
        tvCSEDEPT.startAnimation(hyperspaceJump);
        if(checkPermission())
            SPLASH_TIME_OUT = 2000;
        callHandlerToMainActivity();
    }

    private void intiallize() {
        tvCSEDEPT = (TextView) findViewById(R.id.textView);
    }

    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission Necessary :)");
                    alertBuilder.setMessage("Permission Required for Files Download and Upload!!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        //                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                }
//                checkPermission();
                return false;
            } else {
//                callHandlerToMainActivity();
                return true;
            }
        } else {
//            callHandlerToMainActivity();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission granted!!!!",Toast.LENGTH_SHORT).show();
                    Log.e("a","granted");
                } else {
                    Toast.makeText(this,"Permission Required for File Download and Upload!!!!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void callHandlerToMainActivity() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(checkPermission()) {
                    Intent i = new Intent(SplashMain.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
                else{
                    callHandlerToMainActivity();
                }
            }
        }, SPLASH_TIME_OUT);
    }


}