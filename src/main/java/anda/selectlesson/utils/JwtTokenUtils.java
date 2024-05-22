package anda.selectlesson.utils;

import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.UserRepo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtils {

    private static UserRepo userRepoStatus;
    @Resource
    private UserRepo userRepo;
    @PostConstruct
    public void setUserService() {
        userRepoStatus = userRepo;
    }

    public static String getToken(String userId, String password) {
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(password));
    }

    public static User getCurrentUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        InputStream inputStream = request.getInputStream();

        // 将 JSON 字符串解析为 Map
        Map<String, Object> jsonMap = objectMapper.readValue(inputStream, Map.class);

        // 从 Map 中获取 token
        String token = (String) jsonMap.get("token");
        if (StrUtil.isBlank(token)) {
            return null;
        }
        String userId = JWT.decode(token).getAudience().get(0);
        return userRepoStatus.getUserById(Long.parseLong(userId));
    }
}
