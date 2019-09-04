package kr.co.thinkup.exsample.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import kr.co.thinkup.exsample.R;


/**
 * 04/12/2018 by yh.Choi
 */
public class WaitingDialog {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final Object waitingDialogLock = new Object();
    private static Dialog waitingDialog;
    private static int  ndialogNumber;


    private static Dialog getWaitingDialog(Context context) {
        synchronized (waitingDialogLock) {
            if (waitingDialog != null) {
                return waitingDialog;
            }

            //waitingDialog = new Dialog(context, R.style.CustomProgressDialog);
            waitingDialog = new Dialog(context);
            waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return waitingDialog;
        }
    }

    public static void showWaitingDialog(Context context) {
        showWaitingDialog(context, "");
    }

    public static void showWaitingDialog(Context context, int dialogNumber) {
        showWaitingDialog(context, "", dialogNumber);
    }

    public static void showWaitingDialog(Context context, String message) {
        showWaitingDialog(context, false, null, message, 0);
    }

    public static void showWaitingDialog(Context context, String message, int dialogNumber) {
        showWaitingDialog(context, false, null, message, dialogNumber);
    }

    public static void showWaitingDialog(final Context context, final boolean cancelable) {
        showWaitingDialog(context, cancelable, null, "", 0);
    }

    public static void showWaitingDialog(final Context context, final boolean cancelable, final DialogInterface.OnCancelListener listener, final String message, final int dialogNumber) {
        cancelWaitingDialog();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                waitingDialog = getWaitingDialog(context);
                // here we set layout of progress dialog
                waitingDialog.setContentView(R.layout.kakao_layout_waiting_dialog);
                TextView msg = waitingDialog.findViewById(R.id.kakao_layout_waiting_dialog_msg);
                if(message.length() > 0) {
                    msg.setText(message);
                }
                waitingDialog.setCancelable(cancelable);
                if (listener != null) {
                    waitingDialog.setOnCancelListener(listener);
                }

                ndialogNumber = dialogNumber;
                waitingDialog.show();
            }
        });
    }

    public static void cancelWaitingDialog() {
        cancelWaitingDialog(0);
    }

    public static void cancelWaitingDialog(final int dialogNumber) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (waitingDialog != null && ndialogNumber == dialogNumber) {
                        synchronized (waitingDialogLock) {
                            waitingDialog.cancel();
                            waitingDialog = null;
                        }
                    }
                } catch (Exception e) {
                    Log.e("WaitingDialog", "run: ", e);
                }
            }
        });
    }
}
/*
        // 이렇게 사용을 하면 뒤로가기시 없어진다.
        WaitingDialog.showWaitingDialog(SetupWifiInfoActivity.this,
                true,
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        WaitingDialog.cancelWaitingDialog(WIFI_AP_CHANGE);
                    }
                },
                "저장중",
                WIFI_AP_CHANGE);
* */