/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.wildfly.cluster.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.jberet.creation.ArtifactFactoryWrapper;
import org.jberet.operations.AbstractJobOperator;
import org.jberet.repository.JobRepository;
import org.jberet.spi.ArtifactFactory;
import org.jberet.spi.BatchEnvironment;
import org.jberet.spi.PartitionInfo;
import org.jberet.wildfly.cluster.jms.JmsPartitionResource;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class PartitionSingletonBean {

    @Resource(name = "jms/connectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "jms/stopRequestTopic")
    private Topic stopRequestTopic;

    @Resource(name = "jms/partitionQueue")
    private Queue partitionQueue;

    private BatchEnvironment batchEnvironment;
    private JobRepository jobRepository;
    private ArtifactFactory artifactFactory;

    @PostConstruct
    private void postConstruct() {
        final AbstractJobOperator jobOperator = JmsPartitionResource.getJobOperator();
        batchEnvironment = jobOperator.getBatchEnvironment();
        jobRepository = jobOperator.getJobRepository();
        artifactFactory = new ArtifactFactoryWrapper(batchEnvironment.getArtifactFactory());
    }

    public void runPartition(final PartitionInfo partitionInfo) {
        JmsPartitionResource.runPartition(partitionInfo, batchEnvironment, jobRepository, artifactFactory,
                connectionFactory, partitionQueue, stopRequestTopic);
    }
}
