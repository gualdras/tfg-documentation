def obtener_imagenes_colaborativo(usuario_id, palabras_clave):
	imagenes_colaborativo = []
	imagenes = Imagen.query(Image.keyWords.keyWord.IN(key_words) or Image.tags.tag.IN(key_words))

	if len(imagenes) > 0:
		usuarios = ImagenUsada.query(ImagenUsada.imagen.IN(imagenes)).usuarios

		query = UsuariosRelacionados.query(UsuariosRelacionados.usuario1 == usuario or UsuariosRelacionados.usuario2 == usuario).filter(ndb.OR(UsuariosRelacionados.usuario1.IN(usuarios), UsuariosRelacionados.usuario2.IN(usuarios))).order(-UsuariosRelacionados.relacion)

		relacionado = query.fetch(LIMITE_USUARIOS_RELACIONADOS)
		for ru in relacionado:
			if ru.relacion > MINIMUM_RELATION:
				usuario_relacionado = ru.get_usuarios_relacionados(usuario)
				imagenes_usada = ImagenUsada.query(ImagenUsada.usuario == usuario_relacionado, ImagenUsada.imagen.IN(imagenes)).order(ImagenUsada.contador)
				for imagen_usada in imagenes_usadas:
					imagen = imagen_usada.imagen
					if not (imagen.id() in imagenes_colaborativo):
						imagenes_colaborativo.append({tfg_server.BLOB: imagen.id(), tfg_server.LINK: imagen.get().link})
						break
				if len(imagenes_colaborativo) > IMAGENES_COLABORATIVO:
					break

			else:
				break
	return imagenes_colaborativo

