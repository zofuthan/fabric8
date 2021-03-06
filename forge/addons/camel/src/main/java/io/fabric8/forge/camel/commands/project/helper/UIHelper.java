/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.forge.camel.commands.project.helper;

import java.util.Arrays;
import java.util.Objects;

import org.jboss.forge.addon.ui.input.InputComponent;
import org.jboss.forge.addon.ui.input.InputComponentFactory;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UISelectOne;

public final class UIHelper {

    /**
     * Create the input widget to use for the given option.
     *
     * @return the input widget, or <tt>null</tt> if not supported because of inputClazz not possible to be used
     */
    public static InputComponent createUIInput(InputComponentFactory factory, String name, Class inputClazz,
                                               String required, String defaultValue, String enums, String description) {

        InputComponent input;
        if (enums != null) {
            UISelectOne ui = factory.createSelectOne(name, inputClazz);
            if (defaultValue != null) {
                ui.setDefaultValue(defaultValue);
            }
            // the enums are comma separated
            String[] values = enums.split(",");
            ui.setValueChoices(Arrays.asList(values));

            input = ui;
        } else {
            UIInput ui = factory.createInput(name, inputClazz);
            if (defaultValue != null) {
                ui.setDefaultValue(defaultValue);
            }

            input = ui;
        }

        if (input != null) {
            if (Objects.equals("true", required)) {
                input.setRequired(true);
            }
            input.setLabel(name);
            // must use an empty description otherwise the UI prints null
            input.setDescription(description != null ? description : "");
        }

        return input;
    }

}
