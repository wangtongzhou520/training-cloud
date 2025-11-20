-- =============================================
-- 考试域 (Exam Domain) 数据库表结构设计
-- =============================================
-- 说明：考试域负责题库、试卷、考试、答题记录等功能
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
-- 1. 题库表（核心表）
-- =============================================
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '题目ID',
    `course_id` BIGINT DEFAULT NULL COMMENT '课程ID（通用题库时为空）',
    `chapter_id` BIGINT DEFAULT NULL COMMENT '章节ID',
    `lesson_id` BIGINT DEFAULT NULL COMMENT '课时ID',
    -- 题目类型
    `question_type` VARCHAR(20) NOT NULL COMMENT '题型（SINGLE_CHOICE-单选，MULTIPLE_CHOICE-多选，TRUE_FALSE-判断，FILL_BLANK-填空，SHORT_ANSWER-简答，ESSAY-论述）',
    `difficulty_level` INT NOT NULL DEFAULT 2 COMMENT '难度级别（1-简单，2-中等，3-困难）',
    -- 题目内容
    `question_content` TEXT NOT NULL COMMENT '题目内容',
    `question_images` JSON DEFAULT NULL COMMENT '题目图片（JSON数组）',
    `question_options` JSON DEFAULT NULL COMMENT '选项（JSON格式，如：[{"key":"A","value":"选项A"},{"key":"B","value":"选项B"}]）',
    `correct_answer` TEXT NOT NULL COMMENT '正确答案（单选：A，多选：A,B,C，判断：TRUE/FALSE，填空/简答：文本）',
    `answer_analysis` TEXT DEFAULT NULL COMMENT '答案解析',
    `answer_tips` VARCHAR(500) DEFAULT NULL COMMENT '答题提示',
    -- 题目属性
    `default_score` DECIMAL(5,2) NOT NULL DEFAULT 1.00 COMMENT '默认分值',
    `knowledge_point` VARCHAR(200) DEFAULT NULL COMMENT '知识点',
    -- 统计数据
    `use_count` INT NOT NULL DEFAULT 0 COMMENT '使用次数',
    `correct_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '正确率（百分比）',
    `avg_answer_time` INT DEFAULT NULL COMMENT '平均答题时长（秒）',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_chapter` (`chapter_id`),
    KEY `idx_type_difficulty` (`question_type`, `difficulty_level`),
    KEY `idx_knowledge_point` (`knowledge_point`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题库表';

-- =============================================
-- 2. 试卷/作业表
-- =============================================
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `chapter_id` BIGINT DEFAULT NULL COMMENT '章节ID（章节作业）',
    `teacher_id` BIGINT NOT NULL COMMENT '出题教师ID',
    -- 试卷基本信息
    `paper_name` VARCHAR(200) NOT NULL COMMENT '试卷名称',
    `paper_type` VARCHAR(20) NOT NULL COMMENT '类型（HOMEWORK-作业，QUIZ-小测验，MIDTERM-期中考试，FINAL-期末考试，MOCK-模拟考试）',
    `paper_description` TEXT DEFAULT NULL COMMENT '试卷说明',
    `total_score` DECIMAL(5,2) NOT NULL DEFAULT 100.00 COMMENT '总分',
    `pass_score` DECIMAL(5,2) NOT NULL DEFAULT 60.00 COMMENT '及格分',
    -- 考试设置
    `duration_minutes` INT DEFAULT 0 COMMENT '考试时长（分钟，0表示不限时）',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `attempt_limit` INT NOT NULL DEFAULT 1 COMMENT '答题次数限制（0表示不限）',
    -- 试卷配置
    `is_random_question` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否随机出题',
    `is_random_option` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否随机选项',
    `is_show_answer` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '提交后是否显示答案',
    `is_show_analysis` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否显示解析',
    `is_auto_submit` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '时间到是否自动提交',
    `is_anti_cheat` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否开启防作弊（禁止复制、切屏检测等）',
    -- 发布状态
    `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '状态（DRAFT-草稿，PUBLISHED-已发布，CLOSED-已关闭）',
    `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
    -- 统计数据
    `total_question_count` INT NOT NULL DEFAULT 0 COMMENT '题目总数',
    `submit_count` INT NOT NULL DEFAULT 0 COMMENT '提交人数',
    `avg_score` DECIMAL(5,2) DEFAULT NULL COMMENT '平均分',
    `pass_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '及格率（百分比）',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_chapter` (`chapter_id`),
    KEY `idx_teacher` (`teacher_id`),
    KEY `idx_status` (`status`),
    KEY `idx_time` (`start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试卷表';

-- =============================================
-- 3. 试卷题目关联表（关系表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `exam_paper_question`;
CREATE TABLE `exam_paper_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `question_order` INT NOT NULL COMMENT '题目顺序',
    `question_score` DECIMAL(5,2) NOT NULL COMMENT '题目分值',
    `question_group` VARCHAR(50) DEFAULT NULL COMMENT '题目分组（如：第一大题、第二大题）',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_paper` (`paper_id`),
    KEY `idx_question` (`question_id`),
    KEY `idx_order` (`paper_id`, `question_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试卷题目关联表';

-- =============================================
-- 4. 答题记录表
-- =============================================
DROP TABLE IF EXISTS `exam_answer_record`;
CREATE TABLE `exam_answer_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `attempt_number` INT NOT NULL DEFAULT 1 COMMENT '第几次答题',
    -- 答题详情
    `answers` JSON NOT NULL COMMENT '答题内容（JSON格式，如：[{"questionId":1,"answer":"A","isCorrect":true,"score":5}]）',
    `total_score` DECIMAL(5,2) DEFAULT NULL COMMENT '总得分',
    `objective_score` DECIMAL(5,2) DEFAULT NULL COMMENT '客观题得分（自动批改）',
    `subjective_score` DECIMAL(5,2) DEFAULT NULL COMMENT '主观题得分（人工批改）',
    -- 答题状态
    `submit_status` VARCHAR(20) NOT NULL DEFAULT 'ANSWERING' COMMENT '提交状态（ANSWERING-答题中，SUBMITTED-已提交，GRADED-已批改）',
    `is_passed` BOOLEAN DEFAULT NULL COMMENT '是否通过',
    -- 时间记录
    `start_time` DATETIME NOT NULL COMMENT '开始答题时间',
    `submit_time` DATETIME DEFAULT NULL COMMENT '提交时间',
    `duration_seconds` INT DEFAULT NULL COMMENT '答题耗时（秒）',
    `grade_time` DATETIME DEFAULT NULL COMMENT '批改时间',
    `graded_by` BIGINT DEFAULT NULL COMMENT '批改人ID',
    -- 统计数据
    `correct_count` INT DEFAULT 0 COMMENT '正确题数',
    `wrong_count` INT DEFAULT 0 COMMENT '错误题数',
    `accuracy_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '正确率（百分比）',
    -- 批改意见
    `teacher_comment` TEXT DEFAULT NULL COMMENT '教师评语',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_operator` VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    `modified_operator` VARCHAR(50) DEFAULT NULL COMMENT '修改者',
    `delete_state` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_paper_user_attempt` (`paper_id`, `user_id`, `attempt_number`),
    KEY `idx_user` (`user_id`),
    KEY `idx_paper` (`paper_id`),
    KEY `idx_status` (`submit_status`),
    KEY `idx_submit_time` (`submit_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答题记录表';

-- =============================================
-- 5. 答题详情表（每题详情）
-- =============================================
DROP TABLE IF EXISTS `exam_answer_detail`;
CREATE TABLE `exam_answer_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '详情ID',
    `record_id` BIGINT NOT NULL COMMENT '答题记录ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `paper_question_id` BIGINT NOT NULL COMMENT '试卷题目关联ID',
    -- 答题内容
    `user_answer` TEXT DEFAULT NULL COMMENT '用户答案',
    `correct_answer` TEXT NOT NULL COMMENT '正确答案',
    `is_correct` BOOLEAN DEFAULT NULL COMMENT '是否正确（客观题自动判断）',
    -- 得分
    `question_score` DECIMAL(5,2) NOT NULL COMMENT '题目分值',
    `actual_score` DECIMAL(5,2) DEFAULT NULL COMMENT '实际得分',
    -- 批改信息
    `is_graded` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已批改',
    `graded_by` BIGINT DEFAULT NULL COMMENT '批改人ID',
    `graded_time` DATETIME DEFAULT NULL COMMENT '批改时间',
    `teacher_comment` VARCHAR(500) DEFAULT NULL COMMENT '批改意见',
    -- 答题时长
    `answer_time_seconds` INT DEFAULT NULL COMMENT '答题耗时（秒）',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_record` (`record_id`),
    KEY `idx_question` (`question_id`),
    KEY `idx_is_correct` (`is_correct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答题详情表';

-- =============================================
-- 6. 错题本表
-- =============================================
DROP TABLE IF EXISTS `exam_wrong_question`;
CREATE TABLE `exam_wrong_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `course_id` BIGINT DEFAULT NULL COMMENT '课程ID',
    -- 错题记录
    `wrong_count` INT NOT NULL DEFAULT 1 COMMENT '错误次数',
    `last_wrong_time` DATETIME NOT NULL COMMENT '最后错误时间',
    `last_wrong_answer` TEXT DEFAULT NULL COMMENT '最后错误答案',
    -- 掌握情况
    `is_mastered` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已掌握',
    `mastered_time` DATETIME DEFAULT NULL COMMENT '掌握时间',
    `practice_count` INT NOT NULL DEFAULT 0 COMMENT '练习次数',
    `correct_count` INT NOT NULL DEFAULT 0 COMMENT '正确次数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_question` (`user_id`, `question_id`),
    KEY `idx_user_course` (`user_id`, `course_id`),
    KEY `idx_is_mastered` (`is_mastered`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错题本表';

-- =============================================
-- 7. 练习记录表（刷题记录）
-- =============================================
DROP TABLE IF EXISTS `exam_practice_record`;
CREATE TABLE `exam_practice_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `practice_type` VARCHAR(20) NOT NULL COMMENT '练习类型（CHAPTER-章节练习，WRONG-错题练习，RANDOM-随机练习，DAILY-每日一练）',
    `course_id` BIGINT DEFAULT NULL COMMENT '课程ID',
    `chapter_id` BIGINT DEFAULT NULL COMMENT '章节ID',
    -- 练习统计
    `total_question_count` INT NOT NULL DEFAULT 0 COMMENT '题目总数',
    `correct_count` INT NOT NULL DEFAULT 0 COMMENT '正确数',
    `wrong_count` INT NOT NULL DEFAULT 0 COMMENT '错误数',
    `accuracy_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '正确率（百分比）',
    `total_duration_seconds` INT DEFAULT NULL COMMENT '总耗时（秒）',
    -- 练习详情
    `practice_data` JSON DEFAULT NULL COMMENT '练习详情（JSON格式）',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_type` (`practice_type`),
    KEY `idx_create_time` (`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='练习记录表';

-- =============================================
-- 8. 成绩统计表（用户课程维度）
-- =============================================
DROP TABLE IF EXISTS `exam_grade_statistics`;
CREATE TABLE `exam_grade_statistics` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '统计ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    -- 考试统计
    `total_exam_count` INT NOT NULL DEFAULT 0 COMMENT '参加考试总数',
    `passed_exam_count` INT NOT NULL DEFAULT 0 COMMENT '通过考试数',
    `failed_exam_count` INT NOT NULL DEFAULT 0 COMMENT '未通过考试数',
    `avg_score` DECIMAL(5,2) DEFAULT NULL COMMENT '平均分',
    `highest_score` DECIMAL(5,2) DEFAULT NULL COMMENT '最高分',
    `lowest_score` DECIMAL(5,2) DEFAULT NULL COMMENT '最低分',
    -- 练习统计
    `total_practice_count` INT NOT NULL DEFAULT 0 COMMENT '练习次数',
    `total_question_count` INT NOT NULL DEFAULT 0 COMMENT '练习题目数',
    `correct_question_count` INT NOT NULL DEFAULT 0 COMMENT '正确题目数',
    `avg_accuracy_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '平均正确率（百分比）',
    -- 错题统计
    `wrong_question_count` INT NOT NULL DEFAULT 0 COMMENT '错题总数',
    `mastered_question_count` INT NOT NULL DEFAULT 0 COMMENT '已掌握题目数',
    -- 排名信息
    `course_rank` INT DEFAULT NULL COMMENT '课程排名',
    `total_student_count` INT DEFAULT NULL COMMENT '总学生数',
    -- ========== BaseDO审计字段 ==========
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_course` (`user_id`, `course_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_course` (`course_id`),
    KEY `idx_avg_score` (`avg_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩统计表';

-- =============================================
-- 9. 题目收藏表（关系表，不继承BaseDO）
-- =============================================
DROP TABLE IF EXISTS `question_collect`;
CREATE TABLE `question_collect` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `collect_reason` VARCHAR(200) DEFAULT NULL COMMENT '收藏原因',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_question` (`user_id`, `question_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目收藏表';

-- =============================================
-- 10. 成绩公示表（排行榜）
-- =============================================
DROP TABLE IF EXISTS `exam_leaderboard`;
CREATE TABLE `exam_leaderboard` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `rank_number` INT NOT NULL COMMENT '排名',
    `total_score` DECIMAL(5,2) NOT NULL COMMENT '总分',
    `accuracy_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '正确率',
    `duration_seconds` INT DEFAULT NULL COMMENT '答题耗时（秒）',
    `is_show` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否显示（用户可选择隐藏）',
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_paper_user` (`paper_id`, `user_id`),
    KEY `idx_paper_rank` (`paper_id`, `rank_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='考试排行榜表';
