package com.org.Blog.confign;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	public static final String AUTHPRIZATION_HEADER = "Authorization";

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHPRIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContext() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}

	private List<SecurityReference> securityReferences() {
		AuthorizationScope scopes = new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scopes }));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging appliction:backend", "this project is developed by avinash ", "1.0",
				"tearm of service", new Contact("avinash", "avinashpawar0295@gmail.com", "8554064039"),
				"License of API", "APL licese URL", Collections.emptyList());
	}

}
