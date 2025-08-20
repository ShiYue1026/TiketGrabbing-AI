package com.shiyue.vo.result;

import com.shiyue.common.ApiResponse;
import com.shiyue.vo.TicketCategoryDetailVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TicketCategoryListResultVo extends ApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<TicketCategoryDetailVo> data;
}
