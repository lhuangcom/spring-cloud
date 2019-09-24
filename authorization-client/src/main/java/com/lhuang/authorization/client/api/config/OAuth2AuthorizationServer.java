package com.lhuang.authorization.client.api.config;

import com.lhuang.authorization.client.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * @author lhunag
 * date 2019/7/26
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter  {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserDetailsService sysUserDetailsService;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    /**
     * <p>设置令牌存储方式</p>
     * InMemoryTokenStore 在内存中存储令牌。
     * RedisTokenStore 在Redis缓存中存储令牌。
     * JwkTokenStore 支持使用JSON Web Key (JWK)验证JSON Web令牌(JwT)的子Web签名(JWS)
     * JwtTokenStore 不是真正的存储，不持久化数据，身份和访问令牌可以相互转换。
     * JdbcTokenStore 在数据库存储，需要创建相应的表存储数据
     */

    @Bean
    public TokenStore tokenStore(){
        //通过redis存储token实现授权服务器和资源服务器认证
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 设置access_token和refresh_token的有效时长,使用jwt时注释掉
     * @return
     */
    @Bean
    public DefaultTokenServices tokenService(){

        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);

        //refreshToken是否可以重复使用。 默认：true;
        tokenServices.setReuseRefreshToken(false);
        //token有效期设置2个小时
        tokenServices.setAccessTokenValiditySeconds(60*60*2);
        //Refresh_token:12个小时
        tokenServices.setRefreshTokenValiditySeconds(60*60*12);

        return tokenServices;

    }

    /**
     * 设置密码编码器
     *  NoOpPasswordEncoder 直接文本比较  equals
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置令牌端点的约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        //允许客户表单认证，主要是让/oauth/token支持client_id以及client_secret作登录认证
        security.allowFormAuthenticationForClients();

        //设置oauth_client_details中的密码编码器
        security.passwordEncoder(passwordEncoder());

        //对于CheckEndpoint控制器[框架自带的校验]的/oauth/check_token端点允许所有客户端发送器请求而不会被Spring-security拦截
        security.checkTokenAccess("permitAll()");

        //oauth/token_key(如果使用JWT，可以获的公钥用于 token 的验签)
        security.tokenKeyAccess("permitAll()");
    }

    /**
     *    切换redis 和 jwt 的方法
     * 配置授权服务器端点的属性和增强功能。
     * 设置自定义验证规则， token存储设置使用...
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        //redis存储token的配置
       endpoints.tokenServices(tokenService())
                //要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(sysUserDetailsService);


        //实现jwt存储token的配置
        /*TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(tokenEnhancerChain);*/
    }

    /**
     * 配置OAuth2的客户端相关信息。使用了数据库存储
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // jdbcTemplate 会查询指定数据源表： oauth_client_details;
        clients.jdbc(dataSource);

    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //  Sets the JWT signing key
        jwtAccessTokenConverter.setSigningKey("lhuang");
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnhancer();
    }

}
