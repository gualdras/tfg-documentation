@app.route("/images/<path:id_img>", methods=[GET, PUT])
def manager_image(id_img):
	if request.method == GET:
		return download_image(id_img)

def download_image(id_img):
	blob_info = blobstore.get(id_img)
	response = make_response(blob_info.open().read())
	response.headers['Content-Type'] = blob_info.content_type
	return response
