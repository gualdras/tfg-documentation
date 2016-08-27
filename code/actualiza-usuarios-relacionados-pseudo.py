def updateRelatedUsers(usuario1, imgagen_id)
	imagenes_usadas = ImagenesUsadas.query(ImagenesUsadas.img == imagen_id)
	for imagen_usada in imagenes_usadas:
		usuario2 = imagen_usada.user.get()
		if(usuario1 != usuario2):
			usuarios_relacionados = UsuariosRelacionados.query(UsuariosRelacionados.usuario1.IN([usuario1, usuario2]), UsuariosRelacionados.usuario2.IN([usuario1, usuario2]))
			if(usuarios_relacionados != None):
				rUser.relation = rUser.relation
