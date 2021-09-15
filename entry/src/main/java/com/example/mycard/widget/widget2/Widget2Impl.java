package com.example.mycard.widget.widget2;

import com.example.mycard.widget.controller.FormController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.FormBindingData;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;


public class Widget2Impl extends FormController {
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, Widget2Impl.class.getName());
    private static final int DIMENSION_1X2 = 1;
    private static final int DIMENSION_2X4 = 3;

    public Widget2Impl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData() {
        HiLog.info(TAG, "bind form data");
        ZSONObject zsonObject = new ZSONObject();
        ProviderFormInfo providerFormInfo = new ProviderFormInfo();
        if (dimension == DIMENSION_1X2) {
            zsonObject.put("mini", true);
        }
        if (dimension == DIMENSION_2X4) {
            zsonObject.put("dim2X4", true);
        }
        providerFormInfo.setJsBindingData(new FormBindingData(zsonObject));
        return providerFormInfo;
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
    }

    @Override
    public void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "handle card click event.");

        if (message != null) {
            HiLog.info(TAG, "message = " + message);
            ZSONObject zsonObject = ZSONObject.stringToZSON(message);
            String strParam1 = zsonObject.getString("p1");
            String strParam2 = zsonObject.getString("p2");
            HiLog.info(TAG, "p1 = " + strParam1 + ", p2 = " + strParam2);
        }
    }

    @Override
    public Class<? extends AbilitySlice> getRoutePageSlice(Intent intent) {
        HiLog.info(TAG, "get the default page to route when you click card.");
        return null;
    }
}
