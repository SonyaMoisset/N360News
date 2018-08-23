package com.sonyamoisset.android.n360news.ui.sources;

public abstract class SourcesCategories {

    public static final String EVERYTHING = "Everything";
    public static final String GENERAL = "General";
    public static final String SPORTS = "Sports";
    public static final String TECHNOLOGY = "Technology";
    public static final String BUSINESS = "Business";
    public static final String ENTERTAINMENT = "Entertainment";
    public static final String SCIENCE = "Science";
    public static final String HEALTH = "Health";

    public static String[] filterByNewsSourcesCategories() {
        return new String[]{
                EVERYTHING,
                GENERAL,
                SPORTS,
                TECHNOLOGY,
                BUSINESS,
                ENTERTAINMENT,
                SCIENCE,
                HEALTH
        };
    }
}
