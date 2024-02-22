package stores;

import structures.*;


import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;
    private HashMap<Integer, CreditInfo> creditInfo;
    private HashMap<String, Person> uniqueCast;
    private HashMap<String, Person> uniqueCrew;
    private HashMap<Integer, String> castIDToCast;
    private HashMap<Integer, String> crewIDToCrew;
    private HashMap<Integer, LinkedList<Integer>> castIDToFilmID;
    private HashMap<Integer, LinkedList<Integer>> crewIDToFilmID;
    


    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        this.stores = stores;
        this.creditInfo = new HashMap<>();        
        this.uniqueCast = new HashMap<>();
        this.castIDToCast = new HashMap<>();
        this.castIDToFilmID = new HashMap<>();
        this.uniqueCrew = new HashMap<>();
        this.crewIDToCrew = new HashMap<>();
        this.crewIDToFilmID = new HashMap<>();
        }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        CreditInfo info = new CreditInfo(cast, crew);      
        for (CastCredit castMember : cast){
            if (!uniqueCast.containsKey(castMember.getName())){
                uniqueCast.put(castMember.getName(), new Person(castMember.getID(), castMember.getName(), castMember.getProfilePath()));
                castIDToCast.put(castMember.getID(), castMember.getName());
            }
            LinkedList<Integer> temp = castIDToFilmID.get(castMember.getID());
            if (temp == null){
                LinkedList<Integer> idList = new LinkedList<>();
                idList.add(id);
                castIDToFilmID.put(castMember.getID(), idList);
            }
            else{
                temp.add(id);
            }
        }
        for (CrewCredit crewMember : crew){
            if (!uniqueCrew.containsKey(crewMember.getName())){ 
                uniqueCrew.put(crewMember.getName(), new Person(crewMember.getID(), crewMember.getName(), crewMember.getProfilePath())); 
                crewIDToCrew.put(crewMember.getID(), crewMember.getName());
            }
            LinkedList<Integer> temp = crewIDToFilmID.get(crewMember.getID());;
            if (temp == null){
                LinkedList<Integer> idList = new LinkedList<>();
                idList.add(id);
                crewIDToFilmID.put(crewMember.getID(), idList);
            }
            else{
                temp.add(id);
            }
        }
        return creditInfo.put(id, info);
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        CreditInfo temp = creditInfo.get(id);
        if (temp != null){
            CastCredit[] cast = temp.cast;
            CrewCredit[] crew = temp.crew;
            for (int i = 0; i < cast.length; i++){
                uniqueCast.take(cast[i].getName());
                castIDToCast.take(cast[i].getID());
                LinkedList<Integer> tempL = castIDToFilmID.get(cast[i].getID());
                if (tempL != null && tempL.size != 0){
                    tempL.remove(id);
                }
            }
            for (int j = 0; j < crew.length; j++){
                uniqueCrew.take(crew[j].getName());
                crewIDToCrew.take(crew[j].getID());
                LinkedList<Integer> tempL = crewIDToFilmID.get(crew[j].getID());
                if (tempL != null && tempL.size != 0){
                    tempL.remove(id);
                }
            }
        }
        return creditInfo.take(id) != null;
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film canot be 
     *         found, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        CreditInfo info = creditInfo.get(filmID);
        return info != null ? info.getCast() : new CastCredit[0];
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in ID order. If there 
     *         is no crew members attached to a film, or the film canot be found, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        CreditInfo info = creditInfo.get(filmID);
        return info != null ? info.getCrew() : new CrewCredit[0];
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        CreditInfo info = creditInfo.get(filmID);
        return info != null ? info.getCast().length : -1;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeofCrew(int filmID) {
        CreditInfo info = creditInfo.get(filmID);
        return info != null ? info.getCrew().length : -1;
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return creditInfo.size();
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        if (uniqueCast.isEmpty()) {
            return new Person[0]; 
        }
        return uniqueCast.valuesS();
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        if (uniqueCrew.isEmpty()) {
            return new Person[0]; 
        }
        return uniqueCrew.valuesS();
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name
     */
    @Override
    public Person[] findCast(String cast) {
        if (uniqueCast.isEmpty()) return new Person[0];
        Person[] temp = uniqueCast.valuesS();
        LinkedList<Person> results = new LinkedList<>();
        for (int i = 0; i < temp.length; i++){
            if (temp[i].getName().toLowerCase().contains(cast.toLowerCase())){
                results.add(temp[i]);
            }
        }
        return results.getValuesPerson();
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name
     */
    @Override
    public Person[] findCrew(String crew) {
        if (uniqueCrew.isEmpty()) return new Person[0];
        Person[] temp = uniqueCrew.valuesS();
        LinkedList<Person> results = new LinkedList<>();
        for (int i = 0; i < temp.length; i++){
            if (temp[i].getName().toLowerCase().contains(crew.toLowerCase())){
                results.add(temp[i]);
            }
        }
        return results.getValuesPerson();
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        String checkNull = castIDToCast.get(castID);
        if (checkNull == null) return null;
        return uniqueCast.get(checkNull);
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID){
        String temp = crewIDToCrew.get(crewID);
        if (temp == null) return null; 
        return uniqueCrew.get(temp);
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        LinkedList<Integer> returnList = castIDToFilmID.get(castID);
        if (returnList == null || returnList.size == 0) return new int[0]; 
        return returnList.getValues();
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        LinkedList<Integer> returnList = crewIDToFilmID.get(crewID);
        if (returnList == null || returnList.size == 0) return new int[0];
        return returnList.getValues();
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    public int[] getCastStarsInFilms(int castID) {
        LinkedList<Integer> topBilledFilms = new LinkedList<>();
        LinkedList<Integer> temp = castIDToFilmID.get(castID);
        if (temp == null) return new int[0]; 
        int[] filmIDs = temp.getValues(); 
        for (int filmID : filmIDs) {
            CreditInfo creditInfoT = this.creditInfo.get(filmID); 
            if (creditInfoT != null) {
                for (CastCredit castCredit : creditInfoT.getCast()) { 
                    if (castCredit.getID() == castID && castCredit.getOrder() <= 3) {
                        topBilledFilms.add(filmID);
                        break; 
                    }
                }
            }
        }
    
        return topBilledFilms.getValues();
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        if (castIDToCast.isEmpty()) return new Person[0];
        int[] castIDList = castIDToFilmID.keyList();
        CastCount[] mostCastCredits = new CastCount[castIDList.length];
        for (int i = 0; i < castIDList.length; i++){
            LinkedList<Integer> temp = castIDToFilmID.get(castIDList[i]);
            mostCastCredits[i] = new CastCount(castIDList[i], temp.size);
        }
        if (mostCastCredits.length < numResults) numResults = mostCastCredits.length;
        sortMostCastCredits(mostCastCredits);
        Person[] returnArray = new Person[numResults];
        for(int i = 0; i < numResults; i++){
            returnArray[i] = uniqueCast.get(castIDToCast.get(mostCastCredits[i].castID));
        }
        return returnArray;
    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        if (castIDToFilmID.containsKey(castID)) {
            LinkedList<Integer> films = castIDToFilmID.get(castID);
            return films.size; 
        }
        return -1; 
    }

    private class CreditInfo {
        private CastCredit[] cast;
        private CrewCredit[] crew;

        public CreditInfo(CastCredit[] cast, CrewCredit[] crew) {
            this.cast = cast;
            this.crew = crew;
        }

        public CastCredit[] getCast() {
            return cast;
        }

        public CrewCredit[] getCrew() {
            return crew;
        }
    }

    private static class CastCount{
        private int castID;
        private int moviesStarred;

        public CastCount(int castID, int moviesStarred){
            this.castID = castID;
            this.moviesStarred = moviesStarred;
        }

        public int getMoviesStarred(){
            return moviesStarred;
        }
    }
    

    public static void sortMostCastCredits(CastCount[] array) {
        if (array.length <= 1) {
            return;
        }

        CastCount[] leftHalf = new CastCount[array.length / 2];
        CastCount[] rightHalf = new CastCount[array.length - leftHalf.length];

        System.arraycopy(array, 0, leftHalf, 0, leftHalf.length);
        System.arraycopy(array, leftHalf.length, rightHalf, 0, rightHalf.length);

        sortMostCastCredits(leftHalf);
        sortMostCastCredits(rightHalf);

        mostCastCreditsMerge(array, leftHalf, rightHalf);
    }

    private static void mostCastCreditsMerge(CastCount[] outputArray, CastCount[] leftHalf, CastCount[] rightHalf) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftHalf.length && rightIndex < rightHalf.length) {
            if (leftHalf[leftIndex].getMoviesStarred() >= rightHalf[rightIndex].getMoviesStarred()) {
                outputArray[mergeIndex] = leftHalf[leftIndex];
                leftIndex++;
            } else {
                outputArray[mergeIndex] = rightHalf[rightIndex];
                rightIndex++;
            }
            mergeIndex++;
        }

        System.arraycopy(leftHalf, leftIndex, outputArray, mergeIndex, leftHalf.length - leftIndex);
        System.arraycopy(rightHalf, rightIndex, outputArray, mergeIndex, rightHalf.length - rightIndex);
    }
}
