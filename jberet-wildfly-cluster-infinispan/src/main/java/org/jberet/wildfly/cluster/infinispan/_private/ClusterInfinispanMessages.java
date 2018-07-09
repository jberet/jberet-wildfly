/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.wildfly.cluster.infinispan._private;

import org.jboss.logging.Messages;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;
import org.jboss.logging.annotations.ValidIdRange;

@MessageBundle(projectCode = "JBERET")
@ValidIdRange(min = 76000, max = 76499)
public interface ClusterInfinispanMessages {
    ClusterInfinispanMessages MESSAGES = Messages.getBundle(ClusterInfinispanMessages.class);

    @Message(id = 76000, value = "Failed to lookup %s")
    IllegalStateException failedToLookup(@Cause Throwable throwable, String name);

    @Message(id = 76001, value = "Failed instantiate naming context")
    IllegalStateException failedToNewNamingContext(@Cause Throwable throwable);

    @Message(id = 76002, value = "Failed in JMS operation")
    IllegalStateException failedInJms(@Cause Throwable throwable);

    @Message(id = 76003, value = "Failed to get job operator")
    IllegalStateException failedToGetJobOperator();

}
