package ru.prooftechit.smh.configuration.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Переопределяет методы getRemoteAddr() and getRemoteHost().
 * Используется для получения внешнего IP адреса клиента, при пробросе запроса через nginx.
 *
 * @author Roman Zdoronok
 */
public class RealIpHttpRequestWrapper extends HttpServletRequestWrapper
{
    private static final String NGINX_REAL_IP_HEADER = "X-Real-IP";

    public RealIpHttpRequestWrapper(final HttpServletRequest request)
    {
        super(request);
    }

    @Override
    public String getRemoteAddr()
    {
        final String realIP = super.getHeader(NGINX_REAL_IP_HEADER);
        return realIP != null ? realIP : super.getRemoteAddr();
    }

    @Override
    public String getRemoteHost()
    {
        return getRemoteAddr();
    }
}
