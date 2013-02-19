package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface definying commons methods for a CRUD action
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public interface CrudAction {

    /**
     * Create the entity and persist it in database
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     * @see MappingDispatchAction
     */
    String create(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    /**
     * Modify the entity in database
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     * @see MappingDispatchAction
     */
    String modify(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    /**
     * Delete the entity from database
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    String delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    /**
     * Search entities in database
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    String search(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    /**
     * Display details on one entity
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    String display(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;
}
