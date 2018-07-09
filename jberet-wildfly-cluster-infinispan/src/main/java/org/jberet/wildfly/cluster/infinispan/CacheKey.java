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

public final class CacheKey implements Serializable{
    private static final long serialVersionUID = 7713646573727432642L;

    private final long jobExecutionId;
    private final long stepExecutionId;
    private final int partitionId;

    public CacheKey(final long jobExecutionId, final long stepExecutionId, final int partitionId) {
        this.jobExecutionId = jobExecutionId;
        this.stepExecutionId = stepExecutionId;
        this.partitionId = partitionId;
    }

    public long getJobExecutionId() {
        return jobExecutionId;
    }

    public long getStepExecutionId() {
        return stepExecutionId;
    }

    public int getPartitionId() {
        return partitionId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CacheKey cacheKey = (CacheKey) o;

        if (jobExecutionId != cacheKey.jobExecutionId) return false;
        if (stepExecutionId != cacheKey.stepExecutionId) return false;
        return partitionId == cacheKey.partitionId;
    }

    @Override
    public int hashCode() {
        int result = (int) (jobExecutionId ^ (jobExecutionId >>> 32));
        result = 31 * result + (int) (stepExecutionId ^ (stepExecutionId >>> 32));
        result = 31 * result + partitionId;
        return result;
    }

    @Override
    public String toString() {
        return "CacheKey{" +
                "jobExecutionId=" + jobExecutionId +
                ", stepExecutionId=" + stepExecutionId +
                ", partitionId=" + partitionId +
                '}';
    }
}
