package com.sureal.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 10:48
 * @PackageName com.sureal.pojo
 * @Project_Name tensquare_parent
 * @Description
 */
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String id;
    private String labelname;
    private String state;
    private Integer count;
    private String recommend;
    private Integer fans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", labelname='" + labelname + '\'' +
                ", state='" + state + '\'' +
                ", count=" + count +
                ", recommend='" + recommend + '\'' +
                ", fans=" + fans +
                '}';
    }
}
