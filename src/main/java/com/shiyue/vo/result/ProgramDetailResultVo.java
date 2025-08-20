package com.shiyue.vo.result;

import com.shiyue.vo.ProgramDetailVo;
import com.shiyue.vo.result.base.ApiResponse;
import lombok.Data;

@Data
public class ProgramDetailResultVo extends ApiResponse {

    private ProgramDetailVo data;

}
