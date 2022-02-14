package com.aispeech.aistore.tp.admin.export;

import com.aispeech.aistore.tp.admin.export.dto.AsyncExportTaskProp;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import lombok.Data;

@Data
public class ExportTask<T> implements Serializable {

    private static final long serialVersionUID = -7912974487864723499L;

    private AsyncExportTaskProp props;

    /**
     * 任务执行逻辑
     */
    private transient Callable<T> task;

    /**
     * 任务执行补充逻辑
     */
    private transient ExportAround exportAround;

    /**
     * 任务执行结果值
     */
    private transient T value;


    public ExportTask(Callable<T> callable, String userId, String taskName, ExportAround around) {
        this.task = callable;
        this.exportAround = around;
        this.props = new AsyncExportTaskProp(userId, taskName);
    }

    public void executeBegin() {
        if (Objects.nonNull(this.props)) {
            this.props.begin();
        }
    }

    public void executeFinished() {
        if (Objects.nonNull(this.props)) {
            this.props.finish(value);
        }
    }

    public T start() throws Exception {
        return this.task.call();
    }

    public Optional<Consumer> getBefore() {
        if (Objects.nonNull(this.exportAround)) {
            return Optional.of(this.exportAround.beforeExecute());
        }
        return Optional.empty();
    }

    public Optional<Consumer> getAfter() {
        if (Objects.nonNull(this.exportAround)) {
            return Optional.of(this.exportAround.afterExecute());
        }
        return Optional.empty();
    }

    public void setPropId(long id) {
        if (Objects.nonNull(props)) {
            props.setId(id);
        }
    }

    public long getPropId() {
        return Objects.isNull(props) ? 0L : props.getId();
    }

}
