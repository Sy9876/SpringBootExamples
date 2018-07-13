spring-security base on springboot2

- application.properties 里前缀与springboot1有变化。 变为： spring.security.user.name

在SecurityConfig里配置：

- InMemoryUserDetailsManager（UserDetailsService的实现类）
- DaoAuthenticationProvider（AuthenticationProvider的实现类）
- ProviderManager（AuthenticationManager的实现类）

在config里，

- @EnableSpringHttpSession来启用session
- 声明一个sessionRepository（@Bean MapSessionRepository）

配置使用x-auth-token代替cookie传递sessionId

- 声明一个HttpSessionIdResolver

在WebSecurityConfigurerAdapter里配置http校验：

- .csrf().disable() 关闭csrf （不使用cookie传递sessionId，不受csrf影响）
- .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)？
- 或通过配置 security.sessions=always ?


# login

两种方式login：

- AuthenticationManager.authenticate()
- HttpServletRequest.login()

第一种，需要注入AuthenticationManager，为此，需要自己声明InMemoryUserDetailsManager，
无法使用自动配置的InMemoryUserDetailsManager（因为此类有OnMissingBean条件）。

第二种，比较简单，不需要自己声明，靠springboot自动配置就可以。



# curl模拟ajax请求

``` bat

set CX=curl -i -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"

%CX% "http://localhost:8080/void.do"

%CX% "http://localhost:8080/login.do?name=admin&password=admin123"

set X_AUTH_TOKEN=
%CX% "http://localhost:8080/whoami.do"

%CX% "http://localhost:8080/logout.do"


```
