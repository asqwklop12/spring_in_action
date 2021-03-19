package com.example.demo;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultReplyChannel = "inChannel",
    defaultRequestChannel = "outChannel")
public interface UpperUseGateWay {

  String uppercase(String in);
}
