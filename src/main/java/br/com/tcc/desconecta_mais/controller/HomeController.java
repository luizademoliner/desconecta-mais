package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.dto.DashboardResponseDto;
import br.com.tcc.desconecta_mais.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final DashboardService dashboardService;

    @GetMapping
    public String inicio(@AuthenticationPrincipal UsuarioEntity usuario, Model model) {
        if (usuario != null) {
            DashboardResponseDto dashboard = dashboardService.montar(usuario);
            model.addAttribute("dashboard", dashboard);
            model.addAttribute("nomeUsuario", usuario.getNome());
        }
        return "index";
    }
}
