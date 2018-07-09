/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.wildfly.cluster.jms;

import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.jberet.runtime.AbstractStepExecution;
import org.jberet.runtime.PartitionExecutionImpl;
import org.jberet.spi.PartitionWorker;
import org.jberet.wildfly.cluster.jms._private.ClusterJmsLogger;

public class JmsPartitionWorker implements PartitionWorker {
    private final ConnectionFactory connectionFactory;
    private final Queue partitionQueue;
    private final JMSContext stopRequestTopicContext;

    public JmsPartitionWorker(final ConnectionFactory connectionFactory,
                              final Queue partitionQueue,
                              final JMSContext stopRequestTopicContext) {
        this.connectionFactory = connectionFactory;
        this.partitionQueue = partitionQueue;
        this.stopRequestTopicContext = stopRequestTopicContext;
    }

    @Override
    public void reportData(final Serializable data,
                           final AbstractStepExecution partitionExecution) throws Exception {
        final long stepExecutionId = partitionExecution.getStepExecutionId();

        try (JMSContext partitionQueueContext = connectionFactory.createContext()) {
            final ObjectMessage message = partitionQueueContext.createObjectMessage(data);
            message.setStringProperty(JmsPartitionResource.MESSAGE_TYPE_KEY, JmsPartitionResource.MESSAGE_TYPE_RESULT);
            message.setLongProperty(JmsPartitionResource.MESSAGE_STEP_EXECUTION_ID_KEY, stepExecutionId);
            partitionQueueContext.createProducer().send(partitionQueue, message);
        }

        ClusterJmsLogger.LOGGER.sendCollectorData(stepExecutionId,
                ((PartitionExecutionImpl) partitionExecution).getPartitionId(), data);
    }

    @Override
    public void partitionDone(final AbstractStepExecution partitionExecution) throws Exception {
        reportData(partitionExecution, partitionExecution);
        stopRequestTopicContext.close();
    }

}
