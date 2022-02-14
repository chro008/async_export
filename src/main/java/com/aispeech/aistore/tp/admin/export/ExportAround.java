package com.aispeech.aistore.tp.admin.export;

import java.util.function.Consumer;

/**
 * 导出逻辑补充
 */
public interface ExportAround {

    /**
     * 导出逻辑执行之前
     *
     * @return
     */
    Consumer beforeExecute();

    /**
     * 导出逻辑执行之后
     *
     * @return
     */
    Consumer afterExecute();

}
