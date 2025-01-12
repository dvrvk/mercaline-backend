package com.mercaline.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercaline.error.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class JwtAuthenticationEntryPoint.
 */
@Component

/**
 * Instantiates a new jwt authentication entry point.
 *
 * @param mapper the mapper
 */
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/** The mapper. */
	private final ObjectMapper mapper;

	/**
	 * Commence.
	 *
	 * @param request       the request
	 * @param response      the response
	 * @param authException the auth exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");

		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());
		String strError = mapper.writeValueAsString(error);

		PrintWriter write = response.getWriter();
		write.println(strError);

	}
}
