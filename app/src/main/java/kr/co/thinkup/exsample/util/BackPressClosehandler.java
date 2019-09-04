package kr.co.thinkup.exsample.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * 03/12/2018 by yh.Choi
 */
public class BackPressClosehandler {

//    private Globals     globals;

    private long        backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;


    public BackPressClosehandler(Activity context) {
        this.activity = context;
//        this.globals = (Globals) activity.getApplicationContext();
    }

    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();

//            Intent intent = new Intent(activity, MainActivity.class);
//            intent.putExtra(EXTRA_FINISH, true);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            activity.startActivity(intent);

//            activity.moveTaskToBack(true);
//            activity.finish();
//            Process.killProcess(Process.myPid());
//            activity.finish();


//            globals.showOKCancel(activity,
//                    activity.getString(R.string.app_name),
//                    activity.getString(R.string.app_exits1),
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            // OK
//                            programExit();
//                        }
//                    },
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            // Cancel
//                            return;
//                        }
//                    });

            programExit();
        }
    }

    public void programExit() {
        activity.finishAffinity();
        System.runFinalization();
        System.exit(0);
    }

    public void showGuide() {
//        toast = Toast.makeText(activity, activity.getString(R.string.app_exits), Toast.LENGTH_SHORT);
        toast.show();
    }
}
