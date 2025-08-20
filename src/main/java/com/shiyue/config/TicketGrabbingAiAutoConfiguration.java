package com.shiyue.config;

import com.shiyue.advisor.ChatTypeHistoryAdvisor;
import com.shiyue.ai.function.AiProgram;
import com.shiyue.enums.ChatType;
import com.shiyue.service.ChatTypeHistoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;

import static com.shiyue.constant.TicketGrabbingAiConstant.*;

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

    @Bean
    public ChatClient assistantChatClient(DeepSeekChatModel model, ChatMemory chatMemory,
                                          ChatTypeHistoryService chatTypeHistoryService,
                                          AiProgram aiProgram) {
        return ChatClient
                .builder(model)
                .defaultSystem(TICKET_GRABBING_AI_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        ChatTypeHistoryAdvisor.builder(chatTypeHistoryService).type(ChatType.ASSISTANT.getCode())
                                .order(CHAT_TYPE_HISTORY_ADVISOR_ORDER).build(),
                        MessageChatMemoryAdvisor.builder(chatMemory).order(MESSAGE_CHAT_MEMORY_ADVISOR_ORDER).build()
                )
                .defaultTools(aiProgram)
                .build();
    }
}
