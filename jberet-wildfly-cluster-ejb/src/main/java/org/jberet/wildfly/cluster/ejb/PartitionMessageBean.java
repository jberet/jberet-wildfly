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

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jberet.spi.PartitionInfo;
import org.jberet.wildfly.cluster.jms._private.ClusterJmsMessages;

@MessageDriven(name = "PartitionMessageBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/partitionQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type = 'P'")
})
@TransactionManagement(TransactionManagementType.BEAN)
public class PartitionMessageBean implements MessageListener {
    @Inject
    private PartitionSingletonBean partitionSingletonBean;

    @Override
    public void onMessage(final Message message) {
        final PartitionInfo partitionInfo;
        try {
            partitionInfo = message.getBody(PartitionInfo.class);
        } catch (JMSException e) {
            throw ClusterJmsMessages.MESSAGES.failedInJms(e);
        }
        partitionSingletonBean.runPartition(partitionInfo);
    }

}
