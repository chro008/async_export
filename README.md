# async_export
离线导出模块

### 表结构设计：async_export_task

#### 建表语句
```
drop table if exists async_export_task;
CREATE TABLE `async_export_task` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下载任务名称',
  `user_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下载人唯一标识',
  `status` int(1) DEFAULT '1' COMMENT '任务状态：1.排队中,2.生成中,3.生成失败,4.生成完成',
  `message` text COLLATE utf8mb4_unicode_ci COMMENT '任务描述',
  `file_url` text COLLATE utf8mb4_unicode_ci COMMENT '下载任务文件地址',
  `creator` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '任务创建时间',
  `execute_time` datetime DEFAULT NULL COMMENT '任务开始执行时间',
  `finish_time` datetime DEFAULT NULL COMMENT '任务完成时间',
  `modifier` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='离线下载任务'
```
