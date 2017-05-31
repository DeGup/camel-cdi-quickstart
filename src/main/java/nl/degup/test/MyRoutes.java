/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package nl.degup.test;

import javax.inject.Inject;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;

import static org.apache.camel.LoggingLevel.INFO;

/**
 * Configures all our Camel routes, components, endpoints and beans
 */
@ContextName("myCamel")
public class MyRoutes extends RouteBuilder {

    @Inject
    @Uri("timer:foo?period=5000")
    private Endpoint inputEndpoint;

    @Inject
    @Uri("log:output")
    private Endpoint resultEndpoint;

    @Override
    public void configure() throws Exception {
        // you can configure the route rule with Java DSL here

        getContext().addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));

        from(inputEndpoint)
            .beanRef("counterBean")
            .to("activemq:queue:test");

        from("activemq:queue:test")
                .log(INFO,"Got message on queue: ${body}")
                .to(resultEndpoint);
    }

}
