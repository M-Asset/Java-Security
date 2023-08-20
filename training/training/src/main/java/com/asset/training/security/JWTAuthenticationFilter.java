package com.asset.training.security;

import com.asset.training.constants.ErrorCodes;
import com.asset.training.exceptions.TrainingException;
import com.asset.training.models.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            HttpServletRequest hrw = doAuthenticationFilter(request, response, filterChain);
            filterChain.doFilter(hrw, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TrainingException(ErrorCodes.ERROR.NOT_AUTHORIZED, 2);
        }
    }

    protected HttpServletRequest doAuthenticationFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain){
        String authToken = req.getHeader("Authorization");
        if(authToken != null){
            UserModel user = jwtProvider.getUserModelFromToken(authToken);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null){
                boolean validation = jwtProvider.validateToken.test(authToken);
                if(validation){
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUsername(), null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else
                    throw new TrainingException(ErrorCodes.ERROR.EXPIRED_TOKEN, 1);
            }
        }
        return req;
    }
}
