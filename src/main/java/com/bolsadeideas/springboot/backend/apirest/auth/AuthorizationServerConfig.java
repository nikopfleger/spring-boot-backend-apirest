package com.bolsadeideas.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager") //Por si hay mas beans con ese nombre
	private AuthenticationManager authenticationManager;

	@Override
	//RUTAS DE ACCESO
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	@Override
	//VAMOS A REGISTRAR LA APLICACION DE ANGULAR
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read","write") //QUE ACCESO LE DOY A LA APP
		.authorizedGrantTypes("password", "refresh_token") //UTILIZAMOS PASSWORD CUANDO USAMOS CREDENCIALES DE USUARIO
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}

	//SE ENCARGA DE ANTENTICACION Y VALIDAR EL TOKEN
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//ACA AGREGO LA INFO ADICIONAL DE LA CLASE INFO ADICIONAL
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

		endpoints.authenticationManager(authenticationManager)
		//el tokenStore no es obligatorio si el token converter es del tipo Jwt .tokenStore(tokenStore), lo que estoy haciendo abajo es lo mismo que hace por dentro sin necesidad de replicar
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain); //INFO ADICIONAL
	}

	@Bean
	public JwtTokenStore tokenStore() {
		// TODO Auto-generated method stub
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jtwAccessTokenConverter = new JwtAccessTokenConverter();
		jtwAccessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA); //SI NO PONGO LA LLAVE LO MANEJA AUTOMATICAMENTE PERO LO PUEDO MANEJAR YO
		return jtwAccessTokenConverter;
	}
	
	
}
