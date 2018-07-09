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

import org.jberet.runtime.context.StepContextImpl;
import org.jberet.runtime.runner.StepExecutionRunner;
import org.jberet.spi.PartitionHandler;
import org.jberet.spi.PartitionHandlerFactory;

public class JmsPartitionHandlerFactory implements PartitionHandlerFactory {
    @Override
    public PartitionHandler createPartitionHandler(final StepContextImpl stepContext,
                                                   final StepExecutionRunner stepExecutionRunner) {
        return new JmsPartitionHandler(stepContext);
    }
}
