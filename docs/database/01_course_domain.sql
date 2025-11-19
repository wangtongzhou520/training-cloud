-- =============================================
-- 课程域 (Course Domain) 数据库表结构设计
-- =============================================
-- 说明：课程域负责课程、章节、课时、分类、讲师等管理
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
-- 1. 课程分类表
-- =============================================
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父分类ID（0表示顶级分类）',
    `category_level` INT NOT NULL DEFAULT 1 COMMENT '分类层级（1-一级，2-二级，3-三级）',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序序号（数字越小越靠前）',
    `category_icon` VARCHAR(500) DEFAULT NULL COMMENT '分类图标URL',
    `category_description` VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    `is_show` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否显示（0-隐藏，1-显示）',
    `course_count` INT NOT NULL DEFAULT 0 COMMENT '课程数量（冗余字段，便于展示）',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_level` (`category_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程分类表';

-- =============================================
-- 2. 课程表（核心表）
-- =============================================
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `course_name` VARCHAR(200) NOT NULL COMMENT '课程名称',
    `course_sub_title` VARCHAR(300) DEFAULT NULL COMMENT '课程副标题',
    `course_description` TEXT COMMENT '课程描述',
    `category_id` BIGINT NOT NULL COMMENT '课程分类ID',
    `thumbnail_url` VARCHAR(500) DEFAULT NULL COMMENT '封面图URL',
    `intro_video_url` VARCHAR(500) DEFAULT NULL COMMENT '课程介绍视频URL',
    -- 课程状态
    `is_published` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否发布（0-未发布，1-已发布）',
    `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
    `chapter_state` INT NOT NULL DEFAULT 0 COMMENT '章节状态（0-无章节，1-存在章节）',
    -- 课程统计（冗余字段，定期更新）
    `student_count` INT NOT NULL DEFAULT 0 COMMENT '学习人数',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `collect_count` INT NOT NULL DEFAULT 0 COMMENT '收藏次数',
    `share_count` INT NOT NULL DEFAULT 0 COMMENT '分享次数',
    `average_rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '平均评分（0-5分）',
    `review_count` INT NOT NULL DEFAULT 0 COMMENT '评价数量',
    -- 课程属性
    `difficulty_level` INT NOT NULL DEFAULT 1 COMMENT '难度级别（1-入门，2-初级，3-中级，4-高级，5-专家）',
    `estimated_duration` INT DEFAULT NULL COMMENT '预计学习时长（分钟）',
    `total_lesson_count` INT NOT NULL DEFAULT 0 COMMENT '总课时数',
    `course_language` VARCHAR(20) DEFAULT 'zh_CN' COMMENT '课程语言（zh_CN-中文，en_US-英文）',
    -- 课程设置
    `allow_download` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否允许下载（0-不允许，1-允许）',
    `allow_trial` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否允许试听（0-不允许，1-允许）',
    `trial_lesson_count` INT DEFAULT 0 COMMENT '可试听课时数',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_publish_time` (`publish_time`),
    KEY `idx_is_published` (`is_published`),
    KEY `idx_student_count` (`student_count`),
    KEY `idx_average_rating` (`average_rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- =============================================
-- 3. 课程章节表
-- =============================================
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '章节ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `chapter_name` VARCHAR(200) NOT NULL COMMENT '章节名称',
    `chapter_description` VARCHAR(500) DEFAULT NULL COMMENT '章节描述',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    `lesson_count` INT NOT NULL DEFAULT 0 COMMENT '课时数量',
    `total_duration` INT NOT NULL DEFAULT 0 COMMENT '章节总时长（秒）',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程章节表';

