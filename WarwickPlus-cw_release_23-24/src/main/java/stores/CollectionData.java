package stores;

import structures.*;

public class CollectionData {
    private LinkedList<Integer> filmStore;
    private String collectionName;
    private String collectionPosterPath;
    private String collectionBackdropPath;

    public CollectionData(int filmID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        this.filmStore = new LinkedList<>();
        filmStore.add(filmID);
        this.collectionName = collectionName;
        this.collectionPosterPath = collectionPosterPath;
        this.collectionBackdropPath = collectionBackdropPath;
    }

    public boolean filmAdd(int filmID){
        return filmStore.add(filmID);
    }

    public LinkedList<Integer> getFilmStore() {
        return filmStore;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionPosterPath() {
        return collectionPosterPath;
    }

    public String getCollectionBackdropPath() {
        return collectionBackdropPath;
    }
}