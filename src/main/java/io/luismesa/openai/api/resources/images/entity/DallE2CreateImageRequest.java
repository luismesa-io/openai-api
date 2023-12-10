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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import static io.luismesa.openai.api.resources.images.entity.DallE2CreateImageRequest.DallE2ImageSize.LARGE;
import static io.luismesa.openai.api.resources.images.entity.ImageGenerationModel.DALL_E_2;
import static io.luismesa.openai.api.resources.images.entity.ResponseFormat.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Luis Daniel Mesa Velásquez {@literal <admin@luismesa.io>}
 */
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record DallE2CreateImageRequest(
        //@NotNull
        //@Size(min = 1, max = 1000)
        @JsonProperty(required = true)
        String prompt,
        ImageGenerationModel model,
        //@Size(min = 1, max = 10)
        int n,
        @JsonProperty("response_format")
        ResponseFormat response_format,
        DallE2ImageSize size,
        String user) {

    @JsonCreator
    public DallE2CreateImageRequest(
            //@NotNull @Size(min = 1, max = 1000)
            @JsonProperty(required = true) String prompt,
            //@NotNull
            ImageGenerationModel model,
            //@NotNull @Size(min = 1, max = 10)
            int n,
            //@NotNull
            @JsonProperty("response_format") ResponseFormat response_format,
            //@NotNull
            DallE2ImageSize size, String user) {
        this.prompt = Optional.ofNullable(prompt).filter(p -> p.length() > 0 && p.length() < 1001).orElse("A siamese cat looking at the camera"); //A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2.
        this.model = Optional.ofNullable(model).filter(m -> m.equals(DALL_E_2)).orElse(DALL_E_2); //The model to use for image generation.
        this.n = (n > 0 && n < 11) ? n : 1; //The number of images to generate. Must be between 1 and 10.
        this.response_format = Optional.ofNullable(response_format).orElse(URL); //The format in which the generated images are returned. Must be one of url or b64_json.
        this.size = Optional.ofNullable(size).orElse(LARGE); //The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024 for dall-e-2.
        this.user = Optional.ofNullable(user).orElse(""); //A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
    }

    public static enum DallE2ImageSize {

        @JsonProperty("256x256")
        SMALL("256x256"),
        @JsonProperty("512x512")
        MEDIUM("512x512"),
        @JsonProperty("1024x1024")
        LARGE("1024x1024");

        private static final Map<String, DallE2ImageSize> SIZES = Arrays.stream(DallE2ImageSize.values()).collect(Collectors.toMap(Objects::toString, Function.identity()));

        private final String key;

        DallE2ImageSize(String key) {
            this.key = key;
        }

        @Override
        @JsonValue
        public String toString() {
            return this.key;
        }

        @JsonCreator
        public static DallE2ImageSize fromString(String key) {
            return SIZES.get(key);
        }

    }

}
