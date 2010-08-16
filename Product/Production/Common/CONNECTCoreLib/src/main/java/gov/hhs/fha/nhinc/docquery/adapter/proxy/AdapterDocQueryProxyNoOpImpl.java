package gov.hhs.fha.nhinc.docquery.adapter.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdapterDocQueryProxyNoOpImpl implements AdapterDocQueryProxy
{
    private static Log log = LogFactory.getLog(AdapterDocQueryProxyNoOpImpl.class);
    public AdhocQueryResponse respondingGatewayCrossGatewayQuery(AdhocQueryRequest msg, AssertionType assertion)
    {
        log.debug("Using NoOp Implementation for Adapter Doc Query Service");
        return new AdhocQueryResponse();
    }
}