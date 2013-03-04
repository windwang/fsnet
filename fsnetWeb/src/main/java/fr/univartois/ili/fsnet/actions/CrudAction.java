package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import com.opensymphony.xwork2.ActionSupport;

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
     * @see ActionSupport
     */
    String create()
            throws Exception;

    /**
     * Modify the entity in database
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     * @see ActionSupport
     */
    String modify()
            throws Exception;

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
    String delete()
            throws Exception;

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
    String search()
            throws Exception;

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
    String display()
            throws Exception;
}
