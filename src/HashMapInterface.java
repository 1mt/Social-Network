public interface HashMapInterface
{
    // Initalise the hashmap using an array of the specificed array length
    // you can assume that it is safe to clear anything already in the hash map
    // when this function gets called and everything is reset
    public void InitHashMap(int initlen);

    // Adds the {key, value} pair into the hash map
    public void AddItem(String key, String value);

    // Deletes the {key, value} pair for key from the hash map if it exists
    // key is case sensitive
    public void DeleteItem(String key);

    // returns the value component of the map that contains the key or null if it isn't found
    // this should be case sensitive
    public String GetValue(String key);

    // Returns the number of items in the hash map
    public int GetNoOfItems();

    // Returns the "index"th valid item in the collection
    // i.e. GetItem(0) returns the first valid item, GetItem(1) returns the second valid item, etc,
    // up to GetNoOfItems()-1.
    public HashPair GetItem(int index);
}
