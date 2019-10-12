package kr.co.thinkup.exsample.db.wifi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 2019-10-09 by yh.Choi
 */
public class CandyInfo extends RealmObject {

    @PrimaryKey
    private long        id;                 // id primary key

    // Led Info
    private String      alias;              // Nick Name
    private int         power;              // 전원 on/off
    private int         use;                // 사용여부
    private int         brightness;         // 디머 밝기
    private int         sleep_time;         // turn off 남은시간
    private String      sleep_setup_time;   // turn off로 설정한 시간
    private int         sleep_status;       // turn off 상태 정보
    private int         rise_time;          // turn on 남은시간
    private String      rise_setup_time;    // turn on 설정한 시간
    private int         rise_status;        // turn on 상태 정보

    // WIFI Info
    // 캔디를 AP로 사용을 할 경우에는 AP 에만 값을 넣어주고
    // STA 모드로 사용할 경우에는 ap, sta 두개다 모두 넣어준다.

    private int         device_mode;        // AP, STA, DEMO 모드  인지 구분한다.

    private String      ap_ssid;            // ap ssid
    private String      ap_bssid;           // ap bssid
    private String      ap_password;        // ap password
    private int         ap_capabilities;    // ap capabilities
    private String      ap_ip;              // ap ip 주소

    private String      sta_ssid;           // sta ssid
    private String      sta_bssid;          // sta bssid
    private String      sta_password;       // sta password
    private int         sta_capabilities;   // sta capabilities
    private String      sta_ip;             // sta ip 주소

    public CandyInfo() {
        id = 0;
        power = 101;
        use = 201;
        brightness = 50;
        sleep_time = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(int sleep_time) {
        this.sleep_time = sleep_time;
    }

    public String getSleep_setup_time() {
        return sleep_setup_time;
    }

    public void setSleep_setup_time(String sleep_setup_time) {
        this.sleep_setup_time = sleep_setup_time;
    }

    public int getSleep_status() {
        return sleep_status;
    }

    public void setSleep_status(int sleep_status) {
        this.sleep_status = sleep_status;
    }

    public int getRise_time() {
        return rise_time;
    }

    public void setRise_time(int rise_time) {
        this.rise_time = rise_time;
    }

    public String getRise_setup_time() {
        return rise_setup_time;
    }

    public void setRise_setup_time(String rise_setup_time) {
        this.rise_setup_time = rise_setup_time;
    }

    public int getRise_status() {
        return rise_status;
    }

    public void setRise_status(int rise_status) {
        this.rise_status = rise_status;
    }

    public int getDevice_mode() {
        return device_mode;
    }

    public void setDevice_mode(int device_mode) {
        this.device_mode = device_mode;
    }

    public String getAp_ssid() {
        return ap_ssid;
    }

    public void setAp_ssid(String ap_ssid) {
        this.ap_ssid = ap_ssid;
    }

    public String getAp_bssid() {
        return ap_bssid;
    }

    public void setAp_bssid(String ap_bssid) {
        this.ap_bssid = ap_bssid;
    }

    public String getAp_password() {
        return ap_password;
    }

    public void setAp_password(String ap_password) {
        this.ap_password = ap_password;
    }

    public int getAp_capabilities() {
        return ap_capabilities;
    }

    public void setAp_capabilities(int ap_capabilities) {
        this.ap_capabilities = ap_capabilities;
    }

    public String getAp_ip() {
        return ap_ip;
    }

    public void setAp_ip(String ap_ip) {
        this.ap_ip = ap_ip;
    }

    public String getSta_ssid() {
        return sta_ssid;
    }

    public void setSta_ssid(String sta_ssid) {
        this.sta_ssid = sta_ssid;
    }

    public String getSta_bssid() {
        return sta_bssid;
    }

    public void setSta_bssid(String sta_bssid) {
        this.sta_bssid = sta_bssid;
    }

    public String getSta_password() {
        return sta_password;
    }

    public void setSta_password(String sta_password) {
        this.sta_password = sta_password;
    }

    public int getSta_capabilities() {
        return sta_capabilities;
    }

    public void setSta_capabilities(int sta_capabilities) {
        this.sta_capabilities = sta_capabilities;
    }

    public String getSta_ip() {
        return sta_ip;
    }

    public void setSta_ip(String sta_ip) {
        this.sta_ip = sta_ip;
    }
}
