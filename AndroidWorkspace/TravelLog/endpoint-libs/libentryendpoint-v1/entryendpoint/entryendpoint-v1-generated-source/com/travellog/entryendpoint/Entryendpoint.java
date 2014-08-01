/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-08-01 at 15:33:47 UTC 
 * Modify at your own risk.
 */

package com.travellog.entryendpoint;

/**
 * Service definition for Entryendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link EntryendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Entryendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the entryendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://gjtravellog.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "entryendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Entryendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Entryendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getEntry".
   *
   * This request holds the parameters needed by the the entryendpoint server.  After setting any
   * optional parameters, call the {@link GetEntry#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetEntry getEntry(java.lang.Long id) throws java.io.IOException {
    GetEntry result = new GetEntry(id);
    initialize(result);
    return result;
  }

  public class GetEntry extends EntryendpointRequest<com.travellog.entryendpoint.model.EntryendpointEntry> {

    private static final String REST_PATH = "getEntry";

    /**
     * Create a request for the method "getEntry".
     *
     * This request holds the parameters needed by the the entryendpoint server.  After setting any
     * optional parameters, call the {@link GetEntry#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetEntry#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetEntry(java.lang.Long id) {
      super(Entryendpoint.this, "GET", REST_PATH, null, com.travellog.entryendpoint.model.EntryendpointEntry.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetEntry setAlt(java.lang.String alt) {
      return (GetEntry) super.setAlt(alt);
    }

    @Override
    public GetEntry setFields(java.lang.String fields) {
      return (GetEntry) super.setFields(fields);
    }

    @Override
    public GetEntry setKey(java.lang.String key) {
      return (GetEntry) super.setKey(key);
    }

    @Override
    public GetEntry setOauthToken(java.lang.String oauthToken) {
      return (GetEntry) super.setOauthToken(oauthToken);
    }

    @Override
    public GetEntry setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetEntry) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetEntry setQuotaUser(java.lang.String quotaUser) {
      return (GetEntry) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetEntry setUserIp(java.lang.String userIp) {
      return (GetEntry) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetEntry setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetEntry set(String parameterName, Object value) {
      return (GetEntry) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertEntry".
   *
   * This request holds the parameters needed by the the entryendpoint server.  After setting any
   * optional parameters, call the {@link InsertEntry#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.travellog.entryendpoint.model.EntryendpointEntry}
   * @return the request
   */
  public InsertEntry insertEntry(com.travellog.entryendpoint.model.EntryendpointEntry content) throws java.io.IOException {
    InsertEntry result = new InsertEntry(content);
    initialize(result);
    return result;
  }

  public class InsertEntry extends EntryendpointRequest<com.travellog.entryendpoint.model.EntryendpointEntry> {

    private static final String REST_PATH = "entry";

    /**
     * Create a request for the method "insertEntry".
     *
     * This request holds the parameters needed by the the entryendpoint server.  After setting any
     * optional parameters, call the {@link InsertEntry#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertEntry#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.travellog.entryendpoint.model.EntryendpointEntry}
     * @since 1.13
     */
    protected InsertEntry(com.travellog.entryendpoint.model.EntryendpointEntry content) {
      super(Entryendpoint.this, "POST", REST_PATH, content, com.travellog.entryendpoint.model.EntryendpointEntry.class);
    }

    @Override
    public InsertEntry setAlt(java.lang.String alt) {
      return (InsertEntry) super.setAlt(alt);
    }

    @Override
    public InsertEntry setFields(java.lang.String fields) {
      return (InsertEntry) super.setFields(fields);
    }

    @Override
    public InsertEntry setKey(java.lang.String key) {
      return (InsertEntry) super.setKey(key);
    }

    @Override
    public InsertEntry setOauthToken(java.lang.String oauthToken) {
      return (InsertEntry) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertEntry setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertEntry) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertEntry setQuotaUser(java.lang.String quotaUser) {
      return (InsertEntry) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertEntry setUserIp(java.lang.String userIp) {
      return (InsertEntry) super.setUserIp(userIp);
    }

    @Override
    public InsertEntry set(String parameterName, Object value) {
      return (InsertEntry) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listEntry".
   *
   * This request holds the parameters needed by the the entryendpoint server.  After setting any
   * optional parameters, call the {@link ListEntry#execute()} method to invoke the remote operation.
   *
   * @param tripPoster
   * @return the request
   */
  public ListEntry listEntry(java.lang.String tripPoster) throws java.io.IOException {
    ListEntry result = new ListEntry(tripPoster);
    initialize(result);
    return result;
  }

  public class ListEntry extends EntryendpointRequest<com.travellog.entryendpoint.model.CollectionResponseEntry> {

    private static final String REST_PATH = "listEntry";

    /**
     * Create a request for the method "listEntry".
     *
     * This request holds the parameters needed by the the entryendpoint server.  After setting any
     * optional parameters, call the {@link ListEntry#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListEntry#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param tripPoster
     * @since 1.13
     */
    protected ListEntry(java.lang.String tripPoster) {
      super(Entryendpoint.this, "GET", REST_PATH, null, com.travellog.entryendpoint.model.CollectionResponseEntry.class);
      this.tripPoster = com.google.api.client.util.Preconditions.checkNotNull(tripPoster, "Required parameter tripPoster must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListEntry setAlt(java.lang.String alt) {
      return (ListEntry) super.setAlt(alt);
    }

    @Override
    public ListEntry setFields(java.lang.String fields) {
      return (ListEntry) super.setFields(fields);
    }

    @Override
    public ListEntry setKey(java.lang.String key) {
      return (ListEntry) super.setKey(key);
    }

    @Override
    public ListEntry setOauthToken(java.lang.String oauthToken) {
      return (ListEntry) super.setOauthToken(oauthToken);
    }

    @Override
    public ListEntry setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListEntry) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListEntry setQuotaUser(java.lang.String quotaUser) {
      return (ListEntry) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListEntry setUserIp(java.lang.String userIp) {
      return (ListEntry) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String tripPoster;

    /**

     */
    public java.lang.String getTripPoster() {
      return tripPoster;
    }

    public ListEntry setTripPoster(java.lang.String tripPoster) {
      this.tripPoster = tripPoster;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListEntry setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListEntry setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListEntry set(String parameterName, Object value) {
      return (ListEntry) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeEntry".
   *
   * This request holds the parameters needed by the the entryendpoint server.  After setting any
   * optional parameters, call the {@link RemoveEntry#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveEntry removeEntry(java.lang.Long id) throws java.io.IOException {
    RemoveEntry result = new RemoveEntry(id);
    initialize(result);
    return result;
  }

  public class RemoveEntry extends EntryendpointRequest<Void> {

    private static final String REST_PATH = "entry/{id}";

    /**
     * Create a request for the method "removeEntry".
     *
     * This request holds the parameters needed by the the entryendpoint server.  After setting any
     * optional parameters, call the {@link RemoveEntry#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveEntry#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveEntry(java.lang.Long id) {
      super(Entryendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveEntry setAlt(java.lang.String alt) {
      return (RemoveEntry) super.setAlt(alt);
    }

    @Override
    public RemoveEntry setFields(java.lang.String fields) {
      return (RemoveEntry) super.setFields(fields);
    }

    @Override
    public RemoveEntry setKey(java.lang.String key) {
      return (RemoveEntry) super.setKey(key);
    }

    @Override
    public RemoveEntry setOauthToken(java.lang.String oauthToken) {
      return (RemoveEntry) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveEntry setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveEntry) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveEntry setQuotaUser(java.lang.String quotaUser) {
      return (RemoveEntry) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveEntry setUserIp(java.lang.String userIp) {
      return (RemoveEntry) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveEntry setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveEntry set(String parameterName, Object value) {
      return (RemoveEntry) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateEntry".
   *
   * This request holds the parameters needed by the the entryendpoint server.  After setting any
   * optional parameters, call the {@link UpdateEntry#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.travellog.entryendpoint.model.EntryendpointEntry}
   * @return the request
   */
  public UpdateEntry updateEntry(com.travellog.entryendpoint.model.EntryendpointEntry content) throws java.io.IOException {
    UpdateEntry result = new UpdateEntry(content);
    initialize(result);
    return result;
  }

  public class UpdateEntry extends EntryendpointRequest<com.travellog.entryendpoint.model.EntryendpointEntry> {

    private static final String REST_PATH = "entry";

    /**
     * Create a request for the method "updateEntry".
     *
     * This request holds the parameters needed by the the entryendpoint server.  After setting any
     * optional parameters, call the {@link UpdateEntry#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateEntry#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.travellog.entryendpoint.model.EntryendpointEntry}
     * @since 1.13
     */
    protected UpdateEntry(com.travellog.entryendpoint.model.EntryendpointEntry content) {
      super(Entryendpoint.this, "PUT", REST_PATH, content, com.travellog.entryendpoint.model.EntryendpointEntry.class);
    }

    @Override
    public UpdateEntry setAlt(java.lang.String alt) {
      return (UpdateEntry) super.setAlt(alt);
    }

    @Override
    public UpdateEntry setFields(java.lang.String fields) {
      return (UpdateEntry) super.setFields(fields);
    }

    @Override
    public UpdateEntry setKey(java.lang.String key) {
      return (UpdateEntry) super.setKey(key);
    }

    @Override
    public UpdateEntry setOauthToken(java.lang.String oauthToken) {
      return (UpdateEntry) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateEntry setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateEntry) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateEntry setQuotaUser(java.lang.String quotaUser) {
      return (UpdateEntry) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateEntry setUserIp(java.lang.String userIp) {
      return (UpdateEntry) super.setUserIp(userIp);
    }

    @Override
    public UpdateEntry set(String parameterName, Object value) {
      return (UpdateEntry) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Entryendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Entryendpoint}. */
    @Override
    public Entryendpoint build() {
      return new Entryendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link EntryendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setEntryendpointRequestInitializer(
        EntryendpointRequestInitializer entryendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(entryendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
