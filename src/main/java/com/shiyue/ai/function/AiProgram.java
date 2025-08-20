package com.shiyue.ai.function;

import cn.hutool.core.collection.CollectionUtil;
import com.shiyue.ai.function.call.ProgramCall;
import com.shiyue.ai.function.call.TicketCategoryCall;
import com.shiyue.ai.function.dto.ProgramRecommendFunctionDto;
import com.shiyue.ai.function.dto.ProgramSearchFunctionDto;
import com.shiyue.dto.ProgramDetailDto;
import com.shiyue.dto.TicketCategoryListByProgramDto;
import com.shiyue.vo.ProgramDetailVo;
import com.shiyue.vo.ProgramSearchVo;
import com.shiyue.vo.TicketCategoryDetailVo;
import com.shiyue.vo.TicketCategoryVo;
import com.shiyue.vo.result.ProgramDetailResultVo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AiProgram {

    @Autowired
    private ProgramCall programCall;

    @Autowired
    private TicketCategoryCall ticketCategoryCall;

    @Tool(description = "根据区域或节目类型查询推荐的节目")
    public List<ProgramSearchVo> selectProgramRecommendList(@ToolParam(description = "查询的条件", required = false) ProgramRecommendFunctionDto programRecommendFunctionDto) {
        return programCall.recommendList(programRecommendFunctionDto);
    }

    @Tool(description = "根据条件查询节目")
    public List<ProgramSearchVo> selectProgramList(@ToolParam(description = "查询的条件", required = false) ProgramSearchFunctionDto programSearchFunctionDto) {
        return programCall.search(programSearchFunctionDto);
    }

    @Tool(description = "根据条件查询节目和演唱会的详情")
    public ProgramDetailVo detail(@ToolParam(description = "查询的条件", required = false) ProgramSearchFunctionDto programSearchFunctionDto) {
        return select(programSearchFunctionDto);
    }

    @Tool(description = "根据条件查询节目和演唱会的票档信息")
    public ProgramDetailVo select(@ToolParam(description = "查询的条件") ProgramSearchFunctionDto programSearchFunctionDto) {
        List<ProgramSearchVo> programSearchVoList = programCall.search(programSearchFunctionDto);
        if (CollectionUtil.isEmpty(programSearchVoList)) {
            return null;
        }
        ProgramSearchVo programSearchVo = programSearchVoList.get(0);
        ProgramDetailDto programDetailDto = new ProgramDetailDto();
        programDetailDto.setId(programSearchVo.getId());
        ProgramDetailResultVo programDetailResultVo = programCall.detail(programDetailDto);
        if (programDetailResultVo.getData() == null) {
            return null;
        }
        ProgramDetailVo programDetailVo = programDetailResultVo.getData();

//        // 查询票档
//        TicketCategoryListByProgramDto ticketCategoryListByProgramDto = new TicketCategoryListByProgramDto();
//        ticketCategoryListByProgramDto.setProgramId(programSearchVo.getId());
//        List<TicketCategoryDetailVo> ticketCategoryDetailVoList = ticketCategoryCall.selectListByProgram(ticketCategoryListByProgramDto);
//        Map<Long, TicketCategoryDetailVo> ticketCategoryDetailVoMap = ticketCategoryDetailVoList.stream().collect(Collectors.toMap(TicketCategoryDetailVo::getId, Function.identity()));
//        for (TicketCategoryVo ticketCategoryVo : programDetailVo.getTicketCategoryVoList()) {
//            TicketCategoryDetailVo ticketCategoryDetailVo = ticketCategoryDetailVoMap.get(ticketCategoryVo.getId());
//            if (Objects.nonNull(ticketCategoryDetailVo)) {
//                ticketCategoryVo.setRemainNumber(ticketCategoryDetailVo.getRemainNumber());
//                ticketCategoryVo.setTotalNumber(ticketCategoryDetailVo.getTotalNumber());
//            }
//        }
        return programDetailVo;
    }
}
