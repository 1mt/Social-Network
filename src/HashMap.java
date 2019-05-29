import java.util.Objects;

public class HashMap implements HashMapInterface
{
    private int noofitems;
    private HashPair[] data;

    private int[] hits;

    public HashMap() {}


    public void InitHashMap(int initlen)
    {
        noofitems=0;
        data = new HashPair[initlen];

        hits=new int[initlen];
        for (int i=0; i<initlen; i++)
            hits[i]=0;
    }

    public void AddItem(String key, String value)
    {
        int index=HashFunction(key);
        index = index%data.length;

        ++hits[index%hits.length];

        HashPair h = new HashPair(key, value);


        double loadFactor;
        loadFactor = noofitems/data.length;
        if(loadFactor >= 0.7){
            data = resize();
        }

        index++;
        index = index%data.length;
        if(data[index] == null){
            data[index] = h;
        }
        else{
            while(data[index] != null){
                index++;
                index = index%data.length;
            }
            data[index] = h;
        }
    }




    private int HashFunction(String key)
    {
        int sum = 8;
        for(int i =0;i<key.length();i++){
            sum = Math.abs(sum*42 + key.charAt(i));
        }

        return sum;
    }


    public String GetValue(String key)
    {
        double loadFactor;
        loadFactor = noofitems/data.length;
        if(loadFactor >= 0.7){
            data = resize();
        }
        int index = HashFunction(key);
        index = index%data.length;

        if(data[index] == null){
            return null;
        }
        else{
            while(data[index]!= null && (!key.equals(data[index].key))){
                index++;
                index = index % data.length;
            }
            if(data[index] == null){
                return null;
            }
            else{
                return data[index].value;
            }
        }
    }


    public void DeleteItem(String key)
    {
        int index=HashFunction(key);
        index = index%data.length;

        while(data[index].key != key){
            index++;
            index = index % data.length;
        }

        data[index].key=null;
    }


    // Help function: Returns the number of items in the hash map
    public int GetNoOfItems()
    {
        return data.length;
    }



    public HashPair GetItem(int index)
    {
        return data[index];
    }


    public void DisplayStats()
    {
        // Display the top 10 index hashes and their counts
        int maxids[]=new int[10];
        for (int i=0; i<maxids.length; i++)
        {
            //maxids[i]=;
            int max=-1;
            for (int j=0; j<hits.length; j++)
            {
                if (hits[j]>max)
                {
                    boolean found=false;
                    for (int k=0; k<i && !found; k++)
                        if (maxids[k]==j)
                            found=true;

                    if (!found)
                    {
                        max=hits[j];
                        maxids[i]=j;
                    }
                }
            }
        }

        for (int i=0; i<maxids.length; i++)
            System.out.println(maxids[i]+": "+hits[maxids[i]]);
    }

    public HashPair[] resize(){
        HashPair[] newArray = new HashPair[noofitems+100];

        for(int i =0; i<noofitems+1;i++){
            newArray[i] = data[i];
        }

        return newArray;
    }

}
