/*
 * Copyright 2016 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.test.integration.rest.plugins.policies.corsplugin;

import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

/**
 * @author jkaspar
 */
public class CORSApiPolicyIT extends AbstractCORSPolicyIT {

    @ApiVersion(api = "api", policies = @Policies(value = "plugins/cors_001", params = {"error", "false"}))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private static String endpoint;

    @ApiVersion(api = "api", version = "CorsValidation",
        policies = @Policies(value = "plugins/cors_001", params = {"error", "true"}))
    private static ApiVersionBean corsValidationApiVersion;

    @ManagedEndpoint("corsValidationApiVersion")
    private static String corsValidationEndpoint;

    @Override
    protected String getResourceURL() {
        return endpoint;
    }

    @Override
    protected String getCorsValidationResourceURL() {
        return corsValidationEndpoint;
    }
}
