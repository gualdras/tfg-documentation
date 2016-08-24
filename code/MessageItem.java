public class MessageItem {

    private String from, to, type, msg, localResource;

    public static final String TEXT_TYPE = "text";
    public static final String IMG_TYPE = "img";

    public MessageItem(String from, String to, String type, String msg, String localResource) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.msg = msg;
        this.localResource = localResource;
    }

    public MessageItem(String from, String to, String type, String msg) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.msg = msg;
    }

    public MessageItem(String from, String type, String msg) {
        this.from = from;
        this.type = type;
        this.msg = msg;
        this.to = null;
    }

    public JSONObject messageToJSON(){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put(ServerSharedConstants.FROM, from);
            jsonParam.put(ServerSharedConstants.TYPE, type);
            jsonParam.put(ServerSharedConstants.MSG, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam;
    }

    public void saveMessageSent(Context mContext){
        ContentValues values = new ContentValues(2);
        values.put(DataProvider.COL_TO, to);
        if(localResource == null){
            values.put(DataProvider.COL_MSG, msg);
        } else{
            values.put(DataProvider.COL_MSG, localResource);
        }
        values.put(DataProvider.COL_TYPE, type);
        mContext.getContentResolver().insert(DataProvider.CONTENT_URI_MESSAGES, values);
    }
    public void saveMessageReceived(Context mContext){
        ContentValues values = new ContentValues(2);
        if(localResource == null){
            values.put(DataProvider.COL_MSG, msg);
        } else{
            values.put(DataProvider.COL_MSG, localResource);
        }        values.put(DataProvider.COL_TYPE, type);
        values.put(DataProvider.COL_FROM, from);
        mContext.getContentResolver().insert(DataProvider.CONTENT_URI_MESSAGES, values);
    }
}
