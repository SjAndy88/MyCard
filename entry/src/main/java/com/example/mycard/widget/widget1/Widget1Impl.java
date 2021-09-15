package com.example.mycard.widget.widget1;

import com.example.mycard.MainAbility;
import com.example.mycard.widget.controller.FormController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.FormBindingData;
import ohos.aafwk.ability.FormException;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;


public class Widget1Impl extends FormController {
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, Widget1Impl.class.getName());
    private static final int DIMENSION_1X2 = 1;
    private static final int DIMENSION_2X4 = 3;

    public Widget1Impl(Context context, String formName, Integer dimension) {
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

        ZSONObject zsonObject = new ZSONObject();
        zsonObject.put("title", "title18");
        FormBindingData bindingData = new FormBindingData(zsonObject);
        try {
            ((MainAbility) context).updateForm(formId, bindingData);
        } catch (FormException ex) {
            HiLog.error(TAG, ex.toString());
        }
    }

    @Override
    public void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "handle card click event.");
    }

    @Override
    public Class<? extends AbilitySlice> getRoutePageSlice(Intent intent) {
        HiLog.info(TAG, "get the default page to route when you click card.");
        return null;
    }
}
