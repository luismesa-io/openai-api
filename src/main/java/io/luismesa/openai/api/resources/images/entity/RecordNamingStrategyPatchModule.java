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

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecordNamingStrategyPatchModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    @Override
    public void setupModule(SetupContext context) {
        context.addValueInstantiators(new ValueInstantiatorsModifier());
        super.setupModule(context);
    }

    /**
     * Remove when the following issue is resolved:
     * <a href="https://github.com/FasterXML/jackson-databind/issues/2992">Properties naming strategy do not work with Record #2992</a>
     */
    private static class ValueInstantiatorsModifier extends ValueInstantiators.Base {

        @Override
        public ValueInstantiator findValueInstantiator(
                DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator
        ) {
            if (!beanDesc.getBeanClass().isRecord() || !(defaultInstantiator instanceof StdValueInstantiator) || !defaultInstantiator.canCreateFromObjectWith()) {
                return defaultInstantiator;
            }
            Map<String, BeanPropertyDefinition> map = beanDesc.findProperties().stream().collect(Collectors.toMap(p -> p.getInternalName(), Function.identity()));
            SettableBeanProperty[] renamedConstructorArgs = Arrays.stream(defaultInstantiator.getFromObjectArguments(config))
                    .map(p -> {
                        BeanPropertyDefinition prop = map.get(p.getName());
                        return prop != null ? p.withName(prop.getFullName()) : p;
                    })
                    .toArray(SettableBeanProperty[]::new);

            return new PatchedValueInstantiator((StdValueInstantiator) defaultInstantiator, renamedConstructorArgs);
        }
    }

    private static class PatchedValueInstantiator extends StdValueInstantiator {

        private static final long serialVersionUID = 1L;

        protected PatchedValueInstantiator(StdValueInstantiator src, SettableBeanProperty[] constructorArguments) {
            super(src);
            _constructorArguments = constructorArguments;
        }
    }
}
