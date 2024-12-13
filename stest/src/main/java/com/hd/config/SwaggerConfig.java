package com.hd.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(title = "Sample API",
                description = "연습용 api명세서입니다.",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi SampleOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Sample v1")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi SampleUserOpenApi() {
        String[] paths = {"/api/login/**"};

        return GroupedOpenApi.builder()
                .group("Sample v1-user")
                .pathsToMatch(paths)
                .build();
    }
    @Bean
    public GroupedOpenApi SampleTodoOpenApi() {
        String[] paths = {"/api/item/**"};

        return GroupedOpenApi.builder()
                .group("Sample v1-todo")
                .pathsToMatch(paths)
                .build();
    }
}
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI openAPI() {
//        Info info = new Info()
//                .version("v1.0.0")
//                .title("API")
//                .description("");
//
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt); // 헤더에 토큰 포함
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                .name(jwt)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//        );
//
//        return new OpenAPI()
//                .info(info)
//                .addSecurityItem(securityRequirement)
//                .components(components);
//    }
//
//}


//
//@Configuration
//public class SwaggerConfig {
//    private static final String BEARER_TOKEN_PREFIX = "Bearer";
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("test")
//                .description("description")
//                .version("1.0.0")
//                .build();
//    }
//    @Bean
//    public OpenAPI openAPI(){
//        String securityJwtName = "JWT";
//        // API가 JWT 인증을 요구한다는 것을 나타냄
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
//
//        // Swagger에서 사용하는 보안 스키마(인증 방식)를 정의
//        // SecurityScheme.Type.HTTP 타입의 Bearer 토큰 인증 방식을 사용하며, Bearer 포맷으로 JWT를 지정
//        Components components = new Components()
//                .addSecuritySchemes(securityJwtName, new SecurityScheme()
//                        .name(securityJwtName)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme(BEARER_TOKEN_PREFIX)
//                        .bearerFormat(securityJwtName)
//                );
//        return new OpenAPI()
//                .addSecurityItem(securityRequirement)
//                .components(components);
//    }
//}
//
//@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Project Test", description = "Open API Test",version = "1.0"))
//@RequiredArgsConstructor
//@Configuration
//public class SwaggerConfig {
//
//    private static final String BEARER_TOKEN_PREFIX = "Bearer";
//
//    @Bean
//    public OpenAPI openAPI(){
//        String securityJwtName = "JWT";
//        // API가 JWT 인증을 요구한다는 것을 나타냄
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
//
//        // Swagger에서 사용하는 보안 스키마(인증 방식)를 정의
//        // SecurityScheme.Type.HTTP 타입의 Bearer 토큰 인증 방식을 사용하며, Bearer 포맷으로 JWT를 지정
//        Components components = new Components()
//                .addSecuritySchemes(securityJwtName, new SecurityScheme()
//                        .name(securityJwtName)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme(BEARER_TOKEN_PREFIX)
//                        .bearerFormat(securityJwtName)
//                );
//        return new OpenAPI()
//                .addSecurityItem(securityRequirement)
//                .components(components);
//    }
//
////    @Bean
////    public OpenAPI openAPI(){
////        SecurityScheme securityScheme = new SecurityScheme()
////                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
////                .in(SecurityScheme.In.HEADER).name("Authorization");
////        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
////
////        return new OpenAPI()
////                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
////                .security(Arrays.asList(securityRequirement));
////    }
////    @Bean
////    public OpenAPI openAPI() {
////        return new OpenAPI()
////                .components(new Components())
////                .info(apiInfo());
////    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("CodeArena Swagger")
//                .description("CodeArena 유저 및 인증 , ps, 알림에 관한 REST API")
//                .version("1.0.0");
//    }
////    @Bean
////    public GroupedOpenApi chatOpenApi() {
////        String[] paths = { "/api//login/**" }; // 해당 path인경우에만 스웨거에 추가되도록 설정
////
////        return GroupedOpenApi
////                .builder()
////                .group("login API v1")
////                .pathsToMatch(paths)
////                .addOpenApiCustomizer(
////                        openApi -> openApi.setInfo(
////                                new Info()
////                                        .title("login") // API 제목
////                                        .description("Login API") // API 설명
////                                        .version("1.0.0") // API 버전
////                        )
////                )
////                .build();
////    }
////    @Bean
////    public GroupedOpenApi chatOpenApi2() {
////        String[] paths = { "/api//v1/**" }; // 해당 path인경우에만 스웨거에 추가되도록 설정
////
////        return GroupedOpenApi
////                .builder()
////                .group("Pro API v1")
////                .pathsToMatch(paths)
////                .addOpenApiCustomizer(
////                        openApi -> openApi.setInfo(
////                                new Info()
////                                        .title("My Open API Project") // API 제목
////                                        .description("업무 처리를 위한 API") // API 설명
////                                        .version("1.0.1") // API 버전
////                        )
////                )
////                .build();
////    }
//}
