/*
 * Copyright 2017 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.authsvc.client;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 10:37:55 PM
 */
public interface JsonResponseIsErrorTest extends Predicate<Map>{

    @Override
    boolean test(Map json);
}
