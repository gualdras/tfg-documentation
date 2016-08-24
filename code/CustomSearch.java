private List<Result> searchGSE(@Nullable String site){
        Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
        com.google.api.services.customsearch.Customsearch.Cse.List list;
        Search results;
        List<Result> searchResults = null;

        try {
            list = customsearch.cse().list(search);
            list.setKey(key);
            list.setCx(cx);
            list.setSearchType("image");
            if(site != null){
                list.setSiteSearch(search);
            }

            results = list.execute();
            searchResults = results.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResults;
    }
