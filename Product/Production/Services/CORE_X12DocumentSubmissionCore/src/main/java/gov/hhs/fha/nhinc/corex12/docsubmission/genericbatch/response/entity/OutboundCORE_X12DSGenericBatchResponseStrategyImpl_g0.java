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
package gov.hhs.fha.nhinc.corex12.docsubmission.genericbatch.response.entity;

import gov.hhs.fha.nhinc.corex12.docsubmission.genericbatch.response.nhin.proxy.NhinCORE_X12DGenericBatchResponseProxyObjectFactory;
import gov.hhs.fha.nhinc.corex12.docsubmission.genericbatch.response.nhin.proxy.NhinCORE_X12DSGenericBatchResponseProxy;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.orchestration.Orchestratable;
import gov.hhs.fha.nhinc.orchestration.OrchestrationStrategy;
import org.apache.log4j.Logger;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmissionResponse;

/**
 *
 * @author svalluripalli
 */
public class OutboundCORE_X12DSGenericBatchResponseStrategyImpl_g0 implements OrchestrationStrategy {

    private static final Logger LOG = Logger.getLogger(OutboundCORE_X12DSGenericBatchResponseStrategyImpl_g0.class);

    /**
     *
     * @param message
     */
    @Override
    public void execute(Orchestratable message) {
        if (message instanceof OutboundCORE_X12DSGenericBatchResponseOrchestratable) {
            process((OutboundCORE_X12DSGenericBatchResponseOrchestratable) message);
        } else {
            LOG.error("Not an OutboundCORE_X12DSGenericBatchResponseOrchestratable.");
        }
    }

    private void process(OutboundCORE_X12DSGenericBatchResponseOrchestratable message) {
        LOG.info("Begin OutboundCORE_X12DSGenericBatchResponseStrategyImpl_g0.process()");
        NhinCORE_X12DGenericBatchResponseProxyObjectFactory factory = new NhinCORE_X12DGenericBatchResponseProxyObjectFactory();
        NhinCORE_X12DSGenericBatchResponseProxy proxy = factory.getNhinCORE_X12DSGenericBatchResponseProxy();
        COREEnvelopeBatchSubmissionResponse oResponse = proxy.batchSubmitTransaction(message.getRequest(), message.getAssertion(), message.getTarget(), NhincConstants.GATEWAY_API_LEVEL.LEVEL_g0);
        message.setResponse(oResponse);
        LOG.info("End OutboundCORE_X12DSGenericBatchResponseStrategyImpl_g0.process()");
    }
}
