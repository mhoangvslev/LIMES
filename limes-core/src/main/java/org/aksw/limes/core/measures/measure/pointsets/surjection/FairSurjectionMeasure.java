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
/**
 *
 */
package org.aksw.limes.core.measures.measure.pointsets.surjection;

import org.aksw.limes.core.datastrutures.PairSimilar;
import org.aksw.limes.core.datastrutures.Point;
import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.MappingFactory;
import org.aksw.limes.core.measures.mapper.pointsets.OrchidMapper;
import org.aksw.limes.core.measures.mapper.pointsets.Polygon;
import org.aksw.limes.core.measures.measure.pointsets.APointsetsMeasure;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Approach to computing the Surjection distance between two polygons
 *
 * @author Mohamed Sherif (sherif@informatik.uni-leipzig.de)
 * @version Jul 15, 2016
 */
public class FairSurjectionMeasure extends APointsetsMeasure {

    public int computations;

    public FairSurjectionMeasure() {
        computations = 0;
    }

    /**
     * @param X
     * @param Y
     * @param threshold
     * @return
     */
    public static double distance(Polygon X, Polygon Y, double threshold) {
        double sum = 0;
        FairSurjectionFinder fsf = new FairSurjectionFinder(X, Y);

        for (PairSimilar<Point> p : fsf.getFairSurjectionPairsList()) {
            sum = APointsetsMeasure.pointToPointDistance(p.a, p.b);
        }
        return sum;
    }

    public double computeDistance(Polygon X, Polygon Y, double threshold) {
        double sum = 0;
        FairSurjectionFinder fsf = new FairSurjectionFinder(X, Y);

        for (PairSimilar<Point> p : fsf.getFairSurjectionPairsList()) {

            sum += pointToPointDistance(p.a, p.b);
        }
        return sum;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.aksw.limes.core.measures.measure.IMeasure#getName()
     */
    public String getName() {
        return "FairSurjection";
    }

    /**
     * Computes the SetMeasure distance for a source and target set
     *
     * @param source
     *            Source polygons
     * @param target
     *            Target polygons
     * @param threshold
     *            Distance threshold
     * @return Mapping of uris
     */
    public AMapping run(Set<Polygon> source, Set<Polygon> target, double threshold) {
        AMapping m = MappingFactory.createDefaultMapping();
        for (Polygon s : source) {
            for (Polygon t : target) {
                double d = computeDistance(s, t, threshold);
                if (d <= threshold) {
                    m.add(s.uri, t.uri, d);
                }
            }
        }
        return m;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aksw.limes.core.measures.measure.IMeasure#getSimilarity(java.lang.
     * Object, java.lang.Object)
     */
    public double getSimilarity(Object object1, Object object2) {
        Polygon p1 = OrchidMapper.getPolygon((String) object1);
        Polygon p2 = OrchidMapper.getPolygon((String) object2);
        double d = computeDistance(p1, p2, 0);
        return 1d / (1d + (double) d);
    }

    public String getType() {
        return "geodistance";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aksw.limes.core.measures.measure.IMeasure#getSimilarity(org.aksw.
     * limes.core.io.cache.Instance, org.aksw.limes.core.io.cache.Instance,
     * java.lang.String, java.lang.String)
     */
    public double getSimilarity(Instance instance1, Instance instance2, String property1, String property2) {
        TreeSet<String> source = instance1.getProperty(property1);
        TreeSet<String> target = instance2.getProperty(property2);
        Set<Polygon> sourcePolygons = new HashSet<Polygon>();
        Set<Polygon> targetPolygons = new HashSet<Polygon>();
        for (String s : source) {
            sourcePolygons.add(OrchidMapper.getPolygon(s));
        }
        for (String t : target) {
            targetPolygons.add(OrchidMapper.getPolygon(t));
        }
        double min = Double.MAX_VALUE;
        double d = 0;
        for (Polygon p1 : sourcePolygons) {
            for (Polygon p2 : targetPolygons) {
                d = computeDistance(p1, p2, 0);
                if (d < min) {
                    min = d;
                }
            }
        }
        return 1d / (1d + (double) d);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aksw.limes.core.measures.measure.IMeasure#getRuntimeApproximation(
     * double)
     */
    public double getRuntimeApproximation(double mappingSize) {
        return mappingSize / 1000d;
    }

}
