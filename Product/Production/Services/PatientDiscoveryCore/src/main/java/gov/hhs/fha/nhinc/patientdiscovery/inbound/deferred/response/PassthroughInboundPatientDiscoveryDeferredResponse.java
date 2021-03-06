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
package gov.hhs.fha.nhinc.patientdiscovery.inbound.deferred.response;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.generic.GenericFactory;
import gov.hhs.fha.nhinc.patientdiscovery.PatientDiscoveryAuditLogger;
import gov.hhs.fha.nhinc.patientdiscovery.PatientDiscoveryAuditor;
import gov.hhs.fha.nhinc.patientdiscovery.adapter.deferred.response.proxy.AdapterPatientDiscoveryDeferredRespProxy;

import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201306UV02;

/**
 * @author akong
 *
 */
public class PassthroughInboundPatientDiscoveryDeferredResponse extends AbstractInboundPatientDiscoveryDeferredResponse {

    private final PatientDiscoveryAuditor auditLogger;

    /**
     * Constructor.
     */
    public PassthroughInboundPatientDiscoveryDeferredResponse() {
        auditLogger = new PatientDiscoveryAuditLogger();
    }

    /**
     * Constructor with dependency injection arguments.
     *
     * @param proxyFactory
     * @param auditLogger
     */
    public PassthroughInboundPatientDiscoveryDeferredResponse(
            GenericFactory<AdapterPatientDiscoveryDeferredRespProxy> proxyFactory, PatientDiscoveryAuditor auditLogger) {
        super(proxyFactory);
        this.auditLogger = auditLogger;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.hhs.fha.nhinc.patientdiscovery.inbound.deferred.response.AbstractInboundPatientDiscoveryDeferredResponse#
     * process(org.hl7.v3.PRPAIN201306UV02, gov.hhs.fha.nhinc.common.nhinccommon.AssertionType)
     */
    @Override
    MCCIIN000002UV01 process(PRPAIN201306UV02 request, AssertionType assertion) {
        MCCIIN000002UV01 response = sendToAdapter(request, assertion);

        return response;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.hhs.fha.nhinc.patientdiscovery.inbound.deferred.response.AbstractInboundPatientDiscoveryDeferredResponse#
     * getAuditLogger()
     */
    @Override
    PatientDiscoveryAuditor getAuditLogger() {
        return auditLogger;
    }

}
