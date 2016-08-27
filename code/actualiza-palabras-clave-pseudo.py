def actualiza_palabras_clave(entidad, palabras_clave):
	for palabra in palabras_clave:
		if(palabra in entidad.palabras_clave):
			entidad.palabras_clave.get(palabra).contador = entidad.palabras_clave.get(palabra).contador + 1
		else: 
			entidad.palabras_clave.append(PalabraClave(palabra_clave = palabra, contador = 1))

