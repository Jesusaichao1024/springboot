package entity;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 10:21
 * @PackageName entity
 * @Project_Name tensquare_parent
 */
public class Result {
    //状态码
    private Integer code;
    //返回的信息
    private String message;
    //返回数据
    private Object data;
    //是否执行成功
    private Boolean flag;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", flag=" + flag +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Result() {
    }

    public Result(Integer code, String message, Boolean flag) {
        this.code = code;
        this.message = message;
        this.flag = flag;
    }

    public Result(Integer code, String message, Object data, Boolean flag) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.flag = flag;
    }
}
