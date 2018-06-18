package hu.romat.shopinglist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        Set<SimpleGrantedAuthority> roles = new HashSet<>();
                        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                        return roles;
                    }

                    @Override
                    public String getPassword() {
                        return "pass";
                    }

                    @Override
                    public String getUsername() {
                        return "user";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }
        }).passwordEncoder(new AbstractPasswordEncoder() {
            @Override
            protected byte[] encode(CharSequence charSequence, byte[] bytes) {
                System.out.println(charSequence);
                System.out.println(new String(bytes));
                return new byte[0];
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin().permitAll()
        .and()
            .csrf().disable()
            .headers().frameOptions().disable();
    }

}
