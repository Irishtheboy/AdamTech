package za.co.admatech.security;

import java.util.Map;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor{
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        System.out.println("===== JWT Handshake Interceptor =====");
        System.out.println("Websocket Handshake attempt for URI: " + request.getURI());
        logger.info("Websocket Handshake attempt for URI: {}" + request.getURI());

        //Extracting the token from the query parameters
        URI uri = request.getURI();
        String token = UriComponentsBuilder.fromUri(uri)
            .build()
            .getQueryParams()
            .getFirst("token");

        System.out.println("Extracted Token: " + (token != null ? "present (" + token.substring(0, 20) + " )" : "null" ));
        System.out.println("====================================");
        logger.info("Extracted Token: {}", token != null ? "present" : "null" );

        if(token != null){
            //Checking whether the token is valid
            boolean isValidToken = jwtUtil.validateToken(token);
            System.out.println("Token validation results: " + isValidToken);

            //Validating whether the token is valid and setting the email, username and token attributes
            if(isValidToken){
                String username = jwtUtil.extractEmail(token);
                attributes.put("username", username);
                attributes.put("token", token);
                System.out.println("Websocket handshake has been successfully authorized for user: " + username);
                System.out.println("====================================");
                logger.info("Websocket handshake has been successfully authorized for user: {}", username);
                return true;
            }
            else{
                System.out.println("Websocket handshake authorization failed due to invalid token.");
                logger.warn("Websocket handshake authorization failed due to invalid token.");
            }
        }
        System.out.println("Websocket handshake REJECTED - invalid or missing token.");
        logger.warn("Websocket handshake REJECTED - invalid or missing token.");
        return false;
    }
    
    @SuppressWarnings("null")
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // No implementation needed after handshake
    }



}
