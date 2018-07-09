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

import org.infinispan.metadata.Metadata;
import org.infinispan.notifications.cachelistener.filter.CacheEventFilter;
import org.infinispan.notifications.cachelistener.filter.EventType;
import org.jberet.spi.PartitionInfo;

public final class PartitionResultFilter implements CacheEventFilter<CacheKey, Object>, Serializable {
    private static final long serialVersionUID = -5075723418649549858L;

    private final long stepExecutionId;

    public PartitionResultFilter(final long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    @Override
    public boolean accept(final CacheKey key,
                          final Object oldValue,
                          final Metadata oldMetadata,
                          final Object newValue,
                          final Metadata newMetadata,
                          final EventType eventType) {
        return !(newValue instanceof StopRequest) &&
                !(newValue instanceof PartitionInfo) &&
                stepExecutionId == key.getStepExecutionId();
    }
}
