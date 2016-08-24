def checkRegisteredUsers():
	users = request.json[USERS]
	matches = []

	for u in users:
		key = ndb.Key(User, u[USER_ID])
		user = key.get()
		if user:
			matches.append(u)

	return make_response(jsonify({USERS: matches}), http.OK)
