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

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
public class Image {

    private String b64Json;
    private String url;
    private String revisedPrompt;

    @JsonGetter("b64_json")
    public String getB64Json() {
        return b64Json;
    }

    @JsonSetter("b64_json")
    public void setB64Json(String b64Json) {
        this.b64Json = b64Json;
    }

    @JsonGetter("url")
    public String getUrl() {
        return url;
    }

    @JsonSetter("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonGetter("revised_prompt")
    public String getRevisedPrompt() {
        return revisedPrompt;
    }

    @JsonSetter("revised_prompt")
    public void setRevisedPrompt(String revisedPrompt) {
        this.revisedPrompt = revisedPrompt;
    }
}
