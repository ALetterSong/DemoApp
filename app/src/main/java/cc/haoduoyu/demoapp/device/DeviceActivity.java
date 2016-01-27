package cc.haoduoyu.demoapp.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/1/27.
 */
public class DeviceActivity extends AppCompatActivity {


    @Bind(R.id.string2Date_tv)
    TextView string2DateTv;
    @Bind(R.id.date2String_tv)
    TextView date2StringTv;
    @Bind(R.id.getYearMonthDay_tv)
    TextView getYearMonthDayTv;
    @Bind(R.id.getTimestampString_tv)
    TextView getTimestampStringTv;
    @Bind(R.id.date2yyyyMMdd_tv)
    TextView date2yyyyMMddTv;
    @Bind(R.id.date2MMddWeek_tv)
    TextView date2MMddWeekTv;
    @Bind(R.id.date2yyyyMMddWeek_tv)
    TextView date2yyyyMMddWeekTv;
    @Bind(R.id.time24To12_tv)
    TextView time24To12Tv;
    @Bind(R.id.device_id_tv)
    TextView deviceIdTv;
    @Bind(R.id.version_code_tv)
    TextView versionCodeTv;
    @Bind(R.id.version_name_tv)
    TextView versionNameTv;
    @Bind(R.id.phone_brand_tv)
    TextView phoneBrandTv;
    @Bind(R.id.phone_model_tv)
    TextView phoneModelTv;
    @Bind(R.id.phone_api_level_tv)
    TextView phoneApiLevelTv;
    @Bind(R.id.phone_api_version_tv)
    TextView phoneApiVersionTv;
    @Bind(R.id.app_process_id_tv)
    TextView appProcessIdTv;
    @Bind(R.id.app_name_tv)
    TextView appNameTv;
    @Bind(R.id.device_width_height_tv)
    TextView deviceWidthHeightTv;
    @Bind(R.id.net_tv)
    TextView netTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);

        long oldTime = System.currentTimeMillis() - 1200000;
        Date date = new Date(oldTime);
        string2DateTv.setText(DateUtils.string2Date(date.toString(), "yyyy-MM-dd").toString());
        date2StringTv.setText(DateUtils.date2String(oldTime, "yyyy-MM-dd HH:mm:ss"));
        getTimestampStringTv.setText(DateUtils.getTimestampString(date));
        getYearMonthDayTv.setText(new Date(DateUtils.getYearMonthDay(oldTime)).toString());
        date2MMddWeekTv.setText(DateUtils.date2MMddWeek(date));
        date2yyyyMMddTv.setText(DateUtils.date2yyyyMMdd(date));
        date2yyyyMMddWeekTv.setText(DateUtils.date2yyyyMMddWeek(date));
        time24To12Tv.setText(DateUtils.time24To12("10:08"));
        deviceIdTv.setText(DeviceUtils.getDeviceId(this));
        versionCodeTv.setText(DeviceUtils.getVersionCode(this));
        versionNameTv.setText(DeviceUtils.getVersionName(this));
        phoneApiLevelTv.setText(DeviceUtils.getBuildLevel() + "");
        phoneApiVersionTv.setText(DeviceUtils.getBuildVersion());
        phoneBrandTv.setText(DeviceUtils.getPhoneBrand());
        phoneModelTv.setText(DeviceUtils.getPhoneModel());
        appNameTv.setText(DeviceUtils.getAppProcessName(this, DeviceUtils.getAppProcessId()));
        appProcessIdTv.setText(DeviceUtils.getAppProcessId() + "");
        deviceWidthHeightTv.setText("width:" + DeviceUtils.deviceWidth(this)
                + " height:" + DeviceUtils.deviceHeight(this));
        netTv.setText(String.valueOf(DeviceUtils.isNetworkConnected(this)));

    }
}
