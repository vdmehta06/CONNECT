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
package gov.hhs.fha.nhinc.util.format;

import java.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rayj
 */
public class DocumentClassCodeBuilderTest {

    public DocumentClassCodeBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void BuildListWith3Items() {
        List<String> documentClassCodeList = new ArrayList<String>();
        documentClassCodeList.add("classcode1");
        documentClassCodeList.add("classcode2");
        documentClassCodeList.add("classcode3");
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("('classcode1','classcode2','classcode3')", classCodes);
    }

    @Test
    public void BuildListWith1Item() {
        List<String> documentClassCodeList = new ArrayList<String>();
        documentClassCodeList.add("classcode1");
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("('classcode1')", classCodes);
    }

    @Test
    public void BuildListWith0Items() {
        List<String> documentClassCodeList = new ArrayList<String>();
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("", classCodes);
    }

    @Test
    public void BuildListWithNullInput() {
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(null);
        assertEquals("", classCodes);
    }
}