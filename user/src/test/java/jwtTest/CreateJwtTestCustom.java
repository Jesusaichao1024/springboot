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
public class CreateJwtTestCustom {
    public static void main(String[] args) {
        //当前时间
        long now = System.currentTimeMillis();
        System.out.println("now = " + now);
        //过期时间 1分钟
        long exp = now + 1000 * 60;
        JwtBuilder builder = Jwts.builder().setId("8888")
                //面向用户
                .setSubject("物理")
                //设置签发时间
                .setIssuedAt(new Date())
                //签名秘钥
                .signWith(SignatureAlgorithm.HS256, "jesus")
                .setExpiration(new Date(exp))
                .claim("roles", "admin")
                .claim("logo","logo.png")
                ;

        System.out.println("builder = " + builder.compact());
    }
}
