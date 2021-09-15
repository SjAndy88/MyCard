package com.example.mycard;

import ohos.aafwk.content.Intent;
import ohos.ace.ability.AceAbility;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

public class SecondAbility extends AceAbility {
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, SecondAbility.class.getName());

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        HiLog.info(TAG, "onStart");

        String strParams = intent.getStringParam("params");
        if (strParams != null) {
            HiLog.info(TAG, "strParams = " + strParams);
            ZSONObject zsonObject = ZSONObject.stringToZSON(strParams);
            String strParam1 = zsonObject.getString("param1");
            String strParam2 = zsonObject.getString("param2");
            HiLog.info(TAG, "strParam1 = " + strParam1 + ", strParam2 = " + strParam2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
