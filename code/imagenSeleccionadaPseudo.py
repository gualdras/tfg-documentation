def imagen_seleccionada(imagen_id, usuario, palabras_clave):
	imagen = imagen_id.get()
	if(not(imagen)):
		crea_blobImagen()
		sube_imagen()
		actualiza_informacionImagen()
	
	imagen.actualiza_palabras_clave_imagen(palabras_clave)
	usuario.actualiza_palabras_clave_usuario(palabras_clave)
	usuario.actualiza_imagenes_utilizadas(imagen_id)
