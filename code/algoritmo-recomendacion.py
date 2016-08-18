NUMBER_OF_IMAGES = 10
LIMIT_OF_RUSERS = NUMBER_OF_IMAGES * 0.5
IMAGES_CONTENT = NUMBER_OF_IMAGES * 0.3

RELACION_MINIMA = 20

def obtener_imagenes_recomendadas(usuario_id, palabras_clave):
	sitios_web_contenido = obtener_imagenes_contenido(usuario_id)
	imagenes_colaborativo = obtener_imagenes_colaborativo(usuario_id, palabras_clave)

	return json({'sitios': sitios_web_contendio, 'imagenes': imagenes})

def obtener_imagenes_colaborativo(usuario_id, palabras_clave):
	imagenes_colaborativo = []
	imagenes = Imagenes.query(Imagenes.palabras_clave.palabra_clave.IN(palabras_clave) or Imagenes.etiquetas.etiqueta.IN(palabras_clave))
	usuarios = Usuarios.query(Usuario.images_used.img.IN(images))
	
	usuarios_relacionados = UsuariosRelacionados.query(UsuariosRelacionados.usuario1 = usuario or UsuarioRelacionado.usuario2 = usuario)).filtrar(UsuariosRelacionados.usuario1.IN(usuarios) or UsuariosRelacionados.usuario2.IN(usuarios)).ordenar(UsuariosRelacionados.relacion)
	
		
	for ur in usuarios_relacionados:
		if(ur.relacion > RELACION_MINIMA):
			buscando_imagen = True
			usuario = ur.get_usuario_relacionado(usuario_id).get()
			imagenes_utilizadas = ImagenesUilizadas.query(ImagenesUilizadas.usuario = usuario, ImagenesUilizadas.imagen.IN(imagenes).ordenar(ImagenesUtilizadas.contador)
			while(buscando_imagen):
				img_candidata = imagenes_utilizadas.pop(0)
				if(not (img_candidata in imagenes_colaborativo)):
					imagenes_colaborativo.append(img_candidata)
					buscando_imagen = False
			if(len(a) >= LIMIT_OF_RUSERS):
				break
		else:
			break
	return imagenes_colaborativo
	

def obtener_imagenes_contenido(usuario_id):
	sitios_preferidos = []
	sitios = {}
	imagenes_utilizadas = ImagenesUtilizadas.query(user = usuario_id)
	for imagen_utilizada in imagenes_utilizadas:
		imagen = imagen_utilizada.imagen.get()
		sitios[imagen.enlace_sitio] = sitios[imagen.enlace_sitio] + 1
		enlaces_totales = enlaces_totales + 1

	mediana = calcularMediana(sitios)
	sitios_ordenados = shorted(sitios, , key=sitios.__getitem__, reverse = True)

	for x in range(IMAGES_CONTENT):
		if sitios[sitios_ordenados[x]] > 2 * mediana:
			sitios_preferidos.append(sitios_ordenados[x])

	return sitios_preferidos
	


