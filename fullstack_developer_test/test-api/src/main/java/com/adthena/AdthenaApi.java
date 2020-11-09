package com.adthena;

import com.adthena.conf.ApiConfiguration;
import com.adthena.health.AdthenaHealthCheck;
import com.adthena.modules.ResourcesModule;
import com.adthena.resources.AdthenaTodoResource;
import com.hubspot.dropwizard.guicier.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AdthenaApi extends Application<ApiConfiguration> {

    public static void main(String[] args) throws Exception {
        new AdthenaApi().run(args);
    }

    public void run(ApiConfiguration apiConfiguration, Environment environment) throws Exception {

        final AdthenaTodoResource resource = new AdthenaTodoResource();
        environment.jersey().register(resource);

        environment.healthChecks().register("adthena", new AdthenaHealthCheck(apiConfiguration));
    }

    @Override
    public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
        GuiceBundle<ApiConfiguration> guiceBundle = GuiceBundle.defaultBuilder(ApiConfiguration.class)
            .modules(new ResourcesModule())
            .build();

        bootstrap.addBundle(guiceBundle);

        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }
}
