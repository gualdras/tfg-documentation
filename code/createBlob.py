@app.route("/upload_form", methods=[GET])
def manager_upload_form():
	if request.method == GET:
		return upload()

def upload():
	uploadUri = blobstore.create_upload_url('/upload_photo')
	return make_response(uploadUri, http.OK)

@app.route("/upload_photo", methods=[POST])
def manager_upload_photo():
	if request.method == POST:
		return upload_photo()

def upload_photo():
	f = request.files['file']
	header = f.headers['Content-Type']
	parsed_header = parse_options_header(header)
	blob_key = parsed_header[1]['blob-key']

	image = Image(blobKey=blob_key, id=blob_key)

	image.put()

	return make_response(blob_key, http.CREATED)

