package com.spring.backproject.Configuration;

import com.spring.backproject.Service.UtilisateurdetaiSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecuConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    UtilisateurdetaiSer utilisateurdetaiSer;

@Override
  public void configure (HttpSecurity http) throws Exception {
      http.csrf().disable()

              .authorizeRequests()
              .antMatchers("/api/auth/**").permitAll()
              .antMatchers("/*").permitAll()
              .antMatchers("/account/**").hasAnyAuthority("user")
              .anyRequest().authenticated();}
    @Override
    public void configure(AuthenticationManagerBuilder auth){
    auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
@Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
   DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
   authenticationProvider.setPasswordEncoder(passwordEncoder());
   authenticationProvider.setUserDetailsService(utilisateurdetaiSer);
   return authenticationProvider;
}



}

