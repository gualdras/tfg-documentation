@app.route('/users/<path:id_user>/send', methods = [POST])
def manager_user_send(id_user):
	if request.method == POST:
		return sendMsg(id_user)

def sendMsg(id_user):
	u = request.json
	key = ndb.Key(User, id_user)
	user = key.get()

	params = json.dumps({"data": {FROM: u[FROM], TYPE: u[TYPE], MESSAGE: u[MESSAGE]}, "to": user.regID})

	conn = http.HTTPSConnection(GCM_SEND_URL)
	conn.request("POST", "", params, GCM_HEADERS)

	response = conn.getresponse()

	status = response.status
	data = response.read()

	conn.close

	return make_response(jsonify({'response':data}), status)

