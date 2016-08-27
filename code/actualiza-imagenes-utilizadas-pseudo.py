def actualizar_imagenes_utilizadas(usuario, imagen_id):
	if(ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id) == None):
		ImagenesUtilizadas(usuario = usuario, imagen = imagen_id, contador = 1)
		actualizar_usuarios_relacionados(user, imagen_id)
	else:
		ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id)[0].contador = ImagenesUtilizadas.query(usuario = usuario, imagen = imagen_id)[0].contador + 1
	
