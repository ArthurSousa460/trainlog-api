package com.trainlog.aspects;

import com.trainlog.configs.TenantContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class TenantAspect {

    private final TenantContext tenantContext;

    @Around(
            "@annotation(org.springframework.transaction.annotation.Transactional)"
                    + " || @annotation(jakarta.transaction.Transactional)"
                    + " || @within(org.springframework.transaction.annotation.Transactional)"
                    + " || @within(jakarta.transaction.Transactional)"
    )
    public Object intercept(ProceedingJoinPoint joinPoint)
            throws Throwable {

        if (!TransactionSynchronizationManager
                .isActualTransactionActive()) {
            throw new IllegalStateException(
                    "Transação não está ativa"
            );
        }

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new AccessDeniedException(
                    "Usuário não autenticado"
            );
        }

        UUID userId;
        try {
            userId = UUID.fromString(jwtAuth.getToken().getSubject());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new AccessDeniedException(
                    "O token não contém um tenant válido",
                    exception
            );
        }

        tenantContext.setTenant(userId);

        return joinPoint.proceed();
    }
}
