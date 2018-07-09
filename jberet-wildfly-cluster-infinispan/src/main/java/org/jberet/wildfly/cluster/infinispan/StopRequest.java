/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.wildfly.cluster.infinispan;

import java.io.Serializable;

public final class StopRequest implements Serializable {
    private static final long serialVersionUID = 631455916595267075L;

    private static final StopRequest instance = new StopRequest();

    private StopRequest() {
    }

    public static StopRequest getInstance() {
        return instance;
    }
}
