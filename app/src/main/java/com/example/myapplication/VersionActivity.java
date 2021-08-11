package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VersionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        TextView tv = findViewById(R.id.tvContent);
        tv.setText("当前运营商：" + getNetExtraInfo(this));
    }

    /**
     * 获取运营商信息
     *
     * @param mContext
     * @return
     */
    public static String getNetExtraInfo(Context mContext) {

        String netExtraInfo = "其他";
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            netExtraInfo = mTm.getSimOperator();
            if (null != netExtraInfo) {
                if (netExtraInfo.equals("46000")
                        || netExtraInfo.equals("46002")
                        || netExtraInfo.equals("46007")
                        || netExtraInfo.equals("46008")) {
                    // 中国移动
                    netExtraInfo = "中国移动";
                } else if (netExtraInfo.equals("46001")
                        || netExtraInfo.equals("46006")
                        || netExtraInfo.equals("46009")
                        || netExtraInfo.equals("46010")) {

                    // 中国联通
                    netExtraInfo = "中国联通";
                } else if (netExtraInfo.equals("46003")
                        || netExtraInfo.equals("46005")
                        || netExtraInfo.equals("46011")) {

                    // 中国电信
                    netExtraInfo = "中国电信";
                } else if (netExtraInfo.equals("46020")) {
                    // 中国铁通
                    netExtraInfo = "中国铁通";
                }
            }
        }
        return netExtraInfo;
    }
}
