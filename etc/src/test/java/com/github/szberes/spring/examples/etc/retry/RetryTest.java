/*
 * Copyright 2016 Szabolcs Balazs Beres.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.szberes.spring.examples.etc.retry;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes = RetryMain.class)
public class RetryTest {

    @Autowired
    private RetryWithAnnotation retryWithAnnotation;

    @Autowired
    private RetryWithTemplate retryWithTemplate;

    @Test
    public void testIncrementAndThrow() throws Exception {
        retryWithAnnotation.incrementAndThrow();
        assertEquals(5, retryWithAnnotation.getCounter());
    }

    @Test
    public void testWithTemplate() throws Exception {
        retryWithTemplate.incrementAndThrow();
        assertEquals(5, retryWithTemplate.getCounter());
    }

    @Test
    public void testWithTemplateRecoverCallback() throws Exception {
        retryWithTemplate.incrementAndThrowWithRecovery();
        assertEquals(42, retryWithTemplate.getCounter());
    }
}