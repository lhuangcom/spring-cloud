package com.lhuang.authorization.client.api.config;

import com.lhuang.authorization.client.api.handler.CustomAuthenticationFailureHandler;
import com.lhuang.authorization.client.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.util.List;

/**
 * @author lhunag
 * date 2019/7/26
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {


    /**
     * 它是认证的一个管理者他是一个接口，里面有个方法authenticate
     * 接受Authentication这个参数来完成验证；
     * ProviderManager（维护一个AuthenticationProvider列表）实现AuthenticationManager这个接口，完成验证工作。
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler(){
        SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
        simpleUrlAuthenticationFailureHandler.setUseForward(true);
        simpleUrlAuthenticationFailureHandler.setDefaultFailureUrl("/validerror");
        return simpleUrlAuthenticationFailureHandler;
    }

    /**
     * UserDetailsService只负责从特定的地方（通常是数据库）加载用户信息
     */
    @Autowired
    private SysUserDetailsService userDetailsService;

    @Value("#{'${ignore-path}'.split(',')}")
    private List<String> ignorePath;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {

       web.ignoring().antMatchers((ignorePath.toArray(new String[ignorePath.size()])));
    }

    /**
     * 配置验证规则；
     * 是AuthenticationProvider的的一个实现类
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setHideUserNotFoundExceptions(false);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider() );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                //设置登录首页，
                .loginPage("/").permitAll()
                //.failureHandler(simpleUrlAuthenticationFailureHandler())
                //设置登录验证路径，与内置的登录逻辑（登录路径、请求方法）一致
                .loginProcessingUrl("/login")
                .failureHandler(new CustomAuthenticationFailureHandler())
                .defaultSuccessUrl("/index")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .cors()
                .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/")
                .and()
                .rememberMe()
                //记住我功能的cookie有效期
                .tokenValiditySeconds(6048000)
                .and()
                .requestMatchers();



    }
}
