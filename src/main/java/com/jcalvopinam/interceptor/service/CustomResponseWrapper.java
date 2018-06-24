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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static java.util.Optional.ofNullable;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 */
public class CustomResponseWrapper extends HttpServletResponseWrapper {
    private StringWriter stringWriter;
    private boolean isOutputStreamCalled;

    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (ofNullable(this.stringWriter).isPresent()) {
            throw new IllegalStateException("The getWriter() method is already called.");
        }
        isOutputStreamCalled = true;
        return super.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() {
        if (isOutputStreamCalled) {
            throw new IllegalStateException("The getOutputStream() method is already called.");
        }

        this.stringWriter = new StringWriter();

        return new PrintWriter(this.stringWriter);
    }

    public String getResponseContent() {
        if (ofNullable(this.stringWriter).isPresent()) {
            return this.stringWriter.toString();
        }
        return "";
    }

}