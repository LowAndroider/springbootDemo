package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Djh
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(List<ResponseMessage> responseMessageList) {

        return docket(responseMessageList)
                .groupName("v1.0.0")
                .apiInfo(apiInfo())
                .select()
                // 扫描的包所在位置
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.modules.controller"))
                .build();
    }

    @Bean
    public List<ResponseMessage> responseMessageList() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").build());
        responseMessageList.add(new ResponseMessageBuilder().code(409).message("业务逻辑异常").build());
        responseMessageList.add(new ResponseMessageBuilder().code(422).message("参数错误").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());
        responseMessageList.add(new ResponseMessageBuilder().code(462).message("sql or database error!").build());
        return responseMessageList;
    }

    private static Docket docket(List<ResponseMessage> responseMessageList) {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList);
    }

    private ApiInfo apiInfo() {
        // 联系信息
        Contact contact = new Contact("后台管理微服务", "localhost:58080/swagger-ui.html", "131@test.com");
        return new ApiInfoBuilder()
                // 大标题
                .title("后台管理微服务")
                // 描述
                .description("new-gyenno-service-admin-center后台管理服务")
                // 服务条款 URL
                .termsOfServiceUrl("localhost:58080/swagger-ui.html")
                .contact(contact)
                // 版本
                .version("1.0.0")
                .build();
    }
}
