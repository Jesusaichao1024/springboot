package com.tensquare.question.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Jesusaichao
 * @Date 2019/3/12
 * @Time 16:50
 * @PackageName com.tensquare.question.pojo
 * @Project_Name tensquare_parent
 * @Description
 */
@Entity
@Table(name = "tb_pl")
public class Pl implements Serializable {
    @Id
    private String problemid;
    @Id
    private String labelid;

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }
}
