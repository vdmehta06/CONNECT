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
package gov.hhs.fha.nhinc.direct.xdr;

import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;

import javax.xml.ws.WebServiceContext;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

/**
 * The Class DirectXDRWebServiceImpl.
 */
public class DirectXDRWebServiceImpl {

    /** The context. */
    WebServiceContext context = null;

    /**
     * Implementation business object of the JAXB web service interface. Manipulates web services headers, and delegates
     * processing to the orchestration object.
     *
     * @param body the body of the XDR message.
     * @param wsContext the ws context for manipulating ws headers.
     * @return the registry response type
     * @throws Exception the exception
     */
    public RegistryResponseType provideAndRegisterDocumentSet(ProvideAndRegisterDocumentSetRequestType body,
            WebServiceContext wsContext) throws Exception {
        RegistryResponseType resp = null;
        this.context = wsContext;

        DirectHeaderExtractor extractor = new DirectHeaderExtractor();

        SoapDirectEdgeOrchestration orch = new SoapDirectEdgeOrchestration();
        resp = orch.orchestrate(body, extractor.getHeaderProperties(wsContext));
        return resp;
    }
}
