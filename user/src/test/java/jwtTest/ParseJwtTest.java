package jwtTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 16:10
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: jwtTest
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoi54mp55CGIiwiaWF0IjoxNTUyOTg1MTUwLCJleHAiOjE1NTI5ODUyMTAsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoibG9nby5wbmcifQ.OJFtxdaFXKJa6zwGEhsI9EiBQwGz4eYOW97qMfxn4BA";
        Claims claims = Jwts.parser().setSigningKey("jesus").parseClaimsJws(token).getBody();
        System.out.println("claimsId = " + claims.getId());
        System.out.println("subject = " + claims.getSubject());
        System.out.println("subject = " + claims.get("roles"));
        System.out.println("subject = " + claims.get("logo"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("签发时间:  " + format.format(claims.getIssuedAt()));
        System.out.println("过期时间:  " + format.format(claims.getExpiration()));
        System.out.println("当前时间 = " + format.format(new Date()));
    }
}
