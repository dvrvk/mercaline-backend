package com.mercaline.service.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Class BaseService.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 * @param <R> the generic type
 */
public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

    /** The repositorio. */
    @Autowired
    protected R repositorio;

    /**
     * Save.
     *
     * @param t the t
     * @return the t
     */
    public T save(T t) {
        return repositorio.save(t);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<T> findById(ID id) {
        return repositorio.findById(id);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List<T> findAll() {
        return repositorio.findAll();
    }


    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<T> findAll(Pageable pageable) {
        return repositorio.findAll(pageable);
    }

    /**
     * Edits the.
     *
     * @param t the t
     * @return the t
     */
    public T edit(T t) {
        return repositorio.save(t);
    }

    /**
     * Delete.
     *
     * @param t the t
     */
    public void delete(T t) {
        repositorio.delete(t);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(ID id) {
        repositorio.deleteById(id);
    }

}
