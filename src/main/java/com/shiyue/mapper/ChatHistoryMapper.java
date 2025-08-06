package com.shiyue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiyue.entity.ChatTypeHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatTypeHistory> {
}
