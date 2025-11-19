# Training-Cloud 数据库设计总览

## 📋 文档说明

本文档提供Training-Cloud在线教育平台的完整数据库设计总览，包括三大核心领域的表结构设计、ER关系图和设计说明。

**版本**: V2.0
**更新时间**: 2025-01-17
**设计原则**: 基于DDD（领域驱动设计）进行领域划分

---

## 🎯 设计理念

### 核心设计原则

1. **领域驱动设计（DDD）**
   - 按照业务领域划分表结构
   - 每个领域独立设计，清晰的边界上下文
   - 支持微服务架构，便于服务拆分

2. **统一审计机制**
   - 所有业务表的Entity继承 `BaseDO`
   - 自动记录创建时间、修改时间、操作人
   - 统一的逻辑删除机制（`@TableLogic`）

3. **高性能设计**
   - 合理的冗余字段设计（如统计数据）
   - 完善的索引设计
   - 分离冷热数据（如日志表）

4. **可扩展性**
   - 使用JSON字段存储扩展数据
   - 预留扩展字段
   - 支持多种业务场景

---

## 📊 领域划分

### 三大核心领域

```
├── 课程域 (Course Domain)           - 10张表
│   ├── 课程管理
│   ├── 章节课时
│   ├── 分类体系
│   ├── 讲师管理
│   └── 课程资料
│
├── 学习域 (Learning Domain)         - 14张表
│   ├── 学习进度跟踪
│   ├── 笔记系统
│   ├── 问答社区
│   ├── 课程评价
│   └── 学习证书
│
└── 考试域 (Exam Domain)             - 10张表
    ├── 题库管理
    ├── 试卷管理
    ├── 答题记录
    ├── 错题本
    └── 成绩统计
```

**总计**: 34张核心业务表

---

## 🗂️ 表结构总览

### 一、课程域 (Course Domain)

| 序号 | 表名 | 说明 | Entity继承BaseDO |
|------|------|------|-----------------|
| 1 | `course_category` | 课程分类表（树形结构） | ✅ |
| 2 | `course` | 课程表（核心表） | ✅ |
| 3 | `course_chapter` | 课程章节表 | ✅ |
| 4 | `course_lesson` | 课程课时表 | ✅ |
| 5 | `course_material` | 课程课件资料表 | ✅ |
| 6 | `teacher_info` | 讲师信息表 | ✅ |
| 7 | `course_teacher_relation` | 课程讲师关联表 | ❌ (关系表) |
| 8 | `course_tag` | 课程标签表 | ✅ |
| 9 | `course_tag_relation` | 课程标签关联表 | ❌ (关系表) |
| 10 | `course_announcement` | 课程公告表 | ✅ |

**核心特性**:
- 支持树形分类（最多3级）
- 课程多讲师支持（主讲+助教）
- 课程标签系统（技术、难度、行业分类）
- 完善的课程统计数据（学习人数、评分、收藏数）

---

### 二、学习域 (Learning Domain)

| 序号 | 表名 | 说明 | Entity继承BaseDO |
|------|------|------|-----------------|
| 1 | `learning_progress` | 学习进度表（核心表） | ✅ |
| 2 | `user_course_learning` | 课程学习统计表 | ✅ |
| 3 | `course_note` | 课程笔记表 | ✅ |
| 4 | `note_like` | 笔记点赞表 | ❌ (关系表) |
| 5 | `note_comment` | 笔记评论表 | ✅ |
| 6 | `course_question` | 课程问答表 | ✅ |
| 7 | `question_answer` | 问答回复表 | ✅ |
| 8 | `question_answer_like` | 问答点赞表 | ❌ (关系表) |
| 9 | `course_review` | 课程评价表 | ✅ |
| 10 | `review_reply` | 评价回复表 | ✅ |
| 11 | `course_collect` | 课程收藏表 | ❌ (关系表) |
| 12 | `collect_folder` | 收藏夹表 | ✅ |
| 13 | `learning_certificate` | 学习证书表 | ✅ |
| 14 | `user_behavior_log` | 用户行为日志表 | ❌ (日志表) |

