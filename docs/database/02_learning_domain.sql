-- =============================================
-- 学习域 (Learning Domain) 数据库表结构设计
-- =============================================
-- 说明：学习域负责学习进度、笔记、问答、评价等学习相关功能
-- 版本：V2.0
-- 更新时间：2025-01-17
-- =============================================
-- 重要说明：
-- 1. 所有业务表的Entity实体类都继承 BaseDO
-- 2. BaseDO提供统一的审计字段：
--    - gmt_create: 创建时间（自动填充）
--    - gmt_modified: 更新时间（自动填充）
--    - create_operator: 创建者（自动填充）
--    - modified_operator: 修改者（自动填充）
--    - delete_state: 逻辑删除标记（@TableLogic）
-- =============================================

-- =============================================
-- 1. 学习进度表（核心表）
-- =============================================
DROP TABLE IF EXISTS `learning_progress`;
CREATE TABLE `learning_progress` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '进度ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `lesson_id` BIGINT NOT NULL COMMENT '课时ID',
    `chapter_id` BIGINT NOT NULL COMMENT '章节ID',
    -- 进度信息
    `progress_percentage` INT NOT NULL DEFAULT 0 COMMENT '学习进度百分比（0-100）',
    `learn_duration` INT NOT NULL DEFAULT 0 COMMENT '累计学习时长（秒）',
    `last_learn_position` INT NOT NULL DEFAULT 0 COMMENT '最后学习位置（秒，用于断点续播）',
    `is_completed` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否完成（0-未完成，1-已完成）',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    -- 学习统计
    `learn_count` INT NOT NULL DEFAULT 0 COMMENT '学习次数',
    `first_learn_time` DATETIME DEFAULT NULL COMMENT '首次学习时间',
    `last_learn_time` DATETIME DEFAULT NULL COMMENT '最后学习时间',
    -- 学习设备
    `last_learn_device` VARCHAR(50) DEFAULT NULL COMMENT '最后学习设备（PC, MOBILE, TABLET）',
    `last_learn_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后学习IP',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_lesson` (`user_id`, `lesson_id`),
    KEY `idx_user_course` (`user_id`, `course_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_is_completed` (`is_completed`),
    KEY `idx_last_learn_time` (`last_learn_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习进度表';

-- =============================================
-- 2. 课程学习统计表（用户维度）
-- =============================================
DROP TABLE IF EXISTS `user_course_learning`;
CREATE TABLE `user_course_learning` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    -- 整体进度
    `total_lesson_count` INT NOT NULL DEFAULT 0 COMMENT '课程总课时数',
    `completed_lesson_count` INT NOT NULL DEFAULT 0 COMMENT '已完成课时数',
    `course_progress_percentage` INT NOT NULL DEFAULT 0 COMMENT '课程整体进度（0-100）',
    `is_course_completed` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '课程是否完成',
    `course_complete_time` DATETIME DEFAULT NULL COMMENT '课程完成时间',
    -- 学习统计
    `total_learn_duration` INT NOT NULL DEFAULT 0 COMMENT '总学习时长（秒）',
    `learn_days` INT NOT NULL DEFAULT 0 COMMENT '学习天数',
    `last_learn_lesson_id` BIGINT DEFAULT NULL COMMENT '最后学习的课时ID',
    `last_learn_time` DATETIME DEFAULT NULL COMMENT '最后学习时间',
    `first_learn_time` DATETIME DEFAULT NULL COMMENT '首次学习时间',
    -- 互动统计
    `note_count` INT NOT NULL DEFAULT 0 COMMENT '笔记数量',
    `question_count` INT NOT NULL DEFAULT 0 COMMENT '提问数量',
    `has_reviewed` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已评价',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_course` (`user_id`, `course_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_is_completed` (`is_course_completed`),
    KEY `idx_last_learn_time` (`last_learn_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程学习统计表';

-- =============================================
-- 3. 课程笔记表
-- =============================================
DROP TABLE IF EXISTS `course_note`;
CREATE TABLE `course_note` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '笔记ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `lesson_id` BIGINT NOT NULL COMMENT '课时ID',
    `chapter_id` BIGINT NOT NULL COMMENT '章节ID',
    -- 笔记内容
    `video_time_point` INT DEFAULT NULL COMMENT '视频时间点（秒）',
    `note_content` TEXT NOT NULL COMMENT '笔记内容',
    `note_images` JSON DEFAULT NULL COMMENT '笔记图片（JSON数组）',
    -- 笔记属性
    `is_public` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否公开（0-私密，1-公开）',
    `is_top` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否置顶（个人笔记）',
    -- 互动统计
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_lesson` (`lesson_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_public_like` (`is_public`, `like_count`),
    KEY `idx_create_time` (`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程笔记表';

-- =============================================
-- 4. 笔记点赞表（关系表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `note_like`;
CREATE TABLE `note_like` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `note_id` BIGINT NOT NULL COMMENT '笔记ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_note_user` (`note_id`, `user_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记点赞表';

-- =============================================
-- 5. 笔记评论表
-- =============================================
DROP TABLE IF EXISTS `note_comment`;
CREATE TABLE `note_comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `note_id` BIGINT NOT NULL COMMENT '笔记ID',
    `user_id` BIGINT NOT NULL COMMENT '评论用户ID',
    `comment_content` VARCHAR(1000) NOT NULL COMMENT '评论内容',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID（0表示一级评论）',
    `reply_to_user_id` BIGINT DEFAULT NULL COMMENT '回复的用户ID',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_note` (`note_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记评论表';

-- =============================================
-- 6. 课程问答表
-- =============================================
DROP TABLE IF EXISTS `course_question`;
CREATE TABLE `course_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '问题ID',
    `user_id` BIGINT NOT NULL COMMENT '提问用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `lesson_id` BIGINT DEFAULT NULL COMMENT '课时ID',
    `chapter_id` BIGINT DEFAULT NULL COMMENT '章节ID',
    -- 问题内容
    `question_title` VARCHAR(200) NOT NULL COMMENT '问题标题',
    `question_content` TEXT NOT NULL COMMENT '问题内容',
    `question_images` JSON DEFAULT NULL COMMENT '问题图片（JSON数组）',
    `video_time_point` INT DEFAULT NULL COMMENT '视频时间点（秒）',
    -- 问题状态
    `is_resolved` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已解决',
    `is_top` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否置顶',
    `best_answer_id` BIGINT DEFAULT NULL COMMENT '最佳答案ID',
    -- 统计数据
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `answer_count` INT NOT NULL DEFAULT 0 COMMENT '回答数',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_lesson` (`lesson_id`),
    KEY `idx_is_resolved` (`is_resolved`),
    KEY `idx_create_time` (`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程问答表';

-- =============================================
-- 7. 问答回复表
-- =============================================
DROP TABLE IF EXISTS `question_answer`;
CREATE TABLE `question_answer` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '回复ID',
    `question_id` BIGINT NOT NULL COMMENT '问题ID',
    `user_id` BIGINT NOT NULL COMMENT '回复用户ID',
    `answer_content` TEXT NOT NULL COMMENT '回复内容',
    `answer_images` JSON DEFAULT NULL COMMENT '回复图片（JSON数组）',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父回复ID（0表示直接回复问题）',
    `reply_to_user_id` BIGINT DEFAULT NULL COMMENT '回复的用户ID',
    -- 回复属性
    `is_teacher_answer` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否讲师回复',
    `is_best_answer` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否最佳答案',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_question` (`question_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_parent` (`parent_id`),
    KEY `idx_is_best` (`is_best_answer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问答回复表';

-- =============================================
-- 8. 问答点赞表（关系表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `question_answer_like`;
CREATE TABLE `question_answer_like` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `target_type` VARCHAR(20) NOT NULL COMMENT '点赞目标类型（QUESTION-问题，ANSWER-回答）',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_target_user` (`target_type`, `target_id`, `user_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问答点赞表';

-- =============================================
-- 9. 课程评价表
-- =============================================
DROP TABLE IF EXISTS `course_review`;
CREATE TABLE `course_review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `order_id` BIGINT DEFAULT NULL COMMENT '订单ID',
    -- 评价内容
    `rating` INT NOT NULL COMMENT '评分（1-5星）',
    `content_rating` INT DEFAULT NULL COMMENT '内容评分（1-5）',
    `teacher_rating` INT DEFAULT NULL COMMENT '讲师评分（1-5）',
    `difficulty_rating` INT DEFAULT NULL COMMENT '难度评分（1-5）',
    `review_content` TEXT COMMENT '评价内容',
    `review_images` JSON DEFAULT NULL COMMENT '评价图片（JSON数组）',
    -- 评价属性
    `is_anonymous` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否匿名',
    `is_top` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否优质评价',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_course` (`user_id`, `course_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_rating` (`rating`),
    KEY `idx_is_top` (`is_top`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程评价表';

-- =============================================
-- 10. 评价回复表（讲师回复学员评价）
-- =============================================
DROP TABLE IF EXISTS `review_reply`;
CREATE TABLE `review_reply` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '回复ID',
    `review_id` BIGINT NOT NULL COMMENT '评价ID',
    `user_id` BIGINT NOT NULL COMMENT '回复用户ID（通常是讲师）',
    `reply_content` VARCHAR(500) NOT NULL COMMENT '回复内容',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_review` (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评价回复表';

-- =============================================
-- 11. 课程收藏表（关系表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `course_collect`;
CREATE TABLE `course_collect` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `collect_folder_id` BIGINT DEFAULT NULL COMMENT '收藏夹ID',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_course` (`user_id`, `course_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_folder` (`collect_folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程收藏表';

-- =============================================
-- 12. 收藏夹表
-- =============================================
DROP TABLE IF EXISTS `collect_folder`;
CREATE TABLE `collect_folder` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏夹ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `folder_name` VARCHAR(50) NOT NULL COMMENT '收藏夹名称',
    `folder_description` VARCHAR(200) DEFAULT NULL COMMENT '收藏夹描述',
    `course_count` INT NOT NULL DEFAULT 0 COMMENT '课程数量',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否默认收藏夹',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏夹表';

-- =============================================
-- 13. 学习证书表
-- =============================================
DROP TABLE IF EXISTS `learning_certificate`;
CREATE TABLE `learning_certificate` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '证书ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `certificate_no` VARCHAR(100) NOT NULL COMMENT '证书编号',
    `certificate_name` VARCHAR(200) NOT NULL COMMENT '证书名称',
    `certificate_image_url` VARCHAR(500) DEFAULT NULL COMMENT '证书图片URL',
    `issue_time` DATETIME NOT NULL COMMENT '颁发时间',
    `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
    `is_valid` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否有效',
    -- 成绩信息
    `exam_score` DECIMAL(5,2) DEFAULT NULL COMMENT '考试成绩',
    `course_score` DECIMAL(5,2) DEFAULT NULL COMMENT '课程总评分',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_certificate_no` (`certificate_no`),
    UNIQUE KEY `uk_user_course` (`user_id`, `course_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习证书表';

-- =============================================
-- 14. 用户行为日志表（日志表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `user_behavior_log`;
CREATE TABLE `user_behavior_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `behavior_type` VARCHAR(50) NOT NULL COMMENT '行为类型（VIEW_COURSE-浏览课程，LEARN_LESSON-学习课时，COLLECT-收藏，SHARE-分享，SEARCH-搜索）',
    `target_type` VARCHAR(50) NOT NULL COMMENT '目标类型（COURSE-课程，LESSON-课时，CATEGORY-分类）',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `behavior_data` JSON DEFAULT NULL COMMENT '行为数据（扩展字段，JSON格式）',
    `device_type` VARCHAR(20) DEFAULT NULL COMMENT '设备类型（PC, MOBILE, TABLET）',
    `ip_address` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User Agent',
    `behavior_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行为时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_time` (`user_id`, `behavior_time`),
    KEY `idx_behavior_type` (`behavior_type`),
    KEY `idx_target` (`target_type`, `target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户行为日志表';
