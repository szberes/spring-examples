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

import java.util.HashMap;
import java.util.Map;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryWithTemplate {

    private int counter;

    public void incrementAndThrow() {
        RetryTemplate retryTemplate = new RetryTemplate();

        Map<Class<? extends Throwable>, Boolean> map = new HashMap<>();
        map.put(RuntimeException.class, true);
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5, map));
        retryTemplate.execute((RetryCallback<Void, RuntimeException>) retryContext -> {
                    ++counter;
                    if (counter < 5) {
                        throw new RuntimeException("Some error");
                    }
                    return null;
                }
        );
    }

    public void incrementAndThrowWithRecovery() {
        RetryTemplate retryTemplate = new RetryTemplate();

        Map<Class<? extends Throwable>, Boolean> map = new HashMap<>();
        map.put(RuntimeException.class, true);
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5, map));
        retryTemplate.execute(retryContext -> {
                    ++counter;
                    throw new RuntimeException("Some error");
                }, (RecoveryCallback<Void>) retryContext -> {
                    counter = 42;
                    return null;
                }
        );
    }

    public int getCounter() {
        return counter;
    }
}
