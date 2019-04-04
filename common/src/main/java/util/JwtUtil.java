package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 16:47
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: util
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    /**
     * 秘钥盐值
     */
    private String key;
    /**
     *一个小时
     */
    private Long ttl;

    @Override
    public String toString() {
        return "JwtUtil{" +
                "key='" + key + '\'' +
                ", ttl=" + ttl +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成jwt
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public  String createJwt(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析jwt
     * @param jstStr
     * @return
     */
    public Claims parseJWT(String jstStr) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jstStr).getBody();
    }
}
