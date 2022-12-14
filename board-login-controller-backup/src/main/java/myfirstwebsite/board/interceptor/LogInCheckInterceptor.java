package myfirstwebsite.board.interceptor;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LogInCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {

    String requestURI = request.getRequestURI();

    log.info("인증 체크 인터셉터 실행 = {}", requestURI);

    HttpSession session = request.getSession();

    if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
      log.info("미인증 사용자 요청!");
      response.sendRedirect("/?redirectURL=" + requestURI);
      return false;
    }

    return true;
  }
}
