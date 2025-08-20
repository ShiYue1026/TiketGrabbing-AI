package com.shiyue.ai.function.call;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.shiyue.ai.function.dto.ProgramRecommendFunctionDto;
import com.shiyue.ai.function.dto.ProgramSearchFunctionDto;
import com.shiyue.dto.ProgramDetailDto;
import com.shiyue.enums.BaseCode;
import com.shiyue.es.mapper.ProgramMapper;
import com.shiyue.util.StringUtil;
import com.shiyue.vo.ProgramDetailVo;
import com.shiyue.vo.ProgramSearchVo;
import com.shiyue.vo.result.ProgramDetailResultVo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.kernel.EsWrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.shiyue.constant.TicketGrabbingAiConstant.PROGRAM_DETAIL_URL;

@Component
public class ProgramCall {

    @Autowired
    private ProgramMapper programMapper;

    public List<ProgramSearchVo> recommendList(ProgramRecommendFunctionDto programRecommendFunctionDto) {
        LambdaEsQueryWrapper<ProgramSearchVo> wrapper = EsWrappers.lambdaQuery(ProgramSearchVo.class)
                .eq(StringUtil.isNotEmpty(programRecommendFunctionDto.getAreaName()), ProgramSearchVo::getAreaName, programRecommendFunctionDto.getAreaName())
                .eq(StringUtil.isNotEmpty(programRecommendFunctionDto.getProgramCategory()), ProgramSearchVo::getProgramCategoryName, programRecommendFunctionDto.getProgramCategory());
        return programMapper.selectList(wrapper);
    }

    public List<ProgramSearchVo> search(ProgramSearchFunctionDto programSearchFunctionDto) {
        LambdaEsQueryWrapper<ProgramSearchVo> wrapper = EsWrappers.lambdaQuery(ProgramSearchVo.class)
                .eq(StringUtil.isNotEmpty(programSearchFunctionDto.getCityName()), ProgramSearchVo::getAreaName, programSearchFunctionDto.getCityName())
                .eq(StringUtil.isNotEmpty(programSearchFunctionDto.getActor()), ProgramSearchVo::getActor, programSearchFunctionDto.getActor())
                .eq(Objects.nonNull(programSearchFunctionDto.getShowTime()), ProgramSearchVo::getShowTime, programSearchFunctionDto.getShowTime());
        return programMapper.selectList(wrapper);
    }

    public ProgramDetailResultVo detail(ProgramDetailDto programDetailDto) {
        String result = HttpRequest.post(PROGRAM_DETAIL_URL)
                .header("no_verify", "true")
                .body(JSON.toJSONString(programDetailDto))
                .timeout(20000)
                .execute()
                .body();
        ProgramDetailResultVo programDetailResultVo = JSON.parseObject(result, ProgramDetailResultVo.class);
        if(!programDetailResultVo.getCode().equals(BaseCode.SUCCESS.getCode())) {
            throw new RuntimeException("调用大麦系统查询节目失败");
        }
        return programDetailResultVo;
    }
}
