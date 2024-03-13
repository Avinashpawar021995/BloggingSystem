package com.org.Blog.confign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.org.Blog.security.CustomUserDetailsService;
import com.org.Blog.security.JwtAuthenticationEntryPoint;
import com.org.Blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	public static final String[] PUBLIC_URLS = { "/api/v1/**", "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**" };

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((auth) -> auth.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())

				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		security.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain defaultSecurityFilterChain = security.build();
		return defaultSecurityFilterChain;
	}

//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.
//                csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .antMatchers(PUBLIC_URLS)
//                .permitAll()
//                .antMatchers(HttpMethod.GET)
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().exceptionHandling()
//                .authenticationEntryPoint(this.authenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.authenticationProvider(daoAuthenticationProvider());
//        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
//
//        return defaultSecurityFilterChain;
//
//
//    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;

	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public FilterRegistrationBean coresFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.setMaxAge(3600L);

		source.registerCorsConfiguration("/**", corsConfiguration);

		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

		bean.setOrder(-110);

		return bean;
	}
}
