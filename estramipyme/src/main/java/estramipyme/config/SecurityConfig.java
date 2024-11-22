package estramipyme.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//    @Lazy
//    private final CustomAuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable()) // Opcional: Desactivar CSRF si no lo necesitas.
//                .authorizeHttpRequests(authRequests -> authRequests
//                        .requestMatchers("/user/login", "/user/register").permitAll() // Endpoints públicos.
//                        .anyRequest().authenticated() // Resto requiere autenticación.
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Página personalizada para login.
//                        .permitAll() // Permitir acceso sin autenticación.
//                        .defaultSuccessUrl("/home", true) // Redirigir tras login exitoso.
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL para cerrar sesión.
//                        .logoutSuccessUrl("/login") // Redirigir tras logout.
//                        .permitAll()
//                )
//                .authenticationProvider(authenticationProvider)
//                .build();
////    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(http -> {
//                    // Configurar los endpoints publicos
//                    http.requestMatchers(HttpMethod.GET, "/user/login").permitAll();
//
//                    // Cofnigurar los endpoints privados
////                    http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER");
////                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("REFACTOR");
//
//                    // Configurar el resto de endpoint - NO ESPECIFICADOS
//                    http.anyRequest().denyAll();
//                })
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userDetailService);
//        return provider;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
