package com.example.mycard.utils;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

public class EventHandlerHelper {
    private static final EventHandlerHelper sEventHandlerHelper = new EventHandlerHelper();

    private EventHandler mEventHandler;

    private EventHandlerHelper() {
        mEventHandler = new EventHandler(EventRunner.getMainEventRunner());
    }

    public static EventHandlerHelper getInstance() {
        return sEventHandlerHelper;
    }

    public static void postNow(Runnable runnable) {
        getInstance().mEventHandler.postTask(runnable, 0);
    }

    public static void postDelay(Runnable runnable, long delayTime) {
        getInstance().mEventHandler.postTask(runnable, delayTime);
    }
}
