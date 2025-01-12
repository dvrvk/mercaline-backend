package com.mercaline.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mercaline.security.jwt.JwtAuthFilter;
import com.mercaline.users.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableJpaAuditing

/**
 * Instantiates a new security config.
 *
 * @param customUserDetailsService    the custom user details service
 * @param jwtAuthenticationEntryPoint the jwt authentication entry point
 * @param passwordEncoder             the password encoder
 * @param jwtAuthFilter               the jwt auth filter
 */
@RequiredArgsConstructor
public class SecurityConfig {

	/** The custom user details service. */
	private final CustomUserDetailsService customUserDetailsService;

	/** The jwt authentication entry point. */
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	/** The password encoder. */
	private final PasswordEncoder passwordEncoder;

	/** The jwt auth filter. */
	private final JwtAuthFilter jwtAuthFilter;

	/**
	 * Security filter chain.
	 *
	 * @param http the http
	 * @return the security filter chain
	 * @throws Exception the exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Deshabilitar CSRF para simplificar pruebas
		http.csrf(csrf -> csrf.disable())
		// Permitir acceso sin autenticación a /users
				.authorizeHttpRequests(auth -> auth.requestMatchers("/user/registrar", "/auth/login").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(
						exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(withDefaults()) // Habilitar cors
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

//    @Bean
//    public JwtAuthFilter jwtAuthFilter() {
//        return new JwtAuthFilter();
//    }

	/**
	 * Cors configure.
	 *
	 * @return the web mvc configurer
	 */
	@Bean
	public WebMvcConfigurer corsConfigure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

	/**
	 * Authentication manager.
	 *
	 * @return the authentication manager
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authProvider);
	}

}