**核心特性**:
- 精确的学习进度跟踪（断点续播）
- 时间轴笔记系统（关联视频时间点）
- 类StackOverflow的问答社区
- 多维度评分系统（内容、讲师、难度）
- 完整的用户行为日志

---

### 三、考试域 (Exam Domain)

| 序号 | 表名 | 说明 | Entity继承BaseDO |
|------|------|------|-----------------|
| 1 | `exam_question` | 题库表（核心表） | ✅ |
| 2 | `exam_paper` | 试卷/作业表 | ✅ |
| 3 | `exam_paper_question` | 试卷题目关联表 | ❌ (关系表) |
| 4 | `exam_answer_record` | 答题记录表 | ✅ |
| 5 | `exam_answer_detail` | 答题详情表 | ✅ |
| 6 | `exam_wrong_question` | 错题本表 | ✅ |
| 7 | `exam_practice_record` | 练习记录表 | ✅ |
| 8 | `exam_grade_statistics` | 成绩统计表 | ✅ |
| 9 | `question_collect` | 题目收藏表 | ❌ (关系表) |
| 10 | `exam_leaderboard` | 考试排行榜表 | ❌ (关系表) |

**核心特性**:
- 6种题型支持（单选、多选、判断、填空、简答、论述）
- 灵活的试卷配置（限时、限次、随机出题）
- 自动批改（客观题）+ 人工批改（主观题）
- 错题本与知识点关联
- 完善的成绩统计与排名

---

## 🔗 核心ER关系图

### 1. 课程域ER关系

```
┌──────────────────┐
│  course_category │ (1:N自关联，树形结构)
│  (课程分类)      │
└────────┬─────────┘
         │ 1:N
         ▼
┌──────────────────┐         ┌──────────────────┐
│     course       │ N:N     │   teacher_info   │
│     (课程)       │◄───────►│    (讲师)        │
└────────┬─────────┘         └──────────────────┘
         │ 1:N              (通过course_teacher_relation关联)
         ▼
┌──────────────────┐
│  course_chapter  │
│   (章节)         │
└────────┬─────────┘
         │ 1:N
         ▼
┌──────────────────┐
│  course_lesson   │
│   (课时)         │
└──────────────────┘
```

**关系说明**:
- `course_category` → `course_category`: 1:N (自关联，支持3级分类)
- `course_category` → `course`: 1:N (一个分类包含多个课程)
- `course` → `course_chapter`: 1:N (一个课程包含多个章节)
- `course_chapter` → `course_lesson`: 1:N (一个章节包含多个课时)
- `course` ↔ `teacher_info`: N:N (一个课程可以有多个讲师，一个讲师可以教多个课程)

---

### 2. 学习域ER关系

```
┌──────────────────┐
│      User        │ (用户表，来自用户域)
└────────┬─────────┘
         │
         ├─── 1:N ───► learning_progress (学习进度)
         │
         ├─── 1:N ───► user_course_learning (课程统计)
         │
         ├─── 1:N ───► course_note (笔记)
         │                   │
         │                   ├─ 1:N ─► note_comment (评论)
         │                   └─ N:N ─► note_like (点赞)
         │
         ├─── 1:N ───► course_question (提问)
         │                   │
         │                   └─ 1:N ─► question_answer (回答)
         │
         ├─── 1:N ───► course_review (评价)
         │                   │
         │                   └─ 1:N ─► review_reply (讲师回复)
         │
         ├─── 1:N ───► course_collect (收藏)
         │                   │
         │                   └─ N:1 ─► collect_folder (收藏夹)
         │
         └─── 1:N ───► learning_certificate (证书)
```

**关系说明**:
- 所有学习相关表都关联到用户和课程
- `learning_progress`: 精确到课时的学习进度
- `user_course_learning`: 课程维度的学习统计汇总
- 笔记、问答支持多级回复和点赞机制
- 评价支持多维度评分（内容、讲师、难度）

---

### 3. 考试域ER关系

