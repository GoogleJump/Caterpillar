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

@Api(name = "tripendpoint", namespace = @ApiNamespace(ownerDomain = "travellog.com", ownerName = "travellog.com", packagePath = ""))
public class TripEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTrip")
	public CollectionResponse<Trip> listTrip(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Trip> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Trip as Trip");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Trip>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Trip obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Trip> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTrip")
	public Trip getTrip(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Trip trip = null;
		try {
			trip = mgr.find(Trip.class, id);
		} finally {
			mgr.close();
		}
		return trip;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param trip the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTrip")
	public Trip insertTrip(Trip trip) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsTrip(trip)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(trip);
		} finally {
			mgr.close();
		}
		return trip;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param trip the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTrip")
	public Trip updateTrip(Trip trip) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsTrip(trip)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(trip);
		} finally {
			mgr.close();
		}
		return trip;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTrip")
	public void removeTrip(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Trip trip = mgr.find(Trip.class, id);
			mgr.remove(trip);
		} finally {
			mgr.close();
		}
	}

	private boolean containsTrip(Trip trip) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Trip item = mgr.find(Trip.class, trip.getKey());
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
