namespace java com.mycompany.thriftexample

typedef string String;
typedef i32 int;
typedef bool boolean;
typedef map<int, Item> HashMap;

struct Item{
    1: required int itemId;
    2: optional String itemmName;
    3: optional int itemPrice;
}

service Vendor{
    String greet(String name);
    void addItem(7: String itemName, 11: int itemPrice);
    HashMap getMenu();
    Item getItem(int itemId);
    oneway void clearMenu();
}

service Advertiser{
    String advertise();
}