public class ImageLabelDetectionTask extends AsyncTask<Void, Void, SuggestedImage> {
    SuggestedImage suggestedImage;
    ImageInteractionListener mListener;

    public ImageLabelDetectionTask(Activity activity, SuggestedImage suggestedImage) {
        this.suggestedImage = suggestedImage;
        this.mListener = (ImageInteractionListener) activity;
    }

    @Override
    protected SuggestedImage doInBackground(Void... params) {
        try {
            HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
            builder.setVisionRequestInitializer(new
                    VisionRequestInitializer(CLOUD_VISION_API_KEY));
            Vision vision = builder.build();

            BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                    new BatchAnnotateImagesRequest();

            ArrayList<AnnotateImageRequest> requests = new ArrayList<>();
            Bitmap bitmap = suggestedImage.getBitmap();
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            Image base64EncodedImage = new Image();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(10);
                add(labelDetection);
            }});
            requests.add(annotateImageRequest);

            batchAnnotateImagesRequest.setRequests(requests);

            Vision.Images.Annotate annotateRequest =
                    vision.images().annotate(batchAnnotateImagesRequest);
            annotateRequest.setDisableGZipContent(true);
            Log.d(TAG, "created Cloud Vision request object, sending request");

            BatchAnnotateImagesResponse response = annotateRequest.execute();
            suggestedImage.setTags(convertResponseToMap(response));

        } catch (GoogleJsonResponseException e) {
            Log.d(TAG, "failed to make API request because " + e.getContent());
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
        return suggestedImage;
    }

    @Override
    protected void onPostExecute(SuggestedImage suggestedImage) {
        mListener.onImageLabeled(suggestedImage);
    }
}
