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
package gov.hhs.fha.nhinc.docsubmission.nhin.deferred.request.proxy20;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.Test;

import ihe.iti.xdr._2007.XDRDeferredRequest20PortType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import gov.hhs.fha.nhinc.aspect.NwhinInvocationEvent;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.docsubmission.DocSubmissionUtils;
import gov.hhs.fha.nhinc.docsubmission.aspect.DocSubmissionBaseEventDescriptionBuilder;
import gov.hhs.fha.nhinc.docsubmission.nhin.deferred.request.proxy20.NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;

/**
 * @author achidamb
 *
 */
public class NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImplTest {

    @SuppressWarnings("unchecked")
    private final CONNECTClient<XDRDeferredRequest20PortType> client = mock(CONNECTClient.class);
    private final DocSubmissionUtils utils = mock(DocSubmissionUtils.class);
    private ProvideAndRegisterDocumentSetRequestType request = mock(ProvideAndRegisterDocumentSetRequestType.class);
    private AssertionType assertion = mock(AssertionType.class);

    @Test
    public void test() {
        NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl impl = getImpl();
        NhinTargetSystemType targetSystem = new NhinTargetSystemType();
        HomeCommunityType hcid = new HomeCommunityType();
        hcid.setHomeCommunityId("1.1");
        targetSystem.setHomeCommunity(hcid);
        impl.provideAndRegisterDocumentSetBRequest20(request, assertion, targetSystem);
        verify(client).enableMtom();
    }

    @Test
    public void hasNwhinInvocationEvent() throws Exception {
        Class<NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl> clazz =
                NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl.class;
        Method method = clazz.getMethod("provideAndRegisterDocumentSetBRequest20",
                ProvideAndRegisterDocumentSetRequestType.class, AssertionType.class,NhinTargetSystemType.class);
        NwhinInvocationEvent annotation = method.getAnnotation(NwhinInvocationEvent.class);
        assertNotNull(annotation);
        assertEquals(DocSubmissionBaseEventDescriptionBuilder.class, annotation.beforeBuilder());
        assertEquals(DocSubmissionBaseEventDescriptionBuilder.class, annotation.afterReturningBuilder());
        assertEquals("Document Submission Deferred Request", annotation.serviceType());
        assertEquals("", annotation.version());
    }

    private NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl getImpl() {
        return new NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl() {
            /*
             * (non-Javadoc)
             *
             * @see gov.hhs.fha.nhinc.docsubmission.nhin.deferred.request.proxy20.
             * NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl
             * #getCONNECTClientSecured(gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor,
             * java.lang.String, gov.hhs.fha.nhinc.common.nhinccommon.AssertionType, java.lang.String, java.lang.String)
             */
            @Override
            protected CONNECTClient<XDRDeferredRequest20PortType> getCONNECTClientSecured(
                    ServicePortDescriptor<XDRDeferredRequest20PortType> portDescriptor, String url,
                    AssertionType assertion, String target, String serviceName) {
                return client;
            }

            /* (non-Javadoc)
             * @see gov.hhs.fha.nhinc.docsubmission.nhin.deferred.request.proxy11.NhinDocSubmissionDeferredRequestProxyWebServiceSecuredImpl#getDocSubmissionUtils()
             */
            @Override
            protected DocSubmissionUtils getDocSubmissionUtils() {
                return utils;
            }
        };
    }

}
