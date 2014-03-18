/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.nativebinaries.toolchain.internal.tools;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.internal.ClosureBackedAction;
import org.gradle.internal.Actions;
import org.gradle.nativebinaries.toolchain.internal.ToolType;

import java.util.ArrayList;
import java.util.List;

public class DefaultTool implements GccToolInternal {
    private final ToolType toolType;
    private String executable;
    private List<Action<? super List<String>>> argActions = new ArrayList<Action<? super List<String>>>();

    public DefaultTool(ToolType toolType, String defaultExecutable) {
        this.toolType = toolType;
        this.executable = defaultExecutable;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String file) {
        executable = file;
    }

    // TODO:DAZ Decorate class and use an action parameter
    public void withArguments(Closure arguments) {
        Action<List<String>> action = new ClosureBackedAction<List<String>>(arguments);
        argActions.add(action);
    }

    // TODO:Rene Decorate class instead of method overloading
    public void withArguments(Action<List<String>> action) {
        argActions.add(action);
    }

    public Action<List<String>> getArgAction() {
        return Actions.composite(argActions);
    }
}
