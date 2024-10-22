package com.univ.tracedin.api.alert;

import java.util.List;

import com.univ.tracedin.api.alert.dto.AppendAlertMethodRequest;
import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.domain.alert.AlertMethod;
import com.univ.tracedin.domain.alert.AlertMethodStatus;
import com.univ.tracedin.domain.auth.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "알림 API")
public interface AlertApiDocs {

    @Operation(summary = "알림 수단 추가", description = "이메일, 핸드폰, 슬랙 등의 알림수단을 추가합니다.")
    Response<Void> appendAlertMethod(UserPrincipal currentUser, AppendAlertMethodRequest request);

    @Operation(summary = "알림 수단 조회", description = "프로젝트에 등록된 모든 알림수단을 조회합니다.")
    Response<List<AlertMethod>> getAllAlertMethods(UserPrincipal currentUser, Long projectId);

    @Operation(summary = "알림 수단 상태 변경", description = "알림수단을 활성화/비활성화 합니다.")
    Response<Void> changeAlertMethod(
            UserPrincipal currentUser, Long alertMethodId, AlertMethodStatus status);

    @Operation(summary = "알림 수단 삭제", description = "알림수단을 삭제합니다.")
    Response<Void> removeAlertMethod(UserPrincipal currentUser, Long alertMethodId);
}
