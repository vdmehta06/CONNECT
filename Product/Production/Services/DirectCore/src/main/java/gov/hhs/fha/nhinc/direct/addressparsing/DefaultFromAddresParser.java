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
package gov.hhs.fha.nhinc.direct.addressparsing;

import gov.hhs.fha.nhinc.direct.DirectException;

import java.net.URI;
import java.net.URISyntaxException;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nhindirect.xd.common.DirectDocuments;
import org.nhindirect.xd.transform.parse.ParserHL7;

public class DefaultFromAddresParser implements FromAddressParser {

    private static final Logger LOG = Logger
            .getLogger(DefaultFromAddresParser.class);

    @Override
    public Address parse(String addresses, DirectDocuments documents) {
        Address address = null;
        String replyEmail = null;

        // Get a reply address (first check direct:from header, then
        // go to authorPerson)
        if (StringUtils.isNotBlank(addresses)) {
            try {
                replyEmail = (new URI(addresses)).getSchemeSpecificPart();
            } catch (URISyntaxException e) {
                LOG.error(
                        "Unable to parse Direct From header, attempting to parse XDR author telecom.",
                        e);
            }
        } else {
            if (null != documents && null != documents.getSubmissionSet()) {
                replyEmail = documents.getSubmissionSet()
                        .getAuthorTelecommunication();
                replyEmail = ParserHL7.parseXTN(replyEmail);
            }
        }

        try {
            if (replyEmail != null) {
                address = new InternetAddress(replyEmail);
            }
        } catch (AddressException e) {
            String errorMesssage = "Unable to create InternetAddress from direct from address.";
            throw new DirectException(errorMesssage, e);
        }

        return address;
    }
}
