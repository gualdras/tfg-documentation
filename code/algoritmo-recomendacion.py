NUMERO_DE_IMAGENES = 10
IMAGES_CONTENT = NUMERO_DE_IMAGENES * 0.3
IMAGENES_COLABORATIVO = int(NUMERO_DE_IMAGENES * 0.4)
RELACION_MINIMA = 20

def obtener_imagenes_recomendadas(usuario_id, palabras_clave, categorias):
	sitios_web_contenido = obtener_sitios_contenido(usuario_id)
	sitios_web_conocimiento = obtener_sitios_conocimiento(categorias)
	sitios_web = sitios_web_contenido + list(set(sitios_web_conocimiento) - set(sitios_web_contenido))
	
	imagenes_colaborativo = obtener_imagenes_colaborativo(usuario_id, palabras_clave)
	pictogramas = obtener_pictogramas(palabras_clave)
	imagenes = imagenes_colaborativo + pictogramas

	return json({'sitios': sitios_web, 'imagenes': imagenes})
