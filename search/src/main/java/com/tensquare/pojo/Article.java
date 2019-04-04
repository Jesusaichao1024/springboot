package com.tensquare.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @Author:hhh
 * @DATE:2019/3/15
 * @TIME:15:15
 * @DAY:15
 * @PROJECT_NAME:tensquare_parent
 * @PACKAGE_NAME:com.tensquare.pojo
 */
@Document(indexName = "tensquare",type = "article")
public class Article implements Serializable {
    @Id
    private String id;
    /**
     * index s是否索引
     * analyzer: ik分词器保存数据的方式
     * searchAnalyzer:ik分词器查询时数据的方式
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;
    private String state;

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
