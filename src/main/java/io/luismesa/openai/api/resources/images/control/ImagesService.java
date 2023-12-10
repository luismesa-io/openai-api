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
package io.luismesa.openai.api.resources.images.control;

import io.luismesa.openai.api.LoggingFilter;
import io.luismesa.openai.api.resources.images.entity.CreateImageResponse;
import io.luismesa.openai.api.resources.images.entity.DallE2CreateImageRequest;
import io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.annotation.RegisterProviders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
@Path("v1/images")
@RegisterRestClient(configKey = "openai.images")
@ClientHeaderParam(name = "Authorization", value = "{getApiKey}", required = true)
@RegisterProviders({
    @RegisterProvider(LoggingFilter.class),
    @RegisterProvider(JacksonFeature.class)
})
public interface ImagesService {

    @POST
    @Path("generations")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    CreateImageResponse generations(DallE2CreateImageRequest createImageRequest);

    @POST
    @Path("generations")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    CreateImageResponse generations(DallE3CreateImageRequest createImageRequest);

    default String getApiKey() {
        String key = ConfigProvider.getConfig().getValue("openai.api.key", String.class);
        System.out.println("key = " + key);
        return "Bearer " + key;
    }
}
