package us.hgmtrebing.swe699.database.hibernate;

import us.hgmtrebing.swe699.search.SearchRequest;

public class DummyData {
    public static void populateWithDummyData() {
        MysqlUtil initializer = null;
        try {
            initializer = new MysqlUtil();
            initializer.connect();
            initializer.initializeDatabaseSchema(true);


            Cuisine c01 = new Cuisine();
            c01.setName("Classic American");

            Cuisine c02 = new Cuisine();
            c02.setName("Seafood");

            Cuisine c03 = new Cuisine();
            c03.setName("Bar");

            Cuisine c04 = new Cuisine();
            c04.setName("Creole");

            Cuisine c05 = new Cuisine();
            c05.setName("Cajun");

            Cuisine c06 = new Cuisine();
            c06.setName("New Mexican");

            Cuisine c07 = new Cuisine();
            c07.setName("Tex-Mex");

            Cuisine c08 = new Cuisine();
            c08.setName("Sushi");

            Cuisine c09 = new Cuisine();
            c09.setName("Japanese");

            Cuisine c10 = new Cuisine();
            c10.setName("East Asian");


            Restaurant r01 = new Restaurant();
            r01.setName("Artie's");
            r01.setPublicId(01);
            r01.setStreetAddress("3260 Old Lee Hwy");
            r01.setCity("Fairfax");
            r01.setState(State.VIRGINIA);
            r01.setZipCode(22030);
            r01.setPricing(Pricing.TWO_STAR);
            r01.incrementSearchCount();
            r01.incrementSearchCount();
            r01.incrementClickCount();
            r01.addCuisine(c01);
            r01.addCuisine(c02);

            Restaurant r02 = new Restaurant();
            r02.setName("Arigato Sushi");
            r02.setPublicId(02);
            r02.setStreetAddress("y");
            r02.setCity("Fairfax");
            r02.setState(State.VIRGINIA);
            r02.setZipCode(22030);
            r02.setPricing(Pricing.TWO_STAR);
            r02.incrementSearchCount();
            r02.incrementSearchCount();
            r02.incrementClickCount();
            r02.addCuisine(c08);
            r02.addCuisine(c09);
            r02.addCuisine(c10);

            HibernateConnection hibernateConnection = new HibernateConnection();
            hibernateConnection.connect();
            hibernateConnection.saveOrUpdate(c01, c02, c03, c04, c05, c06, c07, c08, c09, r01, r02);
            hibernateConnection.flush();
            hibernateConnection.disconnect();

        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        } finally {
            if (initializer != null) {
                initializer.closeConnection();
            }
        }
    }

    public static void main(String[] args) {
        DummyData.populateWithDummyData();
        HibernateConnection connection = new HibernateConnection();
        connection.connect();
        SearchRequest request = new SearchRequest();

        for (Restaurant restaurant : connection.search(request)) {
            System.out.println(restaurant.getName());
        }
    }
}
