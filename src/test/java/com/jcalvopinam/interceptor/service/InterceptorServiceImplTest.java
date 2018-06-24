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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jcalvopinam.interceptor.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Juan Calvopina
 */
public class InterceptorServiceImplTest {

    @InjectMocks
    @Spy
    private InterceptorServiceImpl interceptorService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Map<String, String> headerMap;

    private String header;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        header = "custom-header";
    }

    @Test
    public void doFilterInternal() throws ServletException, IOException {
        doNothing().when(interceptorService).doFilterInternal(request, response, filterChain);
        interceptorService.doFilterInternal(request, response, filterChain);
        verify(interceptorService, times(1)).doFilterInternal(request, response, filterChain);
    }

    @Test
    public void hasCustomHeaderShouldReturnTrue() {
        when(headerMap.get(anyString())).thenReturn(header);
        when(interceptorService.hasCustomHeader(anyString())).thenReturn(true);
        assertTrue(interceptorService.hasCustomHeader(header));
    }

    @Test
    public void hasCustomHeaderShouldReturnFalse() {
        when(headerMap.get(anyString())).thenReturn(null);
        when(interceptorService.hasCustomHeader(anyString())).thenReturn(false);
        assertFalse(interceptorService.hasCustomHeader(header));
    }

    @Test
    public void hasNotCustomHeaderShouldReturnFalse() {
        when(interceptorService.hasCustomHeader(null)).thenReturn(false);
        assertFalse(interceptorService.hasCustomHeader(null));
    }

}