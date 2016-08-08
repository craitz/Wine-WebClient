package com.algaworks.wine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.wine.model.Vinho;
import com.algaworks.wine.repository.Vinhos;
import com.algaworks.wine.storage.FotoStorage;

@Service
public class VinhoService {

	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private FotoStorage fotoStorage;

	public List<Vinho> pesqusiar() {
		return vinhos.findAll();
	}
	
	public void salvar(Vinho vinho) {
		vinhos.save(vinho);
	}
	
	public Vinho buscar(Long codigo) {
		return vinhos.findOne(codigo);
	}
	
	public String salvarFoto(Long codigo, MultipartFile foto) {
		String nomeFoto = fotoStorage.salvar(foto);

		Vinho vinho = buscar(codigo);
		vinho.setFoto(nomeFoto);
		vinhos.save(vinho);
		
		return fotoStorage.getUrl(nomeFoto);
	}
}
