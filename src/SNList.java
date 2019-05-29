class SNList {
    private String[] nameList = new String[100];
    private HashMap userNameMap;
    private int count=0;
    public SNList(){
        userNameMap = new HashMap();
        userNameMap.InitHashMap(100);

    }
    public String getName(int index){
        return nameList[index-1];
    }

    public int getID(String name){
        String temp = userNameMap.GetValue(name);
        if(temp==null){
            return -1;
        }
        else{
            return Integer.parseInt(userNameMap.GetValue(name));
        }        
    }
    public void add(String key, String value){
        userNameMap.AddItem(value, key);
        nameList[count] = value;
        count++;
    }
    
}
