/*
 * The MIT License
 *
 * Copyright 2023 Luis Daniel Mesa Velásquez.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.luismesa.openai.api.resources.images.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
public class CreateImageResponse extends RequestError {

    private long created;
    private List<Image> data;

    @JsonGetter("created")
    public long getCreated() {
        return created;
    }

    @JsonSetter("created")
    public void setCreated(long created) {
        this.created = created;
    }

    @JsonGetter("data")
    public List<Image> getData() {
        return data;
    }

    @JsonSetter("data")
    public void setData(List<Image> data) {
        this.data = data;
    }

}
