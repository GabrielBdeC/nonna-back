package br.com.nonna_back.security;

/**
 * Guarda quem é o usuário da requisição atual, enquanto ela está sendo
 * processada. Um ThreadLocal funciona porque o Tomcat atende cada
 * requisição numa thread só do início ao fim -- então "a thread atual"
 * é um jeito seguro de dizer "a requisição atual".
 *
 * É o mesmo papel que o SecurityContextHolder cumpre no Spring Security de
 * verdade, só que escrito à mão para ficar claro o que está acontecendo.
 */
public class UsuarioAutenticado {
    private static final ThreadLocal<UsuarioAutenticado> CONTEXTO = new ThreadLocal<>();

    private final String id;
    private final String tipo;

    private UsuarioAutenticado(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static void autenticar(String id, String tipo) {
        CONTEXTO.set(new UsuarioAutenticado(id, tipo));
    }

    public static void limpar() {
        CONTEXTO.remove();
    }

    public static boolean estaAutenticado() {
        return CONTEXTO.get() != null;
    }

    public static String obterId() {
        UsuarioAutenticado atual = CONTEXTO.get();
        return atual != null ? atual.id : null;
    }

    public static String obterTipo() {
        UsuarioAutenticado atual = CONTEXTO.get();
        return atual != null ? atual.tipo : null;
    }

    public static boolean ehAdministrador() {
        return "ADMINISTRADOR".equals(obterTipo());
    }
}
