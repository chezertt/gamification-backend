package com.gamification.gamificationbackend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.gamificationbackend.dto.response.ErrorResponseDto;
import com.gamification.gamificationbackend.enumeration.JwtTokenType;
import com.gamification.gamificationbackend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean requestUriNotInWhitelist = !request.getRequestURI().matches("/api/auth.*|/api/docs.*|/api/swagger-ui.*|/api/images.*");
        if (requestUriNotInWhitelist) {
            try {
                String accessToken = jwtService.extractAccessTokenFromRequest(request);
                if (StringUtils.hasLength(accessToken)) {
                    String userEmail = jwtService.validateTokenAndExtractEmail(accessToken, JwtTokenType.ACCESS);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                } else {
                    sendResponseWithUnauthorizedStatus(response, "No jwt access token in Authorization request header");
                }
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                sendResponseWithUnauthorizedStatus(response, "Invalid jwt access token");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void sendResponseWithUnauthorizedStatus(HttpServletResponse response, String message) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(message)
                .displayedMessage(message)
                .build();

        try {
            String errorResponseDtoAsJson = objectMapper.writeValueAsString(errorResponseDto);
            response.getWriter().write(errorResponseDtoAsJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
