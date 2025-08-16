package com.shiyue.controller;

import com.shiyue.service.ChatTypeHistoryService;
import com.shiyue.vo.ChatHistoryMessageVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.shiyue.enums.ChatType.ASSISTANT;

@CrossOrigin("*")
@RestController
@RequestMapping("/program")
public class ProgramController {

    @Resource
    private ChatClient assistantChatClient;

    @Autowired
    private ChatTypeHistoryService chatTypeHistoryService;
    @Autowired
    private ChatMemory chatMemory;

    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam("prompt") String prompt,
                             @RequestParam("chatId") String chatId) {
        chatTypeHistoryService.save(ASSISTANT.getCode(), chatId);
        return assistantChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }

    @RequestMapping("/history/message/list")
    public List<ChatHistoryMessageVO> getChatHistory(@RequestParam("chatId") String chatId,@RequestParam("type") Integer type) {
        List<Message> messages = chatMemory.get(chatId);
        return messages.stream().map(ChatHistoryMessageVO::new).toList();
    }
}