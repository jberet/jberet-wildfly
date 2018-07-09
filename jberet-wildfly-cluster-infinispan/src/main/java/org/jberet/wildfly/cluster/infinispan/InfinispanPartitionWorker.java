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

import org.infinispan.Cache;
import org.jberet.runtime.AbstractStepExecution;
import org.jberet.runtime.PartitionExecutionImpl;
import org.jberet.spi.PartitionWorker;
import org.jberet.wildfly.cluster.infinispan._private.ClusterInfinispanLogger;

public class InfinispanPartitionWorker implements PartitionWorker {
    private final Cache<CacheKey, Object> cache;

    private final CacheKey cacheKey;

    private final PartitionStopListener partitionStopListener;

    public InfinispanPartitionWorker(final Cache<CacheKey, Object> cache,
                                     final CacheKey cacheKey,
                                     final PartitionStopListener partitionStopListener) {
        this.cache = cache;
        this.cacheKey = cacheKey;
        this.partitionStopListener = partitionStopListener;
    }

    @Override
    public void reportData(final Serializable data,
                           final AbstractStepExecution partitionExecution) throws Exception {
        final long stepExecutionId = partitionExecution.getStepExecutionId();
        cache.put(cacheKey, data);

        ClusterInfinispanLogger.LOGGER.sendCollectorData(stepExecutionId,
                ((PartitionExecutionImpl) partitionExecution).getPartitionId(), data);
    }

    @Override
    public void partitionDone(final AbstractStepExecution partitionExecution) throws Exception {
        reportData(partitionExecution, partitionExecution);
        cache.removeListener(partitionStopListener);
    }
}
