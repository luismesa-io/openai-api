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
import static io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest.DallE3ImageFormat.SQUARE;
import static io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest.DallE3ImageQuality.STANDARD;
import static io.luismesa.openai.api.resources.images.entity.DallE3CreateImageRequest.DallE3ImageStyle.VIVID;
import static io.luismesa.openai.api.resources.images.entity.ImageGenerationModel.DALL_E_3;
import static io.luismesa.openai.api.resources.images.entity.ResponseFormat.URL;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
//import jakarta.validation.constraints.NotNull;
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
public record DallE3CreateImageRequest(
        @JsonProperty(required = true)
        String prompt,
        ImageGenerationModel model,
        int n,
        DallE3ImageQuality quality,
        @JsonProperty("response_format")
        @JsonbProperty("response_format")
        ResponseFormat responseFormat,
        DallE3ImageFormat size,
        DallE3ImageStyle style,
        String user) {

    @JsonCreator
    public DallE3CreateImageRequest(
            //@Size(min = 1, max = 4000)
            @JsonProperty(required = true) String prompt,
            ImageGenerationModel model,
            int n,
            DallE3ImageQuality quality,
            @JsonProperty("response_format") ResponseFormat responseFormat,
            DallE3ImageFormat size,
            DallE3ImageStyle style,
            String user) {
        this.prompt = Optional.ofNullable(prompt).filter(p -> p.length() > 0 && p.length() < 4001).orElse("A siamese cat looking at the camera"); //A text description of the desired image(s). The maximum length is 4000 characters for dall-e-3.
        this.model = Optional.ofNullable(model).filter(m -> m.equals(DALL_E_3)).orElse(DALL_E_3); //The model to use for image generation.
        this.n = 1; //The number of images to generate. For dall-e-3, only n=1 is supported.
        this.responseFormat = Optional.ofNullable(responseFormat).orElse(URL); //The format in which the generated images are returned. Must be one of url or b64_json.
        this.quality = Optional.ofNullable(quality).orElse(STANDARD); //The quality of the image that will be generated. hd creates images with finer details and greater consistency across the image. This param is only supported for dall-e-3.
        this.size = Optional.ofNullable(size).orElse(SQUARE); //The size of the generated images. Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3 models.
        this.style = Optional.ofNullable(style).orElse(VIVID); //The style of the generated images. Must be one of vivid or natural. Vivid causes the model to lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less hyper-real looking images. This param is only supported for dall-e-3.
        this.user = Optional.ofNullable(user).orElse("");
    }

    @JsonSerialize
    public static enum DallE3ImageQuality {
        @JsonProperty("standard")
        STANDARD("standard"),
        @JsonProperty("hd")
        HD("hd");
        private static final Map<String, DallE3ImageQuality> OPTIONS = Arrays.stream(DallE3ImageQuality.values()).collect(Collectors.toMap(Objects::toString, Function.identity()));
        private final String key;

        DallE3ImageQuality(String key) {
            this.key = key;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.key;
        }

        @JsonCreator
        public static DallE3ImageQuality fromString(String key) {
            return Optional.ofNullable(OPTIONS.get(key)).orElseGet(() -> OPTIONS.get(key.toUpperCase()));
        }
    }

    @JsonSerialize
    public static enum DallE3ImageFormat {
        @JsonProperty("1024x1024")
        SQUARE("1024x1024"),
        @JsonProperty("1792x1024")
        LANDSCAPE("1792x1024"),
        @JsonProperty("1024x1792")
        PORTRAIT("1024x1792");
        private static final Map<String, DallE3ImageFormat> SIZES = Arrays.stream(DallE3ImageFormat.values()).collect(Collectors.toMap(Objects::toString, Function.identity()));
        private final String key;

        DallE3ImageFormat(String key) {
            this.key = key;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.key;
        }

        @JsonCreator
        public static DallE3ImageFormat fromString(String key) {
            return Optional.ofNullable(SIZES.get(key)).orElseGet(() -> SIZES.get(key.toUpperCase()));
        }
    }

    @JsonSerialize
    public static enum DallE3ImageStyle {
        @JsonProperty("vivid")
        VIVID("vivid"),
        @JsonProperty("natural")
        NATURAL("natural");
        private static final Map<String, DallE3ImageStyle> STYLES = Arrays.stream(DallE3ImageStyle.values()).collect(Collectors.toMap(Objects::toString, Function.identity()));
        private final String key;

        DallE3ImageStyle(String key) {
            this.key = key;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.key;
        }

        @JsonCreator
        public static DallE3ImageStyle fromString(String key) {
            return Optional.ofNullable(STYLES.get(key)).orElseGet(() -> STYLES.get(key.toUpperCase()));
        }
    }
}
