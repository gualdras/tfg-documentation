public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String IMAGE = "Image received";

    @Override
    public void onMessageReceived(String senderID, Bundle data) {
        String from = data.getString(ServerSharedConstants.FROM);
        String type = data.getString(ServerSharedConstants.TYPE);
        String msg = data.getString(ServerSharedConstants.MSG);
        Log.d(TAG, "SenderID: " + senderID);
        Log.d(TAG, "Message: " + msg);

        MessageItem messageItem = new MessageItem(from, type, msg);

   	if(type.equals(MessageItem.IMG_TYPE)){
            new ReceivedImageDownloadTask(this, messageItem).execute();
            msg = IMAGE;
        }
        else {
            messageItem.saveMessageReceived(this);
        }
        sendNotification(msg);
    }
    private void sendNotification(String msg) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Message")
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
    }
}

