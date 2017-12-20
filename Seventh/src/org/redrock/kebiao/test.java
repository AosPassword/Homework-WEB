package org.redrock.kebiao;

import net.sf.json.JSONObject;
import org.redrock.kebiao.send.Send;
import org.redrock.kebiao.util.KebiaoUtil;

public class test {
    public static void main(String[] args) {
        String result = Send.sendPost("https://wx.idsbllp.cn/api/kebiao","stuNum=2017211573");
        String data = KebiaoUtil.jsonToics(JSONObject.fromObject(result));
    }
}
