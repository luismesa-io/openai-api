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
package io.luismesa.openai.api.resources.dimensions.boundary;

import io.luismesa.openai.api.resources.images.entity.DallE2CreateImageRequest.DallE2ImageSize;
import io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest.DallE3ImageFormat;
import io.luismesa.openai.api.resources.images.entity.ImageGenerationModel;
import static io.luismesa.openai.api.resources.images.entity.ImageGenerationModel.DALL_E_2;
import static io.luismesa.openai.api.resources.images.entity.ImageGenerationModel.DALL_E_3;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
@Path("v1/dimensions")
public class DimensionsResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{model}")
    public List<Map<String, String>> getDimensions(@PathParam("model") ImageGenerationModel model) {
        return switch (model) {
            case DALL_E_2 ->
                EnumSet.allOf(DallE2ImageSize.class)
                .stream()
                .map(d -> {
                    HashMap<String, String> obj = new HashMap<>();
                    obj.put("key", d.name());
                    obj.put("value", d.toString());
                    return obj;
                })
                .collect(Collectors.toList());
            case DALL_E_3 ->
                EnumSet.allOf(DallE3ImageFormat.class)
                .stream()
                .map(d -> {
                    HashMap<String, String> obj = new HashMap<>();
                    obj.put("key", d.name());
                    obj.put("value", d.toString());
                    return obj;
                })
                .collect(Collectors.toList());
        };
    }
}
