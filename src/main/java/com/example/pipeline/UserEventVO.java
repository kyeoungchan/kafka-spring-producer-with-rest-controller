package com.example.pipeline;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserEventVO {

    /* REST API 호출받은 시점에 메시지 값에 넣는 역할
    * 컨슈머에서 병렬처리되어 적재 시점이 달라지더라도 최종 적재된 데이터의 timestamp 값을 기준으로 호출 시간을 정렬할 수 있다.*/
    private String timestamp;

    // REST API를 호출받을 때 받을 수 있는 사용자 브라우저 종류
    private String userAgent;

    private String colorName;
    private String userName;
}
