/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.controller.exceptionhandler;

import io.dataspaceconnector.common.exception.SubscriptionProcessingException;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller for handling {@link SubscriptionProcessingException}.
 */
@ControllerAdvice
@Log4j2
@Order(1)
public final class SubscriptionProcessingExceptionHandler {
    /**
     * Handles thrown {@link SubscriptionProcessingException}.
     *
     * @param exception The thrown exception.
     * @return Response entity with code 400.
     */
    @ExceptionHandler(SubscriptionProcessingException.class)
    public ResponseEntity<JSONObject> handleSubscriptionProcessingException(
            final SubscriptionProcessingException exception) {
        if (log.isDebugEnabled()) {
            log.debug("Could not process subscription [exception=({})]", exception == null
                    ? "" : exception.getMessage(), exception);
        }

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var body = new JSONObject();
        body.put("message", exception == null ? "" : exception.getMessage());

        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
}
