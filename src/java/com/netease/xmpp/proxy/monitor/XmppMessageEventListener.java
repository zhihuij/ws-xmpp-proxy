package com.netease.xmpp.proxy.monitor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.jivesoftware.multiplexer.Session;

public class XmppMessageEventListener implements MessageEventListener {
    private AtomicLong requestNum = new AtomicLong(0);
    private AtomicLong responseNum = new AtomicLong(0);

    private Executor executor = Executors.newSingleThreadExecutor();

    private static XmppMessageEventListener instance = null;

    public static XmppMessageEventListener getInstance() {
        if (instance == null) {
            instance = new XmppMessageEventListener();
        }
        return instance;
    }

    class RequestChecker implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("Request number: " + requestNum.get());
                System.out.println("Response number: " + responseNum.get());

                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    // Do nothing, continue
                }
            }
        }
    }

    private XmppMessageEventListener() {
        executor.execute(new RequestChecker());
    }

    @Override
    public void onMessageReceived(Session session, String message) {
        requestNum.incrementAndGet();
    }

    @Override
    public void onMessageSend(Session session, String message) {
        responseNum.incrementAndGet();
    }
}
