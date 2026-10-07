package br.com.nonna_back.dtos;

import java.util.List;

/**
 * Envelope de paginacao: em vez de devolver a lista "pura", devolvemos
 * tambem quantos itens existem no total e em qual pagina estamos.
 * Sem isso o front nao tem como montar "pagina 2 de 5", por exemplo.
 */
public class PageDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;

    public PageDto(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
}
