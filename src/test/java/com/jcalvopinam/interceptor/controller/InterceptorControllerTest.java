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

package com.jcalvopinam.interceptor.controller;

import com.jcalvopinam.interceptor.service.InterceptorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Juan Calvopina
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InterceptorControllerTest {

    private static final String INTERCEPTOR_PATH = "/api";
    private static final String URL_PARAMETER = "url";
    private static final String URL = "www.github.com/juanca87";

    private String header;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InterceptorService interceptorService;

    @Before
    public void setUp() {
        header = "custom-header";
    }

    @Test
    public void testGetInterceptorShouldReturnOk() throws Exception {
        when(interceptorService.hasCustomHeader(header)).thenReturn(true);
        mvc.perform(get(INTERCEPTOR_PATH)
                            .param(URL_PARAMETER, URL)
                            .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    public void testGetInterceptorWhenUrlIsNotSentThenFails() throws Exception {
        when(interceptorService.hasCustomHeader(header)).thenReturn(true);
        mvc.perform(get(INTERCEPTOR_PATH)
                            .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetInterceptorShouldReturnBody() throws Exception {
        when(interceptorService.hasCustomHeader(header)).thenReturn(true);
        mvc.perform(get(INTERCEPTOR_PATH)
                            .param(URL_PARAMETER, URL)
                            .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().string("The URL: \"" + URL + "\" has the custom-header? true"));
    }

    @Test
    public void testGetInterceptorShouldReturnNullBody() throws Exception {
        when(interceptorService.hasCustomHeader(null)).thenReturn(false);
        mvc.perform(get(INTERCEPTOR_PATH)
                            .param(URL_PARAMETER, URL)
                            .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().string("The URL: \"" + URL + "\" has the custom-header? false"));
    }

}