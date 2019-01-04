package com.org.myGameServiceCenter.tcp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RequestDispatcher implements ApplicationContextAware {

    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());
    private ApplicationContext app;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
