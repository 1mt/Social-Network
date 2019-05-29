/**
 * You can also develop help functions and new classes for your system. You 
 * can change the skeleton code if you need but you do not allow to remove the
 * methods provided in this class.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SocialNetwork {
    
    private SNGraph userNetwork;//You may use different container for the social network data
    private SNList userNames;//You may use different container for the user name list
    private int[] myFriendsId;

    public SocialNetwork(){
        
    }
    
    /**
     * Loading social network data from files. 
     * The dataset contains two separate files for user names (NameList.csv) and 
     * the network distributions (SocialNetworkData.csv).
     * Use file I/O functions to load the data.You need to choose suitable data 
     * structure and algorithms for an effective loading function
     */
    public void Load(){
        String csvFile = "NameList.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        userNames = new SNList();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] name = line.split(csvSplitBy);
                userNames.add(String.valueOf(count+1), name[0]);
                count++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        csvFile = "SocialNetworkData.csv";
        // Assumes there is 964 entries in SocialNetworkData.csv
        userNetwork = new SNGraph(964);
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] li = line.split(csvSplitBy);
                userNetwork.addEdge(Integer.parseInt(li[0]), Integer.parseInt(li[1]), Double.parseDouble(li[2]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    /**
     * Locating a user from the network
     * @param fullName users full name as a String
     * @return return the ID based on the user list. return -1 if the user do not exist
     * based on your algorithm, you may also need to locate the reference 
     * of the node from the graph. 
     */
    public int FindUserID(String fullName){
        int searchResult = userNames.getID(fullName);
        return searchResult;
    }
    
   
    /**
     * Listing all the friends belongs to the user
     * You need to retrieval all the directly linked nodes and return their full names.
     * Current Skeleton only have some dummy data. You need to replace the output
     * by using your own algorithms.
     * @param currentUserName use FindUserID or other help functions to locate 
     * the user in the graph first.
     * @return You need to return all the user names in a String Array directly
     * linked to the users node.
     */
    public String[] GetMyFriends(String currentUserName){
        int currentUser = FindUserID(currentUserName);
        ADS2LinkedList list = userNetwork.getList(currentUser + 1);
        String[] myFriends = new String[list.getCount()];
        myFriendsId = new int[list.getCount()];
        for (int j = 0; j <list.getCount() ; j++) {
            SNGraph.Edge tempEdge = (SNGraph.Edge) list.get(j).getData();
            myFriends[j] = userNames.getName(tempEdge.getDest());
            myFriendsId[j] = tempEdge.getDest();
        }
        return myFriends;
        
    }
    
    /**
     * Listing the top 3 recommended friends for the user
     * In the task, you need to calculate the shortest distance between the 
     * current user and all other non-directly linked users. Pick up the top three 
     * closest candidates and return their full names.
     * Use some help functions for sorting and shortest distance algorithms 
     * @param currentUserName use FindUserID or other help functions to locate 
     * the user in the graph first.
     * @return You need to return all the user names in a String Array containing 
     * top 3 closest candidates. 
     */
    public String[] GetRecommended (String currentUserName){
        double[] distsFromUser = userNetwork.dijkstra(userNames.getID(currentUserName)-1);
        // creates an index of the distsFromUser
        int[] indexes = new int[distsFromUser.length];
        for(int i=0; i<distsFromUser.length;i++) {
            indexes[i] = i;
        }

        // creates a sorted index of distsFromUser (from smallest to largest values)
        quickSort(indexes,distsFromUser,0,99);
        String[] recommended = new String[3];
        int count = 0;
        // skip first index, as that is the user themself
        int i = 1;
        while(count < 3){
            boolean friendFound = false;
            for(int friendId: myFriendsId){
                if(friendId == indexes[i]+1){
                    friendFound = true;
                }
            }
            if(!friendFound){
                recommended[count] = userNames.getName(indexes[i]);
                count++;
            }
            i++;
        }
        return recommended;
    }
    public void quickSort(int[] indexes, double distsFromUser[], int begin, int end) {

        if (begin < end) {
            int partitionIndex = partition(indexes, distsFromUser, begin, end);

            quickSort(indexes,distsFromUser, begin, partitionIndex-1);
            quickSort(indexes,distsFromUser, partitionIndex+1, end);
        }


    }
    private int partition(int[] indexes, double distsFromUser[], int begin, int end) {
        double pivot = distsFromUser[indexes[end]];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (distsFromUser[indexes[j]] <= pivot) {
                i++;
                int swapTemp = indexes[i];
                indexes[i] = indexes[j];
                indexes[j] = swapTemp;
            }
        }

        int swapTemp = indexes[i+1];
        indexes[i+1] = indexes[end];
        indexes[end] = swapTemp;

        return i+1;
    }
    
    
}
