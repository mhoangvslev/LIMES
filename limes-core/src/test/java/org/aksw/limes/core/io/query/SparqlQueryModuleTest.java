/*
 * LIMES Core Library - LIMES – Link Discovery Framework for Metric Spaces.
 * Copyright © 2011 Data Science Group (DICE) (ngonga@uni-paderborn.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.limes.core.io.query;

import org.aksw.limes.core.io.cache.ACache;
import org.aksw.limes.core.io.cache.HybridCache;
import org.aksw.limes.core.io.config.KBInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class SparqlQueryModuleTest {

    HashMap<String, String> prefixes;
    LinkedHashMap<String, Map<String, String>> functions;
    KBInfo kbInfo;

    @Before
    public void init() {
        prefixes = new HashMap<>();
        prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixes.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixes.put("dbpo", "http://dbpedia.org/ontology/");

        functions = new LinkedHashMap<>();

        kbInfo = new KBInfo(
                "DBpedia",                                                       //String id
                "http://dbpedia.org/sparql",                                     //String endpoint
                null,                                                            //String graph
                "?x",                                                            //String var
                new ArrayList<String>(Arrays.asList("rdfs:label")),              //List<String> properties
                null,                                                            //List<String> optionlProperties
                new ArrayList<String>(Arrays.asList("?x rdf:type dbpo:Drug")),   //ArrayList<String> restrictions
                functions,                                                       //LinkedHashMap<String, Map<String, String>> functions
                prefixes,                                                        //Map<String, String> prefixes
                1000,                                                            //int pageSize
                "sparql",                                                        //String type
                -1,                                                              //int minOffset
                2000                                                             //int maxoffset
        );
    }

    @Test
    public void testFillCache() {
        SparqlQueryModule sqm = new SparqlQueryModule(kbInfo);
        ACache cache = new HybridCache();
        sqm.fillCache(cache);
        assertTrue(cache.size() > 0);
    }


    @Test
    public void testOptionalProperty() {
        kbInfo.setOptionalProperties(Arrays.asList("rdfs:comment"));
        SparqlQueryModule sqm = new SparqlQueryModule(kbInfo);
        ACache cache = new HybridCache();
        sqm.fillCache(cache);
        assertTrue(cache.size() > 0);
    }

}
