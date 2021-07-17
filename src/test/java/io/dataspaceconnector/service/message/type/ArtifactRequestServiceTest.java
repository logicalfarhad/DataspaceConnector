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
package io.dataspaceconnector.service.message.type;

import de.fraunhofer.iais.eis.ArtifactRequestMessage;
import de.fraunhofer.iais.eis.ArtifactResponseMessage;
import de.fraunhofer.iais.eis.ArtifactResponseMessageBuilder;
import de.fraunhofer.iais.eis.DynamicAttributeTokenBuilder;
import de.fraunhofer.iais.eis.TokenFormat;
import de.fraunhofer.iais.eis.util.Util;
import de.fraunhofer.ids.messaging.protocol.http.IdsHttpService;
import io.dataspaceconnector.exception.MessageResponseException;
import io.dataspaceconnector.model.message.ArtifactRequestMessageDesc;
import io.dataspaceconnector.service.ids.ConnectorService;
import io.dataspaceconnector.service.ids.DeserializationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.datatype.DatatypeFactory;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ArtifactRequestService.class})
class ArtifactRequestServiceTest {

    @MockBean
    private ConnectorService connectorService;

    @MockBean
    private IdsHttpService idsHttpService;

    @MockBean
    private DeserializationService deserializationService;

    @Autowired
    private ArtifactRequestService requestService;

    @Test
    public void buildMessage_null_throwsIllegalArgumentException() {
        /* ARRANGE */
        // Nothing to arrange here.

        /* ACT & ASSERT */
        assertThrows(IllegalArgumentException.class, () -> requestService.buildMessage(null));
    }

    @Test
    public void buildMessage_validDesc_returnValidMessage() {
        /* ARRANGE */
        final var desc = new ArtifactRequestMessageDesc();
        desc.setRecipient(URI.create("https://recipient"));
        desc.setRequestedArtifact(URI.create("https://artifact"));
        desc.setTransferContract(URI.create("https://transferContract"));

        final var connectorId = URI.create("https://connector");
        final var modelVersion = "4.0.0";
        final var token = new DynamicAttributeTokenBuilder()
                ._tokenFormat_(TokenFormat.OTHER)._tokenValue_("").build();
        Mockito.when(connectorService.getConnectorId()).thenReturn(connectorId);
        Mockito.when(connectorService.getOutboundModelVersion()).thenReturn(modelVersion);
        Mockito.when(connectorService.getCurrentDat()).thenReturn(token);

        /* ACT */
        final var result = (ArtifactRequestMessage) requestService.buildMessage(desc);

        /* ASSERT */
        assertEquals(1, result.getRecipientConnector().size());
        assertEquals(desc.getRecipient(), result.getRecipientConnector().get(0));
        assertEquals(desc.getRequestedArtifact(), result.getRequestedArtifact());
        assertEquals(desc.getTransferContract(), result.getTransferContract());
        assertEquals(connectorId, result.getIssuerConnector());
        assertEquals(modelVersion, result.getModelVersion());
        assertEquals(token, result.getSecurityToken());
    }

    @Test
    public void validateResponse_invalidResponseMessage_throwsMessageResponseException() {
        /* ARRANGE */
        final var header = getResponseMessage().toRdf();
        final var payload = "EMPTY";
        final var map = new HashMap<String, String>(){{
            put("header", header);
            put("payload", payload);
        }};

        /* ACT & ASSERT */
        assertThrows(MessageResponseException.class, () -> requestService.validateResponse(map));
    }

    @SneakyThrows
    public ArtifactResponseMessage getResponseMessage() {
        final var calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        final var xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        final var connectorId = URI.create("https://connector");
        final var modelVersion = "4.0.0";
        final var token = new DynamicAttributeTokenBuilder()
                ._tokenFormat_(TokenFormat.OTHER)._tokenValue_("").build();
        final var uri = URI.create("https://object");
        return new ArtifactResponseMessageBuilder()
                ._securityToken_(token)
                ._correlationMessage_(uri)
                ._issued_(xmlCalendar)
                ._issuerConnector_(connectorId)
                ._modelVersion_(modelVersion)
                ._senderAgent_(connectorId)
                ._recipientConnector_(Util.asList(uri))
                ._transferContract_(uri)
                .build();
    }
}