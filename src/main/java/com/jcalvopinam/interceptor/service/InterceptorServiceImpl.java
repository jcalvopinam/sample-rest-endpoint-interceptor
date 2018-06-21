/*
 * MIT License
 *
 * Copyright (c) 2018. JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO CUSTOM_HEADER SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jcalvopinam.interceptor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Juan Calvopina
 */
@Component
public class InterceptorServiceImpl extends OncePerRequestFilter implements InterceptorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorServiceImpl.class);
    private static final String UTF_8 = "UTF-8";

    private Map<String, String> headerMap;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        request.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(UTF_8);

        RequestWrapper myRequestWrapper = new RequestWrapper(request);

        this.getHeaders(myRequestWrapper);
        this.getBody(myRequestWrapper);

        filterChain.doFilter(myRequestWrapper, response);
    }

    @Override
    public boolean hasCustomHeader(String header) {
        return (header != null && headerMap.get(header) != null);
    }

    private void getHeaders(RequestWrapper request) {
        LOGGER.info("> Get Headers from: {}", request.getRequestURL().toString());

        headerMap = request.getHeaders();
        headerMap.forEach((k, v) -> LOGGER.info("Key: {} \t\t Value: {}", k, v));
    }

    private void getBody(RequestWrapper request) {
        LOGGER.info("> Get Body from: {}", request.getRequestURL().toString());
        LOGGER.info("\tbody: {}", request.getBody());
    }

}