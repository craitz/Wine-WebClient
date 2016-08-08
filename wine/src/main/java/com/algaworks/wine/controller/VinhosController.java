package com.algaworks.wine.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.wine.model.TipoVinho;
import com.algaworks.wine.model.Vinho;
import com.algaworks.wine.service.VinhoService;
import com.algaworks.wine.storage.FotoStorage;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private VinhoService vinhoService;
	
	@Autowired
	private FotoStorage fotoStorage;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("/vinho/ListagemVinhos");
		mv.addObject("vinhos", vinhoService.pesqusiar());
		return mv;
	}

	@RequestMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/CadastroVinho");
		mv.addObject("tipos", TipoVinho.values());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return novo(vinho);
		}
		
		ModelAndView mv = new ModelAndView("redirect:/vinhos/novo");
		vinhoService.salvar(vinho);
		attr.addFlashAttribute("mensagem", "Vinho salvo com sucesso");
		return mv;
	}
	
	//Aqui, o Spring acha o vinho pelo código, direto!
	//Pra que essa conversão automática aconteça temos que adicionar um bean no WebCongig
	@RequestMapping("/{codigo}")
	public ModelAndView visualizar(@PathVariable("codigo") Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/VisualizacaoVinho");
		
		if (vinho.temFoto()) {
			vinho.setUrl(fotoStorage.getUrl(vinho.getFoto()));
		}
		
		mv.addObject("vinho", vinho);
		return mv;
	}
}
