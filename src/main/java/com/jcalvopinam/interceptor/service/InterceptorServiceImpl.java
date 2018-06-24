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
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Juan Calvopina
 */
@Service
public class InterceptorServiceImpl extends OncePerRequestFilter implements InterceptorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorServiceImpl.class);
    private static final String UTF_8 = "UTF-8";

    private Map<String, String> headerMap;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        request.setCharacterEncoding(UTF_8);
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(UTF_8);

        CustomRequestWrapper customRequestWrapper = new CustomRequestWrapper(request);

        this.getHeaders(customRequestWrapper);
        this.getBody(customRequestWrapper);

        CustomResponseWrapper customResponseWrapper = new CustomResponseWrapper(response);
        filterChain.doFilter(customRequestWrapper, customResponseWrapper);

        this.getResponseContent(customResponseWrapper);
    }

    @Override
    public boolean hasCustomHeader(String header) {
        return (ofNullable(header).isPresent() && ofNullable(headerMap.get(header)).isPresent());
    }

    private void getHeaders(CustomRequestWrapper request) {
        LOGGER.info("> Get Headers from: {}", request.getRequestURL().toString());

        headerMap = request.getHeaders();
        headerMap.forEach((k, v) -> LOGGER.debug("Key: {} \t\t Value: {}", k, v));
    }

    private void getBody(CustomRequestWrapper request) {
        LOGGER.info("> Get Body from: {}", request.getRequestURL().toString());
        LOGGER.info("\tbody: {}", request.getBody());
    }

    private void getResponseContent(CustomResponseWrapper response) {
        LOGGER.info("> Get Response status: {}", response.getStatus());
        LOGGER.info("\tresponse: {}", response.getResponseContent());
    }

}