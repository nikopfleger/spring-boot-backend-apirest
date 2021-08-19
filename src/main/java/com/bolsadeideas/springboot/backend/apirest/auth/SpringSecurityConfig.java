package com.bolsadeideas.springboot.backend.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



//CLASE DE CONFIGURACION DE SPRING

//ENABLEGLOBAL ES PARA HABILITAR LAS ANOTACIONES PARA LOS ANTMATCHERS
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//INYECTA USUARIO SERVICE, LA IMPLEMENTACION DE USERDETAILSSERVICE
	@Autowired
	private UserDetailsService usuarioService;
	
	//REGISTRO EL OBJETO QUE RETORNO COMO UN BEAN (COMPONENTE) DE SPRING PARA USARLO MAS ADELANTE
	//LO REGISTRA EN EL CONTENEDOR DE SPRING
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//Uso autowired porque tengo que inyectar el AuthenticationManagerBuilder
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService)
		.passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean("authenticationManager")
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	//ESTO ES PARA EL LADO DE SPRING
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and().csrf().disable() //COMO USO ANGULAR NO NECESITO EL CSRF)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //COMO GUARDO LA INFORMACION DEL USUARIO EN EL TOKEN POR EL LADO DEL CLIENTE Y NO EN LA SESION, OSEA EL LADO DEL SERVIDOR ENTONCES TENGO QUE CONFIGURAR PARA QUE SEA STATELESS
	}
	
	

}