```
┌──────────────────┐
│  exam_question   │ (题库)
│    (题目)        │
└────────┬─────────┘
         │
         │ N:N (通过exam_paper_question)
         ▼
┌──────────────────┐
│   exam_paper     │ (试卷)
│   (试卷/作业)    │
└────────┬─────────┘
         │ 1:N
         ▼
┌──────────────────┐
│ exam_answer_     │ (答题记录)
│     record       │
└────────┬─────────┘
         │ 1:N
         ▼
┌──────────────────┐
│ exam_answer_     │ (答题详情)
│     detail       │
└──────────────────┘

用户答错 ──────► exam_wrong_question (错题本)
                         │
                         └─── 关联 ───► exam_question
```

**关系说明**:
- `exam_question` ↔ `exam_paper`: N:N (一个试卷包含多个题目，一个题目可以被多个试卷使用)
- `exam_paper` → `exam_answer_record`: 1:N (一份试卷有多个答题记录)
- `exam_answer_record` → `exam_answer_detail`: 1:N (一次答题有多个题目详情)
- `exam_wrong_question`: 记录用户错题，支持针对性练习

---

## 📐 BaseDO设计说明

### BaseDO定义

```java
/**
 * 基础实体对象
 * 所有业务表的Entity都继承此类
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public abstract class BaseDO implements Serializable {

    /**
     * 创建时间
     * @TableField(fill = FieldFill.INSERT)
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     * @TableField(fill = FieldFill.INSERT_UPDATE)
     */
    private LocalDateTime gmtModified;

    /**
     * 创建者
     * @TableField(fill = FieldFill.INSERT)
     */
    private String createOperator;

    /**
     * 修改者
     * @TableField(fill = FieldFill.INSERT_UPDATE)
     */
    private String modifiedOperator;

    /**
     * 是否删除
     * @TableLogic
     */
    private Boolean deleteState;
}
```

### 继承规则

**✅ 需要继承BaseDO的表**:
- 所有核心业务表
- 需要审计功能的表
- 需要逻辑删除的表

**❌ 不继承BaseDO的表**:
- 纯关系表（如：点赞表、标签关联表）
- 日志表（如：用户行为日志）
- 统计表（定时任务生成）

### 示例

```java
// ✅ 正确示例：课程表继承BaseDO
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "course", autoResultMap = true)
@Accessors(chain = true)
public class Course extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String courseName;
    private String description;
    // ... 其他业务字段
    // 无需定义审计字段，继承自BaseDO
}

// ❌ 错误示例：关系表不应继承BaseDO
@Data
@TableName(value = "course_tag_relation")
public class CourseTagRelation {  // 不继承BaseDO
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long tagId;
    private LocalDateTime gmtCreate;  // 只需要创建时间
}
```

---

## 🎨 索引设计原则

### 索引类型

1. **主键索引**: 所有表都有自增主键 `id`
2. **唯一索引**: 保证数据唯一性（如：用户课程关系、证书编号）
3. **普通索引**: 提升查询性能（如：外键、状态字段、时间字段）
4. **联合索引**: 多字段组合查询（如：用户+课程、用户+时间）

### 索引命名规范

- 主键：`PRIMARY KEY`
- 唯一索引：`uk_{字段名}` (unique key)
- 普通索引：`idx_{字段名}` (index)

### 示例

```sql
-- 主键索引
PRIMARY KEY (`id`)

-- 唯一索引
UNIQUE KEY `uk_user_course` (`user_id`, `course_id`)
UNIQUE KEY `uk_certificate_no` (`certificate_no`)

-- 普通索引
KEY `idx_course` (`course_id`)
KEY `idx_user_time` (`user_id`, `behavior_time`)
KEY `idx_is_published` (`is_published`)
```

---

## 📈 冗余字段设计

为了提升查询性能，部分表设计了冗余字段，通过定时任务或触发器更新。

### 课程表冗余字段

