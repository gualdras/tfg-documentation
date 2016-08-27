    public static String cleanInput(String input){
        StringBuilder output = new StringBuilder();
        if(input.length() > 0) {
            String[] sInput = input.split(" ");
            output = new StringBuilder();
            getDict();

            for (String w : sInput) {
                List<String> s;
                WordnetStemmer ws = new WordnetStemmer(dict);
                if ((s = ws.findStems(w, null)).size() > 0) {
                    output.append(s.get(0) + " ");
                }
            }
        }
        return output.toString().trim();
    }

    private static void getDict(){
        URL url = null;
        try {
            url = new URL("file", null, Constants.PATH_TO_DICT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        dict = new Dictionary(url) ;

        try {
            dict.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
