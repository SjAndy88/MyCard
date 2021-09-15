package com.example.mycard;

import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {

    public static final String UUID = "ef5dce37-51e3-4006-bbb5-c692f859b344";

    public static final String hot24hoursUrl = "https://app.thepaper.cn/clt/jsp/v6/apple_components_todaynews.jsp?uuid=" + UUID;

    public static final String yaowenjingxuansUrl = "https://app.thepaper.cn/clt/jsp/v6/apple_components_homepage.jsp?uuid=" + UUID;

    @Override
    public void onInitialize() {
        super.onInitialize();
    }
}
