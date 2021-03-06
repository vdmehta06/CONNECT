/*
 * Copyright (c) 2009-2015, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.mail;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * Test {@link AbstractMailPoller}.
 */
public class AbstractMailPollerTest {

    private static final String EXCEPTION_MSG = "Arbitrary fake error message";

    /**
     * Test {@link AbstractMailPoller#poll()}.
     * @throws MailClientException on a mail client exception
     */
    @Test
    public void canMailPollerInvokeHandler() throws MailClientException {

        MailReceiver mockMailReceiver = mock(MailReceiver.class);
        MessageHandler mockMessageHandler = mock(MessageHandler.class);

        MailClientException mockException = mock(MailClientException.class);
        when(mockException.getMessage()).thenReturn(EXCEPTION_MSG);

        AbstractMailPoller testMailPoller = new AbstractMailPoller(mockMailReceiver, mockMessageHandler) {
            @Override
            public void handleException(MailClientException e) {
                assertEquals(EXCEPTION_MSG, e.getMessage());
            }
        };

        testMailPoller.poll();
        verify(mockMailReceiver).handleMessages(mockMessageHandler);
        verify(mockException, times(0)).getMessage();

    }

    /**
     * Test {@link AbstractMailPoller#poll()} invokes the override exception handling mechanism.
     * @throws MailClientException
     */
    @Test
    public void canMailPollerHandleException() throws MailClientException {

        MailReceiver mockMailReceiver = mock(MailReceiver.class);
        MessageHandler mockMessageHandler = mock(MessageHandler.class);

        MailClientException mockException = mock(MailClientException.class);
        when(mockException.getMessage()).thenReturn(EXCEPTION_MSG);
        when(mockMailReceiver.handleMessages(mockMessageHandler)).thenThrow(mockException);

        AbstractMailPoller testMailPoller = new AbstractMailPoller(mockMailReceiver, mockMessageHandler) {
            @Override
            public void handleException(MailClientException e) {
                assertEquals(EXCEPTION_MSG, e.getMessage());
            }
        };

        testMailPoller.poll();
        verify(mockMailReceiver).handleMessages(mockMessageHandler);
        verify(mockException).getMessage();
    }
}
