package demos.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config {
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthorizationServerConfigurer authorizationServerConfigurer() {
		return new AuthorizationServerConfigurerAdapter() {
			
			@Override
			public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
				security
					.checkTokenAccess("isAuthenticated()");
			}
			
			@Override
			public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
				endpoints.authenticationManager(authenticationManager);
			}
			
			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
				clients
					.inMemory()
						.withClient("my-client")
							.secret("my-secret")
							.scopes("read", "write")
							.authorizedGrantTypes("password", "client_credentials", "refresh_token")
					.and()
						.withClient("resource-server")
							.secret("resource-server-secret")
							.scopes("read")
							.authorizedGrantTypes("client_credentials", "refresh_token");
			}
			
		};
	}

}
