package com.shiyue.vo;

import lombok.Data;

@Data
public class ChatTypeHistoryVO  {

    private Long id;

    private Integer type;

    private String chatId;

    private String title;
}