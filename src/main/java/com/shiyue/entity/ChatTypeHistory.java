package com.shiyue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shiyue.entity.base.BaseTableData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("d_chat_type_history")
public class ChatTypeHistory extends BaseTableData {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer type;

    private String chatId;

    private String title;
}
