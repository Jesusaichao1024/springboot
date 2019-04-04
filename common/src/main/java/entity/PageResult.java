package entity;

import java.util.List;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 10:25
 * @PackageName entity
 * @Project_Name tensquare_parent
 * @Description 分页数据的实体类
 */
public class PageResult<T> {
    //总记录数
    private Long total;
    //带有分页的结果集
    private List<T> rows;

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
