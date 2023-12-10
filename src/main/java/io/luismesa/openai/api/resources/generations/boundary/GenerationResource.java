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
package io.luismesa.openai.api.resources.generations.boundary;

import io.luismesa.openai.api.resources.images.entity.CreateImageResponse;
import io.luismesa.openai.api.resources.images.entity.DallE2CreateImageRequest;
import io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest;
import io.luismesa.openai.api.resources.images.entity.Image;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Math.round;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
@Path("v1/mocked")
public class GenerationResource {

    private static final String FILES_LOCATION = "META-INF/";

    @POST
    @Path("generate/dall-e-2")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public CreateImageResponse generateImages(DallE2CreateImageRequest dallE2CreateImageRequest) throws IOException {
        CreateImageResponse response = new CreateImageResponse();
        response.setCreated(System.currentTimeMillis());
        List<Image> images = GenerationResource.getListOfImages();
        response.setData(images);
        return response;
    }

    @POST
    @Path("generate/dall-e-3")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public CreateImageResponse generateImages(DallE3CreateImageRequest dallE3CreateImageRequest) throws IOException {
        CreateImageResponse response = new CreateImageResponse();
        response.setCreated(System.currentTimeMillis());
        List<Image> images = GenerationResource.getListOfImages();
        response.setData(images);
        return response;
    }

    private static Optional<Image> getRandomPhotoFromLoremPicsum() {
        try {
            long num = (round(1 + Math.random() * 256));
            String numUrl = "https://picsum.photos/id/" + num + "/384/384";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(numUrl))
                    .timeout(Duration.of(1, SECONDS))
                    .GET()
                    .build();
            HttpResponse<Void> response = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NEVER)
                    .build()
                    .send(request, BodyHandlers.discarding());

            if (response.statusCode() == HTTP_MOVED_TEMP) {
                Image found = new Image();
                found.setUrl(numUrl);
                found.setRevisedPrompt("A random photo from Lorem Picsum. (" + num + ")");
                return Optional.ofNullable(found);
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(GenerationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    private static Optional<Image> getLocalPhoto(String name, String description) {
        try {
            InputStream imgInputStream = GenerationResource.class.getClassLoader().getResourceAsStream(FILES_LOCATION + name);
            String imageString = new String(imgInputStream.readAllBytes(), StandardCharsets.UTF_8);
            Image image = new Image();
            image.setB64Json(imageString);
            image.setRevisedPrompt(description);
            return Optional.ofNullable(image);
        } catch (IOException ex) {
            Logger.getLogger(GenerationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    private static List<Image> getListOfImages() {
        record LocalFile(String name, String description) {

        };

        List<Image> internal = Stream
                .of(new LocalFile("img1.b64", "A puppy and his mother sleeping on a fluffy pillow"),
                        new LocalFile("img2.b64", "A cute puppy looking attentively at the camera"))
                .parallel()
                .map(localFile -> GenerationResource.getLocalPhoto(localFile.name(), localFile.description()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        List<Image> external = Stream
                .generate(GenerationResource::getRandomPhotoFromLoremPicsum)
                .filter(Optional::isPresent)
                .limit(3)
                .map(Optional::get)
                .collect(toList());

        List<Image> images = new ArrayList<>(internal);
        images.addAll(external);
        Collections.shuffle(images);
        return images;
    }
}
