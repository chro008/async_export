package com.aispeech.aistore.tp.admin.export.service;

import com.aispeech.aistore.tp.admin.export.dao.AsyncExportTaskDao;
import com.aispeech.aistore.tp.admin.export.po.AsyncExportTaskPo;
import com.aispeech.smart.common.db.mysql.dao.BaseDao;
import com.aispeech.smart.common.db.mysql.service.BaseService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class AsyncExportTaskService extends BaseService<AsyncExportTaskPo, Long> {

    @Resource
    private AsyncExportTaskDao asyncExportTaskDao;

    @Override
    public BaseDao<AsyncExportTaskPo, Long> getDao() {
        return asyncExportTaskDao;
    }
}
