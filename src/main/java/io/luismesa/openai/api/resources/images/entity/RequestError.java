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

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
public class RequestError {

    public static class ErrorPayload {

        private String code;
        private String message;
        private String param;
        private String type;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
//{
//  "error": {
//    "code": null,
//    "message": "Not allowed to GET on /v1/images/generations. (HINT: Perhaps you meant to use a different HTTP method?)",
//    "param": null,
//    "type": "invalid_request_error"
//  }
//}
//{
//  "error": {
//    "code": null,
//    "message": "Additional properties are not allowed ('responseFormat' was unexpected)",
//    "param": null,
//    "type": "invalid_request_error"
//  }
//}
//{
//  "error": {
//    "code": "billing_hard_limit_reached",
//    "message": "Billing hard limit has been reached",
//    "param": null,
//    "type": "invalid_request_error"
//  }
//}
    private ErrorPayload error;

    public ErrorPayload getError() {
        return error;
    }

    public void setError(ErrorPayload error) {
        this.error = error;
    }

}
