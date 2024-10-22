package com.univ.tracedin.api.alert;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.alert.dto.AppendAlertMethodRequest;
import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.domain.alert.AlertMethod;
import com.univ.tracedin.domain.alert.AlertMethodId;
import com.univ.tracedin.domain.alert.AlertMethodStatus;
import com.univ.tracedin.domain.alert.AlertService;
import com.univ.tracedin.domain.auth.UserPrincipal;
import com.univ.tracedin.domain.project.ProjectId;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertApi implements AlertApiDocs {

    private final AlertService alertService;

    @PostMapping("/methods")
    public Response<Void> appendAlertMethod(
            @AuthenticationPrincipal UserPrincipal currentUser, AppendAlertMethodRequest request) {
        alertService.appendMethod(currentUser.userId(), request.toAlertInfo());
        return Response.success();
    }

    @PatchMapping("/methods/{alertMethodId}/status")
    public Response<Void> changeAlertMethod(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long alertMethodId,
            AlertMethodStatus status) {
        alertService.changeMethodStatus(
                currentUser.userId(), AlertMethodId.from(alertMethodId), status);
        return Response.success();
    }

    @GetMapping("/methods")
    public Response<List<AlertMethod>> getAllAlertMethods(
            @AuthenticationPrincipal UserPrincipal currentUser, Long projectId) {
        return Response.success(
                alertService.readAllMethods(currentUser.userId(), ProjectId.from(projectId)));
    }

    @DeleteMapping("/methods/{alertMethodId}")
    public Response<Void> removeAlertMethod(
            @AuthenticationPrincipal UserPrincipal currentUser, @PathVariable Long alertMethodId) {
        alertService.removeMethod(currentUser.userId(), AlertMethodId.from(alertMethodId));
        return Response.success();
    }
}
