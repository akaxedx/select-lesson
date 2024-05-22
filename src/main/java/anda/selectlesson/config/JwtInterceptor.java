package anda.selectlesson.config;

import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.UserRepo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.InputStream;
import java.util.Map;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    UserRepo userRepo;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        try {
            // 获取请求体的输入流

            InputStream inputStream = new RequestWrapper(request).getInputStream();
            // 将 JSON 字符串解析为 Map
            Map<String, Object> jsonMap = objectMapper.readValue(inputStream, Map.class);

            // 从 Map 中获取 token
            String token = (String) jsonMap.get("token");

            if (token.isBlank()) {
                throw new RuntimeException("token错误，重新登录");
            }
            String userId = JWT.decode(token).getAudience().get(0);
            User user = userRepo.getUserById(Long.parseLong(userId));
            if (user.getUsername().isBlank()) {
                throw new RuntimeException("token错误，重新登录");
            }
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("token失效，重新登陆");
        }
    }
}
