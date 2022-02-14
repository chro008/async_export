package com.aispeech.aistore.tp.admin.export.dto;

import com.aispeech.aistore.tp.admin.export.constant.Constants;
import com.aispeech.smart.common.excel.dto.ExportContent;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 离线下载模块，任务执行结果数据传输对象
 */
@Data
public class ExportResultDto implements Serializable {

    private static final long serialVersionUID = 8265896971977816342L;

    /**
     * 下载导出的excel数据
     */
    private transient List<ExportContent> contentList;

    /**
     * 导出文件名
     */
    private String fileName;

    /**
     * 任务状态是否成功：false->失败，true->成功
     */
    private boolean succeed = true;

    /**
     * 任务状态说明
     */
    private String message = Constants.SUCCEED_DESCRIPTION;

    public static ExportResultDto succeed(List<ExportContent> contentList, String fileName) {
        return succeed(contentList, fileName, Constants.SUCCEED_DESCRIPTION);
    }

    public static ExportResultDto succeed(List<ExportContent> contentList, String fileName, String message) {
        ExportResultDto resultDto = new ExportResultDto();
        resultDto.setSucceed(true);
        resultDto.setMessage(message);
        resultDto.setContentList(contentList);
        resultDto.setFileName(fileName);
        return resultDto;
    }

    public static ExportResultDto failed() {
        return failed(Constants.FAILED_DESCRIPTION);
    }

    public static ExportResultDto failed(String message) {
        ExportResultDto resultDto = new ExportResultDto();
        resultDto.setSucceed(false);
        resultDto.setMessage(message);
        return resultDto;
    }

}
