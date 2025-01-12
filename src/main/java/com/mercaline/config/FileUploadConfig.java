package com.mercaline.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.mercaline.config.utils.AppConstants.PATH_IMG;

/**
 * The Class FileUploadConfig.
 */
@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

	/**
	 * Multipart config element.
	 *
	 * @return the multipart config element
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();

		factory.setMaxFileSize(DataSize.ofMegabytes(10)); // 10 MB
		factory.setMaxRequestSize(DataSize.ofMegabytes(10)); // 10 MB

		return factory.createMultipartConfig();
	}

	/**
	 * Adds the resource handlers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:" + PATH_IMG + "/");
	}
}
