package com.bolsadeideas.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//ESTO ES PARA EL LADO DE OAUTH
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//BUENA PRACTICA: DE PERMISOS MAS ESPECIFICOS PARA MAS GENERICOS
		//FORMA PROGRAMATICA, COMENTADO PORQUE LO HAGO CON ANOTACIONES
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**", "/images/**").permitAll()
		/*.antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
		.antMatchers("/api/clientes/**").hasRole("ADMIN") //SE APLICA PARA TODOS LOS HTTPMETHODS */
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());

	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    //OPTIONS DEPENDE DEL NAVEGADOR, ES PARA AUTENTICAR
	    configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
	    configuration.setAllowCredentials(true);
	    configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	//CREAMOS UN FILTRO DE CORS, LE PASAMOS LA CONFIGURACION Y LO REGISTRAMOS DENTRO DEL STACK DE FILTROS DE SPRING Y LE DIMOS LA PRECEDENCIA MAS ALTA
	//CORS: CROSS ORIGIN RESOURCE SHARING, PARA COMPARTIR RECURSOS ENTRE VARIOS ORIGENES, EN ESTE CASO ANGULAR
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); //LA MAS ALTA
		return bean;
	}
	
	
}
