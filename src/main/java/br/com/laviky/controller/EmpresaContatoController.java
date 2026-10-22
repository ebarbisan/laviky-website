package br.com.laviky.controller;

import br.com.laviky.dto.EmpresaContatoRequest;
import br.com.laviky.service.EmpresaContatoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contato")
@RequiredArgsConstructor
public class EmpresaContatoController {

    private final EmpresaContatoService contatoService;

    @GetMapping
    public String exibirFormulario(Model model) {
        if (!model.containsAttribute("empresaContatoRequest")) {
            model.addAttribute("empresaContatoRequest", new EmpresaContatoRequest());
        }
        return "contato";
    }

    @PostMapping
    public String processarFormulario(
            @Valid @ModelAttribute("empresaContatoRequest") EmpresaContatoRequest request,
            BindingResult result,
            HttpServletRequest httpRequest,
            Model model) {

        if (result.hasErrors()) {
            return "contato";
        }

        try {
            String ip = httpRequest.getRemoteAddr();
            String userAgent = httpRequest.getHeader("User-Agent");
            contatoService.processarSolicitacao(request, ip, userAgent);
            return "redirect:/contato?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "contato";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
            return "contato";
        }
    }
}
