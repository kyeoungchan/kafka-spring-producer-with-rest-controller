package com.example.pipeline;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // REST API를 다른 도메인에서도 호출할 수 있도록 CORS 설정
public class ProduceController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/api/select")
    public void selectColor(
            // 브라우저에서 호출시 자동으로 들어가는 헤더 값이다.
            @RequestHeader("user-agent") String userAgentName,
            @RequestParam(value = "color") String colorName,
            @RequestParam(value = "user") String userName
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now = new Date();
        Gson gson = new Gson();
        UserEventVO userEventVO = new UserEventVO(simpleDateFormat.format(now), userAgentName, colorName, userName);
        String jsonColorLog = gson.toJson(userEventVO);
        kafkaTemplate.send("select-color", jsonColorLog)
                .thenAccept(result -> log.info("result: {}", result))
                .exceptionally(ex -> {
                    log.error(ex.getMessage(), ex);
                    return null;
                });
    }
}
