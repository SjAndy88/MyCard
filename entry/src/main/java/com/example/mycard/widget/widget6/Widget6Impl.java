package com.example.mycard.widget.widget6;

import com.example.mycard.ResourceTable;
import com.example.mycard.widget.controller.FormController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.HashMap;
import java.util.Map;

public class Widget6Impl extends FormController {
    public static final int DIMENSION_2X4 = 3;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, Widget6Impl.class.getName());
    private static final int DEFAULT_DIMENSION_2X2 = 2;
    private static final Map<Integer, Integer> RESOURCE_ID_MAP = new HashMap<>();

    static {
        RESOURCE_ID_MAP.put(DEFAULT_DIMENSION_2X2, ResourceTable.Layout_form_list_pattern_widget6_2_2);
        RESOURCE_ID_MAP.put(DIMENSION_2X4, ResourceTable.Layout_form_list_pattern_widget6_2_4);
    }

    public Widget6Impl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData(long formId) {
        HiLog.info(TAG, "bind form data when create form");
        return new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
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