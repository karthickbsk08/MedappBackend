package com.example.medapp.globalConfiguration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

import com.example.medapp.model.Login.LoginResponse;
import com.example.medapp.model.Login.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig {
        /*
         * public WebMvcConfigurer corsConfigurer() {
         * return new WebMvcConfigurer() {
         * // Configure CORS settings here if needed
         * 
         * @SuppressWarnings("null")
         * 
         * @Override
         * public void addCorsMappings(CorsRegistry registry) {
         * registry.addMapping("/**")
         * .allowedOrigins("*") // same as Access-Control-Allow-Origin: *
         * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // same as
         * // Access-Control-Allow-Methods
         * .allowedHeaders("Accept", "Content-Type", "Content-Length",
         * "Accept-Encoding", "X-CSTF-Token", "Authorization") // like
         * Access-Control-Allow-Headers
         * .allowCredentials(false); // optional - if you need to send cookies
         * }
         * 
         * @Bean
         * public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         * http
         * .cors(cors -> cors.configurationSource(corsConfigurer()))
         * .csrf(csrf -> csrf.disable())
         * .authorizeHttpRequests(auth -> auth
         * .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
         * .anyRequest().permitAll());
         * 
         * return http.build(); // ✅ proper return
         * }
         * };
         * }
         */

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("*")); // allow all origins
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("Accept", "Content-Type", "Content-Length",
                                "Accept-Encoding", "X-CSTF-Token", "Authorization", "USER","LOGINID")); // allow specific headers
                config.setAllowCredentials(false); // or true if you use cookies

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // attach CORS config
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // allow
                                                                                                        // preflight
                                                .requestMatchers("/**").permitAll() // allow all requests
                                                .anyRequest().authenticated())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessHandler((request, response, authentication) -> {
                                                        ObjectMapper objmap = new ObjectMapper();
                                                        LoginResponse lp = new LoginResponse(new UserDetails(), "", "S",
                                                                        "Logout Successful");
                                                        response.setStatus(HttpServletResponse.SC_OK);
                                                        response.setContentType("application/json");
                                                        objmap.writeValue(response.getWriter(), lp);

                                                })
                                                .permitAll());

                return http.build();
        }

        /*
         * @Bean
         * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
         * Exception {
         * http
         * .authorizeHttpRequests(authorize -> authorize
         * .anyRequest().authenticated())
         * .logout(logout -> logout
         * .logoutUrl("/logout")
         * .logoutSuccessUrl("/login") // 👈 redirect path after logout
         * .permitAll());
         * return http.build();
         * }
         */

}