-- =============================================
-- 4. 课程课时表
-- =============================================
DROP TABLE IF EXISTS `course_lesson`;
CREATE TABLE `course_lesson` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '课时ID',
    `chapter_id` BIGINT NOT NULL COMMENT '章节ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID（冗余字段，便于查询）',
    `lesson_name` VARCHAR(200) NOT NULL COMMENT '课时名称',
    `lesson_type` VARCHAR(20) NOT NULL DEFAULT 'VIDEO' COMMENT '课时类型（VIDEO-视频，LIVE-直播，DOC-文档，AUDIO-音频）',
    `lesson_url` VARCHAR(1000) DEFAULT NULL COMMENT '课时资源URL',
    `lesson_duration` INT DEFAULT 0 COMMENT '课时时长（秒）',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    -- 课时属性
    `is_free` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否免费试听（0-否，1-是）',
    `is_preview` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否可预览（0-否，1-是）',
    `video_quality` VARCHAR(50) DEFAULT NULL COMMENT '视频清晰度（SD-标清，HD-高清，FHD-超清，4K）',
    `file_size` BIGINT DEFAULT 0 COMMENT '文件大小（字节）',
    -- 学习统计
    `learn_count` INT NOT NULL DEFAULT 0 COMMENT '学习人数',
    `avg_learn_progress` DECIMAL(5,2) DEFAULT 0.00 COMMENT '平均学习进度（百分比）',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_chapter_id` (`chapter_id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_is_free` (`is_free`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程课时表';

-- =============================================
-- 5. 课程课件资料表
-- =============================================
DROP TABLE IF EXISTS `course_material`;
CREATE TABLE `course_material` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资料ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `lesson_id` BIGINT DEFAULT NULL COMMENT '课时ID（为空表示课程资料）',
    `material_name` VARCHAR(200) NOT NULL COMMENT '资料名称',
    `material_type` VARCHAR(20) NOT NULL COMMENT '资料类型（PPT, PDF, WORD, EXCEL, CODE, OTHER）',
    `material_url` VARCHAR(1000) NOT NULL COMMENT '资料URL',
    `file_size` BIGINT NOT NULL DEFAULT 0 COMMENT '文件大小（字节）',
    `download_count` INT NOT NULL DEFAULT 0 COMMENT '下载次数',
    `is_free` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否免费下载',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_lesson_id` (`lesson_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程课件资料表';

-- =============================================
-- 6. 讲师信息表
-- =============================================
DROP TABLE IF EXISTS `teacher_info`;
CREATE TABLE `teacher_info` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '讲师ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（关联用户表）',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `teacher_title` VARCHAR(100) DEFAULT NULL COMMENT '职称/头衔',
    `avatar_url` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `introduction` TEXT COMMENT '个人简介',
    `expertise_area` VARCHAR(500) DEFAULT NULL COMMENT '擅长领域（多个用逗号分隔）',
    -- 统计数据
    `total_student_count` INT NOT NULL DEFAULT 0 COMMENT '累计学生数',
    `total_course_count` INT NOT NULL DEFAULT 0 COMMENT '课程数量',
    `average_rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '平均评分',
    `total_revenue` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计收益（元）',
    -- 认证信息
    `is_certified` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否认证讲师',
    `certification_info` VARCHAR(500) DEFAULT NULL COMMENT '认证信息',
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING-待审核，APPROVED-已通过，REJECTED-已拒绝，DISABLED-已禁用）',
    `audit_opinion` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_average_rating` (`average_rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='讲师信息表';

-- =============================================
-- 7. 课程讲师关联表
-- =============================================
DROP TABLE IF EXISTS `course_teacher_relation`;
CREATE TABLE `course_teacher_relation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `teacher_id` BIGINT NOT NULL COMMENT '讲师ID',
    `teacher_role` VARCHAR(20) NOT NULL DEFAULT 'MAIN' COMMENT '讲师角色（MAIN-主讲，ASSISTANT-助教）',
    `revenue_share_rate` DECIMAL(3,2) DEFAULT 0.00 COMMENT '收益分成比例（0-1）',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_course_teacher` (`course_id`, `teacher_id`),
    KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程讲师关联表';

-- =============================================
-- 8. 课程标签表
-- =============================================
DROP TABLE IF EXISTS `course_tag`;
CREATE TABLE `course_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `tag_name` VARCHAR(50) NOT NULL COMMENT '标签名称',
    `tag_type` VARCHAR(20) DEFAULT 'CUSTOM' COMMENT '标签类型（TECH-技术，LEVEL-难度，INDUSTRY-行业，CUSTOM-自定义）',
    `use_count` INT NOT NULL DEFAULT 0 COMMENT '使用次数',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tag_name` (`tag_name`),
    KEY `idx_tag_type` (`tag_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程标签表';

-- =============================================
-- 9. 课程标签关联表
-- =============================================
DROP TABLE IF EXISTS `course_tag_relation`;
CREATE TABLE `course_tag_relation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `tag_id` BIGINT NOT NULL COMMENT '标签ID',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_course_tag` (`course_id`, `tag_id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程标签关联表';

-- =============================================
-- 10. 课程公告表
-- =============================================
DROP TABLE IF EXISTS `course_announcement`;
CREATE TABLE `course_announcement` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `announcement_title` VARCHAR(200) NOT NULL COMMENT '公告标题',
    `announcement_content` TEXT NOT NULL COMMENT '公告内容',
    `is_top` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否置顶',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    -- 审计字段
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_is_top` (`is_top`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程公告表';
