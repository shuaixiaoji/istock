package com.github.istock.enums;

/**
 * @author shuaixiaoji
 * @Description 市场相关接口
 * @date 2022/6/14 9:28
 */
public enum MarketInterfacesEnums {
    /************   分析师   ******************/

    // year='2015' : 从 2015 年至今
    STOCK_ANALYST_RANK_EM("分析师排行","stock_analyst_rank_em"),

    // analyst_id="11000257131"; 分析师ID
    // indicator="最新跟踪成分股"; 从 {"最新跟踪成分股", "历史跟踪成分股", "历史指数"} 中选择一个
    STOCK_ANALYST_DETAIL_EM("分析师详情","stock_analyst_detail_em"),

    /************   分析师  ******************/

    /************   千股千评start ******************/

    STOCK_COMMENT_EM("千股千评-所有数据","stock_comment_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_ZLKP_JGCYD_EM("千股千评-主力参与度","stock_comment_detail_zlkp_jgcyd_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_ZHPJ_LSPF_EM("千股千评-综合评价-历史评分","stock_comment_detail_zhpj_lspf_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_SCRD_FOCUS_EM("千股千评-用户关注度","stock_comment_detail_scrd_focus_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_SCRD_DESIRE_EM("千股千评-市场热度-市场参与意愿","stock_comment_detail_scrd_desire_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_SCRD_DESIRE_DAILY_EM("日度市场参与意愿","stock_comment_detail_scrd_desire_daily_em"),

    // symbol="600000"
    STOCK_COMMENT_DETAIL_SCRD_COST_EM("千股千评-市场热度-市场成本","stock_comment_detail_scrd_cost_em"),

    /************   千股千评end ******************/

    /************   沪深京A股信息 start ******************/
    STOCK_ZH_A_SPOT_EM("千股千评-市场热度-市场成本","stock_zh_a_spot_em"),
    /************   沪深京A股信息 end ******************/

    /************   沪深京A股信息 start ******************/
    STOCK_ZH_A_HIST("个股历史数据","stock_zh_a_hist"),
    STOCK_INDIVIDUAL_INFO_EM("新浪个股信息","stock_individual_info_em");
    /************   沪深京A股信息 end ******************/

    private String desc;
    private String interfaceUrl;

    MarketInterfacesEnums(String desc, String interfaceUrl) {
        this.desc = desc;
        this.interfaceUrl = interfaceUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }
}
