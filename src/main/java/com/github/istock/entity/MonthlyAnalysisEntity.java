package com.github.istock.entity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author shuaixiaoji
 * @Description 评分 & 关注度
 * @date 2022/6/16 22:47
 */
@Data
public class MonthlyAnalysisEntity {

    // 编码
    private String code;

    // 名称
    private String name;

    // 统计数据日期
    private String staticDate;

    // 当前价
    private BigDecimal recentPrice;

    // 10月均线价
    private BigDecimal month10Avg;

    // 30月均线价
    private BigDecimal month30Avg;

}
