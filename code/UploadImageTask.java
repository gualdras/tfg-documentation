public class UploadImageTask extends AsyncTask<Void, Void, String> {
    Context mContext;
    MessageItem messageItem;
    SuggestedImage suggestedImage;

    public UploadImageTask(Context context, SuggestedImage suggestedImage, MessageItem messageItem){
        this.suggestedImage = suggestedImage;
        this.mContext = context;
        this.messageItem = messageItem;
    }

    @Override
    protected String doInBackground(Void... params) {
        String imgURL = suggestedImage.getBlobUrl();
        if(imgURL == null){
            String blobURL = getBlobURL();

            imgURL = uploadImage(blobURL);
            suggestedImage.setBlobUrl(imgURL);
            putImageInformation();
        }

        return imgURL;
    }

    private String getBlobURL(){
        String blobUrl = "";
        HttpURLConnection httpUrlConnection = null;

        try {
            httpUrlConnection = (HttpURLConnection) new URL(Constants.UPLOAD_FORM_URL)
                    .openConnection();

            switch (httpUrlConnection.getResponseCode()){
                case HttpURLConnection.HTTP_OK:
                    InputStream in = new BufferedInputStream(
                            httpUrlConnection.getInputStream());

                    blobUrl = NetworkUtils.readStream(in);
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    InputStream err = new BufferedInputStream(httpUrlConnection.getErrorStream());

                    blobUrl = NetworkUtils.readStream(err);
                    break;
            }
        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (null != httpUrlConnection)
                httpUrlConnection.disconnect();
        }
        return blobUrl;
    }

    private String uploadImage(String imgUploadURL){
        String imgURL = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.png", RequestBody.create(MediaType.parse("image/png"), new File(messageItem.getLocalResource())))
                .addFormDataPart("title", "My photo")
                .build();

        Request request = new Request.Builder()
                .url(imgUploadURL)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            imgURL = response.body().string();
            Log.d("response", "uploadImage:"+imgURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgURL;
    }

    @Override
    protected void onPostExecute(String imgURL) {
        messageItem.setMsg(imgURL);
        new SendMessageTask(mContext, messageItem).execute();
    }
}

