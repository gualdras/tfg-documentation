def imagen_seleccionada(imagen_id, usuario, palabras_clave):
	imagen = imagen_id.get()
	if(not(imagen)):
		crea_blobImagen()
		sube_imagen()
		actualiza_informacionImagen()
	
	imagen.actualiza_palabras_clave_imagen(palabras_clave)
	usuario.actualiza_palabras_clave_usuario(palabras_clave)
	usuario.actualiza_imagenes_utilizadas(imagen_id)	


def actualiza_palabras_clave(entidad, palabras_clave):
	for palabra in palabras_clave:
		if(palabra in entidad.palabras_clave):
			entidad.palabras_clave.get(palabra).contador = entidad.palabras_clave.get(palabra).contador + 1
		else: 
			entidad.palabras_clave.append(PalabraClave(palabra_clave = palabra, contador = 1))

def actualizar_imagenes_utilizadas(usuario, imagen_id):
	if(ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id) == None):
		ImagenesUtilizadas(usuario = usuario, imagen = imagen_id, contador = 1)
		actualizar_usuarios_relacionados(user, imagen_id)
	else:
		ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id)[0].contador = ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id)[0].contador + 1
	

def updateRelatedUsers(usuario1, imgagen_id)
	imagenes_usadas = ImagenesUsadas.query(ImagenesUsadas.img == imagen_id)
	for imagen_usada in imagenes_usadas:
		usuario2 = imagen_usada.user.get()
		if(usuario1 != usuario2):
			usuarios_relacionados = UsuariosRelacionados.query(UsuariosRelacionados.usuario1.IN([usuario1, usuario2]), UsuariosRelacionados.usuario2.IN([usuario1, usuario2]))
			if(usuarios_relacionados != None):
				rUser.relation = rUser.relation
