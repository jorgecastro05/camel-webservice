package com.fuse;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRoute extends CamelSpringTestSupport {

    private String getMockEndpoint() {
        return "mock:finish";
    }

    @Test
    public void testRoute() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint(getMockEndpoint());
        mockEndpoint.expectedMinimumMessageCount(1);
        context.getRouteDefinition("cxf-to-queue").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveAddLast().to(getMockEndpoint());
            }
        });

        mockEndpoint.assertIsSatisfied();
    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}