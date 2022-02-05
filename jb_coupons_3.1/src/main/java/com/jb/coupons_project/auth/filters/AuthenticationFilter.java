package com.jb.coupons_project.auth.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.coupons_project.auth.utils.JwtUtil;
import com.jb.coupons_project.rest.error_response_entity.ErrorResponse;

@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
											throws ServletException, IOException, RuntimeException {
		String jwt = null;
		Integer id = null;
		String role = null;
		String name = null;
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		// check if authorization header is present and has prefix "Bearer "
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			errorResponse.setMessage("No jwt token or invalid token");
			errorResponse.setTimeStamp(System.currentTimeMillis());
			
			byte[] responseToSend = restResponseBytes(errorResponse);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
//            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse) response).setStatus(401);
            response.getOutputStream().write(responseToSend);
            return;
		}
		else { 
			jwt = authorizationHeader.substring(7);
			Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
			id = (Integer)claims.get("id");
			role = (String)claims.get("role");
			name = (String)claims.get("name");
		}
		
		// check if token contains user role and user id
		if(id == null || role == null || name == null) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			errorResponse.setMessage("Cannot identify user");
			errorResponse.setTimeStamp(System.currentTimeMillis());
			
			byte[] responseToSend = restResponseBytes(errorResponse);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(401);
            response.getOutputStream().write(responseToSend);
            return;
		}
		
		filterChain.doFilter(request, response);
	}
	
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//		String path = request.getServletPath();
//		return path.equals("/authenticate");
//	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		if(path.startsWith("/api/admin/") 
				|| path.startsWith("/api/company/") 
				|| path.startsWith("/api/customer/")) {
			return false;
			
		}
		else {
			return true;
		}
	}
	
	private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }
}
