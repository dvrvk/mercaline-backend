package com.mercaline.security.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mercaline.error.exceptions.DatabaseConnectionException;
import com.mercaline.error.exceptions.InvalidTokenException;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * The Class JwtAuthFilter.
 */
@Component

/**
 * Instantiates a new jwt auth filter.
 *
 * @param jwtTokenProvider the jwt token provider
 * @param customUserDetailsService the custom user details service
 */
@RequiredArgsConstructor

/** The Constant log. */
@Log
public class JwtAuthFilter extends OncePerRequestFilter{

    /** The jwt token provider. */
    private final JwtTokenProvider jwtTokenProvider;
    
    /** The custom user details service. */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Do filter internal.
     *
     * @param request the request
     * @param response the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);

            //boolean texto = StringUtils.hasText(token);
            //boolean provider = jwtTokenProvider.validateToken(token);

            if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(token);

                UserEntity user = (UserEntity) customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (CannotCreateTransactionException ex) {
            log.info("No se ha podido conectar a la base de datos.");
            handleAutorizationException(response, new DatabaseConnectionException(), HttpStatus.SERVICE_UNAVAILABLE);
            return;
        } catch (InvalidTokenException ex) {
            handleAutorizationException(response, ex, HttpStatus.UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);

    }

    /**
     * Gets the token from request.
     *
     * @param request the request
     * @return the token from request
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length(), bearerToken.length());
        }
        return null;
    }

    /**
     * Handle autorization exception.
     *
     * @param response the response
     * @param ex the ex
     * @param status the status
     * @throws IOException Signals that an I/O exception has occurred.
     */
    // Control de errores de autenticación - conexión
    private void handleAutorizationException(HttpServletResponse response, Exception ex, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        String fechaString = (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
        //response.getWriter().write("{\"status\": \"" + status.value() + "\" , \"fecha\": \"" + fechaString + "\", \"message\": \"" + ex.getMessage() + "\"}");
        response.getWriter().write("{\"status\": \"" + status.value() + "\" , \"fecha\": \"" + fechaString + "\", \"mensaje\": \"" + ex.getMessage() + "\"}");

    }


    
}
