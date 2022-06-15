package com.github.istock.enums;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/14 9:28
 */
public enum InterfacesEnums {
    STOCK_SSE_SUMMARY("数据总貌","stock_sse_summary"),
    STOCK_NEW_A_SPOT_EM("新股实时","stock_new_a_spot_em"),
    STOCK_ZH_A_STOP_EM("退市查询","stock_zh_a_stop_em");
    private String desc;
    private String interfaceUrl;

    InterfacesEnums(String desc, String interfaceUrl) {
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
