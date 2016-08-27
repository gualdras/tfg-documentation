	public static ArrayList<String> getSynonyms(String search) {
        String[] input = search.split(" ");
        ArrayList<String> synList = new ArrayList<>();
        getDict();

        for (String w : input) {
            for (POS pos : POS.values()) {
                IIndexWord idxWord = dict.getIndexWord(w, pos);
                if (idxWord != null) {
                    IWordID wordID = idxWord.getWordIDs().get(0);
                    IWord word = dict.getWord(wordID);

                    ISynset synSet = word.getSynset();
                    if(synSet.getWords().size() > 0) {
                        for (IWord syn : synSet.getWords()) {
                            synList.add(syn.getLemma());
                        }
                        break;
                    }
                }
            }
        }
        return synList;
    }
