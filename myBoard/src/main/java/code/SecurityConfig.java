package code;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration          // 설정이라는 뜻
@EnableWebSecurity      // 필터 체인 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter  //<< 이거 이제 쓸 수 없음. 상속하지 않고 @Bean 달아주기
{
    /*
        필터체인 : 필터를 여러개 거쳐가며

     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/**").permitAll() // 모든 접근을 허용한다

        ;

    }

    @Bean // 암호화 방법 지정.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
