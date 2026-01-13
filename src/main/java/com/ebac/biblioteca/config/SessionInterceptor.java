package com.ebac.biblioteca.config;

import com.ebac.biblioteca.repository.AdminSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private final AdminSessionRepository sessionRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/login")) {
            return true;
        }

        String sessionIdHeader = request.getHeader("X-SESSION-ID");

        if (sessionIdHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Long sessionId = Long.parseLong(sessionIdHeader);

        boolean activeSession = sessionRepository
                .findByIdAndActiveTrue(sessionId)
                .isPresent();

        if (!activeSession) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}