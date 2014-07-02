package com.travellog;

import com.travellog.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "entryendpoint", namespace = @ApiNamespace(ownerDomain = "travellog.com", ownerName = "travellog.com", packagePath = ""))
public class EntryEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listEntry")
	public CollectionResponse<Entry> listEntry(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Entry> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Entry as Entry");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Entry>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Entry obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Entry> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getEntry")
	public Entry getEntry(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Entry entry = null;
		try {
			entry = mgr.find(Entry.class, id);
		} finally {
			mgr.close();
		}
		return entry;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param entry the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertEntry")
	public Entry insertEntry(Entry entry) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsEntry(entry)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(entry);
		} finally {
			mgr.close();
		}
		return entry;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param entry the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateEntry")
	public Entry updateEntry(Entry entry) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsEntry(entry)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(entry);
		} finally {
			mgr.close();
		}
		return entry;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeEntry")
	public void removeEntry(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Entry entry = mgr.find(Entry.class, id);
			mgr.remove(entry);
		} finally {
			mgr.close();
		}
	}

	private boolean containsEntry(Entry entry) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Entry item = mgr.find(Entry.class, entry.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
