package org.training.cloud.course.entity.material;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "course_material", autoResultMap = true)
@Accessors(chain = true)
public class CourseMaterial extends BaseDO {
    /**
     * 资料ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 课时ID（为空表示课程资料）
     */
    private Long lessonId;
    /**
     * 资料名称
     */
    private String materialName;
    /**
     * 资料类型（PPT, PDF, WORD, EXCEL, CODE, OTHER）
     */
    private String materialType;
    /**
     * 资料URL
     */
    private String materialUrl;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 下载次数
     */
    private Integer downloadCount;
    /**
     * 是否免费下载（0-否，1-是）
     */
    private Boolean isFree;
    /**
     * 排序序号
     */
    private Integer sort;

}
