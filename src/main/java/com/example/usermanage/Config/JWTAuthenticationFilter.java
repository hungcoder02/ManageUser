package com.example.usermanage.Config;

import com.example.usermanage.Model.MyUserDetails;
import com.example.usermanage.Service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try {
            if(request.getRequestURI().equals("/login")){
            }else {
                String jwt = getJwtFromRequest(request);
                if(jwt != null && jwtTokenProvider.validateToken(jwt)){
                    String username = jwtTokenProvider.getUsernameFromJWT(jwt);
                    MyUserDetails myUserDetails = (MyUserDetails) userDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    response.setStatus(401);
                    return;
                }
            }
        }catch (Exception ex){

        }
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            System.out.println(bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}
