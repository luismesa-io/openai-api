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
package io.luismesa.openai.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
@Provider
@Priority(1)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    public static final ObjectMapper MAPPER
            = JsonMapper.builder()
                    .findAndAddModules()
                    //.findAndRegisterModules()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                    .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                    .serializationInclusion(JsonInclude.Include.NON_NULL)
                    .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                    //.enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                    //.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                    //.addModule(new JavaTimeModule())
                    //.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .build()
                    .setPropertyNamingStrategy(SNAKE_CASE);

    @Override
    public ObjectMapper getContext(Class<?> type) {
        System.out.println("ObjectMapperContextResolver::getContext(type = " + type + ")");
        return MAPPER;
    }
}
