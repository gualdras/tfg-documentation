def obtener_sitios_contenido(usuario_id):
	sitios_preferidos = []
	sitios = {}
	imagenes_utilizadas = ImagenesUtilizadas.query(user = usuario_id)
	for imagen_utilizada in imagenes_utilizadas:
		imagen = imagen_utilizada.imagen.get()
		sitios[imagen.enlace_sitio] += logaritmo_neperiano(imagen_utilizada.contador)

	if len(sitios) > 0:
		mediana = calcularMediana(sitios)
		#Sitios ordenados en función del número de veces  que se han visitado de mayor a menor
		sitios_ordenados = sorted(sitios, key=sitios.__getitem__, reverse = True)

		for x in range(IMAGES_CONTENT):
			if sitios[sitios_ordenados[x]] > 2 * mediana:
				sitios_preferidos.append(sitios_ordenados[x])
			else 
				break

	return sitios_preferidos
