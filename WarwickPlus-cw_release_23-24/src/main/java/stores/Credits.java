package stores;

import structures.*;

import java.util.Comparator;

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
        for (CastCredit castMember : cast){
            if (uniqueCast.get(castMember.getName()) == null){
                uniqueCast.put(castMember.getName(), new Person(castMember.getID(), castMember.getName(), castMember.getProfilePath()));
                castIDToCast.put(castMember.getID(), castMember.getName());
            }
            if (castIDToFilmID.get(castMember.getID()) == null){
                LinkedList<Integer> idList = new LinkedList<>();
                idList.add(id);
                castIDToFilmID.put(castMember.getID(), idList);
            }
            else{
                castIDToFilmID.get(castMember.getID()).add(id);
            }
        }
        for (CrewCredit crewMember : crew){
            if (uniqueCrew.get(crewMember.getName()) == null){ 
                uniqueCrew.put(crewMember.getName(), new Person(crewMember.getID(), crewMember.getName(), crewMember.getProfilePath())); 
                crewIDToCrew.put(crewMember.getID(), crewMember.getName());
            }
            if (crewIDToFilmID.get(crewMember.getID()) == null){
                LinkedList<Integer> idList = new LinkedList<>();
                idList.add(id);
                crewIDToFilmID.put(crewMember.getID(), idList);
            }
            else{
                crewIDToFilmID.get(crewMember.getID()).add(id);
            }
        }
        return creditInfo.put(id, new CreditInfo(cast, crew));
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        if (creditInfo.get(id) != null){
            CastCredit[] cast = creditInfo.get(id).cast;
            CrewCredit[] crew = creditInfo.get(id).crew;
            for (int i = 0; i < cast.length; i++){
                uniqueCast.take(cast[i].getName());
                castIDToCast.take(cast[i].getID());
                if (castIDToFilmID.get(cast[i].getID()) != null){
                    castIDToFilmID.get(cast[i].getID()).remove(id);
                }
            }
            for (int j = 0; j < crew.length; j++){
                uniqueCrew.take(crew[j].getName());
                crewIDToCrew.take(crew[j].getID());
                if (crewIDToFilmID.get(crew[j].getID()) != null){
                    crewIDToFilmID.get(crew[j].getID()).remove(id);
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
        return creditInfo.get(filmID) != null ? creditInfo.get(filmID).getCast() : new CastCredit[0];
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
        return creditInfo.get(filmID) != null ? creditInfo.get(filmID).getCrew() : new CrewCredit[0];
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
        return creditInfo.get(filmID) != null ? creditInfo.get(filmID).getCast().length : -1;
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
        return creditInfo.get(filmID) != null ? creditInfo.get(filmID).getCrew().length : -1;
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
        return uniqueCast.valuez(Person.class);
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        return uniqueCrew.valuez(Person.class);
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
        Person[] temp = uniqueCast.valuez(Person.class);
        LinkedList<Person> results = new LinkedList<>();
        for (int i = 0; i < temp.length; i++){
            if (temp[i].getName().toLowerCase().contains(cast.toLowerCase())){
                results.add(temp[i]);
            }
        }
        return results.getValuez(Person.class);
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
        Person[] temp = uniqueCrew.valuez(Person.class);
        LinkedList<Person> results = new LinkedList<>();
        for (int i = 0; i < temp.length; i++){
            if (temp[i].getName().toLowerCase().contains(crew.toLowerCase())){
                results.add(temp[i]);
            }
        }
        return results.getValuez(Person.class);
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
        if (castIDToCast.get(castID) == null) return null;
        return uniqueCast.get(castIDToCast.get(castID));
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
        if (crewIDToCrew.get(crewID) == null) return null; 
        return uniqueCrew.get(crewIDToCrew.get(crewID));
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
        if (castIDToFilmID.get(castID) == null) return new int[0]; 
        return castIDToFilmID.get(castID).getValues();
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
        if (crewIDToFilmID.get(crewID) == null) return new int[0];
        return crewIDToFilmID.get(crewID).getValues();
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

     // HEAVY NULL CHECKS DUE TO THIS PREVIOUSLY BRICKING THE PROGRAM

    @Override
    public Person[] getMostCastCredits(int numResults) {
        if (castIDToCast.isEmpty() || castIDToFilmID.isEmpty()) return new Person[0];
        int[] castIDList = castIDToFilmID.keyList();
        if (castIDList == null) return new Person[0];
        CastCount[] mostCastCredits = new CastCount[castIDList.length];
        for (int i = 0; i < castIDList.length; i++){
            LinkedList<Integer> temp = castIDToFilmID.get(castIDList[i]);
            if (temp != null) { mostCastCredits[i] = new CastCount(castIDList[i], temp.getSize()); } 
            else { mostCastCredits[i] = new CastCount(castIDList[i], 0); }
        }

        if (mostCastCredits.length < numResults) numResults = mostCastCredits.length;
        
        Comparator<CastCount> moviesStarredCount = (o1, o2) -> Integer.compare(o1.getMoviesStarred(), o2.getMoviesStarred());
        Sort.genericSort(mostCastCredits, moviesStarredCount);

        Person[] returnArray = new Person[numResults];
        for (int i = 0; i < numResults; i++) {
            String castName = castIDToCast.get(mostCastCredits[i].getCastID());
            if (castName != null) returnArray[i] = uniqueCast.get(castName);
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
        if (castIDToFilmID.get(castID) != null) {
            if (castIDToFilmID.get(castID) == null) return -1;
            return castIDToFilmID.get(castID).getSize(); 
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

        public int getCastID(){
            return castID;
        }
    }
}
