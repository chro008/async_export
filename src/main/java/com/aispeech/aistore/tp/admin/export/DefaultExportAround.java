package com.aispeech.aistore.tp.admin.export;

import com.aispeech.aistore.tp.admin.export.constant.Constants;
import com.aispeech.aistore.tp.admin.export.dto.ExportResultDto;
import com.aispeech.aistore.tp.admin.export.po.AsyncExportTaskPo;
import com.aispeech.aistore.tp.admin.export.service.AsyncExportTaskService;
import com.aispeech.ba.logger.LoggerUtils;
import com.aispeech.smart.common.excel.dto.ExportContent;
import com.aispeech.smart.common.excel.export.Exporter;
import com.aispeech.smart.common.file.server.IFileServer;
import com.aispeech.smart.common.util.DateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DefaultExportAround implements ExportAround {

    @Autowired
    private IFileServer fileServer;

    @Autowired
    private AsyncExportTaskService exportTaskService;

    @Override
    public Consumer<ExportTask<ExportResultDto>> beforeExecute() {
        return exportTask -> {
            LoggerUtils.debug("async_before_start");
            exportTask.executeBegin();
            AsyncExportTaskPo asyncExportTaskPo = new AsyncExportTaskPo();
            BeanUtils.copyProperties(exportTask.getProps(), asyncExportTaskPo);
            long id = exportTaskService.insert(asyncExportTaskPo, "");
            exportTask.setPropId(id);
            LoggerUtils.debug("async_before_end");
        };
    }

    @Override
    public Consumer<ExportTask<ExportResultDto>> afterExecute() {
        return exportTask -> {
            LoggerUtils.debug("async_after_start");
            exportTask.executeFinished();
            String fileUrl = uploadAndReturnUrl(exportTask).orElse("");
            AsyncExportTaskPo exportTaskPo = exportTaskService.findById(exportTask.getPropId());
            BeanUtils.copyProperties(exportTask.getProps(), exportTaskPo);
            exportTaskPo.setFileUrl(fileUrl);
            exportTaskService.update(exportTaskPo, "");
            LoggerUtils.debug("async_after_end");
        };
    }

    private Optional<String> uploadAndReturnUrl(ExportTask<ExportResultDto> exportTask) {
        ExportResultDto value = exportTask.getValue();
        List<ExportContent> contentList = value.getContentList();
        if (CollectionUtils.isEmpty(contentList)) {
            return Optional.empty();
        }
        // 1.生成excel临时文件
        String fileName = value.getFileName();
        if (!StringUtils.hasText(fileName)) {
            fileName = DateUtil.date2Str(new Date(), DateUtil.DATE_FORMAT_YMDHMS_ABBREVIATION) + Constants.DOT_XLSX;
        }
        File excelFile = new File(fileName);
        String fileUrl = null;
        // 2.将报表内容写到excel中
        try (Workbook workbook = Exporter.export(contentList);
             FileOutputStream fos = new FileOutputStream(excelFile)) {
            workbook.write(fos);
            fos.flush();
            // 3.将excel文件上传,并返回url
            String filePath = Constants.FILE_PATH_PREFFIX + UUID.randomUUID().toString() + "/" + fileName;
            fileServer.upload(filePath, excelFile, Constants.DEFAULT_CONTENTTYPE);
            fileUrl = fileServer.getFileUrl(filePath);
        } catch (Exception e) {
            LoggerUtils.error("uploadAndReturnUrl error ", e);
        } finally {
            // 4. 删除临时文件
            boolean result = false;
            try {
                result = Files.deleteIfExists(excelFile.toPath());
            } catch (IOException e) {
                LoggerUtils.error("delete local file error for io:" + excelFile.getAbsolutePath(), e);
            }
            if (!result) {
                LoggerUtils.error("delete local file error {}", excelFile.getAbsolutePath());
            }
        }
        return Optional.ofNullable(fileUrl);
    }
}
