/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.patientdiscovery.adapter.deferred.request.queue.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201305UV02;

/**
 * This is the proxy interface for the Adapter Patient Discovery Async Reuest Queue.
 * 
 * @author JHOPPESC, Les westberg
 */
public interface AdapterPatientDiscoveryAsyncReqQueueProxy
{
    public MCCIIN000002UV01 addPatientDiscoveryAsyncReq(PRPAIN201305UV02 request, AssertionType assertion);
}