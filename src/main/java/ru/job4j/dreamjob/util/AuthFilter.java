package ru.job4j.dreamjob.util;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private final Set<String> uriSet = Set.of("/loginPage", "/login", "/formAddUser",
            "/registration", "/fail", "/success");

    private boolean checkUri(String uri, Set<String> uriSet) {
        return uriSet.contains(uri);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (checkUri(uri, uriSet)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/loginPage");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
