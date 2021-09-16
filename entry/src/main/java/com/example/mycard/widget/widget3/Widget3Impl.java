package com.example.mycard.widget.widget3;

import com.example.mycard.MainAbility;
import com.example.mycard.ResourceTable;
import com.example.mycard.widget.controller.FormController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.FormException;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.ComponentProvider;
import ohos.app.Context;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentConstant;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.intentagent.IntentAgentInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Widget3Impl extends FormController {
    public static final int DIMENSION_2X4 = 3;
    public static final int DIMENSION_4X4 = 4;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, Widget3Impl.class.getName());
    private static final int DEFAULT_DIMENSION_2X2 = 2;
    private static final Map<Integer, Integer> RESOURCE_ID_MAP = new HashMap<>();

    static {
        RESOURCE_ID_MAP.put(DEFAULT_DIMENSION_2X2, ResourceTable.Layout_form_grid_pattern_widget3_2_2);
        RESOURCE_ID_MAP.put(DIMENSION_2X4, ResourceTable.Layout_form_grid_pattern_widget3_2_4);
        RESOURCE_ID_MAP.put(DIMENSION_4X4, ResourceTable.Layout_form_grid_pattern_widget3_4_4);
    }

    public Widget3Impl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData(long formId) {
        HiLog.info(TAG, this + " bind form data when create form");
        ProviderFormInfo providerFormInfo = new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
        ComponentProvider componentProvider = new ComponentProvider();
        if (dimension == DEFAULT_DIMENSION_2X2) {
            componentProvider.setText(ResourceTable.Id_title, "title3_2*2");
        } else if (dimension == DIMENSION_2X4) {
            componentProvider.setText(ResourceTable.Id_title, "title3_2*4");
            componentProvider.setIntentAgent(ResourceTable.Id_title, getStartAbilityIntentAgent());
        } else if (dimension == DIMENSION_4X4) {
            componentProvider.setText(ResourceTable.Id_title, "title3_4*4");
        }
        providerFormInfo.mergeActions(componentProvider);
        return providerFormInfo;
    }

    private IntentAgent getStartAbilityIntentAgent() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("com.example.mycard")
                .withAbilityName("com.example.mycard.SecondAbility")
                .build();
        intent.setOperation(operation);
        List<Intent> intentList = new ArrayList<>();
        intentList.add(intent);
        List<IntentAgentConstant.Flags> flagsList = new ArrayList<>();
        flagsList.add(IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG);
        IntentAgentInfo agentInfo =
                new IntentAgentInfo(200, IntentAgentConstant.OperationType.START_ABILITY, flagsList, intentList, null);
        return IntentAgentHelper.getIntentAgent(context, agentInfo);
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
        ComponentProvider componentProvider = new ComponentProvider(RESOURCE_ID_MAP.get(dimension), context);
        componentProvider.setText(ResourceTable.Id_title, "title3_updateFormData");
        try {
            ((MainAbility) context).updateForm(formId, componentProvider);
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