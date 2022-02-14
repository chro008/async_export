package com.aispeech.aistore.tp.admin.export.enums;

import lombok.Getter;

@Getter
public enum ExportStatusEnum {

    /**
     * 下载状态：包含 排队中，生成中，生成失败和生成完成
     */
    IN_THE_LINE(1, "排队中"),
    GENERATING(2, "生成中"),
    BUILD_FAILED(3, "生成失败"),
    BUILD_COMPLETE(4, "生成完成");

    private int code;
    private String description;

    ExportStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
