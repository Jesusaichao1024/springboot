package entity;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 10:27
 * @PackageName util
 * @Project_Name tensquare_parent
 * @Description
 */
public class StatusCode {
    //成功
    public static final Integer OK = 20000;
    //失败
    public static final Integer ERROR = 20001;
    //登录用户名密码错误
    public static final Integer LOGINERROR = 20002;
    //权限不足
    public static final Integer ACCESSERROR = 20003;
    //远程调用失败
    public static final Integer REMOTEERROR = 20004;
    //重复操作
    public static final Integer REPERROR = 20005;
}
