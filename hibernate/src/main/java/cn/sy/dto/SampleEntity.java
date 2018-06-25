package cn.sy.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/21.
 */
@Entity
public class SampleEntity {
    /**
     * id
     *   uuid
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /**
     * 名称
     *   可变长字符串, not null
     */
    @Column(nullable=false)
    private String seName;

    /**
     * 备注
     *   需要指定长度 1020
     */
    @Column(length=1020)
    private String seComment;

    /**
     * 删除标记
     *   布尔
     */
    private boolean seDelete;

    /**
     * 日期
     */
    private Date seDate;

    /**
     * 数量
     *   整数
     */
    private int seAmount;

    /**
     * 价格
     *   带小数
     */
    private BigDecimal sePrice;

    /**
     * 计量工时
     *   需要指定精度 NUMBER	5,2
     */
    @Column(precision=5, scale=2)
    private BigDecimal seDoHour;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
