	public static ArrayList<String> getHypernyms(String search){
        String[] input = search.split(" ");
        ArrayList<String> hyperList = new ArrayList<>();
        getDict();

        for (String w : input) {
            for (POS pos : POS.values()) {
                IIndexWord idxWord = dict.getIndexWord(w, pos);
                if (idxWord != null) {
                    IWordID wordID = idxWord.getWordIDs().get(0);
                    IWord word = dict.getWord(wordID);

                    ISynset synSet = word.getSynset();
                    List<ISynsetID> hypernyms = synSet.getRelatedSynsets(Pointer.HYPERNYM);
                    List < IWord > words ;
                    for (ISynsetID sid : hypernyms) {
                        words = dict.getSynset(sid).getWords();
                        for(IWord hyper: words){
                            hyperList.add(hyper.getLemma());
                        }
                    }
                }
            }
        }
        return hyperList;
    }
