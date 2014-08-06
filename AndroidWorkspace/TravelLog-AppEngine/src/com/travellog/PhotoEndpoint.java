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

@Api(name = "photoendpoint", namespace = @ApiNamespace(ownerDomain = "travellog.com", ownerName = "travellog.com", packagePath = ""))
public class PhotoEndpoint {

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listPhoto", path = "listPhoto")
    public CollectionResponse<Photo> listPhoto(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit,
            @Named("key") String photoId) {

        EntityManager mgr = null;
        Cursor cursor = null;
        List<Photo> execute = null;

        try {
            mgr = getEntityManager();
            Query query = mgr.createQuery("select from Photo as Photo where Photo.key = :id").setParameter("id", photoId);
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<Photo>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Photo obj : execute)
                ;
        } finally {
            mgr.close();
        }

        return CollectionResponse.<Photo> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getPhoto", path = "getPhoto")
    public Photo getPhoto(@Named("id") Long id) {
        EntityManager mgr = getEntityManager();
        Photo photo = null;
        try {
            photo = mgr.find(Photo.class, id);
        } finally {
            mgr.close();
        }
        return photo;
    }

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param photo the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPhoto")
	public Photo insertPhoto(Photo photo) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(photo);
		} finally {
			mgr.close();
		}
		return photo;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param photo the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePhoto")
	public Photo updatePhoto(Photo photo) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPhoto(photo)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(photo);
		} finally {
			mgr.close();
		}
		return photo;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePhoto")
	public void removePhoto(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Photo photo = mgr.find(Photo.class, id);
			mgr.remove(photo);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPhoto(Photo photo) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Photo item = mgr.find(Photo.class, photo.getKey());
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
