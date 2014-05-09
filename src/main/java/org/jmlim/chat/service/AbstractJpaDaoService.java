package org.jmlim.chat.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author jmlim
 * 
 */
public abstract class AbstractJpaDaoService {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return this.em;
	}
}
