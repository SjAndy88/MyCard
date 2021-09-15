package com.example.mycard.widget.widget5;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mycard.MainAbility;
import com.example.mycard.MyApplication;
import com.example.mycard.ResourceTable;
import com.example.mycard.data.ContListResult;
import com.example.mycard.data.ContObject;
import com.example.mycard.widget.controller.FormController;
import com.zzrv5.mylibrary.ZZRCallBack;
import com.zzrv5.mylibrary.ZZRHttp;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.FormException;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.ComponentProvider;
import ohos.agp.components.Image;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.PixelMapElement;
import ohos.app.Context;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentConstant;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.intentagent.IntentAgentInfo;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Widget5Impl extends FormController {
    public static final int DIMENSION_4X4 = 4;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, Widget5Impl.class.getName());
    private static final int DEFAULT_DIMENSION_2X2 = 2;
    private static final Map<Integer, Integer> RESOURCE_ID_MAP = new HashMap<>();

    static {
        RESOURCE_ID_MAP.put(DEFAULT_DIMENSION_2X2, ResourceTable.Layout_form_list_pattern_widget5_2_2);
        RESOURCE_ID_MAP.put(DIMENSION_4X4, ResourceTable.Layout_form_list_pattern_widget5_4_4);
    }

    private static final int[] cardIds = {ResourceTable.Id_card1, ResourceTable.Id_card2, ResourceTable.Id_card3, ResourceTable.Id_card4};
    private static final int[] imageIds = {ResourceTable.Id_card1_image, ResourceTable.Id_card2_image, ResourceTable.Id_card3_image, ResourceTable.Id_card4_image};
    private static final int[] titleIds = {ResourceTable.Id_card1_title, ResourceTable.Id_card2_title, ResourceTable.Id_card3_title, ResourceTable.Id_card4_title};
    private static final int[] timeIds = {ResourceTable.Id_card1_time, ResourceTable.Id_card2_time, ResourceTable.Id_card3_time, ResourceTable.Id_card4_time};
    private static final int[] commentsIds = {ResourceTable.Id_card1_comments, ResourceTable.Id_card2_comments, ResourceTable.Id_card3_comments, ResourceTable.Id_card4_comments};


    public Widget5Impl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData() {
        HiLog.info(TAG, "bind form data when create form");
        return new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
        HiLog.info(TAG, "dimension = " + dimension);
        if (dimension == DIMENSION_4X4) {
            HiLog.info(TAG, "ZZRHttp get yaowenjingxuansUrl");
            ZZRHttp.get(MyApplication.yaowenjingxuansUrl, new ZZRCallBack.CallBackString() {

                @Override
                public void onFailure(int i, String s) {
                    HiLog.info(TAG, "API返回失败");
                }

                @Override
                public void onResponse(String s) {
                    HiLog.info(TAG, "API返回成功");
                    ContListResult contListResult = JSON.parseObject(s, ContListResult.class);
                    ArrayList<ContObject> contList = contListResult.getContList();
                    ProviderFormInfo providerFormInfo = bindCard(formId, contList);

                    try {
                        ((MainAbility) context).updateForm(formId, providerFormInfo.getComponentProvider());
                    } catch (FormException e) {
                        e.printStackTrace();
                        HiLog.info(TAG, "更新卡片失败");
                    }
                }
            });
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

    private ProviderFormInfo bindCard(long formId, ArrayList<ContObject> contObjects) {

        ProviderFormInfo providerFormInfo = new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
        ComponentProvider componentProvider = new ComponentProvider(RESOURCE_ID_MAP.get(dimension), context);

        for (int i = 0; i < Math.min(contObjects.size(), cardIds.length); i++) {
            int cardId = cardIds[i];
            int cardImageId = imageIds[i];
            int cardTitleId = titleIds[i];
            int cardTimeId = timeIds[i];
            int cardCommentsId = commentsIds[i];

            ContObject contObject = contObjects.get(i);
            componentProvider.setText(cardTitleId, contObject.getName());
            componentProvider.setText(cardTimeId, contObject.getPubTime());
            componentProvider.setText(cardCommentsId, contObject.getInteractionNum() + getString(ResourceTable.String_comments_num_suffix));
            componentProvider.setIntentAgent(cardId, getStartAbilityIntentAgent(contObject, i));

            Glide.with(context)
                    .load(contObject.getPic())
                    .transform(new RoundedCorners(AttrHelper.vp2px(4, context)))
                    .addListener(new RequestListener<Element>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Element> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Element element, Object o, Target<Element> target, DataSource dataSource, boolean b) {
                            if (element instanceof PixelMapElement) {
                                ComponentProvider componentProvider = new ComponentProvider(RESOURCE_ID_MAP.get(dimension), context);
                                componentProvider.setImagePixelMap(cardImageId, ((PixelMapElement) element).getPixelMap());
                                try {
                                    ((MainAbility) context).updateForm(formId, componentProvider);
                                } catch (FormException e) {
                                    e.printStackTrace();
                                    HiLog.info(TAG, "更新卡片失败");
                                }
                            }

                            return false;
                        }
                    })
                    .into(new Image(context));
        }

        componentProvider.setIntentAgent(ResourceTable.Id_card_hot, getStartAbilityIntentAgent());
        providerFormInfo.mergeActions(componentProvider);

        return providerFormInfo;
    }

    private IntentAgent getStartAbilityIntentAgent(ContObject contObject, int index) {
        Intent intent = new Intent();
        String contId = contObject.getContId();
        String contType = contObject.getForwordType();
        intent.setParam("contId", contId);
        intent.setParam("contType", contType);

        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withFlags(Intent.FLAG_NOT_OHOS_COMPONENT)
                .withBundleName("com.wondertek.paper")
                .withAbilityName("cn.thepaper.paper.lib.link.LinkTransferActivity")
                .build();
        intent.setOperation(operation);
        List<Intent> intentList = new ArrayList<>();
        intentList.add(intent);
        List<IntentAgentConstant.Flags> flagsList = new ArrayList<>();
        flagsList.add(IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG);
        IntentAgentInfo agentInfo =
                new IntentAgentInfo(400 + index, IntentAgentConstant.OperationType.START_ABILITY, flagsList, intentList, null);
        return IntentAgentHelper.getIntentAgent(context, agentInfo);
    }


    private IntentAgent getStartAbilityIntentAgent() {
        Intent intent = new Intent();
        intent.setParam("contId", "-1"); // 不需要传
        intent.setParam("contType", "1"); // 要闻精选

        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withFlags(Intent.FLAG_NOT_OHOS_COMPONENT)
                .withBundleName("com.wondertek.paper")
                .withAbilityName("cn.thepaper.paper.lib.link.LinkTransferActivity")
                .build();
        intent.setOperation(operation);
        List<Intent> intentList = new ArrayList<>();
        intentList.add(intent);
        List<IntentAgentConstant.Flags> flagsList = new ArrayList<>();
        flagsList.add(IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG);
        IntentAgentInfo agentInfo =
                new IntentAgentInfo(500 + 1, IntentAgentConstant.OperationType.START_ABILITY, flagsList, intentList, null);
        return IntentAgentHelper.getIntentAgent(context, agentInfo);
    }

    private String getString(int stringId) {
        try {
            return context.getResourceManager().getElement(ResourceTable.String_comments_num_suffix).getString();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }
        return "";
    }
}