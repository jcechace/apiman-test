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

package io.apiman.test.integration.rest.apis.metrics;

import static org.junit.Assert.assertEquals;

import io.apiman.manager.api.beans.metrics.ResponseStatsPerPlanBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author jkaspar
 */
public class PlanResponseStatsMetricsIT extends AbstractMetricsIT {

    @Test
    public void requestsAfterIntervalAreNotIncluded() throws Exception {
        ResponseStatsPerPlanBean metricsBefore = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);
        recordFailedRequests(1, apiKey_Client1v1);
        recordSuccessfulRequests(1, apiKey_Client1v1);
        TimeUnit.SECONDS.sleep(TIME_DELAY);
        ResponseStatsPerPlanBean metricsAfter = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests",
                metricsBefore.getData().get(plan.getId()).getTotal(),
                metricsAfter.getData().get(plan.getId()).getTotal());
    }

    @Test
    public void numberOfFailuresForEachPlanIsCorrect() throws Exception {
        ResponseStatsPerPlanBean metrics = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of failed requests for plan", PLAN1_FAIL,
                metrics.getData().get(plan.getId()).getFailures());

        assertEquals("Unexpected number of failed requests for plan2", PLAN2_FAIL,
                metrics.getData().get(plan2.getId()).getFailures());
    }

    @Test
    public void numberOfTotalRequestsForEachPlanIsCorrect() throws Exception {
        ResponseStatsPerPlanBean metrics = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests for plan", PLAN1_SUCC + PLAN1_FAIL,
                metrics.getData().get(plan.getId()).getTotal());

        assertEquals("Unexpected number of total requests for plan2", PLAN2_SUCC + PLAN2_FAIL,
                metrics.getData().get(plan2.getId()).getTotal());
    }
}