```sql
-- 统计数据（冗余字段，定期更新）
`student_count` INT NOT NULL DEFAULT 0 COMMENT '学习人数',
`view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
`collect_count` INT NOT NULL DEFAULT 0 COMMENT '收藏次数',
`average_rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '平均评分',
`review_count` INT NOT NULL DEFAULT 0 COMMENT '评价数量',
```

**更新策略**:
- 实时更新：通过应用层计数器（如Redis）
- 定时更新：每小时/每天通过定时任务同步
- 触发更新：重要操作后立即更新

### 分类表冗余字段

```sql
`course_count` INT NOT NULL DEFAULT 0 COMMENT '课程数量（冗余字段）',
```

**更新时机**:
- 课程创建/删除时更新
- 课程分类变更时更新

---

## 🔧 JSON字段使用

部分字段使用JSON类型存储复杂数据结构，提升扩展性。

### 使用场景

| 字段 | 表 | 说明 |
|------|-----|------|
| `question_options` | `exam_question` | 题目选项（如：[{"key":"A","value":"选项A"}]） |
| `answers` | `exam_answer_record` | 答题内容（如：[{"questionId":1,"answer":"A"}]） |
| `note_images` | `course_note` | 笔记图片数组 |
| `question_images` | `course_question` | 问题图片数组 |
| `behavior_data` | `user_behavior_log` | 行为扩展数据 |

### 示例

```json
// 题目选项
{
  "question_options": [
    {"key": "A", "value": "Spring Framework"},
    {"key": "B", "value": "Spring Boot"},
    {"key": "C", "value": "Spring Cloud"},
    {"key": "D", "value": "Spring Data"}
  ]
}

// 答题内容
{
  "answers": [
    {
      "questionId": 1,
      "answer": "A",
      "isCorrect": true,
      "score": 5,
      "answerTime": 30
    },
    {
      "questionId": 2,
      "answer": "B,C",
      "isCorrect": false,
      "score": 0,
      "answerTime": 45
    }
  ]
}
```

---

## 🚀 性能优化建议

### 1. 分表策略

**行为日志表** (`user_behavior_log`):
- 按月分表：`user_behavior_log_202501`, `user_behavior_log_202502`
- 历史数据归档

**答题记录表** (`exam_answer_record`):
- 数据量大时按年度分表
- 考虑使用ClickHouse存储分析数据

### 2. 读写分离

- 主库：写操作 + 实时查询
- 从库：统计查询 + 报表查询
- 缓存：热点数据（课程详情、讲师信息）

### 3. 缓存策略

```
Redis缓存：
├── 课程详情：course:{id}         (过期时间：1小时)
├── 学习进度：progress:{userId}:{courseId}  (过期时间：5分钟)
├── 排行榜：leaderboard:{paperId}   (过期时间：10分钟)
└── 热门课程：hot:courses           (过期时间：30分钟)
```

### 4. 数据归档

定期归档冷数据：
- 3个月前的行为日志
- 1年前的答题记录
- 已关闭的试卷数据

---

## 📚 SQL文件清单

| 文件名 | 说明 | 表数量 |
|--------|------|--------|
| `01_course_domain.sql` | 课程域表结构 | 10张 |
| `02_learning_domain.sql` | 学习域表结构 | 14张 |
| `03_exam_domain.sql` | 考试域表结构 | 10张 |

---

## ✅ 设计特点总结

### 1. 清晰的领域划分
- 基于DDD设计理念
- 每个领域独立完整
- 支持微服务拆分

### 2. 完善的审计机制
- 统一继承BaseDO
- 自动记录操作时间和操作人
- 逻辑删除保证数据安全

### 3. 高性能设计
- 合理的冗余字段
- 完善的索引设计
- 支持分表分库

### 4. 良好的扩展性
- JSON字段存储扩展数据
- 预留扩展字段
- 支持多种业务场景

### 5. 丰富的业务功能
- 学习进度精确跟踪
- 完整的笔记问答系统
- 灵活的考试评测体系
- 多维度数据统计

---

## 📞 技术支持

如有疑问，请参考：
- `.claude/guidelines.md` - 项目开发规范
- 各领域SQL文件中的详细注释

---

**文档版本**: V2.0

