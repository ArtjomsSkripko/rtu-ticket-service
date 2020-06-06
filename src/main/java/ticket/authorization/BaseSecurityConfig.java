package ticket.authorization;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthTokenService authTokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
        http.headers().cacheControl().disable();
        BaseAuthenticationFilter filter = new BaseAuthenticationFilter(authenticationManagerBean());
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider());
    }

    private AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(authTokenService);
    }

    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> Utils.writeJSONResponse(Utils.getAuthenticationError(),
                response, HttpServletResponse.SC_UNAUTHORIZED);
    }
}