package com.shiyue.es.mapper;

import com.shiyue.vo.ProgramSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.dromara.easyes.core.kernel.BaseEsMapper;

@Mapper
public interface ProgramMapper extends BaseEsMapper<ProgramSearchVo> {
}
