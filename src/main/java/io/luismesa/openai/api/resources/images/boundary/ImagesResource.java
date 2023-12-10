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
package io.luismesa.openai.api.resources.images.boundary;

import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.luismesa.openai.api.resources.images.entity.CreateImageResponse;
import io.luismesa.openai.api.resources.images.entity.DallE2CreateImageRequest;
import io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest;
import io.luismesa.openai.api.resources.images.control.ImagesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
//@Stateless
@Path("v1/images")
public class ImagesResource {

    @Inject
    @RestClient
    ImagesService imagesService;

    @POST
    @Path("generate/dall-e-2")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public CreateImageResponse generateImages(DallE2CreateImageRequest createImageRequest) {
        return imagesService.generations(createImageRequest);
    }

    @POST
    @Path("generate/dall-e-3")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public CreateImageResponse generateImages(DallE3CreateImageRequest createImageRequest) {
        return imagesService.generations(createImageRequest);
    }
}
