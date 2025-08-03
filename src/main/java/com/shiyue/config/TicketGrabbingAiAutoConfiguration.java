package com.shiyue.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;

public class TicketGrabbingAiAutoConfiguration {

    @Bean
    public ChatClient chatClient(DeepSeekChatModel model) {
        return ChatClient
                .builder(model)
                .defaultSystem("你是一位优秀的赛事与演出购票助手，你的名字叫智能小麦，你的特点是善良、聪明，要结合你的特点积极回答用户的问题")
                .defaultAdvisors(
                        // 日志组件
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

}
