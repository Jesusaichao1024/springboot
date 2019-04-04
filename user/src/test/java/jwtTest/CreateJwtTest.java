package jwtTest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 15:27
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: jwtTest
 */
public class CreateJwtTest {
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder().setId("777")
                                            //面向用户
                                            .setSubject("数学")
                                            //设置签发时间
                                            .setIssuedAt(new Date())
                                            //签名秘钥
                                            .signWith(SignatureAlgorithm.HS256, "jesus");
        System.out.println("builder = " + builder.compact());
    }
}
