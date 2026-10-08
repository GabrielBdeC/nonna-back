package br.com.nonna_back.security;

import br.com.nonna_back.exceptions.NaoAutenticadoException;
import br.com.nonna_back.exceptions.NaoAutorizadoException;

/**
 * Guardas de autorização. A convenção do projeto é chamar um destes
 * métodos como a primeira linha de qualquer método de Controller que
 * precisa de proteção -- sem anotação, sem interceptor escondido:
 * quem lê o Controller vê exatamente quem pode chamar aquela rota.
 */
public class Autorizacao {

    public static void exigirAutenticado() {
        if (!UsuarioAutenticado.estaAutenticado()) {
            throw new NaoAutenticadoException("É NECESSÁRIO ESTAR LOGADO PARA ACESSAR ESSE RECURSO");
        }
    }

    public static void exigirAdministrador() {
        exigirAutenticado();
        if (!UsuarioAutenticado.ehAdministrador()) {
            throw new NaoAutorizadoException("APENAS ADMINISTRADORES PODEM ACESSAR ESSE RECURSO");
        }
    }

    /** Fazer pedido é coisa de CLIENTE -- administrador não compra, ele gerencia. */
    public static void exigirCliente() {
        exigirAutenticado();
        if (UsuarioAutenticado.ehAdministrador()) {
            throw new NaoAutorizadoException("ADMINISTRADORES NÃO PODEM REALIZAR ESSA AÇÃO");
        }
    }

    /**
     * Para rotas públicas que também aceitam gente logada (como a reserva,
     * que não exige conta): não bloqueia quem não está autenticado, só
     * recusa quem está logado como administrador.
     */
    public static void exigirNaoAdministrador() {
        if (UsuarioAutenticado.estaAutenticado() && UsuarioAutenticado.ehAdministrador()) {
            throw new NaoAutorizadoException("ADMINISTRADORES NÃO PODEM REALIZAR ESSA AÇÃO");
        }
    }

    /** Libera para o administrador OU para o dono do próprio recurso (mesmo id de usuário). */
    public static void exigirProprioOuAdministrador(String idDoRecurso) {
        exigirAutenticado();
        boolean ehDono = UsuarioAutenticado.obterId().equals(idDoRecurso);
        if (!ehDono && !UsuarioAutenticado.ehAdministrador()) {
            throw new NaoAutorizadoException("VOCÊ SÓ PODE ACESSAR OS SEUS PRÓPRIOS DADOS");
        }
    }
}
