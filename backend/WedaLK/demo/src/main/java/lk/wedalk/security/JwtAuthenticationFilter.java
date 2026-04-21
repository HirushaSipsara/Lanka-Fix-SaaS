package lk.wedalk.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  // #region agent log
  private static void debugLog(String location, String message, String data) {
    try {
      String line = "{\"sessionId\":\"ba51df\",\"runId\":\"run1\",\"hypothesisId\":\"H2,H3,H4\",\"location\":\""
          + location + "\",\"message\":\"" + message + "\",\"data\":" + data
          + ",\"timestamp\":" + System.currentTimeMillis() + "}\n";
      java.nio.file.Files.write(
          java.nio.file.Paths.get("D:/skill-exchange/debug-ba51df.log"),
          line.getBytes(java.nio.charset.StandardCharsets.UTF_8),
          java.nio.file.StandardOpenOption.CREATE,
          java.nio.file.StandardOpenOption.APPEND);
    } catch (Exception ignored) {}
  }
  private static String q(String s) {
    if (s == null) return "null";
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }
  // #endregion

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;
    // #region agent log
    String reqPath = request.getMethod() + " " + request.getRequestURI();
    // #endregion

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      // #region agent log
      debugLog("JwtAuthenticationFilter.java:no-auth-header",
          "no Bearer header, skipping auth",
          "{\"path\":" + q(reqPath) + ",\"hasHeader\":" + (authHeader != null)
              + ",\"headerPrefix\":" + q(authHeader == null ? null
                  : authHeader.substring(0, Math.min(authHeader.length(), 10))) + "}");
      // #endregion
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);
    try {
      userEmail = jwtService.extractUsername(jwt);
    } catch (Exception ex) {
      // #region agent log
      debugLog("JwtAuthenticationFilter.java:extract-username-exception",
          "extractUsername threw",
          "{\"path\":" + q(reqPath) + ",\"jwtLen\":" + jwt.length()
              + ",\"exClass\":" + q(ex.getClass().getSimpleName())
              + ",\"exMsg\":" + q(ex.getMessage()) + "}");
      // #endregion
      filterChain.doFilter(request, response);
      return;
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails;
      try {
        userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      } catch (Exception ex) {
        filterChain.doFilter(request, response);
        return;
      }
      boolean valid = jwtService.isTokenValid(jwt, userDetails);
      // #region agent log
      debugLog("JwtAuthenticationFilter.java:token-check",
          "extracted + loaded user, validation result",
          "{\"path\":" + q(reqPath) + ",\"email\":" + q(userEmail)
              + ",\"enabled\":" + userDetails.isEnabled()
              + ",\"authorities\":" + q(userDetails.getAuthorities().toString())
              + ",\"tokenValid\":" + valid + "}");
      // #endregion
      if (valid) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // #region agent log
    try {
      filterChain.doFilter(request, response);
    } catch (ServletException | IOException | RuntimeException t) {
      StringBuilder stack = new StringBuilder();
      Throwable cur = t;
      int depth = 0;
      while (cur != null && depth < 5) {
        stack.append(cur.getClass().getName()).append(": ")
            .append(cur.getMessage() == null ? "" : cur.getMessage().replace("\n", " ")).append(" | ");
        cur = cur.getCause();
        depth++;
      }
      debugLog("JwtAuthenticationFilter.java:downstream-exception",
          "exception from downstream filterChain.doFilter",
          "{\"path\":" + q(reqPath) + ",\"chain\":" + q(stack.toString()) + "}");
      throw t;
    }
    // #endregion
  }
}
