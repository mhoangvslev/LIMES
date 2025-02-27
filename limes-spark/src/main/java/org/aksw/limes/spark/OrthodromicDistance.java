package org.aksw.limes.spark;

public class OrthodromicDistance {

    public static double R = 6371f;

    /**
     * Computes the distance between two points on earth Input
     * latitudes/longitudes by converting their latitude and longitude into
     * radians.
     *
     * @param lat1,
     *         Latitude of first point
     * @param long1,
     *         Longitude of first point
     * @param lat2,
     *         Latitude of second point
     * @param long2,
     *         Longitude of second point
     * @return Distance between both points
     */
    public static double getDistanceInDegrees(double lat1, double long1, double lat2, double long2) {
        double la1 = Math.toRadians(lat1);
        double lo1 = Math.toRadians(long1);
        double la2 = Math.toRadians(lat2);
        double lo2 = Math.toRadians(long2);
        return getDistance(la1, lo1, la2, lo2);
    }

    /**
     * Computes the distance between two points on earth Input
     * latitudes/longitudes are in Radians
     *
     * @param lat1,
     *         Latitude of first point
     * @param long1,
     *         Longitude of first point
     * @param lat2,
     *         Latitude of second point
     * @param long2,
     *         Longitude of second point
     * @return Distance between both points
     */
    public static double getDistance(double lat1, double long1, double lat2, double long2) {
        double dLat = lat2 - lat1;
        double dLon = long2 - long1;
        double sinLat = Math.sin(dLat / 2);
        double sinLon = Math.sin(dLon / 2);

        double a = sinLat * sinLat + sinLon * sinLon * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
